package bobc;

import bobc.types.ShortType;

public class Test1 {
	@ShortType
	public Short short1;
	@ShortType(allowLossyConversionTo = true)
	public Integer short2;
	@ShortType
	public String str;
}
