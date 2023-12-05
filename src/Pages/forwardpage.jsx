import React, { useEffect, useState } from 'react';
import { getAllOpenForwardAuctions } from '../Services/forwardauctionservice';
import ForwardComponent from '../Components/forwardcomponent';

const ForwardPage = () => {
    const [auctions, setAuctions] = useState([]);

    useEffect(() => {
        const fetchAuctions = async () => {
            const openAuctions = await getAllOpenForwardAuctions();
            setAuctions(openAuctions);
        };

        fetchAuctions();
        // Set up a timer or WebSocket connection to update auctions in real-time
    }, []);

    return (
        <div>
            <h1>Forward Auctions</h1>
            {auctions.length === 0 ? (
                <p>No open auctions at the moment.</p>
            ) : (
                auctions.map((auction) => (
                    <ForwardComponent key={auction.auctionId} auctionInfo={auction} />
                ))
            )}
        </div>
    );
};

export default ForwardPage;
