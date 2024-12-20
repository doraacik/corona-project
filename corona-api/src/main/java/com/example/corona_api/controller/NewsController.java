package com.example.corona_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.corona_api.model.News;
import com.example.corona_api.service.NewsService;

import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @PostMapping("/add")
    public void addNews(@RequestBody String newsText) {
        newsService.saveNews(newsText);  // metni alıp parse eder ve veritabanına kaydeder
    }

    @GetMapping
    public List<News> getAllNews() {
        return newsService.getAllNews();  // mongodan tüm haberleri çeker
    }
}
