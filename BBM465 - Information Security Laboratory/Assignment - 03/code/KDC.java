
import javax.crypto.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Base64;

public class KDC {

    private static SecretKey AliceSessionKey;
    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream in =  null;
    private DataOutputStream out = null;
    public KDC(int port)
    {
        try
        {
            server = new ServerSocket(port);
            System.out.println("KDC started");

            System.out.println("Waiting for Alice...");

            socket = server.accept();

            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

            String line = "";

            while (!line.equals("Over"))
            {
                try
                {
                    line = in.readUTF();
                    if(!line.equals("Over"))
                    {
                        appendLogFile("Alice->KDC", line);
                        String[] recivedMessage = line.split(",");
                        String cryptedPass = makeMessageDecrypted(recivedMessage[1],1);
                        String server = makeMessageDecrypted(recivedMessage[1],2);
                        String base64PassInFile = passwordInFile();
                        appendLogFile("Message Decrypted", makeMessageDecrypted(recivedMessage[1],-1));
                        if(cryptedPass.equals(base64PassInFile)){
                            appendLogFile("KDC->Alice", "\"Password Verified\"");
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
                            java.sql.Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                            String timeStamp = sdf.format(timestamp);
                            String firstPart = generateFirstPart(server,timeStamp);
                            appendLogFile("KDC->Alice", generateFirstPartNonEnc(server,timeStamp));
                            String ticket = generateTicket(server,timeStamp);
                            String returnData = firstPart + "," + ticket;
                            appendLogFile("KDC->Alice", returnData);
                            out.writeUTF(returnData);
                        }else{
                            out.writeUTF("Invalid");
                            appendLogFile("KDC->Alice", "\"Password Denied\"");
                            line = "Over";
                        }
                        out.flush();
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
            socket.close();
            in.close();
        }
        catch(IOException ignored)
        {
        }
    }

    public static String encrypt(String password) throws Exception {
        String result;
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(password.getBytes(StandardCharsets.ISO_8859_1), 0, password.length());
        byte[] sha1hash = md.digest();
        result = Base64.getEncoder().encodeToString(sha1hash);
        result = result.substring(0, result.length()-1);
        return result;
    }

    public static String passwordInFile(){
        String base64PassInFile = null;
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/passwd"));
            String lineInPasswordFile = reader.readLine();
            while (lineInPasswordFile != null) {
                base64PassInFile = lineInPasswordFile;
                lineInPasswordFile = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64PassInFile;
    }

    public static String generateFirstPart(String server, String timestamp) throws UnrecoverableKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchProviderException {
        KeyPair AliceKey = KeyStoreJKS.getKeyPairFromKeyStore(System.getProperty("user.dir") + "/cert/","Alice");
        KeyGenerator sessionKey = KeyGenerator.getInstance("AES");
        sessionKey.init(256);
        SecretKey key = sessionKey.generateKey();
        AliceSessionKey = key;
        String nonEncryptMessage = Base64.getEncoder().encodeToString(key.getEncoded()) + "," + server + "," + timestamp;
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, AliceKey.getPublic());
        byte[] encryptedBytes = cipher.doFinal(nonEncryptMessage.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String generateFirstPartNonEnc(String server, String timestamp) throws UnrecoverableKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchProviderException {
        return Base64.getEncoder().encodeToString(AliceSessionKey.getEncoded()) + "," + server + "," + timestamp;
    }

    public static String generateTicket(String server, String timestamp) throws UnrecoverableKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchProviderException {
        KeyPair ServerKey = KeyStoreJKS.getKeyPairFromKeyStore(System.getProperty("user.dir") + "/cert/",server);
        String nonEncryptMessage = "Alice," + server + "," + timestamp + "," + Base64.getEncoder().encodeToString(AliceSessionKey.getEncoded());;
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, ServerKey.getPublic());
        byte[] encryptedBytes = cipher.doFinal(nonEncryptMessage.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String makeMessageDecrypted(String passwordInMessage,int wantedPart) throws Exception {
        KeyPair KDCKey = KeyStoreJKS.getKeyPairFromKeyStore(System.getProperty("user.dir") + "/cert/","KDC");
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, KDCKey.getPrivate());
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(passwordInMessage));
        String[] decryptedStringArray = new String(decryptedBytes).split(",");
        if(wantedPart == 2)
            return decryptedStringArray[wantedPart];
        if(wantedPart == -1)
            return new String(decryptedBytes);
        return encrypt(decryptedStringArray[wantedPart]);
    }

    public static void createLogFiles() throws IOException {
        String[] filesWillCreates ={"KDC","Web","Database","Mail","Alice"};
        for (String fileName: filesWillCreates) {
            File log = new File(fileName + "_Log.txt");
            if (!log.exists()){
                FileWriter logFile = new FileWriter(log);
                logFile.close();
            }
        }
    }

    public static void appendLogFile(String sender, String line) throws IOException {
        SimpleDateFormat sdfFile = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        java.sql.Timestamp timestampFile = new Timestamp(System.currentTimeMillis());
        String timeStampFile = sdfFile.format(timestampFile);
        File logFile = new File("KDC_Log.txt");
        FileWriter logWriter = new FileWriter(logFile, true);
        logWriter.write(timeStampFile + " " + sender + " : " + line + "\r\n");
        logWriter.close();
    }

    public static void main(String args[]) throws Exception {
        System.out.println("Initiation please wait...");
        createLogFiles();
        KeyStoreJKS keyStoreJKS = new KeyStoreJKS();
        KDC server;
        do{
            server = new KDC(3000);
        }while(server.AliceSessionKey == null);
    }
}