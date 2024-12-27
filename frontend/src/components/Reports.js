import React, { useState } from 'react';
import DailyReports from './DailyReport';
import CumulativeReports from './CumulativeReport';


const Reports = () => {
    const [selectedView, setSelectedView] = useState("daily");

    return (
        <div style={{ textAlign: 'center' }}>
            <h1 style={{ color: '#fff' }}>COVID-19 Reports</h1>
            <div style={{ marginBottom: '50px' }}>
                <button
                    onClick={() => setSelectedView("daily")}
                    style={{
                        padding: '10px 20px',
                        border: 'none',
                        borderRadius: '5px',
                        marginRight: '10px',
                        backgroundColor: selectedView === "daily" ? "#a70000" : "#ddd",
                        color: selectedView === "daily" ? "#fff" : "#000",
                    }}
                >
                    Daily Reports
                </button>
                <button
                    onClick={() => setSelectedView("cumulative")}
                    style={{
                        padding: '10px 20px',
                        border: 'none',
                        borderRadius: '5px',
                        backgroundColor: selectedView === "cumulative" ? "#a70000" : "#ddd",
                        color: selectedView === "cumulative" ? "#fff" : "#000",
                    }}
                >
                    Cumulative Reports
                </button>
            </div>

            {selectedView === "daily" ? <DailyReports /> : <CumulativeReports />}
        </div>
    );
};

export default Reports;
