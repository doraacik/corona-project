import React, { useState } from 'react';
import axios from 'axios';
import '../style/css/NewsForm.css';

const NewsForm = () => {
  const [newsText, setNewsText] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post('http://localhost:8081/api/news/add', newsText, {
        headers: {
          'Content-Type': 'text/plain',
        },
      });
      console.log('News added:', response.data);
      setNewsText('');  // Clear the input after submit
      alert('News added successfully!');
    } catch (error) {
      console.error('Error adding news:', error);
      alert('There was an error adding the news.');
    }
  };

  return (
    <div className="news-form-container">
    
      <h1 style={{ color: '#b60c0c' }}>Add News</h1>
      <form onSubmit={handleSubmit} className="news-form">
        <textarea
          value={newsText}
          onChange={(e) => setNewsText(e.target.value)}
          required
          placeholder="Enter news details here..."
          className="news-textarea"
        />
        <button type="submit" className="submit-btn">Add News</button>
      </form>
    </div>
  );
};

export default NewsForm;
