package bobc.core.processing.fieldConverters;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;

import bobc.core.processing.ConversionProcessor;
import bobc.core.processing.ConversionUtil;

public class ShortConversionProcessor implements ConversionProcessor {

	@Override
	public Integer getSize() {
		return 16;
	}

	@Override
	public Object fromBytes(Class<?> target, Annotation[] fieldAnnotations, ByteBuffer buffer) {
		// now this field extracts short value from the buffer, then it depends how we
		// are converting it to the target
		if (target.equals(Short.class) || target.equals(Short.TYPE)) {
			return buffer.getShort();
		} else if (target.equals(Integer.class) || target.equals(Integer.TYPE)) {
			return ConversionUtil.toUnsignedInteger(buffer.getShort());
		} else if (target.equals(Long.class) || target.equals(Long.TYPE)) {
			return ConversionUtil.toUnsignedLong(buffer.getShort());
		} else if (target.equals(String.class)) {
			return String.valueOf(buffer.getShort());
		} else if (target.equals(Byte.class) || target.equals(Byte.TYPE)) {
			System.out.println("short => byte conversion is lossy conversion");
		}

		// if the conversion is not possible, then do this
		return null;
	}

	@Override
	public <T> byte[] fromField(Field field, Annotation annotation) {
		// TODO Auto-generated method stub
		return null;
	}

}