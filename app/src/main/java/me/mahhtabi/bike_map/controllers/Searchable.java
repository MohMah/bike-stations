package me.mahhtabi.bike_map.controllers;

/**
 * Created by MohMah on 2/28/2018.
 */

public interface Searchable {

    void onSearchTexChanged(String newQuery);
    void performSearch(String query);
}
