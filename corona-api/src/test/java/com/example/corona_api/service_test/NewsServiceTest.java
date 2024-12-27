package com.example.corona_api.service_test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.corona_api.model.News;
import com.example.corona_api.repository.NewsRepository;
import com.example.corona_api.service.NewsParser;
import com.example.corona_api.service.NewsService;

public class NewsServiceTest {

    @Mock
    private NewsRepository newsRepository;
    
    @Mock
    private NewsParser newsParser;

    private NewsService newsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        newsService = new NewsService(newsRepository, newsParser);
    }

    @Test
    public void testSaveNewsFromText() {
        String newsText = "20.04.2020 tarihinde Ankara da Korona virüs salgınında yapılan testlerde 15 yeni vaka bulundu. 1 kişi korona'dan vefat etti. 5 kişide taburcu oldu.";
        
        News mockNews = new News("20.04.2020", "Ankara", 15, 1, 5, "20.04.2020 tarihinde Ankara da Korona virüs salgınında yapılan testlerde 15 yeni vaka bulundu. 1 kişi korona'dan vefat etti. 5 kişide taburcu oldu.");
        when(newsParser.parseNews(newsText)).thenReturn(mockNews);
        
        newsService.saveNews(newsText);
        
        verify(newsRepository, times(1)).save(mockNews); // mock repoya kaydetme işlemini doğrular
    }

    @Test
    public void testGetNewsByCity() {
        String city = "Ankara";

        News news1 = new News("20.04.2020", "Ankara", 10, 1, 5, "20.04.2020 tarihinde Ankara da Korona virüs salgınında yapılan testlerde 10 yeni vaka bulundu. 1 kişi korona'dan vefat etti. 5 kişide taburcu oldu.");
        News news2 = new News("21.04.2020", "Ankara", 5, 0, 3, "21.04.2020 tarihinde Ankara da Korona virüs salgınında yapılan testlerde 5 yeni vaka bulundu. 0 kişi korona'dan vefat etti. 3 kişide taburcu oldu.");

        List<News> mockNewsList = Arrays.asList(news1, news2);

        when(newsRepository.findByCity(city)).thenReturn(mockNewsList);

        List<News> result = newsService.getNewsByCity(city);

        assertEquals(2, result.size());
        verify(newsRepository, times(1)).findByCity(city);
    }
    
}
