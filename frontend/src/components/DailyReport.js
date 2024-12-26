import React, { useEffect, useState } from 'react';
import { LineChart, Line, CartesianGrid, XAxis, YAxis, Tooltip, Legend } from 'recharts';

const DailyReports = () => {
    const [allDailyData, setAllDailyData] = useState([]);
    const [dailyData, setDailyData] = useState([]);
    const [cities, setCities] = useState([]);
    const [selectedCity, setSelectedCity] = useState("All Cities");


    useEffect(() => {
        console.log('daily fetchleme başladı')
        fetch('/api/news/reports/daily')
            .then(response => response.json())
            .then(data => {
                console.log('Received daily data:', data); 
                const formattedData = Object.entries(data).map(([date, values]) => ({
                    date: Object.keys(data),
                    ...values,
                }));
                console.log(formattedData);
                setAllDailyData(formattedData);
                //setDailyData(formattedData);
            })
            .catch(error => console.error('Error fetching daily reports:', error));
    }, []);

    useEffect(() => {
        fetch('/api/news/reports/cities')
            .then(response => response.json())
            .then(data => {
                setCities(["All Cities", ...data]);
                //setSelectedCity(data[0]); 
            })
            .catch(error => console.error('Error fetching cities:', error));
    }, []);

    useEffect(() => {
        if (selectedCity === "All Cities") {
            setDailyData(allDailyData);
            return;
          }
        fetch(`/api/news/city/${selectedCity}`)
            .then(response => response.json())
            .then(data => {
                console.log('Received data:', JSON.stringify(data));
           
            const groupedData = data.reduce((acc, item) => {
                if (acc[item.date]) {
                    acc[item.date].cases += item.cases;
                    acc[item.date].deaths += item.deaths;
                    acc[item.date].recoveries += item.recoveries;
                } else {
                    acc[item.date] = {
                        date: item.date,
                        cases: item.cases,
                        deaths: item.deaths,
                        recoveries: item.recoveries
                    };
                }
                return acc;
            }, {});
            const formattedData = Object.values(groupedData);
            
                console.log('formatted Data:', JSON.stringify(formattedData));
                setDailyData(formattedData);
            })
            .catch(error => console.error('Error fetching daily reports:', error));
    }, [selectedCity, allDailyData]);


    return (
        
        <div>
            <h2 style={{ color: '#fff' }}>Daily Reports</h2>
            
            <div style={{ marginBottom: '20px' }}>
                <label htmlFor="city-select" style={{ color: '#fff', marginRight: '10px' }}>Select a City:</label>
                <select
                    id="city-select"
                    value={selectedCity}
                    onChange={(e) => setSelectedCity(e.target.value)}
                    style={{
                        padding: '5px',
                        borderRadius: '5px',
                        fontSize: '1rem',
                    }}
                >
                    {cities.map((city, index) => (
                        <option key={index} value={city}>
                            {city}
                        </option>
                    ))}
                </select>
            </div>
            {selectedCity === "None" ? (
    <p>No city selected.</p>
) : (
            <LineChart width={800} height={400} data={dailyData}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="date" />
                <YAxis />
                <Tooltip />
                <Legend />
                <Line type="monotone" dataKey="cases" stroke="#8884d8" name="Cases" />
                <Line type="monotone" dataKey="deaths" stroke="#ff0000" name="Deaths" />
                <Line type="monotone" dataKey="recoveries" stroke="#82ca9d" name="Recoveries" />
            </LineChart>
             )}
        </div>
    );
};

export default DailyReports;
