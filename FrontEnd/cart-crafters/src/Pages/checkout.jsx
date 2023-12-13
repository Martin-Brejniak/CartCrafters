import React, { useState, useEffect } from 'react';
import Cookies from 'js-cookie';
import AccountService from '../Services/accountservice';
import { getItemById } from '../Services/itemservice';
import { jwtDecode } from 'jwt-decode';
import { useParams } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';

const Checkout = () => {
    const navigate = useNavigate();
    const { itemId, totalPrice } = useParams(); // Extract both itemId and totalPrice from the URL
    const [paymentDetails, setPaymentDetails] = useState({
      cardNumber: '',
      nameOnCard: '',
      expiryDate: '',
      cvv: '',
      address: '',
      fName: '',
      lName: '',
      postal: '',
      city: '',
      province: '',
      country: '',
      shippingCost: 0,
      expeditedShippingCost: 0,
      itemPrice: 0, // Assuming item price is required
      totalPrice: parseFloat(totalPrice) || 0 // Parse totalPrice from URL
    });

    useEffect(() => {
      const fetchUserDataAndItemDetails = async () => {
        const token = Cookies.get('sessionToken');
        if (token) {
          const decodedToken = jwtDecode(token);
          try {
            // Fetch user data
            const userResponse = await AccountService.getUserByID(decodedToken.userId);
            const userData = userResponse.data[0];
            setPaymentDetails(prevDetails => ({
              ...prevDetails,
              address: userData.address,
              fName: userData.fName,
              lName: userData.lName,
              postal: userData.postal,
              city: userData.city,
              province: userData.province,
              country: userData.country
            }));

            // Fetch item details
            console.log(itemId);
            const itemResponse = await getItemById(itemId);
            console.log(itemResponse);
            const itemData = itemResponse[0];
            console.log(itemData);
            setPaymentDetails(prevDetails => ({
              ...prevDetails,
              shippingCost: itemData.shipcost,
              expeditedShippingCost: itemData.expShipCost,
              itemPrice: itemData.price
            }));
          } catch (error) {
            console.error('Error fetching data:', error);
          }
        }
      };

      fetchUserDataAndItemDetails();
    }, [itemId]);


    const handleChange = (e) => {
      setPaymentDetails({
        ...paymentDetails,
        [e.target.name]: e.target.value
      });
    };

    const handleSubmit = (e) => {
      e.preventDefault();
      const expOrno = paymentDetails.totalPrice - paymentDetails.itemPrice > 0 ? 1 : 0;
      navigate(`/receipt/${itemId}/${expOrno}`);
      console.log('Submitting Payment:', paymentDetails);
      alert('Payment processing is not implemented in this demo.');
    };

    return (
      <div>
        <h2>Payment</h2>
        <form onSubmit={handleSubmit}>
          <p>First Name: {paymentDetails.fName}</p>
          <p>Last Name: {paymentDetails.lName}</p>
          <p>Street: {paymentDetails.address}</p>
          <p>Postal Code: {paymentDetails.postal}</p>
          <p>City:{paymentDetails.city}</p>
          <p>Province:{paymentDetails.province}</p>
          <p>Country:{paymentDetails.country}</p>
          <p>Item Price: ${paymentDetails.itemPrice}</p>
          <p>Total Price: ${paymentDetails.totalPrice}</p> {}
          <p>Credit card number:</p>
          <input name="Credit card number" placeholder="Credit card number" /><br/>
          <p>Name on card:</p>
          <input name="Name on Credit card" placeholder="Name on Credit card" /><br/>
          <p>Expiry Date:</p>
          <input type="date" name="Expiry Date" placeholder="Expiry Date" /><br/>
          <p>Security Code:</p>
          <input name="Security Code" placeholder="Security Code" /><br/>
          {}
          {}
          <button type="submit">Submit Payment</button>
        </form>
      </div>
    );
  };

  export default Checkout;
