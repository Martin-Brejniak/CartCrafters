import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getAllOpenForwardAuctions, getForwardAuctionDetails } from '../Services/forwardauctionservice';
import ForwardComponent from '../Components/forwardcomponent';

const POLL_INTERVAL = 1000; // Time in milliseconds

const ForwardPage = () => {
    const navigate = useNavigate();
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

    const handleLogout = () => {
       // Clear any stored authentication data
       localStorage.removeItem('userToken'); // or your specific method of storing authentication data

       // Navigate to the homepage
       navigate('/');
   };

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
            <button onClick={handleLogout} style={{ margin: '20px 0' }}>Logout</button>
        </div>
    );
};

export default ForwardPage;
