import React, { useState, useEffect } from 'react';
import { placeBid, closeAuction } from '../Services/forwardauctionservice';

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
        if (parseInt(bidAmount) <= auctionInfo.highestBid) {
            setError('Bid must be higher than the current highest bid.');
            return;
        }

        try {
            await placeBid(auctionInfo.auctionId, 1, parseInt(bidAmount)); // Replace '1' with the actual user ID
            setBidAmount('');
            // After bidding, you might want to fetch the latest auction details
            // to update the highest bid and bidder user ID
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
