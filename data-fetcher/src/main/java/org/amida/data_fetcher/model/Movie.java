package org.amida.data_fetcher.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Movie(
        @JsonProperty("Title")
        String title,
        @JsonProperty("Year")
        String year,
        @JsonProperty("Genre")
        String genre,
        @JsonProperty("Director")
        String director
) {
}
