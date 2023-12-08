import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { getForwardAuctionDetails } from '../Services/forwardauctionservice';
import { getItemById } from '../Services/itemservice';

const AuctionEndedComponent = () => {
  const { auctionId } = useParams();
  const [item, setItem] = useState(null);
  const [auction, setAuction] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchAuctionAndItemDetails = async () => {
      try {
        // Fetch auction details to get the item ID
        const auctionDetails = await getForwardAuctionDetails(auctionId);
        setAuction(auctionDetails);

        // Now fetch item details
        const itemDetails = await getItemById(auctionDetails.itemId);
        setItem(itemDetails);
      } catch (error) {
        console.error('Error fetching details:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchAuctionAndItemDetails();
  }, [auctionId]);

  if (loading) {
    return <div>Loading...</div>;
  }

  return (
    <div>
      <h1>The Logo of the Company</h1>
      <h2>Bidding Ended!</h2>
      {item && (
        <>
          <p>Item Description: {item.description}</p>
          <p>Shipping Price: ${item.shippingCost}</p>
          <div>
            <input type="checkbox" id="expeditedShipping" name="expeditedShipping" />
            <label htmlFor="expeditedShipping">Expedited Shipping</label>
          </div>
          <p>Winning Price: ${auction.highestBid}</p>
          <p>Highest Bidder: {auction.highestBidderUserId}</p>
          <button>Pay Now</button>
        </>
      )}
    </div>
  );
};

export default AuctionEndedComponent;
