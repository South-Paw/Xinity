package seng302.util;

import com.google.gson.Gson;

import spark.ResponseTransformer;

import java.util.Map;

/**
 * The handy dandy little json util that makes our lives easier when managing json stuff.
 *
 * @author adg62
 */
public final class JsonUtil {

    /**
     * Turns an object into json!
     *
     * @param object An object.
     * @return A json string of the object.
     */
    public static String toJson(Object object) {
        return new Gson().toJson(object);
    }

    /**
     * Turn json into a map object!
     *
     * @param json A string of json.
     * @return A map of Strings to Objects (make sure you cast them when you use them!)
     */
    public static Map<String, Object> fromJson(String json) {
        return new Gson().fromJson(json, Map.class);
    }

    /**
     * Makes use of Java 8 method references to return a ResponseTransformer instance.
     * Basically a shortcut to toJson().
     *
     * @return A ResponseTransformer instance.
     */
    public static ResponseTransformer json() {
        return JsonUtil::toJson;
    }
}
