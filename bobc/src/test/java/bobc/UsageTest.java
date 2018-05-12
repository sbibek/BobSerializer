package bobc;

import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;

import bobc.core.ByteOrder;
import bobc.core.ByteOrdering;
import bobc.core.Converter;
import bobc.core.ObjectResults;
import bobc.core.Struct;
import bobc.types.ShortType;

public class UsageTest {

	@ByteOrdering(ByteOrder.LITTLE_ENDIAN)
	static public class TT extends Struct<TT> {
		@ShortType
		public Short var;

		@ShortType
		public String test;
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
		Converter converter = Converter.builder().order(ByteOrder.LITTLE_ENDIAN).add(Test2.class, Test1.class).build();

		ObjectResults r = converter.convert(toBytes());
		Test1 t1 = r.get(Test1.class);
		Test2 t2 = r.get(Test2.class);

		TT tt = new TT();
		System.out.println(tt.var);
		tt.from(toBytes());
		System.out.println(tt.var + " " + tt.test);
	}
}
