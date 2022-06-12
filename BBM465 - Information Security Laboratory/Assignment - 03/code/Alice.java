
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.Socket;
import java.security.*;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Base64;

public class Alice {

    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream out = null;
    private String password = null;
    private String server = null;
    private String ticket = null;
    private SecretKey sessionKey = null;
    private byte[] nonce;

    public Alice(String address, String inputPassword, String inputServer, int port, String ticketOfAlice, SecretKey sessionKeyOfAlice) throws IOException {
        String line = "";
        boolean isAuthentication = false;
        boolean step = false;
        password = inputPassword;
        server = inputServer;

        socket = new Socket(address, port);

        input = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        while (!line.equals("Over"))
        {
            try
            {
                if(port == 3000){
                    String firstStep = "Alice,";

                    KeyPair KDCKey = getKeyPair();

                    firstStep += encryptMessage(password, server, KDCKey, false);

                    appendLogFile("Alice->KDC",encryptMessage(password, server, KDCKey, true));
                    appendLogFile("Alice->KDC",firstStep);

                    out.writeUTF(firstStep);
                    String serverResponse = input.readUTF();
                    if(serverResponse.equals("Invalid")){
                        appendLogFile("KDC->Alice","\"Password Denied\"");
                        line = "Over";
                    }else{
                        String[] serverResponseArray = serverResponse.split(",");
                        appendLogFile("KDC->Alice","\"Password Verified\"");
                        appendLogFile("KDC->Alice",serverResponse);
                        if(serverResponseArray.length == 2){
                            ticket = serverResponseArray[1];
                            sessionKey = getSessionKey(serverResponseArray[0]);
                            appendLogFile("KDC->Alice",serverResponse);
                            appendLogFile("Message Decrypted",getSessionKeyLog(serverResponseArray[0]));
                            line = "Over";
                            out.writeUTF(line);
                        }

                    }
                }else{
                    if(!isAuthentication){
                        nonce = getRandomNonce();
                        appendLogFile("Alice->" + inputServer,"Alice,"+ Arrays.toString(nonce));
                        String encryptMessage = encryptSecondMessage(sessionKeyOfAlice,nonce);
                        String sendingValue = "Alice," + ticketOfAlice + "," + encryptMessage;
                        appendLogFile("Alice->" + inputServer,sendingValue);
                        out.writeUTF(sendingValue);
                        while(!isAuthentication){
                            if(!line.equals("Over")){
                                while(!step){
                                    String serverResponse = input.readUTF();
                                    appendLogFile(inputServer + "->Alice",serverResponse);
                                    String response = new String(decryptSecondMessage(sessionKeyOfAlice,serverResponse));
                                    String[] serverResponseArray = response.split("],");
                                    byte[] n1PlusOneResponse = transform(serverResponseArray[0]+"]");
                                    byte[] n1PlusOne = addOne(nonce);
                                    if(Arrays.equals(n1PlusOneResponse,n1PlusOne)) {
                                        byte[] n2Response = transform(serverResponseArray[1]);
                                        appendLogFile("Message Decrypted","N1 is OK, N2="+ Arrays.toString(n2Response));
                                        byte[] n2PlusOneNonencypted = addOne(n2Response);
                                        appendLogFile("Alice->" + inputServer,Arrays.toString(n2PlusOneNonencypted));
                                        String encryptSecondMessage = encryptSecondMessage(sessionKeyOfAlice,n2PlusOneNonencypted);
                                        appendLogFile("Alice->" + inputServer,encryptSecondMessage);
                                        out.writeUTF(encryptSecondMessage);
                                        step = true;
                                    }else{
                                        System.out.println("Authentication Failed");
                                        appendLogFile(inputServer + "->Alice","Authentication is failed!");
                                        out.writeUTF("Failed");
                                        out.flush();
                                        return;
                                    }
                                }
                            }
                            if(!line.equals("Over")){
                                String serverResponse = input.readUTF();
                                if(serverResponse.equals("Authentication Success")){
                                    appendLogFile(inputServer + "->Alice","Authentication is completed!");
                                    System.out.println(server+"server Says: " + serverResponse);
                                    isAuthentication = true;
                                }else{
                                    System.out.println(server+"server Says: Authentication Failed");
                                    appendLogFile(inputServer + "->Alice","Authentication is failed!");
                                    line = "Over";
                                    break;
                                }
                            }
                        }
                    }
                    if(!line.equals("Over")){
                        input = new DataInputStream(System.in);
                        line = input.readLine();
                        out.writeUTF(line);
                    }
                }
            } catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        try
        {
            input.close();
            out.close();
            socket.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }

    public static KeyPair getKeyPair() throws UnrecoverableKeyException {
        String certPath = System.getProperty("user.dir") + "/cert/";
        return KeyStoreJKS.getKeyPairFromKeyStore(certPath,"KDC");
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

    public static byte[] transform(String response){
        String[] byteValues = response.substring(1, response.length() - 1).split(",");
        byte[] bytes = new byte[byteValues.length];

        for (int i=0, len=bytes.length; i<len; i++) {
            bytes[i] = Byte.parseByte(byteValues[i].trim());
        }
        return bytes;
    }

    public static byte[] getRandomNonce() {
        byte[] nonce = new byte[12];
        new SecureRandom().nextBytes(nonce);
        return nonce;
    }

    public static String encryptMessage(String password, String server, KeyPair KDCKey, boolean isLog) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        if(isLog)
           return "Alice," + password + "," + server + "," + sdf.format(timestamp);
        String nonEncryptMessage = "Alice," + password + "," + server + "," + sdf.format(timestamp);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, KDCKey.getPublic());
        byte[] encryptedBytes = cipher.doFinal(nonEncryptMessage.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String encryptSecondMessage(SecretKey sessionKey, byte[] nonce) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException, InvalidAlgorithmParameterException {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, sessionKey);
        return Base64.getEncoder().encodeToString(cipher.doFinal(nonce));
    }

    public static byte[] decryptSecondMessage(SecretKey sessionKey,String strToDecrypt) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, sessionKey);
        return cipher.doFinal(Base64.getDecoder().decode(strToDecrypt));
    }

