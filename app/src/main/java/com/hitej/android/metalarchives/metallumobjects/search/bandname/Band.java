package com.hitej.android.metalarchives.metallumobjects.search.bandname;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Band {

    @SerializedName("query")
    @Expose
    private String query;
    @SerializedName("search_results")
    @Expose
    private List<SearchResultTest> searchResults = null;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<SearchResultTest> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<SearchResultTest> searchResults) {
        this.searchResults = searchResults;
    }

}