package fileSystem;

import java.util.Arrays;

// "Set" -> 1; "Clear" -> 0
// Use bit operations
// bit 0 is actually on the rightmost end


/**
 * Manage bitwise operations in a byte or array of bytes.
 *
 * Unit tests are in {@see TestBitwise}. See TestBitwise.java.
 */
public class Bitwise {
	
    private static final int bitmasks[] = {1, 2, 4, 8, 16, 32, 64, 128};

    /**
     * Check to see if bit i is set in byte. Returns true if it is
     * set, false otherwise.
     */
    public static boolean isset(int i, byte b) {//FIXME!!!
    	return (bitmasks[i] & b) != 0;
    }

    /**
     * Check to see if bit i is set in array of bytes. Returns true if
     * it is set, false otherwise.
     */
    public static boolean isset(int i, byte bytes[]) {//FIXME!!!
    	/*
    	 * to check bytes[] combined on ith digit
    	 * we can quickly calculate which entry to compare, this is:
    	 * 		bytes.length - 1 - (i / 8)
    	 * to calculate which digit in this entry to compare, simply:
    	 * 		i = i % 8
    	 */
    	int x = bytes.length - 1 - (i / 8);
    	return isset(i % 8, bytes[x]);
    }

    /**
     * Set bit i in byte and return the new byte.
     */
    public static byte set(int i, byte b) {//FIXME!!!
    	return (byte) (bitmasks[i] | b);
    }

    /**
     * Set bit i in array of bytes.
     */
    public static void set(int i, byte bytes[]) {//FIXME!!!
    	int x = bytes.length - 1 - (i / 8);
    	bytes[x] = set(i % 8, bytes[x]);
    }

    /**
     * Clear bit i in byte and return the new byte.
     */
    public static byte clear(int i, byte b) {//FIXME!!!
    	return isset(i, b) ? (byte)(bitmasks[i] ^ b) : b;
    }

    /**
     * Clear bit i in array of bytes and return true if the bit was 1
     * before clearing, false otherwise.
     */
    public static boolean clear(int i, byte bytes[]) {//FIXME!!!
    	boolean res = isset(i, bytes);
    	int x = bytes.length - 1 - (i / 8);
    	bytes[x] = clear(i % 8, bytes[x]);
    	return res;
    }

    /**
     * Clear every bit in array of bytes.
     *
     * There is no clearAll for a single byte, you can just get a new
     * byte for that.
     */
    public static void clearAll(byte bytes[]) {
    	Arrays.fill(bytes, (byte)0);
    }

    /**
     * Convert byte to a string of bits. Each bit is represented as
     * "0" if it is clear, "1" if it is set.
     */
    public static String toString(byte b) {//FIXME!!!
    	String res = "";
    	for (int i = bitmasks.length - 1; i > -1; i--){
    		res += (isset(i, b) ? "1" : "0");
    	}
    	return res;
    }

    /**
     * Convert array of bytes to string of bits (each byte converted
     * to a string by calling {@link #byteToString(byte b)}, every
     * byte separated by sep, every "every" bytes separated by lsep.
     */
    public static String toString(byte bytes[], String sep, String lsep, int every) {
        String s = "";
        for(int i = bytes.length * 8 - 1; i >= 0; --i) {
        	s += isset(i, bytes) ? "1" : "0";
        	if(i > 0)
                if(every > 0 && i % (8 * every) == 0)
                    s += lsep;
                else if(i % 8 == 0)
                    s += sep;
        }
        return s;
    }

    /**
     * Convert array of bytes to string of bits, each byte separated
     * by sep. See {@link #byteToString(byte bytes[], String sep)}.
     */
    public static String toString(byte bytes[], String sep) {
        return toString(bytes, sep, null, 0);
    }

    /**
     * Convert array of bytes to string of bits, each byte separated
     * by a comma, and every 8 bytes separated by a newline. See
     * {@link #byteToString(byte bytes[], String sep)}.
     */
    public static String toString(byte bytes[]) {
        return toString(bytes, ",", "\n", 8);
    }
}