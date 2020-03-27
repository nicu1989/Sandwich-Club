package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    /* Process JSON object to Sanwich object. The JSON format(as seen in Android Studio
    using the debugger) is:

     	"name": {
		"mainName": "Pljeskavica",
		"alsoKnownAs": []
	},
	"placeOfOrigin": "Serbia",
	"description": "Pljeskavica, a grilled dish ..."
	"image": "https://upload.wikimedia.org/...",
	"ingredients": ["Two or more of beef, lamb, pork, veal",
	"Onions",
	"Bread crumbs",
	"Lard"]*/
    public static Sandwich parseSandwichJson(String json) {
        JSONObject sandwichJson;
        Sandwich sandwichData;

        String mainName = null;
        String placeOfOrigin = null;
        String description = null;
        String image = null;
        List<String> ingredients = new ArrayList<>();
        List<String> alsoKnownAs = new ArrayList<>();

        try {
            sandwichJson = new JSONObject(json);
            JSONObject sandwichName = sandwichJson.getJSONObject("name");

            //get the strings
            mainName = sandwichName.getString("mainName");
            description = sandwichJson.getString("description");
            placeOfOrigin = sandwichJson.getString("placeOfOrigin");
            image = sandwichJson.getString("image");

            //now get the arrays
            JSONArray ingredientsJson = sandwichJson.getJSONArray("ingredients");
            for (int i = 0; i < ingredientsJson.length(); i++)
            {
                String currIngred = ingredientsJson.getString(i);
                ingredients.add(currIngred);
            }

            JSONArray akaJson = sandwichName.getJSONArray("alsoKnownAs");
            for (int i = 0; i < akaJson.length(); i++)
            {
                String currAka = akaJson.getString(i);
                alsoKnownAs.add(currAka);
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

        sandwichData = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        return sandwichData;
    }
}
