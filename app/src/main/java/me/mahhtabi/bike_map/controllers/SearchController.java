package me.mahhtabi.bike_map.controllers;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

/**
 * Created by MohMah on 2/28/2018.
 */

public class SearchController {
    private final Searchable searchable;

    public SearchController(final FloatingSearchView floatingSearchView, final Searchable searchable) {
        this.searchable = searchable;
        floatingSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {
                searchable.onSearchTexChanged(newQuery);
            }
        });
        floatingSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
                searchable.performSearch(searchSuggestion.getBody());
            }

            @Override
            public void onSearchAction(String currentQuery) {
            }
        });
    }
}
