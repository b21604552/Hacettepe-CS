

import java.util.HashMap;


public class FeistelCipher {

	public final static int KEY_SIZE = 96;		// 12 byte
	public final static int BLOCK_SIZE = 96;
	public final static int NUM_OF_ROUNDS = 10;
	public static HashMap<String, String> sBox;

	public static void buildSBox() {
		// String -> outer bits + inner bits
		sBox = new HashMap<>();

		// 1st row
		sBox.put("000000", "0010");
		sBox.put("000001", "1100");
		sBox.put("000010", "0100");
		sBox.put("000011", "0001");
		sBox.put("000100", "0111");
		sBox.put("000101", "1010");
		sBox.put("000110", "1011");
		sBox.put("000111", "0110");
		sBox.put("001000", "1000");
		sBox.put("001001", "0101");
		sBox.put("001010", "0011");
		sBox.put("001011", "1111");
		sBox.put("001100", "1101");
		sBox.put("001101", "0000");
		sBox.put("001110", "1110");
		sBox.put("001111", "1001");

		// 2nd row
		sBox.put("010000", "1110");
		sBox.put("010001", "1011");
		sBox.put("010010", "0010");
		sBox.put("010011", "1100");
		sBox.put("010100", "0100");
		sBox.put("010101", "0111");
		sBox.put("010110", "1101");
		sBox.put("010111", "0001");
		sBox.put("011000", "0101");
		sBox.put("011001", "0000");
		sBox.put("011010", "1111");
		sBox.put("011011", "1010");
		sBox.put("011100", "0011");
		sBox.put("011101", "1001");
		sBox.put("011110", "1000");
		sBox.put("011111", "0110");

		// 3rd row
		sBox.put("100000", "0100");
		sBox.put("100001", "0010");
		sBox.put("100010", "0001");
		sBox.put("100011", "1011");
		sBox.put("100100", "1010");
		sBox.put("100101", "1101");
		sBox.put("100110", "0111");
		sBox.put("100111", "1000");
		sBox.put("101000", "1111");
		sBox.put("101001", "1001");
		sBox.put("101010", "1100");
		sBox.put("101011", "0101");
		sBox.put("101100", "0110");
		sBox.put("101101", "0011");
		sBox.put("101110", "0000");
		sBox.put("101111", "1110");

		// 4th row
		sBox.put("110000", "1011");
		sBox.put("110001", "1000");
		sBox.put("110010", "1100");
		sBox.put("110011", "0111");
		sBox.put("110100", "0001");
		sBox.put("110101", "1110");
		sBox.put("110110", "0010");
		sBox.put("110111", "1101");
		sBox.put("111000", "0110");
		sBox.put("111001", "1111");
		sBox.put("111010", "0000");
		sBox.put("111011", "1001");
		sBox.put("111100", "1010");
		sBox.put("111101", "0100");
		sBox.put("111110", "0101");
		sBox.put("111111", "0011");

	}

	public void round(byte[] leftData, byte[] rightData, byte[] subkey, int size) {

		byte[] data = new byte[size];

		// f ( right, key )
		data = scrambleFunc(rightData, subkey);
		//System.out.println(byteArrToStr(data));

		// left xor output of f
		data = xor(leftData, data, size);

		// right <- left, left <- right
		int i;
		for (i = 0; i < leftData.length; i++) {
			leftData[i] = rightData[i];
		}
		for (i = 0; i < rightData.length; i++) {
			rightData[i] = data[i];
		}
		//System.out.println(byteArrToStr(rightData) + " right");

	}

	public byte[] encryptPlainText(byte[] leftData, byte[] rightData, byte[][] subkey, int size) {
		byte[] resultData = new byte[size * 2];
		// 10 rounds
		for (int j = 0; j < NUM_OF_ROUNDS; j++) {
			round(leftData, rightData, subkey[j], size);
		}
		System.arraycopy(leftData, 0, resultData, 0, size);
		System.arraycopy(rightData, 0, resultData, size, size);
		return resultData;
	}

	public byte[] decryptCipherText(byte[] leftData, byte[] rightData, byte[][] subkey, int size) {
		byte[] resultData = new byte[size * 2];
		// 10 rounds
		for (int j = (NUM_OF_ROUNDS - 1); j >= 0; j--) {
			round(rightData, leftData, subkey[j], size);
		}
		System.arraycopy(leftData, 0, resultData, 0, size);
		System.arraycopy(rightData, 0, resultData, size, size);
		return resultData;

	}

