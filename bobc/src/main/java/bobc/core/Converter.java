package bobc.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("rawtypes")
public class Converter {
	private List<Class> classList = new ArrayList<>();
	private ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;

	BobcCore bobCore = new BobcCore();

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
