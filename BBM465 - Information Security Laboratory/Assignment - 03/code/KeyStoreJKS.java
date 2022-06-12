
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Random;

public class KeyStoreJKS {

    public KeyStoreJKS() throws Exception {
        String certPath = System.getProperty("user.dir") + "/cert";

        File certDir = new File(certPath);
        if (!certDir.exists()){
            certDir.mkdirs();
        }

        String keyPath = System.getProperty("user.dir") + "/keys";

        File keyDir = new File(keyPath);
        if (!keyDir.exists()){
            keyDir.mkdirs();
        }

        certPath += "/";
        keyPath += "/";

        File passDir = new File(System.getProperty("user.dir") + "/passwd");
        if (!passDir.exists()){
            String password = passwordGenerator();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
            java.sql.Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String timeStamp = sdf.format(timestamp);
            File logFile = new File("KDC_Log.txt");
            FileWriter logWriter = new FileWriter(logFile, true);
            logWriter.write(timeStamp + " " + password + "\r\n");
            logWriter.close();
            String base64Password = encrypt(password);
            FileWriter base64PasswordWriter = new FileWriter("passwd");
            base64PasswordWriter.write(base64Password);
            base64PasswordWriter.close();
        }

        String[] certWillCreates ={"KDC","Web","Database","Mail","Alice"};

        createCert(certPath,keyPath,certWillCreates);
    }

    public static void createKeyStoreFiles(String certPath, String serverName) throws IOException, InterruptedException {
        Process process = new ProcessBuilder("bash", "-c", "keytool -genkey -alias server -keyalg RSA -keysize 2048 -sigalg SHA256withRSA -storetype JKS -keystore " + certPath + "/" + serverName + "server.keystore.jks "
                + "-storepass mypwdr -keypass mypwdr -dname	\"CN=, OU=, O=, L=, ST=, C=\" -validity 7200").redirectErrorStream(true).start();
        process.waitFor();
    }

    public static void createSelfSignedCertificateFile(String path, String serverName, String certificateFileName)  {
        try {
            Runtime.getRuntime().exec("keytool -export -alias server -keystore " + path + "/" + serverName + "server.keystore.jks -rfc -file " + path + "/" + certificateFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static KeyPair getKeyPairFromKeyStore(String certPath, String serverName) throws UnrecoverableKeyException{
        KeyStore jks = null;
        try {
            jks = KeyStore.getInstance("JKS");
            jks.load(new FileInputStream(certPath + "/" + serverName + "server.keystore.jks"),"mypwdr".toCharArray());
            Key key = jks.getKey("server","mypwdr".toCharArray());
            if (key instanceof PrivateKey) {
                X509Certificate cert = (X509Certificate) jks.getCertificate("server");

                PublicKey publicKey = cert.getPublicKey();

                return new KeyPair(publicKey, (PrivateKey) key);
            }
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String passwordGenerator(){
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

    public static String encrypt(String password) throws Exception {
        String result;
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(password.getBytes("iso-8859-1"), 0, password.length());
        byte[] sha1hash = md.digest();
        result = Base64.getEncoder().encodeToString(sha1hash);
        result = result.substring(0, result.length()-1);
        return result;
    }

    public void createCert(String certPath, String keyPath, String[] certificasWillCreate) throws IOException, InterruptedException, UnrecoverableKeyException {
        for (String certName: certificasWillCreate) {
            File cert = new File(certPath + certName + "server.keystore.jks");
            if (!cert.exists()){
                createKeyStoreFiles(certPath, certName);
                createSelfSignedCertificateFile(certPath, certName,certName+"Cert");
                KeyPair keyPair = getKeyPairFromKeyStore(certPath, certName);
                byte[] encodedPrivateKey = keyPair.getPrivate().getEncoded();
                String base64PrivateKey = Base64.getEncoder().encodeToString(encodedPrivateKey);
                File privateKeyFile = new File(keyPath + certName + "PrivateKey");
                FileWriter privateKeyWriter = new FileWriter(privateKeyFile);
                privateKeyWriter.write(base64PrivateKey);
                privateKeyWriter.close();
            }
        }
    }
}

