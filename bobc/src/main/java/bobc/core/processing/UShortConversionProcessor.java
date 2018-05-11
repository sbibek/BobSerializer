package bobc.core.processing;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class UShortConversionProcessor implements ConversionProcessor {

	@Override
	public <T> T fromBytes(Class<T> target, Annotation annotation, byte[] data) {
		return null;
	}

	@Override
	public <T> byte[] fromField(Field field, Annotation annotation) {
		// TODO Auto-generated method stub
		return null;
	}

}
