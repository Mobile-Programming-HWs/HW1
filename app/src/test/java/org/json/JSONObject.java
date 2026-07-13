package org.json;

import java.util.HashMap;
import java.util.Map;

public class JSONObject {
    private final Map<String, String> values = new HashMap<>();

    public JSONObject(String source) throws JSONException {
        String trimmedSource = source.trim();
        if (!trimmedSource.startsWith("{") || !trimmedSource.endsWith("}")) {
            throw new JSONException("Expected object");
        }

        String body = trimmedSource.substring(1, trimmedSource.length() - 1).trim();
        if (body.isEmpty()) {
            return;
        }

        String[] pairs = body.split(",");
        for (String pair : pairs) {
            String[] keyAndValue = pair.split(":", 2);
            if (keyAndValue.length != 2) {
                throw new JSONException("Expected key value pair");
            }
            values.put(stripQuotes(keyAndValue[0].trim()), stripQuotes(keyAndValue[1].trim()));
        }
    }

    public double getDouble(String key) throws JSONException {
        try {
            return Double.parseDouble(valueFor(key));
        } catch (NumberFormatException exception) {
            throw new JSONException("Expected double");
        }
    }

    public int getInt(String key) throws JSONException {
        try {
            return Integer.parseInt(valueFor(key));
        } catch (NumberFormatException exception) {
            throw new JSONException("Expected int");
        }
    }

    private String valueFor(String key) throws JSONException {
        if (!values.containsKey(key)) {
            throw new JSONException("Missing key");
        }
        return values.get(key);
    }

    private static String stripQuotes(String source) {
        if (source.length() >= 2 && source.startsWith("\"") && source.endsWith("\"")) {
            return source.substring(1, source.length() - 1);
        }
        return source;
    }
}
