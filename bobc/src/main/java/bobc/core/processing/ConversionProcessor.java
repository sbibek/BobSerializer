package bobc.core.processing;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public interface ConversionProcessor {
	public <T> T fromBytes(Class<T> target, Annotation annotation, byte[] data);

	public <T> byte[] fromField(Field field, Annotation annotation);
}
