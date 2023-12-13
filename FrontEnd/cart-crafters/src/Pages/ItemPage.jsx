import React, { useState } from 'react';
import ItemDisplay from '../Components/itemdisplay';
import { useNavigate } from 'react-router-dom';

function ItemPage() {
  const navigate = useNavigate();
  const [searchTerm, setSearchTerm] = useState('');

  const handleSearchTermChange = (e) => {
    setSearchTerm(e.target.value);
  };

  const handleLogout = () => {
    // Clear any authentication data here (like localStorage or cookies)
    localStorage.removeItem('userToken'); // Adjust this to your authentication method

    // Redirect to the homepage
    navigate('/');
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
      <button onClick={handleLogout} style={{ margin: '20px 0' }}>Logout</button>
    </div>
  );
}

export default ItemPage;
