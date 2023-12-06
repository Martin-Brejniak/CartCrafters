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
      }, 3000);
    } catch (error) {
      console.error('Sign up error', error);
    }
  };

  return (
    <div style={{ textAlign: 'center' }}>
      <h2>Sign Up</h2>
      {showWelcome && <p>Welcome! You will be redirected shortly.</p>}
      <form onSubmit={handleSubmit}>
        {/* Form fields as before */}
        <button type="submit">Sign Up</button>
      </form>
    </div>
  );
}

export default SignUp;
