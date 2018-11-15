package inzynierka.animalshelters.helpers;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class DataHelper {
    public static int[] JSONObjectToIntArray(JSONArray jsonArray)
    {
        int numbers[] = new int[jsonArray.length()];

        for (int i = 0; i< jsonArray.length(); i++) {
            numbers[i] = jsonArray.optInt(i);
        }

        return numbers;
    }

    public static String getOrEmpty(String value)
    {
        return value.equals("null") ? "" : value;
    }
}
