package bobc;

import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.nio.ByteBuffer;

import bobc.core.ByteOrder;
import bobc.core.struct.ByteOrdering;
import bobc.core.struct.Struct;
import bobc.types.ByteField;
import bobc.types.CharField;
import bobc.types.DoubleField;
import bobc.types.FloatField;
import bobc.types.Int32Field;
import bobc.types.LongField;
import bobc.types.ShortField;
import bobc.types.UInt64Field;
import bobc.types.ULongField;

public class UsageTest {

	@ByteOrdering(ByteOrder.LITTLE_ENDIAN)
	static public class TT extends Struct<TT> {
		@ByteField
		public Byte bytef = (byte) 1;
		@CharField
		public char charf = '9';
		@ShortField
		public Short shortf = (short) 2;
		@Int32Field
		public Integer intf = 3;
		@LongField
		public Long longf = 4L;
		@FloatField
		public Float floatf = 5.0f;
		@DoubleField
		public Double doublef = 6.0;
		@ULongField
		public BigInteger bigintf = BigInteger.valueOf(7);
		@UInt64Field
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
		test.longf = Long.MAX_VALUE + 1;
		test.floatf = 6.0f;
		test.doublef = 7.0;
		test.bigintf = BigInteger.valueOf(Long.MAX_VALUE + 1);
		test.stringf = "9";
		TT t2 = new TT();

		t2 = test.newInstanceFrom(test.toBytes());

		System.out.println(t2.bytef + " " + t2.charf + " " + t2.shortf + " " + t2.intf + " " + t2.longf + " "
				+ t2.floatf + " " + t2.doublef + " " + t2.bigintf + " " + t2.stringf);

	}
}
