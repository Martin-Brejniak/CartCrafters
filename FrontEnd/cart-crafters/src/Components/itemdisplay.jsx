import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { getAllOpenForwardAuctions, getAllOpenDutchAuctions } from '../Services/itemservice';

const POLL_INTERVAL = 1000;

function ItemDisplay({ searchTerm }) {
  const [auctions, setAuctions] = useState([]);
  const [selectedAuctionId, setSelectedAuctionId] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchAuctions = async () => {
      try {
        let fetchedAuctions = [];
        if (!searchTerm) {
          const forwardAuctions = await getAllOpenForwardAuctions();
          const dutchAuctions = await getAllOpenDutchAuctions();
          fetchedAuctions = [...forwardAuctions, ...dutchAuctions];
        } else {
          // Add search logic here
        }
        setAuctions(fetchedAuctions);
      } catch (error) {
        console.error('Fetching auctions failed', error);
      }
    };

    fetchAuctions();
    // Set up the polling interval
    const intervalId = setInterval(fetchAuctions, POLL_INTERVAL);

    // Cleanup function to clear the interval when the component unmounts
    return () => clearInterval(intervalId);
  }, [searchTerm]);

  const calculateTimeRemaining = (endTime) => {
    const end = new Date(endTime);
    const now = new Date();
    const difference = end - now;

    let timeRemaining = '';
    if (difference > 0) {
      const days = Math.floor(difference / (1000 * 60 * 60 * 24));
      const hours = Math.floor((difference / (1000 * 60 * 60)) % 24);
      const minutes = Math.floor((difference / 1000 / 60) % 60);
      const seconds = Math.floor((difference / 1000) % 60);

      timeRemaining = `${days}d, ${hours}h, ${minutes}m, ${seconds}s`;
    } else {
      timeRemaining = "Auction ended";
    }
    return timeRemaining;
  };

  const handleAuctionSelect = (auctionId) => {
    setSelectedAuctionId(auctionId);
  };

  const navigateToAuction = (auctionId) => {
    const selectedAuction = auctions.find(auction => auction.auctionId === auctionId);
    if (!selectedAuction) {
      console.error('Selected auction not found');
      return;
    }

    const path = selectedAuction.auctionType.toLowerCase() === 'dutch'
      ? `/dutch-auctions/${auctionId}`
      : `/forward-auctions/${auctionId}`;

    navigate(path);
  };

  const handleBidClick = () => {
    if (selectedAuctionId !== null) {
      navigateToAuction(selectedAuctionId);
    } else {
      console.error('No auction selected');
    }
  };

  return (
    <div>
      <h2>Open Auctions</h2>
      {auctions.map(auction => (
        <div key={auction.auctionId}>
          <input
            type="radio"
            name="auctionSelect"
            value={auction.auctionId}
            onChange={() => handleAuctionSelect(auction.auctionId)}
            checked={selectedAuctionId === auction.auctionId}
          />
          <span>{auction.itemId} - {auction.auctionType} Auction - Current Price: ${auction.currentPrice} - Time Remaining: {calculateTimeRemaining(auction.endTimeOfAuction)}</span>
        </div>
      ))}
      <button onClick={handleBidClick} disabled={selectedAuctionId === null}>Go to Auction</button>
    </div>
  );
}

export default ItemDisplay;
