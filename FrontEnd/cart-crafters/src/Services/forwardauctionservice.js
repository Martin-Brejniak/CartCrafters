import axios from 'axios';

const BASE_URL = 'http://localhost:7070/auction/forward';
const USER_BASE_URL = 'http://localhost:8080/user'; 

const config = {
    headers: {
        'Content-Type': 'application/json'
    }
};

export const getAllForwardAuctions = async () => {
    try {
        const response = await axios.get(`${BASE_URL}/get-all`, config);
        return response.data;
    } catch (error) {
        console.error('Error fetching all forward auctions:', error);
        throw error;
    }
};

export const getAllOpenForwardAuctions = async () => {
    try {
        const response = await axios.get(`${BASE_URL}/get-all-open`, config);
        return response.data;
    } catch (error) {
        console.error('Error fetching all open forward auctions:', error);
        throw error;
    }
};

export const getForwardAuctionDetails = async (auctionId) => {
    try {
        const response = await axios.get(`${BASE_URL}/details?auctionId=${auctionId}`, config);
        return response.data;
    } catch (error) {
        console.error('Error fetching forward auction details:', error);
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

export const placeBid = async (auctionId, userId, bidAmount) => {
    try {
        const bid = {
            auctionId,
            userId,
            bidAmount
        };
        const response = await axios.post(`${BASE_URL}/bid`, bid, config);
        return response.data;
    } catch (error) {
        console.error('Error placing bid:', error);
        throw error;
    }
};

export const closeAuction = async (auctionId) => {
    try {
        const response = await axios.post(`${BASE_URL}/close?auctionId=${auctionId}`, {}, config);
        return response.data;
    } catch (error) {
        console.error('Error closing auction:', error);
        throw error;
    }
};


// Function to get user details by userID
export const getUserDetails = async (userId) => {
    try {
        const response = await axios.get(`${USER_BASE_URL}/get-id`, {
            params: { userID: userId },
            headers: {
                'Content-Type': 'application/json'
            }
        });
        return response.data;
    } catch (error) {
        console.error('Error fetching user details:', error);
        throw error;
    }
};


export const highestBid = async (auctionId) => {
    try {
        const response = await axios.get(`${BASE_URL}/details?auctionId=${auctionId}`);
        return response.data.currentPrice;
    } catch (error) {
        console.error('Error fetching highest bid for auction:', error);
        throw error;
    }
}
