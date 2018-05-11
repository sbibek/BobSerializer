package bobc.core.processing;

public class ConversionUtil {
	public static Integer toUnsignedInteger(Short value) {
		return Short.toUnsignedInt(value);
	}

	public static Long toUnsignedLong(Short value) {
		return Integer.toUnsignedLong(toUnsignedInteger(value));
	}
}
