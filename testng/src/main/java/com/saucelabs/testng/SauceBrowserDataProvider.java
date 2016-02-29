package com.saucelabs.testng;

import com.saucelabs.common.Utils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Custom {@link DataProvider} which
 *
 * @author Ross Rowe
 */
public class SauceBrowserDataProvider {

    public static final String SAUCE_ONDEMAND_BROWSERS = "SAUCE_ONDEMAND_BROWSERS";

    /**
     * Constructs a List of Object array instances which represent a series of browser combinations.
     * The method retrieves and parses the value of the SAUCE_ONDEMAND_BROWSERS environment variable/system
     * property which is assumed to be in JSON format.
     *
     * @param testMethod Test method consuming the data
     * @return ArrayList of data provider (List of strings)
     */
    @DataProvider(name = "sauceBrowserDataProvider")
    public static Iterator<Object[]> sauceBrowserDataProvider(Method testMethod) {

        List<Object[]> data = new ArrayList<Object[]>();

        //read browsers from JSON-formatted environment variable if specified
        String json = Utils.readPropertyOrEnv(SAUCE_ONDEMAND_BROWSERS, "");

        if (json == null || json.equals("")) {
            throw new IllegalArgumentException("Unable to find JSON");
        }

        try {
            JSONArray browsers = (JSONArray) new JSONParser().parse(json);
            for (Object object : browsers) {
                JSONObject jsonObject = (JSONObject) object;
                data.add(new Object[]{
                        jsonObject.get("browser"),
                        jsonObject.get("version"),
                        jsonObject.get("os")});
            }
        } catch (ParseException e) {
            throw new IllegalArgumentException("Error parsing JSON String", e);
        }

        return data.iterator();
    }

}
