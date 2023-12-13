import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getForwardAuctionDetails } from '../Services/forwardauctionservice';
import { getItemById } from '../Services/itemservice';

const AuctionEndedComponent = () => {
  const { auctionId } = useParams();
  const [item, setItem] = useState(null);
  const [auction, setAuction] = useState(null);
  const [loading, setLoading] = useState(true);
  const [expeditedShipping, setExpeditedShipping] = useState(false);
  const navigate = useNavigate();



  useEffect(() => {
    const fetchAuctionAndItemDetails = async () => {
      try {



        // Fetch auction details to get the item ID
        const auctionDetails = await getForwardAuctionDetails(auctionId);
        console.log('Auction Details:', auctionDetails);
        setAuction(auctionDetails);

        // Now fetch item details
        const itemDetails = await getItemById(auctionDetails.itemId);
        console.log('Item Details:', itemDetails[0]);
        setItem(itemDetails[0]);
      } catch (error) {
        console.error('Error fetching details:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchAuctionAndItemDetails();
  }, [auctionId]);

  const handleExpeditedShippingChange = () => {
    setExpeditedShipping(!expeditedShipping);
  };

  const calculateTotal = () => {
    if (!auction || !item) return 0;
    return auction.highestBid + item.shipcost + (expeditedShipping ? item.expShipCost : 0);
  };

  if (loading) {
    return <div>Loading...</div>;
  }

  const totalPrice = calculateTotal(); // Calculate total price


    const handlePayNowClick = () => {
    if (item && item.itemID) { // Ensure item and item.id are available
      navigate(`/checkout/${item.itemID}/${totalPrice}`);
    } else {
      console.error("Item ID not found");
    }
  };

  return (
    <div>
      <h1>The Logo of the Company</h1>
      <h2>Bidding Ended!</h2>
      {item && (
        <>
          <p>Item Name: {item.name}</p>
          <p>Item Description: {item.description}</p>
          <p>Shipping Price: ${item.shipcost}</p>
          <p>Expediated shipping Price: ${item.expShipCost}</p>
          <div>
            <input
              type="checkbox"
              id="expeditedShipping"
              name="expeditedShipping"
              checked={expeditedShipping}
              onChange={handleExpeditedShippingChange}
            />
            <label htmlFor="expeditedShipping">Expedited Shipping</label>
          </div>
          <p>Winning Price: ${auction.highestBid}</p>
          <p>Total Price: ${calculateTotal()}</p>
          <p>Highest Bidder USER ID (Name hidden for Privacy): {auction.highestBidderUserId}</p>
          <button onClick={handlePayNowClick}>Pay Now</button>
        </>
      )}
    </div>
  );
};

export default AuctionEndedComponent;
