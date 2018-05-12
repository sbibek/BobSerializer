package bobc.types;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import bobc.core.processing.fieldConverters.Int32FieldConversionProcessor;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Int32Field {
	Class<?> processor() default Int32FieldConversionProcessor.class;

	boolean allowLossyConversionFrom() default false;

	boolean allowLossyConversionTo() default false;

	boolean silent() default false;
}
