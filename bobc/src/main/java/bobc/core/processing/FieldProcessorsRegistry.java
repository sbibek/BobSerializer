package bobc.core.processing;

import java.util.HashMap;
import java.util.Map;

import bobc.core.BobcErrorCodes;
import bobc.core.BobcException;
import bobc.core.processing.fieldConverters.ShortConversionProcessor;
import bobc.types.XShort;

@SuppressWarnings("rawtypes")
public class FieldProcessorsRegistry {
	private Map<Class, ConversionProcessor> conversionProcessorRegistry = new HashMap<>();

	public FieldProcessorsRegistry() {
		this.registerBuiltInProcessors();
	}

	private void registerBuiltInProcessors() {
		conversionProcessorRegistry.put(XShort.class, new ShortConversionProcessor());
	}

	public ConversionProcessor get(Class forClass) {
		if (!conversionProcessorRegistry.containsKey(forClass)) {
			throw new BobcException(BobcErrorCodes.NO_FIELD_PROCESSOR_FOUND,
					"no field processor found for class " + forClass);
		}
		return conversionProcessorRegistry.get(forClass);
	}
}
