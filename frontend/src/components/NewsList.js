import React, { useState, useEffect } from 'react';
import axios from 'axios';

const NewsList = () => {
  const [news, setNews] = useState([]);  // Haberleri tutan state
  const [loading, setLoading] = useState(true);  // Yükleme durumu
  const [error, setError] = useState(null);  // Hata durumu

  useEffect(() => {
    // API'den haberleri çekme
    axios.get('http://localhost:8081/api/news')
      .then(response => {
        setNews(response.data);  // Yanıtı state'e atama
        setLoading(false);  // Yükleme tamamlandı
      })
      .catch(err => {
        setError('Error fetching news');
        setLoading(false);
      });
  }, []);  // Bu efekt sadece bileşen ilk kez render edildiğinde çalışacak

  if (loading) {
    return <div>Loading...</div>;  // Yükleniyor mesajı
  }

  if (error) {
    return <div>{error}</div>;  // Hata mesajı
  }

  return (
    <div>
      <h2>News List</h2>
      <ul>
        {news.map(newsItem => (
          <li key={newsItem.id}>
            <h3>{newsItem.city} - {newsItem.date}</h3>
            <p>{newsItem.originalNews}</p>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default NewsList;
