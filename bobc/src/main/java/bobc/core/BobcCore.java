package bobc.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bobc.core.processing.FieldProcessorsRegistry;
import bobc.utils.ReflectionUtils;

@SuppressWarnings("rawtypes")
public class BobcCore {
	public static final FieldProcessorsRegistry processorRegistry = new FieldProcessorsRegistry();
	private Unpacker unpacker = new Unpacker();

	@SuppressWarnings("unchecked")
	public ObjectResults convertbytesToTargetObjects(ByteBuffer buffer, List<Class> classes, ByteOrder order) {
		Map<Class, Object> results = new HashMap<>();
		classes.forEach(_class -> {
			for (Field field : _class.getDeclaredFields()) {
				List<Annotation> bobcAnnotations = ReflectionUtils.getBobcTypeAnnotationIfExists(field);
				// though we retrieved the bobc annotations, but only one of
				// them
				// should be type related annotaion for the field, right now
				// lets go ahead and assume we have just one type annotation
				results.put(_class, unpacker.unpack(processorRegistry, _class, buffer, order));
			}
		});
		return new ObjectResults(results);
	}
}
