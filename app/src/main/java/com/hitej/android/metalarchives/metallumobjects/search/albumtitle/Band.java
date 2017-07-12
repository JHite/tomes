package com.hitej.android.metalarchives.metallumobjects.search.albumtitle;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Band {

    @SerializedName("query")
    @Expose
    private String query;
    @SerializedName("search_results")
    @Expose
    private List<SearchResult> searchResults = null;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<SearchResult> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<SearchResult> searchResults) {
        this.searchResults = searchResults;
    }

}