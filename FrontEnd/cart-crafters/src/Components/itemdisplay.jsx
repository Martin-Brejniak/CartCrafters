import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import ItemService from '../Services/itemservice';

function ItemDisplay() {
  const [items, setItems] = useState([]);
  const [selectedItemId, setSelectedItemId] = useState(null);
  const navigate = useNavigate();  // Updated to use useNavigate

  useEffect(() => {
    ItemService.getAllItemsWithAuctionType()
      .then(response => setItems(response.data))
      .catch(error => console.error('Fetching items failed', error));
  }, []);

  const handleItemSelect = (itemId) => {
    setSelectedItemId(itemId);
  };

  const navigateToAuction = (itemId) => {
    const selectedItem = items.find(item => item.itemID === itemId);
    if (!selectedItem) {
      console.error('Selected item not found');
      return;
    }

    const path = selectedItem.auctionType === 'dutch' 
      ? `/dutch-auction/${itemId}` 
      : `/forward-auction/${itemId}`;

    navigate(path);  // Updated to use navigate
  };

  const handleBidClick = () => {
    if (selectedItemId !== null) {
      navigateToAuction(selectedItemId);
    } else {
      console.error('No item selected');
    }
  };

  return (
    <div>
      <h2>Items for Auction</h2>
      {items.map(item => (
        <div key={item.itemID}>
          <input
            type="radio"
            name="itemSelect"
            value={item.itemID}
            onChange={() => handleItemSelect(item.itemID)}
            checked={selectedItemId === item.itemID}
          />
          <span>{item.name} - {item.auctionType} Auction</span>
        </div>
      ))}
      <button onClick={handleBidClick} disabled={selectedItemId === null}>Bid</button>
    </div>
  );
}

export default ItemDisplay;
