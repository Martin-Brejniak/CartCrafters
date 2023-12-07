import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { getAllOpenForwardAuctions, getForwardAuctionDetails } from '../Services/forwardauctionservice';
import ForwardComponent from '../Components/forwardcomponent';

const POLL_INTERVAL = 1000; // Time in milliseconds

const ForwardPage = () => {
    const { auctionId } = useParams(); // Get auctionId from URL params
    const [auctionInfo, setAuctionInfo] = useState(null);

    useEffect(() => {
        const fetchAuction = async () => {
            try {
                let auctionData;
                if (auctionId) {
                    // Fetch details for a single auction if auctionId is present
                    auctionData = await getForwardAuctionDetails(auctionId);
                } else {
                    // Fetch all open auctions otherwise
                    auctionData = await getAllOpenForwardAuctions();
                }
                setAuctionInfo(auctionData);
            } catch (error) {
                console.error('Error fetching auction:', error);
            }
        };

        fetchAuction();
        const intervalId = setInterval(fetchAuction, POLL_INTERVAL);
        return () => clearInterval(intervalId);
    }, [auctionId]);

    const renderAuction = () => {
        if (Array.isArray(auctionInfo)) {
            return auctionInfo.map((auction) => (
                <ForwardComponent key={auction.auctionId} auctionInfo={auction} />
            ));
        } else if (auctionInfo) {
            return <ForwardComponent auctionInfo={auctionInfo} />;
        } else {
            return <p>No auction data available.</p>;
        }
    };

    return (
        <div>
            <h1>{auctionId ? `Auction Details` : `Forward Auctions`}</h1>
            {renderAuction()}
        </div>
    );
};

export default ForwardPage;
