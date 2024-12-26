package com.example.corona_api.service;

import org.springframework.stereotype.Service;

import com.example.corona_api.model.News;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class NewsParser {

    public News parseNews(String newsText) {
        News news = new News();

        String date = extractDate(newsText);
        news.setDate(date);

        String city = extractCity(newsText);
        news.setCity(city);

        news.setCases(extractCases(newsText));
        news.setDeaths(extractDeaths(newsText));
        news.setRecoveries(extractRecoveries(newsText));

        return news;
    }

    private String extractDate(String text) {
        Pattern datePattern = Pattern.compile("\\d{2}\\.\\d{2}\\.\\d{4}");
        Matcher matcher = datePattern.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    private String extractCity(String text) {
        // İl adını çıkarma (örneğin: "İstanbul")
        Pattern cityPattern = Pattern.compile("[A-ZÇĞİÖŞÜ][a-zçğöşü]+");
        Matcher matcher = cityPattern.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    private int extractCases(String text) {
        Pattern casesPattern = Pattern.compile("\\d+ yeni vaka");
        Matcher matcher = casesPattern.matcher(text);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group().split(" ")[0]);
        }
        return 0;
    }

    private int extractDeaths(String text) {
        Pattern deathPattern = Pattern.compile("\\d+ kişi korona'dan vefat etti");
        Matcher matcher = deathPattern.matcher(text);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group().split(" ")[0]);
        }
        return 0;
    }

    private int extractRecoveries(String text) {
        Pattern recoveryPattern = Pattern.compile("\\d+ kişide taburcu oldu");
        Matcher matcher = recoveryPattern.matcher(text);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group().split(" ")[0]);
        }
        return 0;
    }
    
}
