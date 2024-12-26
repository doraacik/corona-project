package com.example.corona_api.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.Map;
import java.util.regex.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.corona_api.model.News;
import com.example.corona_api.repository.NewsRepository;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;
    private final NewsParser newsParser;

    private static final Pattern casesPattern = Pattern.compile("(\\d+) yeni vaka");
    //private static final Pattern deathsPattern = Pattern.compile("(\\d+) kişi ([\\w\\s]+vefat|ölüm|kaybetti)");
    //private static final Pattern recoveriesPattern = Pattern.compile("(\\d+(?:[.,]\\d+)?)\\s*(?:kişi(?:de|dir)?)?\\s*taburcu|taburcu\\s*(\\d+(?:[.,]\\d+)?)\\s*(?:kişi(?:de|dir)?)?");
    private static final Pattern cityPattern = Pattern.compile("(?<=tarihinde\\s)([A-Za-zÇÖĞÜŞİçöğüüş]+(?:['-][A-Za-zÇÖĞÜŞİçöğüüş]+)*)"); // 'tarihinde' sonrası şehir ismi


    // Unutma : Constructor Injection!!
    public NewsService(NewsRepository newsRepository, NewsParser newsParser) {
        this.newsRepository = newsRepository;
        this.newsParser = newsParser;
    }

   /*  public void saveNews(String newsText) {
        News news = newsParser.parseNews(newsText); // Haber metnini parse et
        newsRepository.save(news); // mongoya kayıt
    }*/

    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    public List<News> getNewsByCity(String city) {
        return newsRepository.findByCity(city);
    }

    public Map<String, Map<String, Integer>> getDailyReports() {
        List<News> allNews = newsRepository.findAll();
        Map<String, Map<String, Integer>> reports = new TreeMap<>();

        for (News news : allNews) {
            String date = news.getDate();
            reports.putIfAbsent(date, new HashMap<>());
            Map<String, Integer> dailyData = reports.get(date);

            dailyData.put("cases", dailyData.getOrDefault("cases", 0) + news.getCases());
            dailyData.put("deaths", dailyData.getOrDefault("deaths", 0) + news.getDeaths());
            dailyData.put("recovered", dailyData.getOrDefault("recovered", 0) + news.getRecoveries());
        }

        return reports;
    }

     public Map<String, Integer> getCumulativeReports() {
        List<News> allNews = newsRepository.findAll();
        Map<String, Integer> cumulativeData = new HashMap<>();
    
        cumulativeData.put("totalCases", 0);
        cumulativeData.put("totalDeaths", 0);
        cumulativeData.put("totalRecovered", 0);
    
        for (News news : allNews) {
            cumulativeData.put("totalCases", cumulativeData.get("totalCases") + news.getCases());
            cumulativeData.put("totalDeaths", cumulativeData.get("totalDeaths") + news.getDeaths());
            cumulativeData.put("totalRecovered", cumulativeData.get("totalRecovered") + news.getRecoveries());
        }
    
        return cumulativeData;
    }

    public void saveNews(String newsText) {
        News news = new News();
    
        // Orijinal metni kaydet
        news.setOriginalNews(newsText);
    
        // Tarih ayrıştırma
        Matcher dateMatcher = Pattern.compile("(\\d{2}\\.\\d{2}\\.\\d{4})").matcher(newsText);
        if (dateMatcher.find()) {
            news.setDate(dateMatcher.group(1));
        }
    
    
        Matcher cityMatcher = cityPattern.matcher(newsText);
        if (cityMatcher.find()) {
            System.out.println("Found city: " + cityMatcher.group(1)); // debug print
            news.setCity(cityMatcher.group(1)); // şehri ayıkla
        }

    
        Matcher casesMatcher = casesPattern.matcher(newsText);
        if (casesMatcher.find()) {
            news.setCases(Integer.parseInt(casesMatcher.group(1)));
        }
    
        //news.setCases((findCases(newsText)));
    
        news.setDeaths((findDeaths(newsText)));

        news.setRecoveries((findRecoveries(newsText)));
        newsRepository.save(news);
    }

    public static int findRecoveries(String text) { 
       int recoveries = 0;
        String[] sentences = text.split("[.?!]");

        for (String sentence : sentences) {
            if (sentence.contains("taburcu")) {
                recoveries = findIntegerInSentence(sentence);
            }
        }
        return recoveries;
    }

    public static int findDeaths(String text) { 
        int deaths = 0;
         String[] sentences = text.split("[.?!]");
 
         for (String sentence : sentences) {
             if (sentence.contains("vefat")) {
                deaths = findIntegerInSentence(sentence);
             }
         }
         return deaths;
     }
     public static int findCases(String text) {
        int cases = 0;
         String[] sentences = text.split("[.?!]");
 
         for (String sentence : sentences) {
             if (sentence.contains("vaka")) {
                cases = findIntegerInSentence(sentence);
             }
         }
         return cases;
     }

    public static int findIntegerInSentence(String sentence) {
        Pattern numberPattern = Pattern.compile("(\\d+)");
        Matcher matcher = numberPattern.matcher(sentence);

        if (matcher.find()) {
            try {
                return Integer.parseInt(matcher.group(1));
            } catch (NumberFormatException e) {
                System.err.println("Tamsayı dönüştürme hatası: " + matcher.group(1));
                return 0;
            }
        }
        return 0;
    }

    

}
