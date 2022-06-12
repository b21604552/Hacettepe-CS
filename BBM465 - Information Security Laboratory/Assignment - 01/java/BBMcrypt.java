
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class BBMcrypt {

	public final static int BLOCK_SIZE = 96;

	public static void main(String args[]) throws IOException {

		String cryptString, keyFile = null, inputFile = null, outputFile = null, mode = null;
		// read command-line arguments
		int i = 0;
		cryptString = args[i++];	// enc | dec
		for (; i < args.length; i++) {
			if (args[i].contains("K")) {	// key file
				keyFile = args[++i];
			}
			else if (args[i].contains("I")) {	// input file
				inputFile = args[++i];
			}
			else if (args[i].contains("O")) {	// output file
				outputFile = args[++i];
			}
			else {	// Mode
				mode = args[++i];
			}
		}

		byte[] key = FileOp.readKeyFile(keyFile);
		byte[] input = FileOp.readInputFile(inputFile);

		File file = new File(outputFile);
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));

		FeistelCipher fCipher = new FeistelCipher();

		// divide into block size 96 bit -> 12 byte data
		int blockSize = BLOCK_SIZE / 8;
		byte[] data = new byte[blockSize];
		byte[] keyB = new byte[key.length];
		ModeOp modeOperation = new ModeOp();
		for (int j = 0; j < input.length; j += blockSize) {
			System.arraycopy(input, j, data, j % blockSize, blockSize);
			System.arraycopy(key, 0, keyB, 0, key.length);
			// pass data to feistel cipher
			byte[] text = fCipher.feistelCipher(data, keyB, cryptString, mode, modeOperation);
			//System.out.print(fCipher.byteArrToStr(text));
			writer.write(FeistelCipher.byteArrToStr(text));
		}

		// output from feistel cipher will be given to mode of operation: ECB, CBC, OFB
		//ModeOp modeOp = new ModeOp();

		// write encrypted/decrypted data to output


		writer.close();
	}

}
