package bobc.core;

import java.util.Map;

import bobc.core.exception.BobcErrorCodes;
import bobc.core.exception.BobcException;

@SuppressWarnings("rawtypes")
public class ObjectResults {
	private Map<Class, Object> results;

	public ObjectResults(Map<Class, Object> results) {
		this.results = results;
	}

	public <T extends Object> T get(Class<T> _class) {
		if (!results.containsKey(_class)) {
			throw new BobcException(BobcErrorCodes.NO_SUCH_CLASS_WAS_ADDED,
					_class + "no such class was added to converter");
		}
		return _class.cast(results.get(_class));
	}
}
