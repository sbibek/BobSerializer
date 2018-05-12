package bobc.unit.singleTypeTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

import org.junit.Test;

import bobc.core.BobcException;
import bobc.core.ByteOrder;
import bobc.core.Converter;
import bobc.types.ShortType;

public class ShortTypeTest {

	static public class ShortTypeClassA {
		@ShortType
		public Short objectShortVariable;
		@ShortType
		public short primitiveShortVariable;
		@ShortType(allowLossyConversionFrom = true)
		public Byte objectByteVariable;
		@ShortType(allowLossyConversionFrom = true)
		public byte primitiveByteVariable;
		@ShortType
		public Integer objectIntegerVariable;
		@ShortType
		public int primitiveIntegerVariable;
		@ShortType
		public Long objectLongVariable;
		@ShortType
		public long primitiveLongVariable;
		@ShortType
		public Float objectFloatVariable;
		@ShortType
		public float primitiveFloatVariable;
		@ShortType
		public Double objectDoubleVariable;
		@ShortType
		public double primitiveDoubleVariable;
	}

	// class with element that doesn't allow lossy conversios (eg short to byte)
	static public class ShortTypeClassB {
		@ShortType
		public Byte byteData;
	}

	// allpw lossy conversion
	static public class ShortTypeClassC {
		@ShortType(allowLossyConversionFrom = true)
		public Byte data;
	}

	// donot allow lossy converison but also donot throw any exceptions
	static public class ShortTypeClassD {
		@ShortType(silent = true)
		public Byte data;
	}

	private byte[] generateDataForClassA() {
		return ByteBuffer.allocate(24).order(java.nio.ByteOrder.LITTLE_ENDIAN).putShort((short) 1).putShort((short) 2)
				.putShort((short) 3).putShort((short) 4).putShort((short) 5).putShort((short) 6).putShort((short) 7)
				.putShort((short) 8).putShort((short) 9).putShort((short) 10).putShort((short) 11).putShort((short) 12)
				.array();
	}

	@Test
	public void primitivesAndObjectTypeUnpackingTest() {
		byte[] byteData = generateDataForClassA();
		Converter converter = Converter.builder().order(ByteOrder.LITTLE_ENDIAN).add(ShortTypeClassA.class).build();
		ShortTypeClassA testClassA = converter.convert(byteData).get(ShortTypeClassA.class);
		assertNotNull(testClassA);
		assertEquals(testClassA.objectShortVariable, (Short) (short) 1);
		assertEquals(testClassA.primitiveShortVariable, (short) 2);
		assertEquals(testClassA.objectByteVariable, (Byte) (byte) 3);
		assertEquals(testClassA.primitiveByteVariable, (byte) 4);
		assertEquals(testClassA.objectIntegerVariable, new Integer(5));
		assertEquals(testClassA.primitiveIntegerVariable, 6);
		assertEquals(testClassA.objectLongVariable, (Long) (long) 7);
		assertEquals(testClassA.primitiveLongVariable, 8);
		assertEquals(testClassA.objectFloatVariable, (Float) 9f);
		assertEquals(testClassA.primitiveFloatVariable, 10f, 0.0001f);
		assertEquals(testClassA.objectDoubleVariable, new Double(11));
		assertEquals(testClassA.primitiveDoubleVariable, 12, 0.0001f);
	}

	@Test(expected = BufferUnderflowException.class)
	public void bufferUnderflowTestWhenUnpackingExceedsBytes() {
		byte[] byteData = generateDataForClassA();
		Converter converter = Converter.builder().order(ByteOrder.LITTLE_ENDIAN)
				.add(ShortTypeClassA.class, ShortTypeClassA.class, ShortTypeClassA.class).build();
		converter.convert(byteData);
	}

	@Test(expected = BobcException.class)
	public void lossyConversionUnSupressedExceptionTest() {
		byte[] byteData = generateDataForClassA();
		Converter converter = Converter.builder().order(ByteOrder.LITTLE_ENDIAN).add(ShortTypeClassB.class).build();
		converter.convert(byteData);
	}

	@Test
	public void lossyConversionSupressedTest() {
		byte[] byteData = ByteBuffer.allocate(2).order(java.nio.ByteOrder.LITTLE_ENDIAN).putShort((short) 11).array();
		Converter converter = Converter.builder().order(ByteOrder.LITTLE_ENDIAN).add(ShortTypeClassC.class).build();
		assertTrue(converter.convert(byteData).get(ShortTypeClassC.class).data == 11);
		byteData = ByteBuffer.allocate(2).order(java.nio.ByteOrder.LITTLE_ENDIAN).putShort((short) 65535).array();
		// confirming the overflow
		assertTrue(converter.convert(byteData).get(ShortTypeClassC.class).data != 65535
				&& converter.convert(byteData).get(ShortTypeClassC.class).data == (byte) 65535);
	}

	@Test
	public void lossyConversionSilentTest() {
		// should run silently but should not convert means the data should be null
		byte[] byteData = ByteBuffer.allocate(2).order(java.nio.ByteOrder.LITTLE_ENDIAN).putShort((short) 11).array();
		Converter converter = Converter.builder().order(ByteOrder.LITTLE_ENDIAN).add(ShortTypeClassD.class).build();
		assertTrue(converter.convert(byteData).get(ShortTypeClassD.class).data == null);
	}

}
