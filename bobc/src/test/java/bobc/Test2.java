package bobc;

import bobc.types.UShort;

public class Test2 {
	@UShort
	private Integer varB;

	public Test2() {
	}

	public Test2(Integer b) {
		this.varB = b;
	}

	public Integer getB() {
		return varB;
	}

}
