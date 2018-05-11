package bobc;

import java.lang.reflect.Field;

import bobc.core.ByteOrder;
import bobc.core.Converter;
import bobc.utils.ReflectionUtils;

public class UsageTest {
	public static void fn(Object... arguments) {
		for (Object arg : arguments) {
			System.out.println(arg.getClass().toGenericString());
		}
	}

	public static void main(String[] args) {
		Converter converter = Converter.builder().order(ByteOrder.LITTLE_ENDIAN).add(Test2.class).build();
		Test2 t = new Test2(1);
		for (Field f : t.getClass().getDeclaredFields()) {
			System.out.println(ReflectionUtils.getBobcTypeAnnotationIfExists(f));
		}
	}
}
