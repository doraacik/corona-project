import axios from "axios";
import API_BASE_URL from "../config";

export const fetchNews = async () => {
    const response = await axios.get(`${API_BASE_URL}/news`);
    return response.data;
};

export const addNews = async (newsText) => {
    const response = await axios.post(`${API_BASE_URL}/news`, { text: newsText });
    return response.data;
};
