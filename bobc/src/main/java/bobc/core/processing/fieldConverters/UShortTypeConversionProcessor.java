package bobc.core.processing.fieldConverters;

import java.lang.annotation.Annotation;
import java.nio.ByteBuffer;

import bobc.core.ByteOrder;
import bobc.core.exception.BobcErrorCodes;
import bobc.core.exception.BobcException;
import bobc.core.processing.ConversionProcessor;
import bobc.types.UShortType;

public class UShortTypeConversionProcessor implements ConversionProcessor {

	@Override
	public Integer getSize() {
		// UShort is also 16 bits
		return 16;
	}

	@Override
	public Object fromBytes(Class<?> target, Annotation[] fieldAnnotations, ByteBuffer buffer,
			Boolean allowLossyConversion, Boolean isSilent) {
		short value = buffer.getShort();
		// now lets analyze the target and marshall accordingly
		if (target.equals(Byte.class) || target.equals(Byte.TYPE)) {
			// this is lossy conversion
			if (allowLossyConversion) {
				return (byte) value;
			} else {
				if (!isSilent)
					throw new BobcException(BobcErrorCodes.LOSSY_CONVERSION,
							BobcErrorCodes.lossyConversionMsg(UShortType.class, target));
			}
		} else if (target.equals(Short.class) || target.equals(Byte.TYPE)) {
			// this is also lossy conversion as unsigned cant fit in the short itself
			if (allowLossyConversion) {
				return (short) value;
			} else {
				if (!isSilent)
					throw new BobcException(BobcErrorCodes.LOSSY_CONVERSION,
							BobcErrorCodes.lossyConversionMsg(UShortType.class, target));
			}
		} else if (target.equals(Integer.class) || target.equals(Integer.TYPE)) {
			// now in case of java, the unisgned can be handled by upper bits type, for
			// short anything > short
			return Short.toUnsignedInt(value);
		} else if (target.equals(Float.class) || target.equals(Float.TYPE)) {
			return (float) value;
		} else if (target.equals(Double.class) || target.equals(Double.TYPE)) {
			return (double) value;
		} else if (target.equals(Double.class) || target.equals(Double.TYPE)) {
			return Integer.toUnsignedLong(Short.toUnsignedInt(value));
		} else if (target.equals(String.class)) {
			return String.valueOf(Short.toUnsignedInt(value));
		} else if (target.equals(Character.class) || target.equals(Character.TYPE)) {
			return Character.forDigit(value, 10);
		} else {
			if (!isSilent) {
				throw new BobcException(BobcErrorCodes.UNKNOWN_CONVERSION,
						BobcErrorCodes.conversionNotKnown(UShortType.class, target));
			}
		}

		return null;
	}

	@Override
	public byte[] fromField(Class<?> fieldType, Object fieldValue, Annotation[] fieldAnnotations, ByteOrder order,
			Boolean allowLossyConversion, Boolean isSilent) {
		ByteBuffer buffer = ByteBuffer.allocate(2).order(
				order == ByteOrder.LITTLE_ENDIAN ? java.nio.ByteOrder.LITTLE_ENDIAN : java.nio.ByteOrder.BIG_ENDIAN);
		if (fieldValue == null) {
			throw new BobcException(BobcErrorCodes.PACKING_WITH_NULL, BobcErrorCodes.packingWithNull(fieldType));
		}
		if (fieldType.equals(Byte.class) || fieldType.equals(Byte.TYPE)) {
			buffer.putShort((short) fieldValue);
		} else if (fieldType.equals(Short.class) || fieldType.equals(Short.TYPE)) {
			// now this means, there should be unsigned representation of byte in the
			// buffer, but its just the interpretation
			buffer.putShort((short) fieldValue);
		} else if (fieldType.equals(Integer.class) || fieldType.equals(Integer.TYPE)) {
			// representing integer in an unsigned short is not at all possible as there can
			// be loss
			if (allowLossyConversion) {
				buffer.putShort((short) (int) fieldValue);
			} else {
				if (!isSilent)
					throw new BobcException(BobcErrorCodes.LOSSY_CONVERSION,
							BobcErrorCodes.lossyConversionMsg(fieldType, UShortType.class));
			}
		} else if (fieldType.equals(Long.class) || fieldType.equals(Long.TYPE)) {
			// now long => unsigned short
			if (allowLossyConversion) {
				buffer.putShort((short) (long) fieldValue);
			} else {
				if (!isSilent)
					throw new BobcException(BobcErrorCodes.LOSSY_CONVERSION,
							BobcErrorCodes.lossyConversionMsg(fieldType, UShortType.class));
			}
		} else if (fieldType.equals(Float.class) || fieldType.equals(Float.TYPE)) {
			// now long => unsigned short
			if (allowLossyConversion) {
				buffer.putShort((short) (float) fieldValue);
			} else {
				if (!isSilent)
					throw new BobcException(BobcErrorCodes.LOSSY_CONVERSION,
							BobcErrorCodes.lossyConversionMsg(fieldType, UShortType.class));
			}
		} else if (fieldType.equals(Double.class) || fieldType.equals(Double.TYPE)) {
			// now long => unsigned short
			if (allowLossyConversion) {
				buffer.putShort((short) (double) fieldValue);
			} else {
				if (!isSilent)
					throw new BobcException(BobcErrorCodes.LOSSY_CONVERSION,
							BobcErrorCodes.lossyConversionMsg(fieldType, UShortType.class));
			}
		} else if (fieldType.equals(String.class) || fieldType.equals(Character.class)
				|| fieldType.equals(Character.TYPE)) {
			String val = String.valueOf(fieldValue);
			buffer.putShort(Short.valueOf(val));
		} else {
			if (!isSilent)
				throw new BobcException(BobcErrorCodes.UNKNOWN_CONVERSION,
						BobcErrorCodes.conversionNotKnown(fieldType, UShortType.class));
			// we should atleast init the buffer as dummy
			buffer.putShort((short) 0);
		}

		return buffer.array();
	}

}
