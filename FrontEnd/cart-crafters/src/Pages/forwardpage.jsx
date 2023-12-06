import React, { useEffect, useState } from 'react';
import { getAllOpenForwardAuctions } from '../Services/forwardauctionservice';
import ForwardComponent from '../Components/forwardcomponent';

const POLL_INTERVAL = 1000; // Time in milliseconds (e.g., 2000 for 2 seconds)

const ForwardPage = () => {
    const [auctions, setAuctions] = useState([]);

    useEffect(() => {
        const fetchAuctions = async () => {
            try {
                const openAuctions = await getAllOpenForwardAuctions();
                setAuctions(openAuctions);
            } catch (error) {
                console.error('Error fetching auctions:', error);
            }
        };

        fetchAuctions();

        const intervalId = setInterval(fetchAuctions, POLL_INTERVAL);

        return () => clearInterval(intervalId); // Cleanup on unmount
    }, []);

    return (
        <div>
            <h1>Forward Auctions</h1>
            {auctions.length === 0 ? (
                <p>No open forward auctions at the moment.</p>
            ) : (
                auctions.map((auction) => (
                    <ForwardComponent key={auction.auctionId} auctionInfo={auction} />
                ))
            )}
        </div>
    );
};

export default ForwardPage;
