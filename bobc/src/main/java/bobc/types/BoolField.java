package bobc.types;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import bobc.core.processing.fieldConverters.BoolFieldConversionProcessor;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BoolField {
	Class<?> processor() default BoolFieldConversionProcessor.class;

	boolean allowLossyConversionFrom() default false;

	boolean allowLossyConversionTo() default false;

	boolean silent() default false;
}
