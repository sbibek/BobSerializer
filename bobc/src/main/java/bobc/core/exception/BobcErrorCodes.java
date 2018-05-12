package bobc.core.exception;

/**
 * Standard error codes for Bobc
 * 
 * @author bibek.shrestha
 *
 */
public class BobcErrorCodes {
	public static final Integer NO_SUCH_CLASS_WAS_ADDED = 401;
	public static final Integer NO_FIELD_PROCESSOR_FOUND = 402;
	public static final Integer INSUFFICIENT_DATA_PASSED = 403;
	public static final Integer LOSSY_CONVERSION = 450;
	public static final Integer UNKNOWN_CONVERSION = 451;
	public static final Integer SIZE_EXPECTATION_MISMATCH = 452;

	public static final Integer PACKING_WITH_NULL = 600;

	public static final Integer UNKNOWN = 999;

	public static String lossyConversionMsg(Class<?> from, Class<?> to) {
		return "lossy conversion from " + from + " to " + to + " not permitted";
	}

	public static String conversionNotKnown(Class<?> from, Class<?> to) {
		return "conversion from " + from + " to " + to + " not known";
	}

	public static String packingWithNull(Class<?> for_) {
		return "packing null value " + for_ + " not possible";
	}
}
