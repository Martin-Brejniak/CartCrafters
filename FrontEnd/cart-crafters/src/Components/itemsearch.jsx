import React, { useState } from 'react';
import ItemService from '../Services/itemservice';

function ItemSearch() {
  const [searchTerm, setSearchTerm] = useState('');
  const [items, setItems] = useState([]);

  const handleSearch = async () => {
    try {
      const response = await ItemService.searchItem(searchTerm);
      setItems(response.data);
    } catch (error) {
      console.error('Search error', error);
    }
  };

  return (
    <div>
      <input
        type="text"
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
      />
      <button onClick={handleSearch}>Search</button>

      {items.length > 0 ? (
        <ul>
          {items.map((item) => (
            <li key={item.itemID}>
              {item.name} - Price: {item.price}
              {/* Display more item details as needed */}
            </li>
          ))}
        </ul>
      ) : (
        <p>No items found</p>
      )}
    </div>
  );
}

export default ItemSearch;
