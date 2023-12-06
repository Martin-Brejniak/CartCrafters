import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import AccountService from '../Services/accountservice';
import Cookies from 'js-cookie';

function Login() {
  const [credentials, setCredentials] = useState({
    username: '',
    password: ''
  });
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setCredentials({ ...credentials, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await AccountService.authenticateUser(credentials.username, credentials.password);
      const token = response.data; 
      if (token) {
        Cookies.set('sessionToken', token, { expires: 1 }); // Store the token in cookies
        navigate('/items');
      } else {
        alert('Invalid credentials');
      }
    } catch (error) {
      console.error('Login error', error);
    }
  };

  return (
    <form onSubmit={handleSubmit} style={{ display: 'inline-block', margin: 'auto' }}>
      <h2>Login</h2>
      <input
        type="text"
        name="username"
        value={credentials.username}
        onChange={handleChange}
        placeholder="Username"
      /><br/>
      <input
        type="password"
        name="password"
        value={credentials.password}
        onChange={handleChange}
        placeholder="Password"
      /><br/>
      <button type="submit">Log In</button>
    </form>
  );
}

export default Login;
