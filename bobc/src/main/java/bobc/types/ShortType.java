package bobc.types;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import bobc.core.processing.fieldConverters.ShortTypeConversionProcessor;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ShortType {
	Class<?> processor() default ShortTypeConversionProcessor.class;

	boolean allowLossyConversion() default false;

	boolean silent() default false;
}
