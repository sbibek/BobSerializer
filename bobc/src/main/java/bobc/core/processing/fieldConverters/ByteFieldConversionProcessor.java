package bobc.core.processing.fieldConverters;

import java.lang.annotation.Annotation;
import java.nio.ByteBuffer;

import bobc.core.ByteOrder;
import bobc.core.exception.BobcErrorCodes;
import bobc.core.exception.BobcException;
import bobc.core.processing.ConversionProcessor;
import bobc.types.ShortField;

public class ByteFieldConversionProcessor implements ConversionProcessor {
	@Override
	public Integer getSize() {
		return 8;
	}

	@Override
	public Object fromBytes(Class<?> target, Annotation[] fieldAnnotations, ByteBuffer buffer,
			Boolean allowLossyConversion, Boolean isSilent) {
		byte value = buffer.get();
		// short field can only be used with short, string
		if (target.equals(Byte.class) || target.equals(Byte.TYPE)) {
			return value;
		} else if (target.equals(String.class)) {
			return String.valueOf(value);
		} else {
			if (!isSilent)
				throw new BobcException(BobcErrorCodes.UNKNOWN,
						BobcErrorCodes.conversionNotKnown(ShortField.class, target));
		}
		return null;
	}

	@Override
	public byte[] fromField(Class<?> fieldType, Object fieldValue, Annotation[] fieldAnnotations, ByteOrder order,
			Boolean allowLossyConversion, Boolean isSilent) {
		// IMPORTANT your packing should match the size of in getSize()
		ByteBuffer buffer = ByteBuffer.allocate(1).order(
				order == ByteOrder.LITTLE_ENDIAN ? java.nio.ByteOrder.LITTLE_ENDIAN : java.nio.ByteOrder.BIG_ENDIAN);
		if (fieldValue == null) {
			throw new BobcException(BobcErrorCodes.PACKING_WITH_NULL, BobcErrorCodes.packingWithNull(fieldType));
		}

		// only field type of short and string will be processed for shortField
		if (fieldType.equals(Byte.class) || fieldType.equals(Byte.TYPE)) {
			buffer.put((byte) fieldValue);
		} else if (fieldType.equals(String.class)) {
			buffer.put(Byte.valueOf(String.class.cast(fieldValue)));
		} else {
			buffer.put((byte) 0);
			if (!isSilent)
				throw new BobcException(BobcErrorCodes.UNKNOWN,
						BobcErrorCodes.conversionNotKnown(fieldType, ShortField.class));
		}
		return buffer.array();
	}

}
