import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import NewsForm from "./components/NewsForm";
import DefaultPage from "./components/DefaultPage";
import Reports from "./components/Reports";
import NavBar from "./components/Navbar";
import "./App.css";

const App = () => {
  return (
    <Router>
      <NavBar /> 
      <div style={{ marginTop: "200px" }}>
        <Routes>
          <Route path="/" element={<DefaultPage />} />
          <Route path="/api/news/add" element={<NewsForm />} />
          <Route path="/api/news/reports" element={<Reports />} />
        </Routes>
      </div>
    </Router>
  );
};

export default App;
