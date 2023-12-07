import axios from 'axios';

const BASE_URL = 'http://localhost:7070/auction'; // Adjust if necessary

// Function to get all open forward auctions
export const getAllOpenForwardAuctions = async () => {
    try {
        const response = await axios.get(`${BASE_URL}/forward/get-all-open`);
        // Extracting the required fields from each auction
        return response.data.map(auction => ({
            auctionType: auction.auctionType,
            auctionId: auction.auctionId,
            itemId: auction.itemId,
            currentPrice: auction.currentPrice,
            endTimeOfAuction: auction.endTimeOfAuction
        }));
    } catch (error) {
        console.error('Error fetching open forward auctions:', error);
        throw error;
    }
};

// Function to get all open Dutch auctions
export const getAllOpenDutchAuctions = async () => {
    try {
        const response = await axios.get(`${BASE_URL}/dutch/get-all-open`);
        // Extracting the required fields from each auction
        return response.data.map(auction => ({
            auctionType: auction.auctionType,
            auctionId: auction.auctionId,
            itemId: auction.itemId,
            currentPrice: auction.currentPrice,
            endTimeOfAuction: auction.endTimeOfAuction
        }));
    } catch (error) {
        console.error('Error fetching open Dutch auctions:', error);
        throw error;
    }
};
