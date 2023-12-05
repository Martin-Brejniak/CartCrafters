// DutchPage.js
import React, { useEffect, useState } from 'react';
import { getAllOpenDutchAuctions } from '../Services/dutchauctionservice';
import DutchComponent from '../Components/dutchcomponent';

const DutchPage = () => {
    const [auctions, setAuctions] = useState([]);

    useEffect(() => {
        const fetchAuctions = async () => {
            const openAuctions = await getAllOpenDutchAuctions();
            setAuctions(openAuctions);
        };

        fetchAuctions();
        // Set up a timer or WebSocket connection to update auctions in real-time
    }, []);

    return (
        <div>
            <h1>Dutch Auctions</h1>
            {auctions.length === 0 ? (
                <p>No open Dutch auctions at the moment.</p>
            ) : (
                auctions.map((auction) => (
                    <DutchComponent key={auction.auctionId} auctionInfo={auction} />
                ))
            )}
        </div>
    );
};

export default DutchPage;
