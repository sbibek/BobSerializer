package bobc;

import bobc.types.ShortField;

public class Test2 {
	@ShortField
	public Short short3;
	@ShortField(allowLossyConversionTo = true)
	public Long short4;
	@ShortField(allowLossyConversionTo = true)
	public Double short5;
}
