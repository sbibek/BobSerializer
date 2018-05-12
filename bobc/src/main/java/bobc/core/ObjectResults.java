package bobc.core;

import java.util.Map;

import bobc.core.exception.BobcErrorCodes;
import bobc.core.exception.BobcException;

/**
 * Bobc converter api can handle multiple classes at the same time and can
 * produce multiple results. Hence a class to hold the results
 * 
 * @author bibek.shrestha
 *
 */
@SuppressWarnings("rawtypes")
public class ObjectResults {
	private Map<Class, Object> results;

	public ObjectResults(Map<Class, Object> results) {
		this.results = results;
	}

	/**
	 * Results getter by class
	 * 
	 * @param _class
	 *            class to get instance of from the result map
	 * @return instance of the class stored in result
	 */
	public <T extends Object> T get(Class<T> _class) {
		if (!results.containsKey(_class)) {
			throw new BobcException(BobcErrorCodes.NO_SUCH_CLASS_WAS_ADDED,
					_class + "no such class was added to converter");
		}
		return _class.cast(results.get(_class));
	}
}
