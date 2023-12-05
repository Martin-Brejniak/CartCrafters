import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import DutchAuctionPage from './Pages/dutchpage';

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/dutch" element={<DutchAuctionPage />} />
        {/* Other routes */}
      </Routes>
    </Router>
  );
};

export default App;
