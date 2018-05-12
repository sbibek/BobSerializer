package bobc.core.processing.fieldConverters;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;

import bobc.core.processing.ConversionProcessor;
import bobc.core.processing.ConversionUtil;

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
				throw new RuntimeException("unallowed lossy conversion from Short to " + target);
		} else {
			if (!isSilent)
				// no conversion found
				throw new RuntimeException("unknown conversion from Short to " + target + " encountered");
		}
		return null;
	}

	@Override
	public <T> byte[] fromField(Field field, Annotation annotation) {
		// TODO Auto-generated method stub
		return null;
	}

}