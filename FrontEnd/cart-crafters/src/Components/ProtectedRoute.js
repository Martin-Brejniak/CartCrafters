import React from 'react';
import { Navigate } from 'react-router-dom';
import Cookies from 'js-cookie';

const ProtectedRoute = ({ children }) => {
  //const isAuthenticated = Cookies.get('sessionToken') !== undefined;

  //return isAuthenticated ? children : <Navigate to="/login" replace />;

  return children; 
};

export default ProtectedRoute;
