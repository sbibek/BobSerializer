package bobc.core.processing.fieldConverters;

import java.lang.annotation.Annotation;
import java.nio.ByteBuffer;

import bobc.core.ByteOrder;
import bobc.core.processing.ConversionProcessor;

public class ULongFieldConversionProcessor implements ConversionProcessor {

	@Override
	public Integer getSize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object fromBytes(Class<?> target, Annotation[] fieldAnnotations, ByteBuffer buffer,
			Boolean allowLossyConversion, Boolean isSilent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] fromField(Class<?> fieldType, Object fieldValue, Annotation[] fieldAnnotations, ByteOrder order,
			Boolean allowLossyConversion, Boolean isSilent) {
		// TODO Auto-generated method stub
		return null;
	}

}
