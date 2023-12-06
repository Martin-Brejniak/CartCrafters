import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import ForwardPage from './Pages/forwardpage';
import ForwardComponent from './Components/forwardcomponent';
import DutchPage from './Pages/dutchpage';
import DutchComponent from './Components/dutchcomponent';
import ItemDisplay from './Components/itemdisplay';
import SignUp from './Components/signup';
import ItemSearch from './Components/itemsearch';
import ProtectedRoute from './Components/ProtectedRoute';
import LandingPage from './Pages/landingpage';
import Login from './Components/login';

function App() {
  return (
    <Router>
      <div className="App">
        <Routes>
          <Route path="/" element={<LandingPage />} />
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<SignUp />} />
          <Route path="/items" element={
            <ProtectedRoute>
              <div>
                <ItemDisplay />
                <ItemSearch />
              </div>
            </ProtectedRoute>
          } />
          <Route path="/forward-auctions" element={<ForwardPage />} />
          <Route path="/auction/:id" element={<ForwardComponent />} />
          <Route path="/dutch-auctions" element={<DutchPage />} />
          <Route path="/dutch-auction/:id" element={<DutchComponent />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
