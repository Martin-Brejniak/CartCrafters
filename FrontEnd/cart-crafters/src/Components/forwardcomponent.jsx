import React, { useState, useEffect } from 'react';
import { placeBid, closeAuction, getForwardAuctionDetails } from '../Services/forwardauctionservice';
import { jwtDecode } from 'jwt-decode';

const getUserIdFromToken = () => {
  const token = localStorage.getItem('sessionToken');
  if (!token) return null;

  try {
      console.log("got some token")
      const decodedToken = jwtDecode(token);
      console.log(decodedToken)
      return decodedToken.userId;
    } catch (error) {
      console.error("Error decoding token:", error);
      return null;
    }
};

const ForwardComponent = ({ auctionInfo }) => {
    const [bidAmount, setBidAmount] = useState('');
    const [error, setError] = useState('');

    const [timeRemaining, setTimeRemaining] = useState('');
    const [auctionClosed, setAuctionClosed] = useState(auctionInfo.auctionEnded);

    useEffect(() => {
      const calculateTimeRemaining = () => {
  const endTime = new Date(auctionInfo.endTimeOfAuction);
  const currentTime = new Date();
  const difference = endTime - currentTime;

  if (difference > 0) {
      const hours = Math.floor((difference / (1000 * 60 * 60)) % 24);
      const minutes = Math.floor((difference / 1000 / 60) % 60);
      const seconds = Math.floor((difference / 1000) % 60);
      setTimeRemaining(`${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`);
  } else {
      setTimeRemaining("00:00:00");
      if (!auctionClosed) {
          handleAuctionClosure();
      }
  }
};


        const handleAuctionClosure = async () => {
            try {
                await closeAuction(auctionInfo.auctionId);
                setAuctionClosed(true);
            } catch (error) {
                console.error('Error closing auction:', error);
                setError('Error occurred while closing the auction');
            }
        };

        calculateTimeRemaining();
        const intervalId = setInterval(calculateTimeRemaining, 1000);

        return () => clearInterval(intervalId);
    }, [auctionInfo.endTimeOfAuction, auctionInfo.auctionId, auctionClosed]);

    const handleBid = async () => {
      const userId = getUserIdFromToken();
          if (!userId) {
            setError("User not authenticated.");
            return;
          }

        if (parseInt(bidAmount) <= auctionInfo.highestBid) {
            setError('Bid must be higher than the current highest bid.');
            return;
        }

        try {
            await placeBid(auctionInfo.auctionId, userId, parseInt(bidAmount)); // Replace '1' with the actual user ID
            setBidAmount('');
            // Fetch the latest auction details
          const updatedAuctionInfo = await getForwardAuctionDetails(auctionInfo.auctionId);
          // Update highest bid and bidder user ID
          auctionInfo.highestBid = updatedAuctionInfo.currentPrice;
          auctionInfo.highestBidderUserId = updatedAuctionInfo.highestBidderUserId;
            setError('');
        } catch (e) {
            setError(e.message);
        }
    };

    return (
        <div>
            <h2>{auctionInfo.title}</h2>
            <p>Time Remaining: {timeRemaining}</p>
            <p>Current Highest Bid: ${auctionInfo.highestBid}</p>
            <p>Highest Bidder: {auctionInfo.highestBidderUserId !== 0 ? auctionInfo.highestBidderUserId : 'No current bidder'}</p>
            <input
                type="number"
                value={bidAmount}
                onChange={(e) => setBidAmount(e.target.value)}
                placeholder="Enter your bid"
            />
            <button onClick={handleBid}>BID</button>
            {error && <p style={{ color: 'red' }}>{error}</p>}
        </div>
    );
};

export default ForwardComponent;
