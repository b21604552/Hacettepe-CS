import java.util.Arrays;

public class ModeOp {

	private static byte[] vector = {(byte) 0xFF,(byte) 0xFF,(byte) 0xFF,(byte) 0xFF,(byte) 0xFF,(byte) 0xFF,(byte) 0xFF,(byte) 0xFF,(byte) 0xFF,(byte) 0xFF,(byte) 0xFF,(byte) 0xFF};

	public byte[] getVector() {
		return vector;
	}

	public void setVector(byte[] newVector) {
		vector = newVector;
	}

	public static byte[] modeOp(byte[]text) {
		byte[] resultXOR = new byte[vector.length];
		int i = 0;
		for (byte b : text)
			resultXOR[i] = (byte) (b ^ vector[i++]);
		return resultXOR;
	}
}
