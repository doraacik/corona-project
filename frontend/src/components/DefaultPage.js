import React from 'react';
import { useNavigate } from 'react-router-dom';
import '../style/css/DefaultPage.css';

const DefaultPage = () => {
    const navigate = useNavigate();

    const goToAddNews = () => {
        navigate('/api/news/add');
    };

    const goToReports = () => {
        navigate('/api/news/reports');
    };

    return (
        <div className="container">
            <h1 className="title">Corona News Portal</h1>
            <div className="button-container">
                <button className="button" onClick={goToAddNews}>
                    Add News
                </button>
                <button className="button" onClick={goToReports}>
                    Reports
                </button>
            </div>
        </div>
    );
};

export default DefaultPage;
