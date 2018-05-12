package bobc.core;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bobc.core.packunpack.Packer;
import bobc.core.packunpack.Unpacker;

/**
 * BobcCore handles the conversion request from the converter apis and delivers
 * the results expected
 * 
 * @author bibek.shrestha
 *
 */
@SuppressWarnings("rawtypes")
public class BobcCore {
	// byte arr to object unpacker utitlity
	private Unpacker unpacker = new Unpacker();
	// object to byte array packer utility
	private Packer packer = new Packer();

	/**
	 * Convert byte array to target class instances as represented by classes in the
	 * order provided
	 * 
	 * @param buffer
	 *            byte buffer containing data to be converted
	 * @param classes
	 *            added classes in the converter, instances will be created of these
	 *            classes
	 * @param order
	 *            byte order
	 * @param instances
	 *            core will use the instance for the classes if provided in this map
	 *            instead of creating new one
	 * @return object results with instances
	 */
	@SuppressWarnings("unchecked")
	public ObjectResults convertbytesToTargetObjects(ByteBuffer buffer, List<Class> classes, ByteOrder order,
			Map<Class<?>, Object> instances) {
		Map<Class, Object> results = new HashMap<>();
		classes.forEach(_class -> {
			results.put(_class, unpacker.unpack(_class, buffer, order, instances));
		});
		return new ObjectResults(results);
	}

	/**
	 * Convert objects to byte array. The objects must be instances of the classes
	 * added during the converter build phase
	 * 
	 * @param objects
	 *            instances to be converted
	 * @param classList
	 *            list of classes that was added
	 * @param order
	 *            byte ordering
	 * @return byte array as result of conversion
	 */
	public byte[] convertObjectsToBytes(Map<Class<?>, Object> objects, List<Class> classList, ByteOrder order) {
		// now packing should begin in the order of the classList
		List<byte[]> results = new ArrayList<>();
		for (Class<?> _cls : classList) {
			results.addAll(packer.pack(objects.get(_cls), order));
		}
		byte[] byteResults = new byte[] {};
		for (int i = 0; i < results.size(); i++) {
			byteResults = concat(byteResults, results.get(i));
		}
		return byteResults;
	}

	/**
	 * utility to concat the byte arrays
	 * 
	 * @param first
	 * @param second
	 * @return
	 */
	private static byte[] concat(byte[] first, byte[] second) {
		byte[] result = Arrays.copyOf(first, first.length + second.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}

}
