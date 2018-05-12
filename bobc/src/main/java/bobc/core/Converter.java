package bobc.core;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bobc.core.exception.BobcErrorCodes;
import bobc.core.exception.BobcException;

/**
 * Bobc Converter are the APIs facing the user of the library. It provides the
 * builder to build the converter instance to use the apis
 * 
 * @author bibek.shrestha
 *
 */
@SuppressWarnings("rawtypes")
public class Converter {
	// list of classes that the converter has
	private List<Class> classList = new ArrayList<>();
	// byte ordering this instance of converter, default Little endian
	private ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
	// bobc core for handling actual conversions
	BobcCore bobcCore = new BobcCore();

	/**
	 * Builder class for Converter
	 * 
	 * @author bibek.shrestha
	 *
	 */
	static public class ConverterBuilder {
		private Converter converter;

		public ConverterBuilder() {
			converter = new Converter();
		}

		/*
		 * Define order for converter
		 */
		public ConverterBuilder order(ByteOrder order) {
			converter.byteOrder = order;
			return this;
		}

		/**
		 * add classes that will be part of the converter. Order of classes is very
		 * important.
		 * 
		 * @param _class
		 *            classes to be part of this converter, order is very important.
		 * @return
		 */
		public ConverterBuilder add(Class... _class) {
			converter.classList = Arrays.asList(_class);
			return this;
		}

		/**
		 * Build and return converter
		 * 
		 * @return Converter
		 */
		public Converter build() {
			return converter;
		}
	}

	/**
	 * Get converter builder
	 * 
	 * @return ConverterBuilder
	 */
	public static ConverterBuilder builder() {
		return new ConverterBuilder();
	}

	/**
	 * Convert byte array into the classes. Unpack of bytes from byte array will
	 * happen as the order of passing.Passing of instances is optional as if there
	 * is no instance passed for any class, the object results will create new
	 * instance and hold it. If any instance is passed, then object results will
	 * hold the same instance.
	 * 
	 * @param data
	 *            byte array
	 * @param instances
	 *            map of instances per class which can be used to publish the result
	 *            instead of creating new instance
	 * @return
	 */
	public ObjectResults convert(byte[] data, Map<Class<?>, Object> instances) {
		java.nio.ByteOrder order = byteOrder == ByteOrder.LITTLE_ENDIAN ? java.nio.ByteOrder.LITTLE_ENDIAN
				: java.nio.ByteOrder.BIG_ENDIAN;
		ByteBuffer buffer = ByteBuffer.wrap(data).order(order);
		return bobcCore.convertbytesToTargetObjects(buffer, classList, byteOrder, instances);
	}

	/**
	 * conversion to objects. Result will hold newly created instances of classses
	 * passed in the builder
	 * 
	 * @param data
	 * @return
	 */
	public ObjectResults convert(byte[] data) {
		java.nio.ByteOrder order = byteOrder == ByteOrder.LITTLE_ENDIAN ? java.nio.ByteOrder.LITTLE_ENDIAN
				: java.nio.ByteOrder.BIG_ENDIAN;
		ByteBuffer buffer = ByteBuffer.wrap(data).order(order);
		return bobcCore.convertbytesToTargetObjects(buffer, classList, byteOrder, new HashMap<>());
	}

	/**
	 * Convert the objects passed to byte array. Passed objects must correspond to
	 * each classes added in the builder. The sequence of packing will be determined
	 * by sequence of adding the clsses not the passed objects in this call
	 * 
	 * @param objects
	 *            instances of classes to be converted
	 * @return converted byte array
	 */
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

	/**
	 * get current byte order of the converter
	 * 
	 * @return
	 */
	public ByteOrder getByteOrder() {
		return byteOrder;
	}
}
