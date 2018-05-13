package bobc.core.processing.fieldConverters;

import java.lang.annotation.Annotation;
import java.nio.ByteBuffer;

import bobc.core.ByteOrder;
import bobc.core.exception.BobcErrorCodes;
import bobc.core.exception.BobcException;
import bobc.core.processing.ConversionProcessor;
import bobc.types.ShortField;
import bobc.types.UShortField;

public class UShortFieldConversionProcessor implements ConversionProcessor {

	@Override
	public Integer getSize() {
		return 16;
	}

	@Override
	public Object fromBytes(Class<?> target, Annotation[] fieldAnnotations, ByteBuffer buffer,
			Boolean allowLossyConversion, Boolean isSilent) {
		short value = buffer.getShort();
		// short field can only be used with short, string
		if (target.equals(Integer.class) || target.equals(Integer.TYPE)) {
			return Short.toUnsignedInt(value);
		} else if (target.equals(String.class)) {
			return String.valueOf(value);
		} else {
			if (!isSilent)
				throw new BobcException(BobcErrorCodes.UNKNOWN,
						BobcErrorCodes.conversionNotKnown(UShortField.class, target));
		}
		return null;
	}

	@Override
	public byte[] fromField(Class<?> fieldType, Object fieldValue, Annotation[] fieldAnnotations, ByteOrder order,
			Boolean allowLossyConversion, Boolean isSilent) {
		// IMPORTANT your packing should match the size of in getSize()
		ByteBuffer buffer = ByteBuffer.allocate(2).order(
				order == ByteOrder.LITTLE_ENDIAN ? java.nio.ByteOrder.LITTLE_ENDIAN : java.nio.ByteOrder.BIG_ENDIAN);
		if (fieldValue == null) {
			throw new BobcException(BobcErrorCodes.PACKING_WITH_NULL, BobcErrorCodes.packingWithNull(fieldType));
		}

		// only field type of short and string will be processed for shortField
		if (fieldType.equals(Integer.class) || fieldType.equals(Integer.TYPE)) {
			buffer.putShort((short) fieldValue);
		} else if (fieldType.equals(String.class)) {
			buffer.putShort(Short.valueOf(String.class.cast(fieldValue)));
		} else {
			buffer.putShort((short) 0);
			if (!isSilent)
				throw new BobcException(BobcErrorCodes.UNKNOWN,
						BobcErrorCodes.conversionNotKnown(fieldType, ShortField.class));
		}
		return buffer.array();
	}

}