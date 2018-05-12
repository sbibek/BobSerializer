package bobc.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import bobc.core.processing.ConversionProcessor;
import bobc.types.ShortType;

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
		return annotation.annotationType().getPackage() == ShortType.class.getPackage();
	}

	/**
	 * There can be one or more type definition for a field, so check if its of type
	 * bobc and get it
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

	/**
	 * if annotation is of bobc type, then this will return the procesor attached to
	 * it else throws exception
	 * 
	 * @param annotation
	 * @return
	 */
	public static ConversionProcessor getConversionProcessor(Annotation annotation) {
		try {
			Class<?> _class = (Class<?>) annotation.annotationType().getDeclaredMethod("processor").invoke(annotation);
			if (hasConversionProcessorInterface(_class))
				return (ConversionProcessor) _class.newInstance();
			else {
				throw new RuntimeException(_class + " is not a conversion processor");
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException | InstantiationException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Checks if given class is Conversion processor
	 * 
	 * @param _class
	 * @return
	 */
	public static Boolean hasConversionProcessorInterface(Class<?> _class) {
		for (Class<?> cls : _class.getInterfaces()) {
			if (cls.equals(ConversionProcessor.class))
				return true;
		}
		return false;
	}

	/**
	 * Check if lossy conversion is allowed on bobc type
	 * 
	 * @param annotation
	 * @return
	 */
	public static Boolean getAllowedLossyConversionFrom(Annotation annotation) {
		try {
			return (Boolean) annotation.annotationType().getDeclaredMethod("allowLossyConversionFrom")
					.invoke(annotation);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Check if lossy conversion is allowed on bobc type
	 * 
	 * @param annotation
	 * @return
	 */
	public static Boolean getAllowedLossyConversionTo(Annotation annotation) {
		try {
			return (Boolean) annotation.annotationType().getDeclaredMethod("allowLossyConversionTo").invoke(annotation);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Get silent field from type annotation
	 * 
	 * @param annotation
	 * @return
	 */
	public static Boolean getSilent(Annotation annotation) {
		try {
			return (Boolean) annotation.annotationType().getDeclaredMethod("silent").invoke(annotation);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}
}
