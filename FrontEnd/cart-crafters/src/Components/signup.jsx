import React, { useState } from 'react';
import AccountService from '../Services/accountservice';

function SignUp() {
  const [userData, setUserData] = useState({
    username: '',
    password: '',
    firstName: '',
    lastName: '',
    address: ''
  });

  const handleChange = (e) => {
    setUserData({ ...userData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await AccountService.createUser(userData);
      // Handle post sign-up logic
    } catch (error) {
      console.error('Sign up error', error);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      {/* Form fields for each userData property */}
      <button type="submit">Sign Up</button>
    </form>
  );
}

export default SignUp;
