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
		Map<Class<?>, Object> objectsMap = new HashMap<>();
		for (Object obj : objects) {
			objectsMap.put(obj.getClass(), obj);
		}
		// now check if all required objects are there
		this.classList.forEach(cls -> {
			if (!objectsMap.containsKey(cls)) {
				throw new BobcException(BobcErrorCodes.INSUFFICIENT_DATA_PASSED,
						"Conversion requires all objects to be passed");
			}
		});

		// now lets offshore the task to core
		return bobcCore.convertObjectsToBytes(objectsMap, classList, byteOrder);
	}

	public ByteOrder getByteOrder() {
		return byteOrder;
	}
}
