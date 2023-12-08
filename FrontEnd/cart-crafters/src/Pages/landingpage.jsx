import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import Login from '../Components/login';

function LandingPage() {
  const [sellerCredentials, setSellerCredentials] = useState({
    username: '',
    password: ''
  });
  const navigate = useNavigate();

  // Hardcoded credentials for seller login
  const SELLER_CREDENTIALS = {
    username: "admin", // Example username
    password: "seller123" // Example password
  };

  const handleSellerCredentialsChange = (e) => {
    const { name, value } = e.target;
    setSellerCredentials({ ...sellerCredentials, [name]: value });
  };

  const handleSellerLogin = () => {
    if (sellerCredentials.username === SELLER_CREDENTIALS.username && sellerCredentials.password === SELLER_CREDENTIALS.password) {
      navigate('/sell-item');
    } else {
      alert('Incorrect Seller Credentials');
    }
  };

  return (
    <div style={{ textAlign: 'center' }}>
      <h1>Welcome to the Auction Platform</h1>
      <Login />
      <Link to="/signup" style={{ position: 'absolute', top: 20, right: 20 }}>Sign Up</Link>

      <div style={{ marginTop: '20px' }}>
        <h2>Seller Login</h2>
        <input
          type="text"
          name="username"
          value={sellerCredentials.username}
          onChange={handleSellerCredentialsChange}
          placeholder="Seller Username"
        /><br/>
        <input
          type="password"
          name="password"
          value={sellerCredentials.password}
          onChange={handleSellerCredentialsChange}
          placeholder="Seller Password"
        /><br/>
        <button onClick={handleSellerLogin}>Sell an Item</button>
      </div>
    </div>
  );
}

export default LandingPage;
