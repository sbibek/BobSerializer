package bobc;

import bobc.types.ShortType;

public class Test2 {
	@ShortType
	public Short short3;
	@ShortType(allowLossyConversionTo = true)
	public Long short4;
	@ShortType(allowLossyConversionTo = true)
	public Double short5;
}
