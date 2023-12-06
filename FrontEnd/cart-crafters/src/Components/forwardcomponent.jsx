import React, { useState, useEffect } from 'react';
import { placeBid, getForwardAuctionDetails } from '../Services/forwardauctionservice';

const ForwardComponent = ({ auctionInfo }) => {
    const [bidAmount, setBidAmount] = useState('');
    const [highestBidAmount, setHighestBidAmount] = useState(0);
    const [highestBidderUserId, setHighestBidderUserId] = useState(0);
    const [error, setError] = useState('');

    useEffect(() => {
        const fetchAuctionDetails = async () => {
            try {
                const details = await getForwardAuctionDetails(auctionInfo.auctionId);
                setHighestBidAmount(details.currentPrice); // Assuming this is the highest bid
                setHighestBidderUserId(details.highestBidderUserId);
            } catch (error) {
                console.error('Error fetching auction details:', error);
                setError('Could not fetch auction details');
            }
        };

        fetchAuctionDetails();
    }, [auctionInfo.auctionId]);

    const handleBid = async () => {
        if (parseInt(bidAmount) <= highestBidAmount) {
            setError('Bid must be higher than the current highest bid.');
            return;
        }

        try {
            await placeBid(auctionInfo.auctionId, 1, parseInt(bidAmount));
            setBidAmount('');
            setHighestBidAmount(parseInt(bidAmount)); // Update highest bid
            setHighestBidderUserId(1); // Update highest bidder user ID
            setError('');
        } catch (e) {
            setError(e.message);
        }
    };

    return (
        <div>
            <h2>{auctionInfo.title}</h2>
            <p>Current Highest Bid: ${highestBidAmount}</p>
            <p>Highest Bidder: {highestBidderUserId !== 0 ? highestBidderUserId : 'No current bidder'}</p>
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
