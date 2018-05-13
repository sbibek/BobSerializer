package bobc.core.processing.fieldConverters;

import java.lang.annotation.Annotation;
import java.nio.ByteBuffer;

import bobc.core.ByteOrder;
import bobc.core.exception.BobcErrorCodes;
import bobc.core.exception.BobcException;
import bobc.core.processing.ConversionProcessor;
import bobc.types.UInt32Field;

public class UInt32FieldConversionProcessor implements ConversionProcessor {

	@Override
	public Integer getSize() {
		return 32;
	}

	@Override
	public Object fromBytes(Class<?> target, Annotation[] fieldAnnotations, ByteBuffer buffer,
			Boolean allowLossyConversion, Boolean isSilent) {
		int value = buffer.getInt();
		// uint32 field can only be used with long, string
		if (target.equals(Long.class) || target.equals(Long.TYPE)) {
			return Integer.toUnsignedLong(value);
		} else if (target.equals(String.class)) {
			return Integer.toUnsignedString(value);
		} else {
			if (!isSilent)
				throw new BobcException(BobcErrorCodes.UNKNOWN,
						BobcErrorCodes.conversionNotKnown(UInt32Field.class, target));
		}
		return null;
	}

	@Override
	public byte[] fromField(Class<?> fieldType, Object fieldValue, Annotation[] fieldAnnotations, ByteOrder order,
			Boolean allowLossyConversion, Boolean isSilent) {
		// IMPORTANT your packing should match the size of in getSize()
		ByteBuffer buffer = ByteBuffer.allocate(4).order(
				order == ByteOrder.LITTLE_ENDIAN ? java.nio.ByteOrder.LITTLE_ENDIAN : java.nio.ByteOrder.BIG_ENDIAN);
		if (fieldValue == null) {
			throw new BobcException(BobcErrorCodes.PACKING_WITH_NULL, BobcErrorCodes.packingWithNull(fieldType));
		}

		// only field type of short and string will be processed for shortField
		if (fieldType.equals(Long.class) || fieldType.equals(Long.TYPE)) {
			buffer.putInt((int) fieldValue);
		} else if (fieldType.equals(String.class)) {
			buffer.putInt(Integer.valueOf(String.class.cast(fieldValue)));
		} else {
			buffer.putInt(0);
			if (!isSilent)
				throw new BobcException(BobcErrorCodes.UNKNOWN,
						BobcErrorCodes.conversionNotKnown(fieldType, UInt32Field.class));
		}
		return buffer.array();
	}
}
