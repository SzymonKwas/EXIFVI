package sk.exif_vi.Utils;

import org.apache.commons.lang3.StringUtils;

public class FormUtil {

    public static final String AND = " AND ";
    public static final String OR = " OR ";


    public static final String DATETIME = " DATETIME ";
    public static final String FLASH = " FLASH ";
    public static final String FOCAL_LENGTH_OBJECTIVE = " FOCAL_LENGTH_OBJECTIVE ";
    public static final String FOCAL_LENGTH_OCULAR = " FOCAL_LENGTH_OCULAR ";
    public static final String IMAGE_LENGTH = " IMAGE_LENGTH ";
    public static final String IMAGE_WIDTH = " IMAGE_WIDTH ";
    public static final String MAKE = " MAKE ";
    public static final String MODEL = " MODEL ";
    public static final String ORIENTATION = " ORIENTATION ";
    public static final String WHITE_BALANCE = " WHITE_BALANCE ";


    public static String getPredicationSign(String predication) {
        switch (predication) {
            case "gt":
                return " > ";

            case "eq":
                return " = ";

            case "lt":
                return " < ";


            default:
                return "";
        }
    }

    public static String getSwitched(Object predication) {

        String stringPredication = predication.toString();
        if (StringUtils.isEmpty(stringPredication)) {
            return null;
        }

        switch (stringPredication) {
            case "on":
                return "1";

            case "off":
                return "0";

            default:
                return null;
        }
    }

    public static String joinQuery(String query, String field, String predication, String value) {
        if (StringUtils.isNotEmpty(value) && StringUtils.isNotEmpty(field) && StringUtils.isNotEmpty(predication)) {

            String condition = field + predication + value;

            if (StringUtils.isNotEmpty(query)) {
                return query + AND + condition;
            } else {
                return condition;
            }
        }
        return query;
    }

}
