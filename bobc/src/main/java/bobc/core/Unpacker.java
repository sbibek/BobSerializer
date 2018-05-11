package bobc.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.List;

import bobc.core.processing.ConversionProcessor;
import bobc.core.processing.FieldProcessorsRegistry;
import bobc.utils.ReflectionUtils;

public class Unpacker {
	public <T extends Object> T unpack(FieldProcessorsRegistry registry, Class<T> targetClass, ByteBuffer buffer,
			ByteOrder order) {
		try {
			// we will create instance for the target class so that we can set
			// the fields after unpack operation
			Object instance = targetClass.newInstance();
			for (Field field : targetClass.getDeclaredFields()) {
				List<Annotation> bobcAnnotations = ReflectionUtils.getBobcTypeAnnotationIfExists(field);
				// though we retrieved the bobc annotations, but only one of
				// them
				// should be type related annotaion for the field, right now
				// lets go ahead and assume we have just one type annotation
				// TODO
				Annotation annotation = bobcAnnotations.get(0);
				unpackField(instance, field, registry.get(annotation.annotationType()), buffer, order);
			}
			return targetClass.cast(instance);
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private void unpackField(Object instance, Field field, ConversionProcessor processor, ByteBuffer buffer,
			ByteOrder order) throws IllegalArgumentException, IllegalAccessException {
		byte[] data = new byte[(int) Math.ceil(processor.getSize() / 8.0)];
		buffer.get(data);
		ByteBuffer fieldBuffer = ByteBuffer.wrap(data).order(buffer.order());
		// now lets create new Bytebuffer with the proper byte ordering
		Object result = processor.fromBytes(field.getType(), field.getDeclaredAnnotations(), fieldBuffer);
		field.setAccessible(true);
		if (result != null) {
			field.set(instance, result);
		}
		field.setAccessible(false);
	}
}
