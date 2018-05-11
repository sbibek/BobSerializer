package bobc.core.processing;

import bobc.core.processing.conversionUtils.FieldConvertor;

public class ConversionSpecification {
	public Class<?> from;
	public Class<?> to;
	ConversionResult conversionResult;
	FieldConvertor convertorUtil;
}
