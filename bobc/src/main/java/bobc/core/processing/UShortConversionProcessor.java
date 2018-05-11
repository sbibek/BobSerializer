package bobc.core.processing;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;

public class UShortConversionProcessor implements ConversionProcessor {

	@Override
	public Integer getSize() {
		return 32;
	}

	@Override
	public Object fromBytes(Class<?> target, Annotation[] fieldAnnotations, ByteBuffer buffer) {
		return buffer.getInt();
	}

	@Override
	public <T> byte[] fromField(Field field, Annotation annotation) {
		// TODO Auto-generated method stub
		return null;
	}

}