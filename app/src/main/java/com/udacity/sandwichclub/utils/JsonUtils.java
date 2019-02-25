package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        final String NAME = "name";
        final String MAIN_NAME = "mainName";
        final String ALSO_KNOWN_AS = "alsoKnownAs";
        final String PLACE_OF_ORIGIN = "placeOfOrigin";
        final String DESCRIPTION = "description";
        final String IMAGE = "image";
        final String INGREDIENTS = "ingredients";

        try {
            JSONObject sandwichBase = new JSONObject(json);
            JSONObject name = sandwichBase.getJSONObject(NAME);
            String mainName = name.getString(MAIN_NAME);
            JSONArray arrayAlsoKnownAs = name.getJSONArray(ALSO_KNOWN_AS);
            List<String> alsoKnownAs = convertToJavaArray(arrayAlsoKnownAs);
            String placeOfOrigin = sandwichBase.getString(PLACE_OF_ORIGIN);
            String description = sandwichBase.getString(DESCRIPTION);
            String image = sandwichBase.getString(IMAGE);
            JSONArray arrayIngredients = sandwichBase.getJSONArray(INGREDIENTS);
            List<String> ingredients = convertToJavaArray(arrayIngredients);

            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    private static List<String> convertToJavaArray(JSONArray jsonArray) throws JSONException {
        List<String> list = new ArrayList<>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getString(i));
        }
        return list;
    }
}
