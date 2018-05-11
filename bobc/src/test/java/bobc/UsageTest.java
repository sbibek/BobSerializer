package bobc;

import bobc.core.ByteOrder;
import bobc.core.Converter;

public class UsageTest {
	public static void fn(Object... arguments) {
		for (Object arg : arguments) {
			System.out.println(arg.getClass().toGenericString());
		}
	}

	public static void main(String[] args) {
		Converter converter = Converter.builder().order(ByteOrder.LITTLE_ENDIAN).add(Test2.class).build();
		converter.convert(new byte[1]);
	}
}
