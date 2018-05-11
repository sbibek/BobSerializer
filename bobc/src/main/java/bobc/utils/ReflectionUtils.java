package bobc.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import bobc.types.UShort;

public class ReflectionUtils {
	/**
	 * Checks if the passsed annotation belongs to bobc types family
	 * 
	 * @param annotation
	 * @return
	 */
	public static Boolean isBobcType(Annotation annotation) {
		// all internal bobc types will be stored in types package, so the
		// annotation
		// falls in the package then its bobc type, so taking reference of
		// UShort
		// annotation
		return annotation.annotationType().getPackage() == UShort.class.getPackage();
	}

	/**
	 * There can be one or more type definition for a field, so check if its of
	 * type bobc and get it
	 * 
	 * @param field
	 * @return
	 */
	public static List<Annotation> getBobcTypeAnnotationIfExists(Field field) {
		List<Annotation> results = new ArrayList<>();
		for (Annotation annotation : field.getDeclaredAnnotations()) {
			if (ReflectionUtils.isBobcType(annotation))
				results.add(annotation);
		}
		return results;
	}
}
