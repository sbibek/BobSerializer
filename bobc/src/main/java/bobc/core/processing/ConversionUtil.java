package bobc.core.processing;

/**
 * Utity for standard field conversions in java
 * 
 * @author bibek.shrestha
 *
 */
public class ConversionUtil {
	public static Integer toUnsignedInteger(Short value) {
		return Short.toUnsignedInt(value);
	}

	public static Long toUnsignedLong(Short value) {
		return Integer.toUnsignedLong(toUnsignedInteger(value));
	}

	public static int longToUnsignedIntRepresentation(long value) {
		return 0;
	}

}
