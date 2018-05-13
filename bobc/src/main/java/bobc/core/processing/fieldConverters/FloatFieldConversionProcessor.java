package bobc.core.processing.fieldConverters;

import java.lang.annotation.Annotation;
import java.nio.ByteBuffer;

import bobc.core.ByteOrder;
import bobc.core.exception.BobcErrorCodes;
import bobc.core.exception.BobcException;
import bobc.core.processing.ConversionProcessor;
import bobc.types.FloatField;

public class FloatFieldConversionProcessor implements ConversionProcessor {

	@Override
	public Integer getSize() {
		return 32;
	}

	@Override
	public Object fromBytes(Class<?> target, Annotation[] fieldAnnotations, ByteBuffer buffer,
			Boolean allowLossyConversion, Boolean isSilent) {
		float value = buffer.getFloat();
		// short field can only be used with short, string
		if (target.equals(Float.class) || target.equals(Float.TYPE)) {
			return value;
		} else if (target.equals(String.class)) {
			return String.valueOf(value);
		} else {
			if (!isSilent)
				throw new BobcException(BobcErrorCodes.UNKNOWN,
						BobcErrorCodes.conversionNotKnown(FloatField.class, target));
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
		if (fieldType.equals(Float.class) || fieldType.equals(Float.TYPE)) {
			buffer.putFloat((float) fieldValue);
		} else if (fieldType.equals(String.class)) {
			buffer.putFloat(Float.valueOf(String.class.cast(fieldValue)));
		} else {
			buffer.putFloat(0.0f);
			if (!isSilent)
				throw new BobcException(BobcErrorCodes.UNKNOWN,
						BobcErrorCodes.conversionNotKnown(fieldType, FloatField.class));
		}
		return buffer.array();
	}

}
