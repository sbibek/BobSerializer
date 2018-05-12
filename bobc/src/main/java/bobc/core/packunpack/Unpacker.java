package bobc.core.packunpack;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

import bobc.core.ByteOrder;
import bobc.core.exception.BobcErrorCodes;
import bobc.core.exception.BobcException;
import bobc.core.processing.ConversionProcessor;
import bobc.utils.ReflectionUtils;

/**
 * Unpacker utility to unpack byte[] into class instances
 * 
 * @author bibek.shrestha
 *
 */
public class Unpacker {
	/**
	 * unpack byte[] to target class instances
	 * 
	 * @param targetClass
	 *            instances to be created from byte[]
	 * @param buffer
	 *            byte buffer containing data to be converted
	 * @param order
	 *            byte order
	 * @param instances
	 *            instances to be reused instead of creating for class represented
	 *            in targetClass, if not present new instances will be created
	 * @return
	 */
	public <T extends Object> T unpack(Class<T> targetClass, ByteBuffer buffer, ByteOrder order,
			Map<Class<?>, Object> instances) {
		try {
			// we will create instance for the target class so that we can set
			// the fields after unpack operation
			Object instance = instances.containsKey(targetClass) ? instances.get(targetClass)
					: targetClass.newInstance();
			for (Field field : targetClass.getDeclaredFields()) {
				List<Annotation> bobcAnnotations = ReflectionUtils.getBobcTypeAnnotationIfExists(field);
				// if there is no annotation for type, just continue
				if (bobcAnnotations.size() == 0)
					continue;
				// though we retrieved the bobc annotations, but only one of
				// them
				// should be type related annotaion for the field, right now
				// lets go ahead and assume we have just one type annotation
				// TODO, there cannot be multiple types in single field so always take first one
				Annotation annotation = bobcAnnotations.get(0);
				unpackField(instance, field, ReflectionUtils.getConversionProcessor(annotation), buffer, order,
						ReflectionUtils.getAllowedLossyConversionTo(annotation), ReflectionUtils.getSilent(annotation));
			}
			return targetClass.cast(instance);
		} catch (InstantiationException | IllegalAccessException e) {
			throw new BobcException(BobcErrorCodes.UNKNOWN, e.getMessage());
		}
	}

	private void unpackField(Object instance, Field field, ConversionProcessor processor, ByteBuffer buffer,
			ByteOrder order, Boolean allowLossyConversion, Boolean isSilent)
			throws IllegalArgumentException, IllegalAccessException {
		byte[] data = new byte[(int) Math.ceil(processor.getSize() / 8.0)];
		buffer.get(data);
		ByteBuffer fieldBuffer = ByteBuffer.wrap(data).order(buffer.order());
		// now lets create new Bytebuffer with the proper byte ordering
		Object result = processor.fromBytes(field.getType(), field.getDeclaredAnnotations(), fieldBuffer,
				allowLossyConversion, isSilent);
		field.setAccessible(true);
		if (result != null) {
			field.set(instance, result);
		}
		field.setAccessible(false);
	}
}
