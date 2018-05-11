package bobc.core;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bobc.core.processing.FieldProcessorsRegistry;

@SuppressWarnings("rawtypes")
public class BobcCore {
	public static final FieldProcessorsRegistry processorRegistry = new FieldProcessorsRegistry();
	private Unpacker unpacker = new Unpacker();

	@SuppressWarnings("unchecked")
	public ObjectResults convertbytesToTargetObjects(ByteBuffer buffer, List<Class> classes, ByteOrder order) {
		Map<Class, Object> results = new HashMap<>();
		classes.forEach(_class -> {
			results.put(_class, unpacker.unpack(processorRegistry, _class, buffer, order));
		});
		return new ObjectResults(results);
	}
}
