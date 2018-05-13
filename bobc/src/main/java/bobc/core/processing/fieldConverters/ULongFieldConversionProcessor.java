package bobc.core.processing.fieldConverters;

import java.lang.annotation.Annotation;
import java.math.BigInteger;
import java.nio.ByteBuffer;

import bobc.core.ByteOrder;
import bobc.core.exception.BobcErrorCodes;
import bobc.core.exception.BobcException;
import bobc.core.processing.ConversionProcessor;
import bobc.types.ULongField;

public class ULongFieldConversionProcessor implements ConversionProcessor {

	@Override
	public Integer getSize() {
		return 64;
	}

	@Override
	public Object fromBytes(Class<?> target, Annotation[] fieldAnnotations, ByteBuffer buffer,
			Boolean allowLossyConversion, Boolean isSilent) {
		byte[] value = new byte[8];
		buffer.get(value);
		// uint64 field can only be used with BigInteger, string
		if (target.equals(BigInteger.class)) {
			return new BigInteger(1, value);
		} else if (target.equals(String.class)) {
			return Long.toUnsignedString(ByteBuffer.wrap(value).order(buffer.order()).getLong());
		} else {
			if (!isSilent)
				throw new BobcException(BobcErrorCodes.UNKNOWN,
						BobcErrorCodes.conversionNotKnown(ULongField.class, target));
		}
		return null;
	}

	@Override
	public byte[] fromField(Class<?> fieldType, Object fieldValue, Annotation[] fieldAnnotations, ByteOrder order,
			Boolean allowLossyConversion, Boolean isSilent) {
		// IMPORTANT your packing should match the size of in getSize()
		ByteBuffer buffer = ByteBuffer.allocate(8).order(
				order == ByteOrder.LITTLE_ENDIAN ? java.nio.ByteOrder.LITTLE_ENDIAN : java.nio.ByteOrder.BIG_ENDIAN);
		if (fieldValue == null) {
			throw new BobcException(BobcErrorCodes.PACKING_WITH_NULL, BobcErrorCodes.packingWithNull(fieldType));
		}

		// only field type of short and string will be processed for shortField
		if (fieldType.equals(BigInteger.class)) {
			buffer.putLong(((BigInteger) fieldValue).longValueExact());
		} else if (fieldType.equals(String.class)) {
			buffer.putLong(Long.parseUnsignedLong(String.class.cast(fieldValue)));
		} else {
			buffer.putLong(0L);
			if (!isSilent)
				throw new BobcException(BobcErrorCodes.UNKNOWN,
						BobcErrorCodes.conversionNotKnown(fieldType, ULongField.class));
		}
		return buffer.array();
	}

}
