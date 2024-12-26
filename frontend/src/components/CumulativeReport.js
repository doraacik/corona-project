import React, { useEffect, useState } from 'react';
import { AreaChart, Area, CartesianGrid, XAxis, YAxis, Tooltip, Legend } from 'recharts';

const CumulativeReports = () => {
    const [allCumulativeData, setAllCumulativeData] = useState([]);
    const [cumulativeData, setCumulativeData] = useState([]);
    const [cities, setCities] = useState([]); // Şehir listesi
    const [selectedCity, setSelectedCity] = useState("All Cities");


    useEffect(() => {
        fetch('/api/news/reports/cumulative')
            .then(response => response.json())
            .then(data => {
                const formattedData = Object.entries(data).map(([date, values]) => {
                    return {
                        date: date,
                        cases: values.cases,
                        deaths: values.deaths,
                        recoveries: values.recoveries
                    };
                });
                console.log('Formatted data from cumulative:', formattedData);
                setAllCumulativeData(formattedData);
            })
            .catch(error => console.error('Error fetching cumulative reports:', error));
    }, []);
    
    useEffect(() => {
        fetch('/api/news/reports/cities')
            .then(response => response.json())
            .then(data => {
                setCities(data);
                setCities(["All Cities", ...data]);
            })
            .catch(error => console.error('Error fetching cities:', error));
    }, []);


   useEffect(() => {
    if (selectedCity === "All Cities") {
        setCumulativeData(allCumulativeData); //datayı tekrar tekrar çekince renderlamıyo?
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
        
            console.log('Formatted Data:', JSON.stringify(formattedData));
            setCumulativeData(formattedData);
        })
        .catch(error => console.error('Error fetching daily reports:', error));
}, [selectedCity,allCumulativeData]);


    return (
        <div>
            <h2 style={{ color: '#fff' }}>Cumulative Reports</h2>
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
            <AreaChart width={800} height={400} data={cumulativeData}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="date" />
                <YAxis />
                <Tooltip />
                <Legend />
                <Area type="monotone" dataKey="cases" stackId="1" stroke="#8884d8" fill="#8884d8" name="Cases" />
                <Area type="monotone" dataKey="deaths" stackId="1" stroke="#ff0000" fill="#ff0000" name="Deaths" />
                <Area type="monotone" dataKey="recoveries" stackId="1" stroke="#82ca9d" fill="#82ca9d" name="Recoveries" />
            </AreaChart>
             )}
        </div>
    );
};

export default CumulativeReports;
