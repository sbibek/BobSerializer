package bobc;

import bobc.types.XShort;

public class Test2 {
	@XShort
	private String varB;

	@XShort
	private Short s;

	@XShort
	private Integer i;

	@XShort
	private Long l;

	@XShort
	private Byte b;

	public Test2() {
	}

	public String getVarB() {
		return varB;
	}

	public Short getS() {
		return s;
	}

	public Integer getI() {
		return i;
	}

	public Long getL() {
		return l;
	}

	public void setL(Long l) {
		this.l = l;
	}

	public Byte getB() {
		return b;
	}

}
