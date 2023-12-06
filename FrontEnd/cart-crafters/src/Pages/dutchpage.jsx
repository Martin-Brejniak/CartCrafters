import React, { useEffect, useState } from 'react';
import { getAllOpenDutchAuctions } from '../Services/dutchauctionservice';
import DutchComponent from '../Components/dutchcomponent';

const POLL_INTERVAL = 2000;

const DutchPage = () => {
    const [auctions, setAuctions] = useState([]);

    useEffect(() => {
        const fetchAuctions = async () => {
            try {
                const openAuctions = await getAllOpenDutchAuctions();
                setAuctions(openAuctions);
            } catch (error) {
                console.error('Error fetching auctions:', error);
            }
        };

        fetchAuctions();

        const intervalId = setInterval(fetchAuctions, POLL_INTERVAL);

        return () => clearInterval(intervalId);
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
