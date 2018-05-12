package bobc.core;

import java.util.HashMap;
import java.util.Map;

public abstract class Struct<T extends Struct<T>> {
	@SuppressWarnings("unchecked")
	public T newInstanceFrom(byte[] data) {
		// first thing to do is resolve the byte order
		// default is little endian
		ByteOrdering orderAnnotation = this.getClass().getDeclaredAnnotation(ByteOrdering.class);
		ByteOrder order = orderAnnotation != null ? orderAnnotation.value() : ByteOrder.LITTLE_ENDIAN;
		Converter converter = Converter.builder().order(order).add(this.getClass()).build();
		return (T) converter.convert(data).get(this.getClass());
	}

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

	public byte[] toBytes() {
		ByteOrdering orderAnnotation = this.getClass().getDeclaredAnnotation(ByteOrdering.class);
		ByteOrder order = orderAnnotation != null ? orderAnnotation.value() : ByteOrder.LITTLE_ENDIAN;
		Converter converter = Converter.builder().order(order).add(this.getClass()).build();
		return converter.convert(this);
	}
}