    public static SecretKey getSessionKey(String message) throws Exception {
        KeyPair AliceKey = KeyStoreJKS.getKeyPairFromKeyStore(System.getProperty("user.dir") + "/cert/","Alice");
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, AliceKey.getPrivate());
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(message));
        String[] sessionKey = new String(decryptedBytes).split(",");
        byte[] decodedKey = Base64.getDecoder().decode(sessionKey[0]);
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        return originalKey;
    }

    public static String getSessionKeyLog(String message) throws Exception {
        KeyPair AliceKey = KeyStoreJKS.getKeyPairFromKeyStore(System.getProperty("user.dir") + "/cert/","Alice");
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, AliceKey.getPrivate());
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(message));
        return new String(decryptedBytes);
    }

    public static void appendLogFile(String sender, String line) throws IOException {
        SimpleDateFormat sdfFile = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        java.sql.Timestamp timestampFile = new Timestamp(System.currentTimeMillis());
        String timeStampFile = sdfFile.format(timestampFile);
        File logFile = new File("Alice_Log.txt");
        FileWriter logWriter = new FileWriter(logFile, true);
        logWriter.write(timeStampFile + " " + sender + " : " + line + "\r\n");
        logWriter.close();
    }

    public static void main(String[] args) throws IOException {
        Alice alice;
        int KDCPort = 3000;
        int MailServer = 3001;
        int WebServer = 3002;
        int DatabaseServer = 3003;
        boolean isPass = false;
        String password = null;
        String server;
        do{
            if(password != null)
                System.out.println("Password Denied!");
            System.out.print("Enter your password: ");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            password = reader.readLine();

            System.out.print("Enter your server you want to connect: ");

            boolean isValid;

            do{
                server = reader.readLine();

                if(server.equals("Web") || server.equals("Database") || server.equals("Mail"))
                    isValid = true;
                else {
                    isValid = false;
                    System.out.print("You enter invalid type of server. Please try again, server name colud be one of those [\"Web\",\"Database\",\"Mail\"]: ");
                }
            }while(!isValid);

            System.out.println("You are gonig to connect KDC...");

            alice = new Alice("127.0.0.1",password,server,KDCPort,null,null);
            if(alice.ticket != null){
                isPass = true;
            }
        }while(!isPass);
        if(isPass){
            switch (server) {
                case "Web":
                    Alice aliceWeb = new Alice("127.0.0.1", password, server, WebServer, alice.ticket, alice.sessionKey);
                    break;
                case "Database":
                    Alice aliceDatabase = new Alice("127.0.0.1", password, server, DatabaseServer, alice.ticket, alice.sessionKey);
                    break;
                case "Mail":
                    Alice aliceMail = new Alice("127.0.0.1", password, server, MailServer, alice.ticket, alice.sessionKey);
                    break;
            }
        }
    }
}
