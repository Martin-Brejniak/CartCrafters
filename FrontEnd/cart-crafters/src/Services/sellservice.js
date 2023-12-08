// auctionService.js
import axios from 'axios';

const ITEM_API_URL = 'http://localhost:9090/item'; // Replace with your actual ItemServer URL
const AUCTION_API_URL = 'http://localhost:7070/auction'; // Replace with your actual AuctionServer URL

// Function to create an item
export const createItem = async (itemData) => {
  try {
    const response = await axios.post(`${ITEM_API_URL}/create`), {
      name: itemData.name,
      description: itemData.description,
      price: itemData.price,
      type: itemData.type,
      endTime: itemData.endTime,
      auctionType: itemData.auctionType
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
    const response = await axios.get(${ITEM_API_URL}/get-by-name, { params: { name: itemName } });
    if (response.data && response.data.length > 0) {
        return response.data[0].itemID; // Assuming the first item is the one we want
    }
    throw new Error('Item not found');
  } catch (error) {
    console.error('Error fetching item by name:', error);
    throw error;
  }
};

// Function to create an auction
export const createAuction = async (auctionData, itemId) => {
  try {
    // Determine URL based on auction type
    const auctionURL = ${AUCTION_API_URL}/${auctionData.auctionType.toLowerCase()}/create;
    const response = await axios.post(auctionURL, {
      itemId,
      initialPrice: auctionData.initialPrice,
      endTimeOfAuction: auctionData.endTimeOfAuction, // Ensure you calculate and format this correctly based on your application's requirements
      // Add other auction-specific fields if necessary
    });
    return response.data; // Return the created auction
  } catch (error) {
    console.error('Error creating the auction:', error);
    throw error;
  }
};
