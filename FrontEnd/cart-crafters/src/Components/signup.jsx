import React, { useState } from 'react';
import AccountService from '../Services/accountservice';
import { useNavigate } from 'react-router-dom';

function SignUp() {
  const navigate = useNavigate();
  const [userData, setUserData] = useState({
    fName: '',
    lName: '',
    address: '',
    postal: '',
    city: '',
    country: '',
    province: '',
    username: '',
    password: ''
  });
  const [showWelcome, setShowWelcome] = useState(false);

  const handleChange = (e) => {
    setUserData({ ...userData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await AccountService.createUser(userData);
      setShowWelcome(true);
      setTimeout(() => {
        navigate('/');
      }, 1000);
    } catch (error) {
      console.error('Sign up error', error);
    }
  };

  return (
    <div style={{ textAlign: 'center' }}>
      <h2>Sign Up</h2>
      {showWelcome && <p>Welcome! You will be redirected shortly.</p>}
      {showWelcome && <p>Welcome! You will be redirected shortly.</p>}
      <form onSubmit={handleSubmit}>
        <input type="text" name="fName" value={userData.fName} onChange={handleChange} placeholder="First Name" /><br/>
        <input type="text" name="lName" value={userData.lName} onChange={handleChange} placeholder="Last Name" /><br/>
        <input type="text" name="address" value={userData.address} onChange={handleChange} placeholder="Address" /><br/>
        <input type="text" name="postal" value={userData.postal} onChange={handleChange} placeholder="Postal Code" /><br/>
        <input type="text" name="city" value={userData.city} onChange={handleChange} placeholder="City" /><br/>
        <input type="text" name="country" value={userData.country} onChange={handleChange} placeholder="Country" /><br/>
        <input type="text" name="province" value={userData.province} onChange={handleChange} placeholder="Province" /><br/>
        <input type="text" name="username" value={userData.username} onChange={handleChange} placeholder="Username" /><br/>
        <input type="password" name="password" value={userData.password} onChange={handleChange} placeholder="Password" /><br/>
        <button type="submit">Sign Up</button>
      </form>
    </div>
  );
}

export default SignUp;
