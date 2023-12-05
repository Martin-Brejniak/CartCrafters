import axios from "axios";
import Cookies from "js-cookie";

// Hardcoded base URL
const BASE_URL = "http://localhost:7070/";

const config = {
  headers: {
    "Content-Type": "application/json",
  },
};

export async function forwardBid(bidAmount, auctionId) {
  const sessionToken = Cookies.get("sessionToken");

  const bid = {
    auctionId: auctionId,
    bidAmount: parseFloat(bidAmount),
    userId: sessionToken,
  };

  try {
    const response = await axios.post(
      `${BASE_URL}auctions/forward/bid?sessionToken=${sessionToken}`,
      bid,
      config
    );

    return response.data;
  } catch (error) {
    console.error(error);
    
    throw error; 
  }
}

export async function dutchBuy(auctionId) {
  const sessionToken = Cookies.get("sessionToken");

  try {
    const response = await axios.get(
      `${BASE_URL}auctions/dutch/buy?auctionId=${auctionId}&sessionToken=${sessionToken}`,
      config
    );

    return response.data;
  } catch (error) {
    console.error(error);
    
    throw error; 
  }
}
