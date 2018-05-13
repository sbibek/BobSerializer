package bobc;

import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.nio.ByteBuffer;

import bobc.core.ByteOrder;
import bobc.core.struct.ByteOrdering;
import bobc.core.struct.Struct;
import bobc.types.Int32Field;

public class UsageTest {

	@ByteOrdering(ByteOrder.LITTLE_ENDIAN)
	static public class TT extends Struct<TT> {
		@Int32Field(allowLossyConversionTo = true)
		public Byte bytef = (byte) 1;
		@Int32Field(allowLossyConversionTo = true)
		public char charf = '9';
		@Int32Field(allowLossyConversionTo = true)
		public Short shortf = (short) 2;
		@Int32Field
		public Integer intf = 3;
		@Int32Field(allowLossyConversionFrom = true)
		public Long longf = 4L;
		@Int32Field(allowLossyConversionFrom = true)
		public Float floatf = 5.0f;
		@Int32Field(allowLossyConversionFrom = true)
		public Double doublef = 6.0;
		@Int32Field(allowLossyConversionFrom = true)
		public BigInteger bigintf = BigInteger.valueOf(7);
		@Int32Field
		public String stringf = "8";
	}

	public static void fn(Object... arguments) {
		for (Object arg : arguments) {
			System.out.println(arg.getClass().toGenericString());
		}
	}

	public static byte[] toBytes() {
		return ByteBuffer.allocate(10).order(java.nio.ByteOrder.LITTLE_ENDIAN).putShort((short) 1).putShort((short) 2)
				.putShort((short) 8).putShort((short) 99).putShort((short) 11).array();
	}

	public static void main(String[] args) throws NoSuchFieldException, SecurityException, NoSuchMethodException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		TT test = new TT();
		test.bytef = (byte) 1;
		test.charf = '2';
		test.shortf = (short) 3;
		test.intf = 4;
		test.longf = 5L;
		test.floatf = 6.0f;
		test.doublef = 7.0;
		test.bigintf = BigInteger.valueOf(8);
		test.stringf = "9";

		TT t2 = new TT();
		t2 = test.newInstanceFrom(test.toBytes());

		System.out.println(t2.bytef + " " + t2.charf + " " + t2.shortf + " " + t2.intf + " " + t2.longf + " "
				+ t2.floatf + " " + t2.doublef + " " + t2.bigintf + " " + t2.stringf);
	}
}
