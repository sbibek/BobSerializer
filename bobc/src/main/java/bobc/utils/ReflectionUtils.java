package bobc.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import bobc.types.UShort;

public class ReflectionUtils {
	/**
	 * Checks if the passsed annotation belongs to bobc types family
	 * 
	 * @param annotation
	 * @return
	 */
	public static Boolean isBobcType(Annotation annotation) {
		// all internal bobc types will be stored in types package, so the annotation
		// falls in the package then its bobc type, so taking reference of UShort
		// annotation
		return annotation.annotationType().getPackage() == UShort.class.getPackage();
	}

	/**
	 * There can be just one type definition for a field, so check if its of type
	 * bobc and get it
	 * 
	 * @param field
	 * @return
	 */
	public static Annotation getBobcTypeAnnotationIfExists(Field field) {
		for (Annotation annotation : field.getDeclaredAnnotations()) {
			if (ReflectionUtils.isBobcType(annotation))
				return annotation;
		}
		return null;
	}
}
