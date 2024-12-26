import React from "react";
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
//import NewsList from './components/NewsList'; // Haberleri göstermek için bileşen
import NewsForm from './components/NewsForm';
import './App.css';
import DefaultPage from './components/DefaultPage';
import Reports from './components/Reports';
//import Reports from './components/Reports';<Route path="/api/news/reports" element={<Reports />} />


const App = () => {
  return (
      <Router>
          <Routes>
              <Route path="/" element={<DefaultPage />} />
              <Route path="/api/news/add" element={<NewsForm />} />
              <Route path="/api/news/reports" element={<Reports />} />
          </Routes>
      </Router>
  );
};

export default App;
