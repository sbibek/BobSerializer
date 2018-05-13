package bobc.unit.singleTypeTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

import org.junit.Test;

import bobc.core.ByteOrder;
import bobc.core.Converter;
import bobc.types.ShortField;

/**
 * @author bibek.shrestha
 *
 */
public class ShortTypeTest {

	static public class ShortTypeClassA {
		@ShortField
		public Short a;

		@ShortField
		public String b;
	}

	private byte[] generateDataForClassA() {
		return ByteBuffer.allocate(4).order(java.nio.ByteOrder.LITTLE_ENDIAN).putShort((short) 1).putShort((short) 2)
				.array();
	}

	@Test
	public void shortFieldUnpackTestForShortAndString() {
		byte[] byteData = generateDataForClassA();
		Converter converter = Converter.builder().order(ByteOrder.LITTLE_ENDIAN).add(ShortTypeClassA.class).build();
		ShortTypeClassA testClassA = converter.convert(byteData).get(ShortTypeClassA.class);
		assertNotNull(testClassA);
		assertEquals((Short) (short) 1, testClassA.a);
		assertEquals("2", testClassA.b);
	}

	@Test
	public void packingAndUnpackingToSameClassShouldYeildSameValue() {
		Converter converter = Converter.builder().order(ByteOrder.LITTLE_ENDIAN).add(ShortTypeClassA.class).build();
		ShortTypeClassA a = new ShortTypeClassA();
		a.a = (short) 11;
		a.b = "12";

		ShortTypeClassA testClassA = converter.convert(converter.convert(a)).get(ShortTypeClassA.class);
		assertNotNull(testClassA);
		assertEquals((Short) (short) 11, testClassA.a);
		assertEquals("12", testClassA.b);
	}

	@Test(expected = BufferUnderflowException.class)
	public void bufferUnderflowTestWhenUnpackingExceedsBytes() {
		byte[] byteData = generateDataForClassA();
		Converter converter = Converter.builder().order(ByteOrder.LITTLE_ENDIAN)
				.add(ShortTypeClassA.class, ShortTypeClassA.class, ShortTypeClassA.class).build();
		converter.convert(byteData);
	}

}
