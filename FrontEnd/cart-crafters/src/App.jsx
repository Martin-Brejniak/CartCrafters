import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import ForwardPage from './Pages/forwardpage';
import ForwardComponent from './Components/forwardcomponent';
import DutchPage from './Pages/dutchpage'; // Import DutchPage
import DutchComponent from './Components/dutchcomponent'; // Import DutchComponent

function App() {
  return (
    <Router>
      <div className="App">
        <Routes>
          <Route path="/forward-auctions" element={<ForwardPage />} />
          <Route path="/auction/:id" element={<ForwardComponent />} />
          <Route path="/dutch-auctions" element={<DutchPage />} /> {/* Add route for Dutch auctions */}
          <Route path="/dutch-auction/:id" element={<DutchComponent />} /> {/* Add route for Dutch auction details */}
        </Routes>
      </div>
    </Router>
  );
}

export default App;
