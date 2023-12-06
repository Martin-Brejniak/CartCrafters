import React, { useState } from 'react';
import { placeBid } from '../Services/forwardauctionservice';

const ForwardComponent = ({ auctionInfo }) => {
    const [bidAmount, setBidAmount] = useState('');
    const [error, setError] = useState('');

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
