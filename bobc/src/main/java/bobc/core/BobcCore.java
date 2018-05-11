package bobc.core;

import java.util.List;

import bobc.core.processing.FieldProcessorsRegistry;

@SuppressWarnings("rawtypes")
public class BobcCore {
	public static FieldProcessorsRegistry processorRegistry = new FieldProcessorsRegistry();

	public ObjectResults convertbytesToTargetObjects(byte[] data, List<Class> classes) {
		return null;
	}
}
