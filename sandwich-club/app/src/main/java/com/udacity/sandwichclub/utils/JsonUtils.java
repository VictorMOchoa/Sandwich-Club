package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        JSONObject obj = new JSONObject(json);
        Sandwich sandwich = new Sandwich();
        sandwich.setMainName((obj.getJSONObject("name").getString("mainName")));
        sandwich.setDescription(obj.getString("description"));
        sandwich.setImage(obj.getString("image"));
        sandwich.setPlaceOfOrigin(obj.getString("placeOfOrigin"));
        JSONArray alsoKnownAsJsonArr = obj.getJSONObject("name").getJSONArray("alsoKnownAs");
        sandwich.setAlsoKnownAs(jsonArrayToList(alsoKnownAsJsonArr));
        JSONArray ingredientsJsonArr = obj.getJSONArray("ingredients");
        sandwich.setIngredients(jsonArrayToList(ingredientsJsonArr));
        return sandwich;
    }

    public static List<String> jsonArrayToList(JSONArray jsonArray) throws JSONException {
        ArrayList<String> list = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getString(i));
        }
        return list;
    }

}
