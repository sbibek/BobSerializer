package bobc.core.packunpack;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import bobc.core.ByteOrder;
import bobc.core.exception.BobcErrorCodes;
import bobc.core.exception.BobcException;
import bobc.core.processing.ConversionProcessor;
import bobc.utils.ReflectionUtils;

/**
 * Utility to convert Object passed to byte[] representation in the given order
 * 
 * @author bibek.shrestha
 *
 */
public class Packer {
	/**
	 * Convert object to byte[]
	 * 
	 * @param object
	 *            object to be converted
	 * @param order
	 *            byte ordering
	 * @return converted byte[] representation of the object
	 */
	public List<byte[]> pack(Object object, ByteOrder order) {
		List<byte[]> bytes = new ArrayList<>();

		for (Field field : object.getClass().getDeclaredFields()) {
			List<Annotation> annotations = ReflectionUtils.getBobcTypeAnnotationIfExists(field);
			if (annotations.size() == 0)
				continue;

			Annotation typeAnnotation = annotations.get(0);
			try {
				bytes.add(packField(ReflectionUtils.getConversionProcessor(typeAnnotation),
						field.getDeclaredAnnotations(), object, field, order,
						ReflectionUtils.getAllowedLossyConversionFrom(typeAnnotation),
						ReflectionUtils.getSilent(typeAnnotation)));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new BobcException(BobcErrorCodes.UNKNOWN, e.getMessage());
			}
		}
		return bytes;
	}

	private byte[] packField(ConversionProcessor processor, Annotation[] fieldAnnotations, Object object, Field field,
			ByteOrder order, Boolean allowLossyConversion, Boolean isSilent)
			throws IllegalArgumentException, IllegalAccessException {
		field.setAccessible(true);
		Object value = field.get(object);
		field.setAccessible(false);
		byte[] result = processor.fromField(field.getType(), value, fieldAnnotations, order, allowLossyConversion,
				isSilent);
		Integer expectedSize = (int) Math.ceil(processor.getSize() / 8.0);
		if (result.length != expectedSize) {
			throw new BobcException(BobcErrorCodes.SIZE_EXPECTATION_MISMATCH,
					"expected byte[] size " + expectedSize + " got " + result.length);
		}
		return result;
	}
}