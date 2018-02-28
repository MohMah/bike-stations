package me.mahhtabi.bike_map.managers;

import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

/**
 * Created by MohMah on 2/28/2018.
 */

public class BikeStationSuggestion implements SearchSuggestion {
    private String text;

    protected BikeStationSuggestion(Parcel in) {
        text = in.readString();
    }

    public BikeStationSuggestion(String text) {
        this.text = text;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BikeStationSuggestion> CREATOR = new Creator<BikeStationSuggestion>() {
        @Override
        public BikeStationSuggestion createFromParcel(Parcel in) {
            return new BikeStationSuggestion(in);
        }

        @Override
        public BikeStationSuggestion[] newArray(int size) {
            return new BikeStationSuggestion[size];
        }
    };

    @Override
    public String getBody() {
        return text;
    }
}
