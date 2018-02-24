package com.adel.sandwichclub.utils;

import android.content.Context;
import android.text.TextUtils;

import com.adel.sandwichclub.R;
import com.adel.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = new Sandwich();

        try {
            JSONObject currentSandwich = new JSONObject(json);

            JSONObject sandwichName = currentSandwich.getJSONObject("name");
            String mainName = sandwichName.getString("mainName");

            sandwich.setMainName(mainName);

            if (!sandwichName.isNull("alsoKnownAs")){
                JSONArray getAlsoKnownAs = sandwichName.getJSONArray("alsoKnownAs");
                ArrayList<String> alsoKnownAs = new ArrayList<>();
                for (int j = 0; j < getAlsoKnownAs.length(); j++) {
                    alsoKnownAs.add(getAlsoKnownAs.getString(j));
                }

                if (!alsoKnownAs.isEmpty()){
                    sandwich.setAlsoKnownAs(alsoKnownAs);
                }else {
                    alsoKnownAs.add("Detail not available");
                    sandwich.setAlsoKnownAs(alsoKnownAs);
                }
            }

            if (!TextUtils.isEmpty(currentSandwich.getString("placeOfOrigin"))){
                sandwich.setPlaceOfOrigin(currentSandwich.getString("placeOfOrigin"));
            }else {
                sandwich.setPlaceOfOrigin("Detail not available");
            }

            if (!TextUtils.isEmpty(currentSandwich.getString("description"))){
                sandwich.setDescription(currentSandwich.getString("description"));
            }else {
                sandwich.setDescription("Detail not available");
            }

            if (!currentSandwich.isNull("image")){
                sandwich.setImage(currentSandwich.getString("image"));
            }

            if (!currentSandwich.isNull("ingredients")){
                JSONArray getIngredients = currentSandwich.getJSONArray("ingredients");
                ArrayList<String> ingredients = new ArrayList<>();
                for (int j = 0; j < getIngredients.length(); j++) {
                    ingredients.add(getIngredients.getString(j));
                }

                if (!ingredients.isEmpty()){
                    sandwich.setIngredients(ingredients);
                }else {
                    ingredients.add("Detail not available");
                    sandwich.setIngredients(ingredients);
                }
            }

            return sandwich;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
