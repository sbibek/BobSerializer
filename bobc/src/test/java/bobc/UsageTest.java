package bobc;

import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;

import bobc.core.ByteOrder;
import bobc.core.struct.ByteOrdering;
import bobc.core.struct.Struct;
import bobc.types.ShortField;
import bobc.types.UShortField;

public class UsageTest {

	@ByteOrdering(ByteOrder.LITTLE_ENDIAN)
	static public class TT extends Struct<TT> {
		@ShortField
		public Short var;

		@ShortField
		public String test;

		@UShortField(allowLossyConversionFrom = true)
		public Integer value;

		@UShortField
		public String sval;

		@ShortField
		public char c;

		@UShortField(allowLossyConversionFrom = true)
		public Double d;

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

		test.var = (short) 1;
		test.test = "2";
		test.value = -1;
		test.sval = "67";
		test.c = '1';

		TT t = test.newInstanceFrom(test.toBytes());
		System.out.println(t.var + " " + t.value + " " + t.sval + ">" + t.c + " " + t.d);
	}
}
