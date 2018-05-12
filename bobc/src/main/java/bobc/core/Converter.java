package bobc.core;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class Converter {
	private List<Class> classList = new ArrayList<>();
	private ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;

	BobcCore bobcCore = new BobcCore();

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

	public ObjectResults convert(byte[] data, Map<Class<?>, Object> instances) {
		java.nio.ByteOrder order = byteOrder == ByteOrder.LITTLE_ENDIAN ? java.nio.ByteOrder.LITTLE_ENDIAN
				: java.nio.ByteOrder.BIG_ENDIAN;
		ByteBuffer buffer = ByteBuffer.wrap(data).order(order);
		return bobcCore.convertbytesToTargetObjects(buffer, classList, byteOrder, instances);
	}

	public ObjectResults convert(byte[] data) {
		java.nio.ByteOrder order = byteOrder == ByteOrder.LITTLE_ENDIAN ? java.nio.ByteOrder.LITTLE_ENDIAN
				: java.nio.ByteOrder.BIG_ENDIAN;
		ByteBuffer buffer = ByteBuffer.wrap(data).order(order);
		return bobcCore.convertbytesToTargetObjects(buffer, classList, byteOrder, new HashMap<>());
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