	public byte[] scrambleFunc(byte[] rightData, byte[] subkey) {

		// prep result data array
		int byteSize = BLOCK_SIZE / 2 / 8;	// BLOCK_SIZE / 2 / 8 <- 6 byte

		// right data xor key
		byte[] data = xor(rightData, subkey, byteSize);

		String dataStr = byteArrToStr(data);

		// divide into 12 pieces of 6-bit parts
		String[] bitPart = divide6BitChunk(dataStr);

		// apply substitution box to each 6 bit-part
		data = applySBox(bitPart);

		// apply permutation function
		data = permute(data);

		return data;
	}

	public String[] divide6BitChunk(String data) {

		int numOfBits = 6, numOfPieces = 12;
		int j = 0;
		String[] bitParts = new String[numOfPieces];		// 12 pieces of 6-bit parts
		for (int i = 0; i < numOfPieces - 4; i++, j += numOfBits) {
			bitParts[i] = data.substring(j, j + numOfBits);
		}
		for (int i = numOfPieces - 4, k = 0; i < numOfPieces ; i++, j += numOfBits, k += 2) {	// parts to be XORed
			// Pa = P(a - 8) ^ P(a - 7)
			String part1 = bitParts[k];
			String part2 = bitParts[k + 1];
			part1 += "00";
			part2 += "00";
			String str = "";
			str = byteArrToStr(xor(strToByteArr(part1), strToByteArr(part2), 1)).substring(0, numOfBits);
			bitParts[i] = str;
		}
		return bitParts;
	}

	public byte[] xor(byte[] firstData, byte[] secondData, int size) {
		//int byteSize = BLOCK_SIZE / 2 / 8;	// BLOCK_SIZE / 2 / 8 <- 6 byte
		byte[] data = new byte[size];		// result data
		// first data xor second data
		int i = 0;
		for (byte b : firstData) {
			data[i] = (byte) (b ^ secondData[i++]);
		}
		return data;
	}

	public byte[] applySBox(String[] data) {

		String innerBits = "", outerBits = "";
		int numOfPieces = 12, numOfBits = 6;		// 12 pieces
		int j = 0;
		String[] bitParts = new String[numOfPieces];		// 12 pieces of 4-bit result

		for (int i = 0; i < numOfPieces; i++) {
			innerBits = data[i].substring(j + 1, j + 5);		// get inner bits
			outerBits = data[i].substring(j, j + 1) + data[i].substring(j + 5 , j + 6);	// get outer bits

			bitParts[i] = sBox.get(outerBits + innerBits);			// get result of sbox
		}

		// combine result from sbox
		String result = "";
		for (int i = 0; i < numOfPieces; i++) {
			result += bitParts[i];
		}

		// return data
		return strToByteArr(result);
	}


	// tests if bit is set in value
	public char isSet(byte value, int pos){
		return ( value & ( 1 << (8 - (pos + 1)) ) ) != 0 ? '1' : '0';

	}


	public static byte[] strToByteArr(String byteStr) {
		byte[] byteArr = new byte[byteStr.length() / 8];
		int i = 0;
		for (int j = 0; j < byteStr.length(); j += 8) {		// build 96 bit byte array
			String str = byteStr.substring(j, j + 8);
			int val = Integer.parseInt(str, 2);
			byte b = (byte) val;
			byteArr[i++] = b;
		}
		return byteArr;
	}

	public static String byteArrToStr(byte[] byteArr) {
		String byteStr = "";
		for (int h = 0; h < byteArr.length; h++) {
			byte b1 = byteArr[h];
			String s1 = String.format("%8s", Integer.toBinaryString(b1 & 0xFF)).replace(' ', '0');	// get byte as string binary representation
			byteStr += s1;
		}
		return byteStr;
	}

