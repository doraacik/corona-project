package com.example.corona_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.corona_api.model.News;
import com.example.corona_api.repository.NewsRepository;


@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;
    private final NewsParser newsParser;

    // Unutma : Constructor Injection 
    public NewsService(NewsRepository newsRepository, NewsParser newsParser) {
        this.newsRepository = newsRepository;
        this.newsParser = newsParser;
    }

    public void saveNews(String newsText) {
        News news = newsParser.parseNews(newsText); // Haber metnini parse et
        newsRepository.save(news); // mongoya kayıt
    }

    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    /*public News saveNewsObject(News news) {
        return newsRepository.save(news);
    }*/
}
