package com.example.corona_api.model;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//Constructor yerine @AllArgsConstructor da kullanılabilri!!

@Data
@Document(collection = "news")
public class News {

    @Id
    private String id;
    private String date;
    private String city;
    private int cases;
    private int deaths;
    private int recoveries;
    private String originalNews;

    public News() {
    }
    public News(String date, String city, int cases, int deaths, int recoveries) {
        this.date = date;
        this.city = city;
        this.cases = cases;
        this.deaths = deaths;
        this.recoveries = recoveries;
    }

   
    
}
