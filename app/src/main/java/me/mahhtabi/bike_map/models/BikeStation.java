package me.mahhtabi.bike_map.models;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by MohMah on 2/28/2018.
 */

public class BikeStation {
    private int id;


    private int nbbikes;
    private String featurename;
    private double latitude;
    private double longitude;
    private double circleRadius;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNbbikes() {
        return nbbikes;
    }

    public void setNbbikes(int nbbikes) {
        this.nbbikes = nbbikes;
    }

    public String getFeaturename() {
        return featurename;
    }

    public void setFeaturename(String featurename) {
        this.featurename = featurename;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "BikeStation{" +
                "id='" + id + '\'' +
                ", nbbikes='" + nbbikes + '\'' +
                ", featurename='" + featurename + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }

    public double getCircleRadius() {
        //TODO calculate independent of data
        if (nbbikes < 10)
            return 40;
        else if (nbbikes < 20)
            return 60;
        else if (nbbikes < 30)
            return 90;
        else
            return 130;
    }

    public static class ListDeserializer implements JsonDeserializer<BikeStation> {
        @Override
        public BikeStation deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            BikeStation bikeStation = new BikeStation();
            bikeStation.setNbbikes(Integer.parseInt(jsonObject.get("nbbikes").getAsString()));
            bikeStation.setId(Integer.parseInt(jsonObject.get("id").getAsString()));
            bikeStation.setFeaturename(jsonObject.get("featurename").getAsString());
            bikeStation.setLatitude(Double.parseDouble(jsonObject.get("coordinates").getAsJsonObject().get("latitude").getAsString()));
            bikeStation.setLongitude(Double.parseDouble(jsonObject.get("coordinates").getAsJsonObject().get("longitude").getAsString()));
            return bikeStation;
        }
    }

}
