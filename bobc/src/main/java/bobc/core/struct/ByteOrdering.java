package bobc.core.struct;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import bobc.core.ByteOrder;

/**
 * Annotation to denote the byte ordering for struct
 * 
 * @author bibek.shrestha
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ByteOrdering {
	ByteOrder value() default ByteOrder.LITTLE_ENDIAN;
}
