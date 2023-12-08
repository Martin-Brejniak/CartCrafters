//auctionService.js
import axios from 'axios';

const ITEM_API_URL = 'http://localhost:9090/item'; // Replace with your actual ItemServer URL
const AUCTION_API_URL = 'http://localhost:7070/auction'; // Replace with your actual AuctionServer URL

// Function to create an item
export const createItem = async (itemData) => {
    try {
        const response = await axios.post(`${ITEM_API_URL}/create`, {
            name: itemData.name,
            description: itemData.description,
            price: itemData.price,
            auctionType: itemData.auctionType, // Changed from 'type' to 'auctionType'
            endTime: itemData.endTime, // Add the 'endTime' field
            // Add other necessary fields if needed
        });
        return response.data; // Return the created item
    } catch (error) {
        console.error('Error creating the item:', error);
        throw error;
    }
};



// Function to get an item ID by name
export const getItemIdByName = async (itemName) => {
    try {
        // Enclose the itemName in double quotes
        const formattedItemName = `"${itemName}"`;
        const response = await axios.get(`${ITEM_API_URL}/get-by-name`, { params: { name: formattedItemName } });
        if (response.data && response.data.length > 0) {
            return response.data[0].itemID;
        }
        throw new Error('Item not found');
    } catch (error) {
        console.error('Error fetching item by name:', error);
        throw error;
    }
};


//Function to create an auction
// Function to create an auction
export const createAuction = async (auctionData, itemId) => {
    try {
        // Determine URL based on auction type
        const auctionURL = `${AUCTION_API_URL}/${auctionData.auctionType.toLowerCase()}/create`;

        // Preparing the data to send
        const dataToSend = {
            itemId: itemId,
            initialPrice: auctionData.price,
            endTimeOfAuction: auctionData.endTime,
            // Add other fields that your backend expects
        };

        // Making the POST request
        const response = await axios.post(auctionURL, dataToSend);
        return response.data; // Return the created auction
    } catch (error) {
        console.error('Error creating the auction:', error);
        throw error;
    }
};