	public byte[] generateSubkey(byte[] key, int roundNo) {
		// result data array
		int byteSize = KEY_SIZE / 2 / 8;	// KEY_SIZE / 2 / 8 <- 6 byte
		byte[] subkey = new byte[byteSize];		// 48 bit result data

		// left circular shift


		// store left bits of every byte
		int leftBits[] = new int[byteSize * 2];
		String keyStr = byteArrToStr(key);
		for (int i = 0; i < byteSize * 8 * 2; i+= 8) {
			leftBits[i / 8] = keyStr.charAt(i) == '1' ? 0x01 : 0x00;
		}

		// left shift each
		for (int k = 0; k < key.length - 1; k++) {		// update key
			byte b = key[k];
			key[k] = (byte) ((b << 1) | leftBits[k + 1]);
		}
		key[key.length - 1] = (byte) ((key[key.length - 1] << 1) | leftBits[0]);		// 0th bit to 96th


		// permuted choice
		String byteStr = "";
		int j =  roundNo % 2 == 0 ? 0 : 1;
		for (int i = 0; i < key.length; i++) {
			byte b1 = key[i];
			for (; j < 8; j+= 2) {
				byteStr += isSet(b1, j);
			}
			j = roundNo % 2 == 0 ? 0 : 1;
		}

		//System.out.println(j + " " + byteStr);
		subkey = strToByteArr(byteStr);

		return subkey;
	}

	public byte[] permute(byte[] data) {

		byte[] permutedData = new byte[data.length];
		// swap bits
		for (int i = 0; i < data.length; i++) {
			byte b = data[i];
			// Get all even bits of b
			byte even_bits = (byte) (b & 0xAA);
			// Get all odd bits of x
			byte odd_bits = (byte) (b & 0x55);
			// Right shift even bits
			even_bits = (byte) ((even_bits & 0xFF) >>> 1);
			// Left shift odd bits
			odd_bits  = (byte) ((odd_bits & 0xFF) << 1);
			// Combine even and odd bits
			permutedData[i] = (byte) (even_bits | odd_bits);

		}
		return permutedData;
	}

	public byte[] feistelCipher(byte[] text, byte[] key, String cryptString, String mode, ModeOp modeOperation) {
		int byteSize = BLOCK_SIZE / 2 / 8;	// BLOCK_SIZE / 2 / 8 <- 6 byte
		byte[] data = new byte[0];
		byte[] newVector;
		byte[] newChiperInput;
		byte[][] subkeys = new byte[NUM_OF_ROUNDS][byteSize];
		for (int i = 0; i < NUM_OF_ROUNDS; i++) {
			subkeys[i] = generateSubkey(key, i);
			//System.out.println(byteArrToStr(subkeys[i]) + " " + i + " key");
		}
		// build substitution box
		buildSBox();
		if(cryptString.equals("enc"))
		{
			if(mode.equals("ECB")) {
				data = encOrDec(cryptString, text, subkeys, byteSize,false);
			} else if(mode.equals("OFB")) {
				byte[] vector = modeOperation.getVector();
				modeOperation.setVector(text);
				newVector = encOrDec(cryptString, vector, subkeys, byteSize,false);
				data = ModeOp.modeOp(newVector);
				modeOperation.setVector(newVector);
			} else if(mode.equals("CBC")) {
				newChiperInput = ModeOp.modeOp(text);
				data = encOrDec(cryptString, newChiperInput, subkeys, byteSize,false);
				modeOperation.setVector(data);
			}
		}else{
			if(mode.equals("ECB")) {
				data = encOrDec(cryptString, text, subkeys, byteSize,false);
			} else if(mode.equals("OFB")) {
				byte[] vector = modeOperation.getVector();
				modeOperation.setVector(text);
				newVector = encOrDec(cryptString, vector, subkeys, byteSize, true);
				data = ModeOp.modeOp(newVector);
				modeOperation.setVector(newVector);
			} else if(mode.equals("CBC")) {
				data = encOrDec(cryptString, text, subkeys, byteSize,false);
				data = ModeOp.modeOp(data);
				modeOperation.setVector(text);
			}
		}
		return data;
	}

	private byte[] encOrDec(String cryptString, byte[] text, byte[][] subkeys, int byteSize, boolean isOFB) {
		byte[] leftData = new byte[byteSize];
		byte[] rightData = new byte[byteSize];
		System.arraycopy(text, 0, leftData, 0, byteSize);	// build left data
		System.arraycopy(text, byteSize, rightData, 0, byteSize);	// build right data
		byte[] data = new byte[byteSize * 2];

		if (cryptString.equals("enc")) {  // encryption
			data = encryptPlainText(leftData, rightData, subkeys, byteSize);
			//System.out.print(byteArrToStr(data));
		}
		else if(isOFB == true) {		// decryption
			data = encryptPlainText(leftData, rightData, subkeys, byteSize);
			//System.out.print(byteArrToStr(data));
		}
		else {		// decryption
			data = decryptCipherText(leftData, rightData, subkeys, byteSize);
			//System.out.print(byteArrToStr(data));
		}
		return data;
	}

}
