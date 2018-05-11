package bobc.core.processing.conversionUtils;

public class ShortToInteger implements FieldConvertor<Short, Integer> {

	@Override
	public Integer convert(Short value) {
		return Short.toUnsignedInt(value);
	}

	@Override
	public Short reverseConvert(Integer value) {
		return (short)((int)value);
	}

}
