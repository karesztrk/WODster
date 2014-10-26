package util.lang;

import play.Play;

import java.io.Serializable;
import java.util.*;

/**
 *
 */
public class LocaleHelper implements Serializable {

    private static final String SUPPORTED_LOCALES_PATH = "application.langs";

    public static List<String> getSupportedLocaleCodes() {
        String configValue = Play.application().configuration().getString(SUPPORTED_LOCALES_PATH).replaceAll("\\s","");
        String[] locales = configValue.split(",");
        return Arrays.asList(locales);
    }

}
