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
import ItemPage from './Pages/ItemPage';
import AuctionEndedComponent from  './Pages/paynow';
import SellerPage from './Pages/sellerpage';
import PayNowDutch from './Pages/paynowdutch';
import Checkout from './Pages/checkout';
import Receipt from './Pages/receipt';

function App() {
  return (
    <Router>
      <div className="App">
        <Routes>
          <Route path="/" element={<LandingPage />} />
          <Route path="/login" element={<LandingPage />} />
          <Route path="/signup" element={<SignUp />} />
          <Route path="/items" element={
            <ProtectedRoute>
              <ItemPage /> {}
            </ProtectedRoute>
          } />
          <Route path="/forward-auctions" element={<ProtectedRoute> <ForwardPage /> {}
        </ProtectedRoute>} />
          <Route path="/forward-auctions/:auctionId" element={<ProtectedRoute> <ForwardPage /> {}
        </ProtectedRoute>} />
          <Route path="/dutch-auctions" element={<ProtectedRoute> <DutchPage /> {}
        </ProtectedRoute>} />
          <Route path="/dutch-auctions/:auctionId" element={<ProtectedRoute> <DutchPage /> {}
        </ProtectedRoute>} />
          <Route path="/auction-ended/:auctionId" element={<ProtectedRoute> <AuctionEndedComponent /> {}
        </ProtectedRoute>} />
          <Route path="/sell-item" element={<ProtectedRoute> <SellerPage /> {}
        </ProtectedRoute>} />
          <Route path="/auction-ended-dutch/:auctionId" element={<ProtectedRoute> <PayNowDutch /> {}
        </ProtectedRoute>} />
        <Route path="/checkout/:itemId/:totalPrice" element={<ProtectedRoute> <Checkout /> {}
      </ProtectedRoute>} />
      <Route path="/receipt/:itemId/:expediteFlag" element={<Receipt />} />
      </Routes>
      </div>
    </Router>
  );
}

export default App;
