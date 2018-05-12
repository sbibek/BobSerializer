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
		Converter converter = Converter.builder().order(ByteOrder.LITTLE_ENDIAN).add(Test1.class, Test2.class).build();

		Test1 a = new Test1();
		a.short1 = (short) 1;
		a.short2 = 2;
		a.str = "19";
		Test2 b = new Test2();
		b.short3 = (short) 3;
		b.short4 = 4L;
		b.short5 = 5.0;

		ObjectResults r = converter.convert(converter.convert(a, b));
		Test1 t1 = r.get(Test1.class);
		Test2 t2 = r.get(Test2.class);
		System.out.println(t1.short1 + " " + t1.short2 + " " + t1.str + " ");
		System.out.println(t2.short3 + " " + t2.short4 + " " + t2.short5);
	}
}
