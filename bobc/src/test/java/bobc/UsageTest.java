package bobc;

import java.nio.ByteBuffer;

import bobc.core.ByteOrder;
import bobc.core.Converter;

public class UsageTest {
	public static void fn(Object... arguments) {
		for (Object arg : arguments) {
			System.out.println(arg.getClass().toGenericString());
		}
	}

	public static byte[] toBytes(int i) {
		return ByteBuffer.allocate(4).order(java.nio.ByteOrder.LITTLE_ENDIAN).putInt(i).array();
	}

	public static void main(String[] args) {
		Converter converter = Converter.builder().order(ByteOrder.LITTLE_ENDIAN).add(Test2.class).build();
		System.out.println(converter.convert(toBytes(998)).get(Test2.class).getB());
	}
}
