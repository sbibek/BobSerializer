package bobc.core.processing;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;

public interface ConversionProcessor {
	public Integer getSize();

	public Object fromBytes(Class<?> target, Annotation[] fieldAnnotations, ByteBuffer buffer);

	public <T> byte[] fromField(Field field, Annotation annotation);
}
