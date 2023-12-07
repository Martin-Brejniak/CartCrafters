// DutchComponent.js
import React, { useState } from 'react';
import { decrementPrice, buyAuction } from '../Services/dutchauctionservice';

const DutchComponent = ({ auctionInfo }) => {
    const [error, setError] = useState('');

    const handleDecrement = async () => {
        try {
            await decrementPrice(auctionInfo.auctionId);
        } catch (e) {
            setError(e.message);
        }
    };

    const handleBuy = async () => {
        try {
            const buy = {
                auctionId: auctionInfo.auctionId,
                userId: 1, // actual user ID
            };
            await buyAuction(buy);
        } catch (e) {
            setError(e.message);
        }
    };

    return (
        <div>
            <h2>{auctionInfo.title}</h2>
            <p>Current Price: ${auctionInfo.currentPrice}</p>
            <button onClick={handleDecrement}>DECREMENT</button>
            <button onClick={handleBuy}>BUY</button>
            {error && <p style={{ color: 'red' }}>{error}</p>}
        </div>
    );
};

export default DutchComponent;
