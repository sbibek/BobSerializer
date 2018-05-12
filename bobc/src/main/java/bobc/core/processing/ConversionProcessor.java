package bobc.core.processing;

import java.lang.annotation.Annotation;
import java.nio.ByteBuffer;

import bobc.core.ByteOrder;

/**
 * Each field will have its own conversion process which will 1. convert byte[]
 * to objects 2. convert objects to byte[] as per signature of field provided.
 * All the convertors must implement this interface
 * 
 * @author bibek.shrestha
 *
 */
public interface ConversionProcessor {
	/**
	 * Size of the field the current processor is asking for in bits. The bytes
	 * provided to this converter will be ceil of bytes according to this size
	 * 
	 * @return size
	 */
	public Integer getSize();

	/**
	 * Convert the bytes provided into the appropriate field value
	 * 
	 * @param target
	 *            target class of the field
	 * @param fieldAnnotations
	 *            annotations that are availabe in the field, can be used for
	 *            extending functionaltiy
	 * @param buffer
	 *            buffer containing byte[] amount equal to bytes represented in
	 *            getSize()
	 * @param allowLossyConversion
	 *            if true allows lossy conversion from data represented by byte[] to
	 *            target class (eg short to byte)
	 * @param isSilent
	 *            if silent, no exceptions are raised in case of unallowed lossy
	 *            conversions, unknown conversion schemes etc
	 * @return field value
	 */
	public Object fromBytes(Class<?> target, Annotation[] fieldAnnotations, ByteBuffer buffer,
			Boolean allowLossyConversion, Boolean isSilent);

	/**
	 * Convert the field value into the bytes according to field processor
	 * annotation used
	 * 
	 * @param fieldType
	 *            type of field
	 * @param fieldValue
	 *            current value in the field
	 * @param fieldAnnotations
	 *            annotations present in the field, can be used for extending
	 *            functionaltiy
	 * @param order
	 *            byte order
	 * @param allowLossyConversion
	 *            if trues allows lossy conversion from data represented in the
	 *            field to data to be represented in byte[]
	 * @param isSilent
	 *            if silent, no exceptions are thrown in case of unallowed lossy
	 *            conversion or unknown conversion scheme
	 * @return byte[] as representation of value in the field
	 */
	public byte[] fromField(Class<?> fieldType, Object fieldValue, Annotation[] fieldAnnotations, ByteOrder order,
			Boolean allowLossyConversion, Boolean isSilent);
}
