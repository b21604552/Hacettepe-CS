
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;

public class MailServer {

    private static SecretKey AliceSessionKey;
    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream in =  null;
    private DataOutputStream out = null;
    private byte[] nonce = null;
    private byte[] nonceSecond = null;

    public MailServer(int port)
    {
        boolean isAuthenticed = false;
        boolean flag = false;
        String line = "";
        try
        {
            server = new ServerSocket(port);
            System.out.println("MailServer started");

            System.out.println("Waiting for Alice...");

            socket = server.accept();
            System.out.println("Connect Alice...");


            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

            while (!line.equals("Over"))
            {
                try
                {
                    if(!isAuthenticed){
                        line = in.readUTF();
                        while (!isAuthenticed){
                            appendLogFile("Alice->Mail",line);
                            String[] recivedMessage = line.split(",");
                            if(recivedMessage.length == 3){
                                String ticket = makeMessageDecrypted(recivedMessage[1],false);
                                AliceSessionKey = getAliceSessionKey(ticket);
                                appendLogFile("Ticket Decrpyted",makeMessageDecrypted(recivedMessage[1],true));
                                nonce = decrypt(recivedMessage[2]);
                                appendLogFile("Message Decrpyted","N1=" + Arrays.toString(nonce));
                                byte[] noncePlusOne = addOne(nonce);
                                nonceSecond = getRandomNonce();
                                appendLogFile("Mail->Alice",Arrays.toString(noncePlusOne) + "," + Arrays.toString(nonceSecond));
                                String sendingMessage = Arrays.toString(noncePlusOne) + "," + Arrays.toString(nonceSecond);
                                String encryptedMessage = encryptSecondMessage(sendingMessage);
                                appendLogFile("Mail->Alice",encryptedMessage);
                                out.writeUTF(encryptedMessage);
                                out.flush();
                                line = in.readUTF();
                            }else if((recivedMessage.length == 1) && (!recivedMessage[0].equals("Failed"))){
                                byte[] nonceSecondResponse = decrypt(recivedMessage[0]);
                                appendLogFile("Message Decrpyted",Arrays.toString(nonceSecondResponse));
                                byte[] nonceSecondPlus = addOne(nonceSecond);
                                if(Arrays.equals(nonceSecondResponse,nonceSecondPlus)){
                                    appendLogFile("Mail->Alice","Authentication is completed!");
                                    System.out.println("Alices authentication is success.");
                                    out.writeUTF("Authentication Success");
                                    out.flush();
                                    out = null;
                                    isAuthenticed = true;
                                }else{
                                    System.out.println("Authentication failed.");
                                    appendLogFile("Mail->Alice","Authentication is failed!");
                                    out.writeUTF("Failed");
                                    out.flush();
                                    out = null;
                                    line = "Over";
                                    break;
                                }
                            }else if(recivedMessage[0].equals("Failed")){
                                System.out.println("Authentication failed on Alice side.");
                                appendLogFile("Alice->Mail","Authentication is failed!");
                                line = "Over";
                                break;
                            }
                        }
                    }
                    if(!line.equals("Over")){
                        line = in.readUTF();
                        System.out.println("Alice says: " + line);
                    }
                }
                catch(IOException i)
                {
                    System.out.println(i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Closing connection");
            if(out != null)
                out.close();
            socket.close();
            in.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }

    public static byte[] getRandomNonce() {
        byte[] nonce = new byte[12];
        new SecureRandom().nextBytes(nonce);
        return nonce;
    }

    public static byte[] addOne(byte[] A) {
        int lastPosition = A.length - 1;
        for (int i = lastPosition; i >= 0; i--) {
            if (A[i] == 0) {
                A[i] = 1;
                return A;
            }
            A[i] = 0;
        }
        return A;
    }

    public static String encryptSecondMessage(String nonce) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException, InvalidAlgorithmParameterException {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, AliceSessionKey);
        return Base64.getEncoder().encodeToString(cipher.doFinal(nonce.getBytes()));
    }

    public static byte[] decrypt(String strToDecrypt) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, AliceSessionKey);
        byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(strToDecrypt));
        return plainText;
    }

    public static String makeMessageDecrypted(String ticket, boolean isValid) throws Exception {
        KeyPair WebKey = KeyStoreJKS.getKeyPairFromKeyStore(System.getProperty("user.dir") + "/cert/","Mail");
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, WebKey.getPrivate());
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(ticket));
        if(isValid)
            return new String(decryptedBytes);
        String[] decryptedStringArray = new String(decryptedBytes).split(",");
        return decryptedStringArray[3];
    }

    public static SecretKey getAliceSessionKey(String sessionKeyString){
        byte[] decodedKey = Base64.getDecoder().decode(sessionKeyString);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

    public static void appendLogFile(String sender, String line) throws IOException {
        SimpleDateFormat sdfFile = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        java.sql.Timestamp timestampFile = new Timestamp(System.currentTimeMillis());
        String timeStampFile = sdfFile.format(timestampFile);
        File logFile = new File("Mail_Log.txt");
        FileWriter logWriter = new FileWriter(logFile, true);
        logWriter.write(timeStampFile + " " + sender + " : " + line + "\r\n");
        logWriter.close();
    }

    public static void main(String args[]) throws Exception {
        MailServer server = new MailServer(3001);
    }
}
