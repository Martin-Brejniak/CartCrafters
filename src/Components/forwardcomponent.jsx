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
            await placeBid(auctionInfo.auctionId, 1, parseInt(bidAmount));
        } catch (e) {
            setError(e.message);
        }
    };

    return (
        <div>
            <h2>{auctionInfo.title}</h2>
            <p>Current Highest Bid: ${auctionInfo.highestBid}</p>
            <p>Time Remaining: {}</p>
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
