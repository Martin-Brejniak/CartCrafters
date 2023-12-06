import React from 'react';
import { Link } from 'react-router-dom';
import Login from '../Components/login';

function LandingPage() {
  return (
    <div style={{ textAlign: 'center' }}>
      <h1>Welcome to the Auction Platform</h1>
      <Login />
      <Link to="/signup" style={{ position: 'absolute', top: 20, right: 20 }}>Sign Up</Link>
    </div>
  );
}

export default LandingPage;
