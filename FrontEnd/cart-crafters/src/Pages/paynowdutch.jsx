import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getDutchAuctionDetails } from '../Services/dutchauctionservice'; // Import Dutch auction service
import { getItemById } from '../Services/itemservice';

const PayNowDutch = () => {
  const { auctionId } = useParams();
  const navigate = useNavigate();
  const [item, setItem] = useState(null);
  const [auction, setAuction] = useState(null);
  const [loading, setLoading] = useState(true);
  const [expeditedShipping, setExpeditedShipping] = useState(false);

  const handlePayNowClick = () => {
    const totalPrice = calculateTotal();
    if (item && item.itemID) {
      navigate(`/checkout/${item.itemID}/${totalPrice}`);
    } else {
      console.error("Item ID not found");
    }
  };

  useEffect(() => {
    const fetchAuctionAndItemDetails = async () => {
      try {
        const auctionDetails = await getDutchAuctionDetails(auctionId);
        setAuction(auctionDetails);

        const itemDetails = await getItemById(auctionDetails.itemId);
        setItem(itemDetails[0]); // Assuming the response is an array with one object
      } catch (error) {
        console.error('Error fetching details:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchAuctionAndItemDetails();
  }, [auctionId]);

  const handleExpeditedShippingChange = () => {
    setExpeditedShipping(!expeditedShipping);
  };

  const calculateTotal = () => {
    if (!auction || !item) return 0;
    return item.price + item.shipcost + (expeditedShipping ? item.expShipCost : 0);
  };

  if (loading) {
    return <div>Loading...</div>;
  }

  return (
    <div>
      <h1>The Logo of the Company</h1>
      <h2>Bidding Ended!</h2>
      {item && (
        <>
          <p>Item Name: {item.name}</p>
          <p>Item Description: {item.description}</p>
          <p>Shipping Price: ${item.shipcost}</p>
          <p>Expedited Shipping Price: ${item.expShipCost}</p>
          <div>
            <input
              type="checkbox"
              id="expeditedShipping"
              name="expeditedShipping"
              checked={expeditedShipping}
              onChange={handleExpeditedShippingChange}
            />
            <label htmlFor="expeditedShipping">Expedited Shipping</label>
          </div>
          <p>Item Price: ${item.price}</p>
          <p>Total Price: ${calculateTotal()}</p>
          <p>User ID (Name hidden for Privacy): {item.winner}</p>
          <button onClick={handlePayNowClick}>Pay Now</button>
        </>
      )}
    </div>
  );
};

export default PayNowDutch;
