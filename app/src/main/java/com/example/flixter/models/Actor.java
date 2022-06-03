package com.example.flixter.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Actor {
    String name;
    String character;
    String department;

    public Actor(JSONObject jsonObject) {
        try {
            name = jsonObject.getString("name");
            character = jsonObject.getString("character");
            department = jsonObject.getString("known_for_department");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static List<Actor> fromJsonArray(JSONArray actors) {
        List<Actor> res = new ArrayList<>();
        try {
            for(int i = 0; i < actors.length(); i++) {
                    res.add(new Actor(actors.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  res;
    }

    public String getName() {
        return name;
    }

    public String getCharacter() {
        return character;
    }

    public String getDepartment() {
        return department;
    }
}
