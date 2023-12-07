import axios from 'axios';

const AUCTION_BASE_URL = 'http://localhost:7070/auction'; // Adjust if necessary
const ITEM_BASE_URL = 'http://localhost:9090/item'; // Adjust if necessary

// Function to get all open forward auctions
export const getAllOpenForwardAuctions = async () => {
    try {
        const response = await axios.get(`${AUCTION_BASE_URL}/forward/get-all-open`);
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
        const response = await axios.get(`${AUCTION_BASE_URL}/dutch/get-all-open`);
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

// Function to update the price of an item
export const updateItemPrice = async (itemID, newPrice) => {
    try {
        await axios.put(`${ITEM_BASE_URL}/update-price`, null, {
            params: { itemID, newPrice }
        });
    } catch (error) {
        console.error('Error updating item price:', error);
        throw error;
    }
};

// Function to update the winner of an item
export const updateItemWinner = async (itemID, winner) => {
    try {
        await axios.put(`${ITEM_BASE_URL}/update-winner`, null, {
            params: { itemID, winner }
        });
    } catch (error) {
        console.error('Error updating item winner:', error);
        throw error;
    }
};


// Function to get item details by itemID
export const getItemById = async (itemID) => {
    try {
        const response = await axios.get(`${ITEM_BASE_URL}/get-id`, { params: { itemID } });
        // Extracting the required fields from the item
        return response.data.map(item => ({
            name: item.name,
            price: item.price,
            endTime: item.endTime,
            itemID: item.itemID,
            winner: item.winner,
            auctionType: item.auctionType,
            description: item.description,
            shipcost: item.shipcost,
            expShipCost: item.expShipCost
        }));
    } catch (error) {
        console.error('Error fetching item by ID:', error);
        throw error;
    }


};
