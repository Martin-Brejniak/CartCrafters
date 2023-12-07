import axios from "axios";
import Cookies from "js-cookie";
import jwt_decode from "jwt-decode";

const BASE_URL = "http://localhost:7070/";
const config = {
  headers: {
    "Content-Type": "application/json",
  },
};

const getUserIdFromToken = () => {
  const token = Cookies.get("sessionToken");
  if (!token) return null;

  try {
    const decodedToken = jwt_decode(token);
    return decodedToken.userId; // Adjust according to your token's payload structure
  } catch (error) {
    console.error("Error decoding token:", error);
    return null;
  }
};

export async function forwardBid(bidAmount, auctionId) {
  const userId = getUserIdFromToken();
  if (!userId) throw new Error("User not authenticated.");

  const bid = {
    auctionId: auctionId,
    bidAmount: parseFloat(bidAmount),
    userId: userId,
  };

  try {
    const response = await axios.post(
      ${BASE_URL}auctions/forward/bid,
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
      ${BASE_URL}auctions/dutch/buy?auctionId=${auctionId}&sessionToken=${sessionToken},
      config
    );

    return response.data;
  } catch (error) {
    console.error(error);

    throw error;
  }
}
