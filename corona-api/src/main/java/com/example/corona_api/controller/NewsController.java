package com.example.corona_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.corona_api.model.News;
import com.example.corona_api.service.NewsService;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @PostMapping("/add")
    public ResponseEntity<String> addNews(@RequestBody String newsText) {
        newsService.saveNews(newsText);  
        return ResponseEntity.ok("News added successfully"); 
    }
    
    @GetMapping
    public List<News> getAllNews() {
        return newsService.getAllNews();  // mongodan tüm haberleri çeker
    }

    @GetMapping("/city/{city}")
    public List<News> getNewsByCity(@PathVariable String city) {    
        return newsService.getNewsByCity(city);
    }

    @GetMapping("/reports/daily")
public ResponseEntity<Map<String, Map<String, Integer>>> getDailyReports() {
    List<News> allNews = newsService.getAllNews();
    Map<String, Map<String, Integer>> dailyReports = new TreeMap<>();

    for (News news : allNews) {
        dailyReports.putIfAbsent(news.getDate(), new HashMap<>());
        Map<String, Integer> dailyData = dailyReports.get(news.getDate());
        dailyData.put("cases", dailyData.getOrDefault("cases", 0) + news.getCases());
        dailyData.put("deaths", dailyData.getOrDefault("deaths", 0) + news.getDeaths());
        dailyData.put("recoveries", dailyData.getOrDefault("recoveries", 0) + news.getRecoveries());
    }

    return ResponseEntity.ok(dailyReports);
}

@GetMapping("/reports/cumulative")
public ResponseEntity<Map<String, Map<String, Integer>>> getCumulativeReports() {
    List<News> allNews = newsService.getAllNews();
    Map<String, Map<String, Integer>> cumulativeReports = new LinkedHashMap<>();

    int cumulativeCases = 0;
    int cumulativeDeaths = 0;
    int cumulativeRecoveries = 0;

    List<String> dates = allNews.stream()
        .map(news -> news.getDate()) 
        .distinct()
        .sorted()
        .collect(Collectors.toList());

    for (String date : dates) {
        List<News> dailyNews = allNews.stream()
            .filter(news -> news.getDate().equals(date))
            .collect(Collectors.toList());

        int cases = dailyNews.stream().mapToInt(News::getCases).sum();
        int deaths = dailyNews.stream().mapToInt(News::getDeaths).sum();
        int recoveries = dailyNews.stream().mapToInt(News::getRecoveries).sum();

        cumulativeCases += cases;
        cumulativeDeaths += deaths;
        cumulativeRecoveries += recoveries;

        Map<String, Integer> dailyCumulative = new HashMap<>();
        dailyCumulative.put("cases", cumulativeCases);
        dailyCumulative.put("deaths", cumulativeDeaths);
        dailyCumulative.put("recoveries", cumulativeRecoveries);

        cumulativeReports.put(date, dailyCumulative);
    }

    return ResponseEntity.ok(cumulativeReports);
}

@GetMapping("reports/cities")
public List<String> getAllCities() {
    List<News> allNews = newsService.getAllNews();
    return allNews.stream()
                  .map(News::getCity) 
                  .distinct()         
                  .sorted()           
                  .collect(Collectors.toList());
}


}
