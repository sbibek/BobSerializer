package bobc.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("rawtypes")
public class Converter {
	private List<Class> classList = new ArrayList<>();
	private ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;

	static public class ConverterBuilder {
		private Converter converter;

		public ConverterBuilder() {
			converter = new Converter();
		}

		public ConverterBuilder order(ByteOrder order) {
			converter.byteOrder = order;
			return this;
		}

		public ConverterBuilder add(Class... _class) {
			converter.classList = Arrays.asList(_class);
			return this;
		}

		public Converter build() {
			return converter;
		}
	}

	public static ConverterBuilder builder() {
		return new ConverterBuilder();
	}

	public void convert(byte[] data) {
		classList.forEach(_class -> {
			for (Field f : _class.getDeclaredFields()) {
				Annotation[] annotations = f.getDeclaredAnnotations();
				// BobcCore.processorRegistry.get(annotations[0].annotationType()).fromBytes(f.getType(),
				// annotations[0]);
			}
		});
	}

	public void convert(String hexString) {
	}

	public byte[] convert(Object... objects) {
		return new byte[1];
	}

	public ByteOrder getByteOrder() {
		return byteOrder;
	}
}
