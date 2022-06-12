

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;


public class FileOp {

	public final static int BLOCK_SIZE = 96;

	public static byte[] readKeyFile(String keyFile) throws IOException {
		byte[] keyBase64 = Files.readAllBytes(Paths.get(keyFile));	// read key file into byte array
		byte[] keyArray = Base64.getDecoder().decode(keyBase64);	// decode base64 key
		String keyString = new String(keyArray);				// 96 bit key data as string
		byte[] key = new byte[BLOCK_SIZE / 8];
		int i = 0;
		for (int j = 0; j < BLOCK_SIZE; j += 8) {		// build 96 bit key
			String str = keyString.substring(j, j + 8);
			int val = Integer.parseInt(str, 2);
			byte b = (byte) val;
			key[i++] = b;
		}
		return key;
	}

	public static byte[] readInputFile(String inputFile) throws IOException {


		String inputStr = "";
		File file = new File(inputFile);
		FileReader fr = new FileReader(file);
		int content;
		while ((content = fr.read()) != -1) {
			inputStr += (char)content;
		}
		//System.out.println(inputStr);
		if (inputStr.length() % BLOCK_SIZE != 0) {		// pad with 0
			int padSize = BLOCK_SIZE - (inputStr.length() % BLOCK_SIZE);
			System.out.println(padSize + " padsize");
			for (int i = 0; i < padSize; i++) {
				inputStr += "0";
			}
		}
		fr.close();
		return FeistelCipher.strToByteArr(inputStr);
	}

	//  java BBMcrypt enc -K key.txt -I input.txt -O output.txt -M mode
}
