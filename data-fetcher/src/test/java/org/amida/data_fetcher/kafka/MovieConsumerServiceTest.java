package org.amida.data_fetcher.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.amida.data_fetcher.model.Movie;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovieConsumerServiceTest {

    @Test
    public void parseMovieDataTest() throws JsonProcessingException {
        String json = """
    {
      "Title": "Inception",
      "Year": "2010",
      "Genre": "Action, Adventure, Sci-Fi",
      "Director": "Christopher Nolan"
    }
    """;

        ObjectMapper mapper = new ObjectMapper();
        Movie movie = mapper.readValue(json, Movie.class);

        assertEquals("Inception", movie.title());
        assertEquals("2010", movie.year());
        assertEquals("Action, Adventure, Sci-Fi", movie.genre());
        assertEquals("Christopher Nolan", movie.director());
    }

}