package bobc.core.processing.conversionUtils;

public interface FieldConvertor<From extends Object, To extends Object> {
	public To convert(From value);

	public From reverseConvert(To value);
}
