package bobc.core;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bobc.core.packunpack.Packer;
import bobc.core.packunpack.Unpacker;

@SuppressWarnings("rawtypes")
public class BobcCore {
	private Unpacker unpacker = new Unpacker();
	private Packer packer = new Packer();

	@SuppressWarnings("unchecked")
	public ObjectResults convertbytesToTargetObjects(ByteBuffer buffer, List<Class> classes, ByteOrder order,
			Map<Class<?>, Object> instances) {
		Map<Class, Object> results = new HashMap<>();
		classes.forEach(_class -> {
			results.put(_class, unpacker.unpack(_class, buffer, order, instances));
		});
		return new ObjectResults(results);
	}

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

	private static byte[] concat(byte[] first, byte[] second) {
		byte[] result = Arrays.copyOf(first, first.length + second.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}

}
