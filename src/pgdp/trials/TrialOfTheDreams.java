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
		int total_possible_combination = (int) Math.pow(256, maxlen);
		byte[] key = new byte[maxlen];
		for (int i = 0; i < total_possible_combination; i++) {
			int num = i;
			for (int j = 0; j < maxlen; j++) {
				key[j] = (byte) (num % 256);
				num = num / 256;
			}
			if (lock.apply(key)) {
				return key;
			}
		}
		return null;
	}

	private static List<Byte> lockPick(Function<Byte[], Boolean> lock, List<Byte> key, int maxlen) {
		if (key.size() == maxlen) {
			Byte[] keyArray = key.toArray(new Byte[key.size()]);
			if (lock.apply(keyArray)) {
				return key;
			}
		} else {
			for (byte i = 0; i < 256; i++) {
				List<Byte> newKey = new ArrayList<>(key);
				newKey.add(i);
				List<Byte> result = lockPick(lock, newKey, maxlen);
				if (result != null) {
					return result;
				}
			}
		}
		return null;
	}

}
