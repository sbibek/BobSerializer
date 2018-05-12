package bobc.core.processing.fieldConverters;

import java.lang.annotation.Annotation;
import java.nio.ByteBuffer;

import bobc.core.ByteOrder;
import bobc.core.exception.BobcErrorCodes;
import bobc.core.exception.BobcException;
import bobc.core.processing.ConversionProcessor;
import bobc.core.processing.ConversionUtil;
import bobc.types.ShortType;

public class ShortTypeConversionProcessor implements ConversionProcessor {

	@Override
	public Integer getSize() {
		return 16;
	}

	@Override
	public Object fromBytes(Class<?> target, Annotation[] fieldAnnotations, ByteBuffer buffer,
			Boolean allowLossyConversion, Boolean isSilent) {
		// now this field extracts short value from the buffer, then it depends how we
		// are converting it to the target
		if (target.equals(Short.class) || target.equals(Short.TYPE)) {
			return buffer.getShort();
		} else if (target.equals(Integer.class) || target.equals(Integer.TYPE)) {
			return ConversionUtil.toUnsignedInteger(buffer.getShort());
		} else if (target.equals(Long.class) || target.equals(Long.TYPE)) {
			return ConversionUtil.toUnsignedLong(buffer.getShort());
		} else if (target.equals(Float.class) || target.equals(Float.TYPE)) {
			return (float) buffer.getShort();
		} else if (target.equals(Double.class) || target.equals(Double.TYPE)) {
			return (double) buffer.getShort();
		} else if (target.equals(String.class)) {
			return String.valueOf(buffer.getShort());
		} else if (target.equals(Byte.class) || target.equals(Byte.TYPE)) {
			// now this is lossy conversion
			if (allowLossyConversion) {
				return (byte) buffer.getShort();
			}

			if (!isSilent)
				// else throw runtime error
				throw new BobcException(BobcErrorCodes.LOSSY_CONVERSION,
						"unallowed lossy conversion from Short to " + target);
		} else {
			if (!isSilent)
				// no conversion found
				throw new BobcException(BobcErrorCodes.UNKNOWN_CONVERSION,
						"unknown conversion from Short to " + target + " encountered");
		}
		return null;
	}

	@Override
	public byte[] fromField(Class<?> fieldType, Object fieldValue, Annotation[] fieldAnnotations, ByteOrder order,
			Boolean allowLossyConversion, Boolean isSilent) {
		// IMPORTANT your packing should match the size of in getSize()
		ByteBuffer buffer = ByteBuffer.allocate(2).order(
				order == ByteOrder.LITTLE_ENDIAN ? java.nio.ByteOrder.LITTLE_ENDIAN : java.nio.ByteOrder.BIG_ENDIAN);
		if (fieldType.equals(Short.class) || fieldType.equals(Short.TYPE) || fieldType.equals(Byte.class)
				|| fieldType.equals(Byte.TYPE)) {
			// conversion from short to short and byte to short, no issues
			buffer.putShort((short) fieldValue);

		} else if (fieldType.equals(Integer.class) || fieldType.equals(Integer.TYPE)) {
			// conversion from integer, long, float, double to short is lossy
			if (!allowLossyConversion) {
				if (!isSilent) {
					throw new BobcException(BobcErrorCodes.LOSSY_CONVERSION, "unallowed lossy conversion from "
							+ fieldType + " to " + ShortType.class + " during packing");
				}
			} else {
				// means make the lossy conversion
				buffer.putShort((short) (int) (fieldValue));
			}
		} else if (fieldType.equals(Long.class) || fieldType.equals(Long.TYPE)) {
			// conversion from integer, long, float, double to short is lossy
			if (!allowLossyConversion) {
				if (!isSilent) {
					throw new BobcException(BobcErrorCodes.LOSSY_CONVERSION, "unallowed lossy conversion from "
							+ fieldType + " to " + ShortType.class + " during packing");
				}
			} else {
				// means make the lossy conversion
				buffer.putShort((short) (long) (fieldValue));
			}
		} else if (fieldType.equals(Float.class) || fieldType.equals(Float.TYPE)) {
			// conversion from integer, long, float, double to short is lossy
			if (!allowLossyConversion) {
				if (!isSilent) {
					throw new BobcException(BobcErrorCodes.LOSSY_CONVERSION, "unallowed lossy conversion from "
							+ fieldType + " to " + ShortType.class + " during packing");
				}
			} else {
				// means make the lossy conversion
				buffer.putShort((short) (float) (fieldValue));
			}
		} else if (fieldType.equals(Double.class) || fieldType.equals(Double.TYPE)) {
			// conversion from integer, long, float, double to short is lossy
			if (!allowLossyConversion) {
				if (!isSilent) {
					throw new BobcException(BobcErrorCodes.LOSSY_CONVERSION, "unallowed lossy conversion from "
							+ fieldType + " to " + ShortType.class + " during packing");
				}
			} else {
				// means make the lossy conversion
				buffer.putShort((short) (double) (fieldValue));
			}
		} else if (fieldType.equals(String.class)) {
			// now we need to parse string field to short if possible
			// else will throw number format exception
			buffer.putShort(Short.parseShort((String) fieldValue));

		} else {
			// means there is no conversion way for us
			if (!isSilent)
				throw new BobcException(BobcErrorCodes.LOSSY_CONVERSION,
						"unknown conversion from " + fieldType + " to " + ShortType.class + " during packing");
			else {
				// if silent then we need to put dummy data
				short randomShort = 0;
				buffer.putShort(randomShort);
			}
		}
		return buffer.array();
	}

}