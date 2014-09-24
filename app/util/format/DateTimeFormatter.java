package util.format;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.time.DateUtils;

import util.Configuration;

public final class DateTimeFormatter implements Serializable {

	/** Serial version. */
	private static final long serialVersionUID = 9112261553783258461L;
	
	private DateTimeFormatter() {
		super();
	}

	public static String formatTime(long seconds) {
		SimpleDateFormat df = new SimpleDateFormat(Configuration.TIME_PATTERN, Locale.getDefault());
    	df.setTimeZone(java.util.TimeZone.getTimeZone("GMT"));
    	return df.format(new Date(seconds * DateUtils.MILLIS_PER_SECOND));
	}
}
