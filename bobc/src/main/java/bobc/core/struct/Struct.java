package bobc.core.struct;

import java.util.HashMap;
import java.util.Map;

import bobc.core.ByteOrder;
import bobc.core.Converter;

/**
 * Struct is abstract class that provides the serialization/conversion
 * functionality directly to the class instead of accessing the bobc conversion
 * API. The annotations act as the definition of fields in the struct on basis
 * of which the bobc conversion api will convert it to and from bytes to object
 * upon request
 * 
 * @author bibek.shrestha
 *
 * @param <T>
 */
public abstract class Struct<T extends Struct<T>> {
	/**
	 * consumes byte data and returns new object of the class extending struct
	 * 
	 * @param data
	 *            byte array
	 * @return instance of the class extending the struct
	 */
	@SuppressWarnings("unchecked")
	public T newInstanceFrom(byte[] data) {
		// first thing to do is resolve the byte order
		// default is little endian
		ByteOrdering orderAnnotation = this.getClass().getDeclaredAnnotation(ByteOrdering.class);
		ByteOrder order = orderAnnotation != null ? orderAnnotation.value() : ByteOrder.LITTLE_ENDIAN;
		Converter converter = Converter.builder().order(order).add(this.getClass()).build();
		return (T) converter.convert(data).get(this.getClass());
	}

	/**
	 * Consumes the byte array and publishes results to the same object calling this
	 * method instead of creating new instance
	 * 
	 * @param data
	 *            byte array
	 */
	public void from(byte[] data) {
		// first thing to do is resolve the byte order
		// default is little endian
		ByteOrdering orderAnnotation = this.getClass().getDeclaredAnnotation(ByteOrdering.class);
		ByteOrder order = orderAnnotation != null ? orderAnnotation.value() : ByteOrder.LITTLE_ENDIAN;
		Converter converter = Converter.builder().order(order).add(this.getClass()).build();
		Map<Class<?>, Object> instances = new HashMap<>();
		instances.put(this.getClass(), this);
		converter.convert(data, instances);
	}

	/**
	 * Conversion of struct to bytes
	 * 
	 * @return byte array as result of the conversion
	 */
	public byte[] toBytes() {
		ByteOrdering orderAnnotation = this.getClass().getDeclaredAnnotation(ByteOrdering.class);
		ByteOrder order = orderAnnotation != null ? orderAnnotation.value() : ByteOrder.LITTLE_ENDIAN;
		Converter converter = Converter.builder().order(order).add(this.getClass()).build();
		return converter.convert(this);
	}
}
