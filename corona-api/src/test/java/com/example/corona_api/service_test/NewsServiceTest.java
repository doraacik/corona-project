package com.example.corona_api.service_test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        
        News mockNews = new News("20.04.2020", "Ankara", 15, 1, 5);
        when(newsParser.parseNews(newsText)).thenReturn(mockNews);
        
        newsService.saveNews(newsText);
        
        verify(newsRepository, times(1)).save(mockNews); // mock repoya kaydetme işlemini doğrular
    }
    
}
