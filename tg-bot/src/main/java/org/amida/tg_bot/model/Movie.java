package org.amida.tg_bot.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Movie {
    private String title;
    private String year;
    private String genre;
    private String director;

}
