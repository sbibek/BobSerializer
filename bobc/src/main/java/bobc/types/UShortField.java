package bobc.types;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import bobc.core.processing.fieldConverters.UShortFieldConversionProcessor;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UShortField {
	Class<?> processor() default UShortFieldConversionProcessor.class;

	boolean allowLossyConversionFrom() default false;

	boolean allowLossyConversionTo() default false;

	boolean silent() default false;
}
