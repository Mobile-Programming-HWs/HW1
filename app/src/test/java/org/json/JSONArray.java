package org.json;

import java.util.ArrayList;
import java.util.List;

public class JSONArray {
    private final List<JSONObject> values = new ArrayList<>();

    public JSONArray(String source) throws JSONException {
        String trimmedSource = source.trim();
        if (!trimmedSource.startsWith("[") || !trimmedSource.endsWith("]")) {
            throw new JSONException("Expected array");
        }

        String body = trimmedSource.substring(1, trimmedSource.length() - 1).trim();
        if (body.isEmpty()) {
            return;
        }

        String[] objectSources = body.split("\\},\\s*\\{");
        for (int index = 0; index < objectSources.length; index++) {
            String objectSource = objectSources[index];
            if (!objectSource.startsWith("{")) {
                objectSource = "{" + objectSource;
            }
            if (!objectSource.endsWith("}")) {
                objectSource = objectSource + "}";
            }
            values.add(new JSONObject(objectSource));
        }
    }

    public int length() {
        return values.size();
    }

    public JSONObject getJSONObject(int index) throws JSONException {
        if (index < 0 || index >= values.size()) {
            throw new JSONException("Index out of range");
        }
        return values.get(index);
    }
}
