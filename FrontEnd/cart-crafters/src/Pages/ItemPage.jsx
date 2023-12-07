import React, { useState } from 'react';
import ItemDisplay from '../Components/itemdisplay';

function ItemPage() {
  const [searchTerm, setSearchTerm] = useState('');

  const handleSearchTermChange = (e) => {
    setSearchTerm(e.target.value);
  };

  return (
    <div>
      <h1>Item Auctions</h1>
      <input
        type="text"
        value={searchTerm}
        onChange={handleSearchTermChange}
        placeholder="Search for an item"
      />
      <ItemDisplay searchTerm={searchTerm} />
    </div>
  );
}

export default ItemPage;
