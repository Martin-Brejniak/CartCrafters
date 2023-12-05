// dutchauctionservice.js
import axios from 'axios';

const BASE_URL = 'http://localhost:7070/auction/dutch';

const config = {
    headers: {
        'Content-Type': 'application/json'
    }
};

export const getAllDutchAuctions = async () => {
    try {
        const response = await axios.get(`${BASE_URL}/get-all`, config);
        return response.data;
    } catch (error) {
        console.error('Error fetching all Dutch auctions:', error);
        throw error;
    }
};

export const getAllOpenDutchAuctions = async () => {
    try {
        const response = await axios.get(`${BASE_URL}/get-all-open`, config);
        return response.data;
    } catch (error) {
        console.error('Error fetching all open Dutch auctions:', error);
        throw error;
    }
};

export const getDutchAuctionDetails = async (auctionId) => {
    try {
        const response = await axios.get(`${BASE_URL}/details?auctionId=${auctionId}`, config);
        return response.data;
    } catch (error) {
        console.error('Error fetching Dutch auction details:', error);
        throw error;
    }
};

export const isUserWinner = async (auctionId, userId) => {
    try {
        const response = await axios.get(`${BASE_URL}/user/winner?auctionId=${auctionId}&userId=${userId}`, config);
        return response.data;
    } catch (error) {
        console.error('Error checking if user is winner:', error);
        throw error;
    }
};

export const decrementPrice = async (auctionId) => {
    try {
        const response = await axios.post(`${BASE_URL}/decrement?auctionId=${auctionId}`, {}, config);
        return response.data;
    } catch (error) {
        console.error('Error decrementing price:', error);
        throw error;
    }
};

export const buyAuction = async (buy) => {
    try {
        const response = await axios.post(`${BASE_URL}/buy`, buy, config);
        return response.data;
    } catch (error) {
        console.error('Error buying auction:', error);
        throw error;
    }
};
