package com.example.corona_api.repository;

import com.example.corona_api.model.*;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends MongoRepository<News, String> {
    List<News> findByCity(String city);
}
