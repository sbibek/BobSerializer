package bobc.core;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class BobcCore {
	private Unpacker unpacker = new Unpacker();

	@SuppressWarnings("unchecked")
	public ObjectResults convertbytesToTargetObjects(ByteBuffer buffer, List<Class> classes, ByteOrder order,
			Map<Class<?>, Object> instances) {
		Map<Class, Object> results = new HashMap<>();
		classes.forEach(_class -> {
			results.put(_class, unpacker.unpack(_class, buffer, order, instances));
		});
		return new ObjectResults(results);
	}
}
