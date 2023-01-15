package pgdp.trials;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class TrialOfTheDreams {

	protected TrialOfTheDreams() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Picks the specified {@code lock} using iterative deepening search.
	 * 
	 * @param lock arbitrary {@code Function} that takes a key ({@code byte} array)
	 *             and checks if it opens the lock.
	 * @return {@code byte} array containing the combination to open the lock.
	 */
	public static byte[] lockPick(Function<byte[], Boolean> lock) {
		byte[] result = null;
		int depth = 1;
		do {
			if ((result = lockPick(lock, depth)) != null) {
				break;
			}
			depth++;
		} while (depth < Integer.MAX_VALUE);
		return result;
	}

	/**
	 * Picks the specified {@code lock} up to the specified depth using depth first
	 * search.
	 * 
	 * @param lock   arbitrary {@code Function} that takes a key ({@code byte}
	 *               array) and checks if it opens the lock.
	 * @param maxlen maximum length of the combinations to be checked.
	 * @return {@code byte} array containing the combination to open the lock or
	 *         {@code null} if no such combination exists.
	 */
	public static byte[] lockPick(Function<byte[], Boolean> lock, int maxlen) {
		byte[] key = new byte[maxlen];
		Function<Byte[], Boolean> lockFunctionWrapper = (key) -> lock.apply(toPrimitive(key));
		return lockPick(lockFunctionWrapper, key, maxlen);
	}

	private static List<Byte> lockPick(Function<Byte[], Boolean> lock, List<Byte> key, int maxlen) {
		if (key.size() == maxlen) {
			Byte[] keyArr = key.toArray(new Byte[key.size()]);
			if (lock.apply(keyArr)) {
				return key;
			} else {
				return null;
			}
		}
		for (byte i = Byte.MIN_VALUE; i < Byte.MAX_VALUE; i++) {
			key.add(i);
			List<Byte> result = lockPick(lock, key, maxlen);
			if (result != null) {
				return result;
			}
			key.remove(key.size() - 1);
		}
		return null;
	}

	public static Byte[] toByte(Function<byte[], Boolean> array) {
		Byte[] result = new Byte[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = array[i];
		}
		return result;
	}

}
