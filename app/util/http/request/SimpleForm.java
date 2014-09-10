package util.http.request;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import play.mvc.Http.RequestBody;
import util.Pair;
import util.exception.EmptyParameterException;

public class SimpleForm extends RequestBody implements Map<String, String> {
	
	/**
	 * Holder of the original form request values.
	 */
	private Map<String, String[]> values;

	/**
	 * Constructs a simple form request body where only one value belongs to one key.
	 * 
	 * @param values values to be mapped
	 */
	public SimpleForm(Map<String, String[]> values) {
		this.values = values;
	}

	public void clear() {
		values.clear();
	}

	public boolean containsKey(Object key) {
		return values.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return values.containsValue(value);
	}

	public Set<java.util.Map.Entry<String, String>> entrySet() {
		Set<java.util.Map.Entry<String, String>> entries = new HashSet<Map.Entry<String,String>>();
		for (Map.Entry<String, String[]> entry : values.entrySet()) {
			
			entries.add(new Pair(entry.getKey(), entry.getValue()[0]));
		}
		
		return entries;
	}

	public String get(Object key) {
		return values.get(key)[0];
	}
	
	public String getAsRequired(Object key) throws EmptyParameterException {
		String value = get(key);
		
		if(null == value || value.isEmpty()) {
			throw new EmptyParameterException(key);
		}
		
		return value;
	}

	public boolean isEmpty() {
		return values.isEmpty();
	}

	public Set<String> keySet() {
		return values.keySet();
	}

	public String put(String key, String value) {
		return values.put(key, new String[] {value})[0];
	}

	public void putAll(Map<? extends String, ? extends String> m) {
		for (Map.Entry<? extends String, ? extends String> entry : m.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
	}

	public String remove(Object key) {
		return values.remove(key)[0];
	}

	public int size() {
		return values.size();
	}

	public Collection<String> values() {
		List<String> vals = new ArrayList<String>();
		
		for (String[] value : values.values()) {
			vals.add(value[0]);
		}
		
		return vals;
	}

}
