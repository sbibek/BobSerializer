package bobc.core.processing.fieldConverters;

import java.lang.annotation.Annotation;
import java.math.BigInteger;
import java.nio.ByteBuffer;

import bobc.core.ByteOrder;
import bobc.core.exception.BobcErrorCodes;
import bobc.core.exception.BobcException;
import bobc.core.processing.ConversionProcessor;
import bobc.types.Int32Field;

public class Int32FieldConversionProcessor implements ConversionProcessor {

	@Override
	public Integer getSize() {
		return 32;
	}

	@Override
	public Object fromBytes(Class<?> target, Annotation[] fieldAnnotations, ByteBuffer buffer,
			Boolean allowLossyConversion, Boolean isSilent) {
		int value = buffer.getInt();
		if (target.equals(Byte.class) || target.equals(Byte.TYPE)) {
			if (allowLossyConversion)
				return (byte) value;
			else {
				if (!isSilent)
					throw new BobcException(BobcErrorCodes.LOSSY_CONVERSION,
							BobcErrorCodes.lossyConversionMsg(Int32Field.class, target));
			}
		} else if (target.equals(Short.class) || target.equals(Short.TYPE)) {
			if (allowLossyConversion)
				return (short) value;
			else {
				if (!isSilent)
					throw new BobcException(BobcErrorCodes.LOSSY_CONVERSION,
							BobcErrorCodes.lossyConversionMsg(Int32Field.class, target));
			}
		} else if (target.equals(Character.class) || target.equals(Character.TYPE)) {
			if (allowLossyConversion)
				return Character.forDigit(value, 10);
			else {
				if (!isSilent)
					throw new BobcException(BobcErrorCodes.LOSSY_CONVERSION,
							BobcErrorCodes.lossyConversionMsg(Int32Field.class, target));
			}
		} else if (target.equals(Integer.class) || target.equals(Integer.TYPE)) {
			// int32 is int in java so no prob
			return value;
		} else if (target.equals(Long.class) || target.equals(Long.TYPE)) {
			return (long) value;
		} else if (target.equals(Float.class) || target.equals(Float.TYPE)) {
			return (float) value;
		} else if (target.equals(Double.class) || target.equals(Double.TYPE)) {
			return (double) value;
		} else if (target.equals(BigInteger.class)) {
			return BigInteger.valueOf(value);
		} else if (target.equals(String.class)) {
			return String.valueOf(value);
		} else {
			if (!isSilent)
				throw new BobcException(BobcErrorCodes.UNKNOWN_CONVERSION,
						BobcErrorCodes.conversionNotKnown(Int32Field.class, target));
		}
		return null;
	}

	@Override
	public byte[] fromField(Class<?> fieldType, Object fieldValue, Annotation[] fieldAnnotations, ByteOrder order,
			Boolean allowLossyConversion, Boolean isSilent) {
		ByteBuffer buffer = ByteBuffer.allocate(4).order(
				order == ByteOrder.LITTLE_ENDIAN ? java.nio.ByteOrder.LITTLE_ENDIAN : java.nio.ByteOrder.BIG_ENDIAN);
		if (fieldValue == null) {
			throw new BobcException(BobcErrorCodes.PACKING_WITH_NULL, BobcErrorCodes.packingWithNull(fieldType));
		}

		if (fieldType.equals(Byte.class) || fieldType.equals(Byte.TYPE)) {
			buffer.putInt((byte) fieldValue);
		} else if (fieldType.equals(Short.class) || fieldType.equals(Short.TYPE)) {
			buffer.putInt((short) fieldValue);
		} else if (fieldType.equals(Character.class) || fieldType.equals(Character.TYPE)) {
			buffer.putInt(Integer.valueOf(String.valueOf(fieldValue)));
		} else if (fieldType.equals(Integer.class) || fieldType.equals(Integer.TYPE)) {
			buffer.putInt((int) fieldValue);
		} else if (fieldType.equals(Long.class) || fieldType.equals(Long.TYPE)) {
			// long to int32 is loss conv
			if (allowLossyConversion)
				buffer.putInt((int) (long) fieldValue);
			else {
				if (!isSilent)
					throw new BobcException(BobcErrorCodes.LOSSY_CONVERSION,
							BobcErrorCodes.lossyConversionMsg(fieldType, Int32Field.class));
			}
		} else if (fieldType.equals(Float.class) || fieldType.equals(Float.TYPE)) {
			if (allowLossyConversion)
				buffer.putInt((int) (float) fieldValue);
			else {
				if (!isSilent)
					throw new BobcException(BobcErrorCodes.LOSSY_CONVERSION,
							BobcErrorCodes.lossyConversionMsg(fieldType, Int32Field.class));
			}
		} else if (fieldType.equals(Double.class) || fieldType.equals(Double.TYPE)) {
			if (allowLossyConversion)
				buffer.putInt((int) (double) fieldValue);
			else {
				if (!isSilent)
					throw new BobcException(BobcErrorCodes.LOSSY_CONVERSION,
							BobcErrorCodes.lossyConversionMsg(fieldType, Int32Field.class));
			}
		} else if (fieldType.equals(BigInteger.class)) {
			if (allowLossyConversion)
				buffer.putInt(BigInteger.class.cast(fieldValue).intValue());
			else {
				if (!isSilent)
					throw new BobcException(BobcErrorCodes.LOSSY_CONVERSION,
							BobcErrorCodes.lossyConversionMsg(fieldType, Int32Field.class));
			}
		} else if (fieldType.equals(String.class)) {
			buffer.putInt(Integer.valueOf(String.class.cast(fieldValue)));
		} else {
			if (!isSilent)
				throw new BobcException(BobcErrorCodes.UNKNOWN_CONVERSION,
						BobcErrorCodes.conversionNotKnown(fieldType, Int32Field.class));
			// we should atleast init the buffer as dummy
			buffer.putInt(0);
		}
		return buffer.array();
	}

}
