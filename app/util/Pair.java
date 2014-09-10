package util;

import java.util.Map;

public class Pair implements Map.Entry<String, String> {

	private String left;
	
	private String right;
	
	public Pair(String left, String right) {
		this.left = left;
		this.right = right;
	}

	public String getKey() {
		return left;
	}

	public String getValue() {
		return right;
	}

	public String setValue(String right) {
		this.right = right;
		return right;
	}
}
