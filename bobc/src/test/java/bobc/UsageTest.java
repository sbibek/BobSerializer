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

	public static byte[] toBytes() {
		return ByteBuffer.allocate(10).order(java.nio.ByteOrder.LITTLE_ENDIAN).putShort((short) 1).putShort((short) 2)
				.putShort((short) 8).putShort((short) 99).putShort((short) 11).array();
	}

	public static void main(String[] args) {
		Converter converter = Converter.builder().order(ByteOrder.LITTLE_ENDIAN).add(Test2.class).build();
		Test2 t = converter.convert(toBytes()).get(Test2.class);

		System.out.println(t.getVarB() + " " + t.getS() + " " + t.getI() + " " + t.getL() + " " + t.getB());
	}
}
