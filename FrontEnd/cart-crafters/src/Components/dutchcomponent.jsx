// DutchComponent.js
import React, { useState, useEffect } from 'react';
import { buyAuction } from '../Services/dutchauctionservice';
import { getItemById, updateItemWinner } from '../Services/itemservice'; // Import the service
import { jwtDecode } from 'jwt-decode';
import { useNavigate } from 'react-router-dom';

const getUserIdFromToken = () => {
  const token = localStorage.getItem('sessionToken');
  if (!token) return null;

  try {
    const decodedToken = jwtDecode(token);
    return decodedToken.userId;
  } catch (error) {
    console.error("Error decoding token:", error);
    return null;
  }
};

const DutchComponent = ({ auctionInfo }) => {
    const [error, setError] = useState('');
    const [itemName, setItemName] = useState('');
    const [itemDescription, setItemDescription] = useState('');
    const [shippingCost, setShippingCost] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        const fetchItemDetails = async () => {
            try {
                const itemDetails = await getItemById(auctionInfo.itemId);
                setItemName(itemDetails[0].name);
                setItemDescription(itemDetails[0].description);
                setShippingCost(itemDetails[0].shipcost);
            } catch (error) {
                console.error('Error fetching item details:', error);
            }
        };

        fetchItemDetails();
    }, [auctionInfo.itemId]);

    const handleBuy = async () => {
        const userId = getUserIdFromToken();
        if (!userId) {
            setError("User not authenticated.");
            return;
        }

        if (auctionInfo.auctionEnded) {
            navigate(`/auction-ended-dutch/${auctionInfo.auctionId}`);
            return;
        }

        try {
            await buyAuction({ auctionId: auctionInfo.auctionId, userId });
            await updateItemWinner(auctionInfo.itemId, userId); // Update item with buyer's user ID
            setError('');
            alert('Auction purchased successfully!');
            navigate(`/auction-ended-dutch/${auctionInfo.auctionId}`);
        } catch (e) {
            setError(e.response?.data || 'An error occurred while attempting to buy the auction.');
        }
    };

    return (
        <div>
            <h2>{auctionInfo.title}</h2>
            <p>Item: {itemName}</p>
            <p>Description: {itemDescription}</p>
            <p>Shipping Cost: ${shippingCost}</p>
            <p>Current Price: ${auctionInfo.currentPrice}</p>
            <button onClick={handleBuy}>BUY</button>
            {error && <p style={{ color: 'red' }}>{error}</p>}
        </div>
    );
};

export default DutchComponent;
