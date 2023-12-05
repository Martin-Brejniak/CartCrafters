// DutchBuyComponent.js

import React from 'react';
import axios from 'axios';

class DutchBuyComponent extends React.Component {
  payNow = () => {
    // Replace 'auctionId' and 'userId' with the actual data
    const buyData = { auctionId: 1, userId: 123 };

    axios.post('http://localhost:8080/auction/dutch/buy', buyData)
      .then(response => {
        // Handle the response, you might want to redirect or show a success message
        console.log(response.data);
      })
      .catch(error => {
        // Handle errors, show error messages or redirect to an error page
        console.error(error);
      });
  };

  render() {
    return (
      <div>
        <h1>Dutch Auction Pay Now</h1>
        <button onClick={this.payNow}>Pay Now</button>
      </div>
    );
  }
}

export default DutchBuyComponent;
