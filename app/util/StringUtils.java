package util;

public final class StringUtils {

	private StringUtils() {
		super();
	}
	
	public static boolean isEmpty(String input) {
		return null != input && !input.isEmpty();
	}
}
