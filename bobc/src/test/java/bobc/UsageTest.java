package bobc;

import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;

import bobc.core.ByteOrder;
import bobc.core.Converter;
import bobc.core.ObjectResults;

public class UsageTest {
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

		System.out.println(t1.short1 + " " + t1.short2 + " " + t2.short3 + " " + t2.short4 + " " + t2.short5);
	}
}
