import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { getAllOpenDutchAuctions, getDutchAuctionDetails } from '../Services/dutchauctionservice';
import DutchComponent from '../Components/dutchcomponent';

const POLL_INTERVAL = 2000;

const DutchPage = () => {
    const { auctionId } = useParams();
    const [auctionInfo, setAuctionInfo] = useState(null);

    useEffect(() => {
        const fetchAuction = async () => {
            try {
                let auctionData;
                if (auctionId) {
                    auctionData = await getDutchAuctionDetails(auctionId);
                } else {
                    auctionData = await getAllOpenDutchAuctions();
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
                <DutchComponent key={auction.auctionId} auctionInfo={auction} />
            ));
        } else if (auctionInfo) {
            return <DutchComponent auctionInfo={auctionInfo} />;
        } else {
            return <p>No auction data available.</p>;
        }
    };

    return (
        <div>
            <h1>{auctionId ? `Auction Details` : `Dutch Auctions`}</h1>
            {renderAuction()}
        </div>
    );
};

export default DutchPage;
