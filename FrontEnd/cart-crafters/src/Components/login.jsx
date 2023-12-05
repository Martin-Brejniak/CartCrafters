import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import AccountService from '../Services/accountservice'; // Adjust the import path as necessary

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
      const sessionToken = response.data.sessionToken; // Assuming the token is returned in this format
      if (sessionToken) {
        Cookies.set('sessionToken', sessionToken, { expires: 1 }); // Set cookie with expiry of 1 day
        navigate('/items'); // Redirect to the items page
      } else {
        alert('Invalid credentials');
      }
    } catch (error) {
      console.error('Login error', error);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <input
        type="text"
        name="username"
        value={credentials.username}
        onChange={handleChange}
        placeholder="Username"
      />
      <input
        type="password"
        name="password"
        value={credentials.password}
        onChange={handleChange}
        placeholder="Password"
      />
      <button type="submit">Log In</button>
    </form>
  );
}

export default Login;
