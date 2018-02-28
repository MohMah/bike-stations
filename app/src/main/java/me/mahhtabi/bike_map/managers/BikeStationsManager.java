package me.mahhtabi.bike_map.managers;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import me.mahhtabi.bike_map.models.BikeStation;

/**
 * Created by MohMah on 2/28/2018.
 */

public class BikeStationsManager {
    private static final String TAG = "BikeStationsManager";
    private List<BikeStation> bikeStations;
    private String json;

    public BikeStationsManager(String bikeStationsJsonString) {
        this.json = bikeStationsJsonString;
        bikeStations = parseJson(json);
    }

    private static List<BikeStation> parseJson(String json) {
        Gson GSON_DESERIALIZER = new GsonBuilder()
                .registerTypeAdapter(new TypeToken<BikeStation>() {
                }.getType(), new BikeStation.ListDeserializer())
                .create();
        return GSON_DESERIALIZER.fromJson(json, new TypeToken<List<BikeStation>>() {
        }.getType());
    }


    public List<BikeStation> getBikeStations() {
        return bikeStations;
    }

    public List<SearchSuggestion> getSuggestions(String newQuery) {
        List<SearchSuggestion> suggestions = new ArrayList<>();
        for (BikeStation bikeStation : bikeStations) {
            if (bikeStation.getFeaturename().contains(newQuery))
                suggestions.add(new BikeStationSuggestion(bikeStation.getFeaturename()));
        }
        return suggestions;
    }

    public BikeStation getBikeStation(String body) {
        for (BikeStation bikeStation : bikeStations) {
            if (bikeStation.getFeaturename().contains(body))
                return bikeStation;
        }
        return null;
    }
}
