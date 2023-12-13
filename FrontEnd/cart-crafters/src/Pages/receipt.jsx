import React, { useState, useEffect } from 'react';
import Cookies from 'js-cookie';
import AccountService from '../Services/accountservice';
import { getItemById } from '../Services/itemservice';
import { jwtDecode } from 'jwt-decode';
import { useParams } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';

const Receipt = () => {
    const { itemId, expediteFlag } = useParams();
    const navigate = useNavigate();
    const [userDetails, setUserDetails] = useState({
        fName: '',
        lName: '',
        address: '',
        postal: '',
        city: '',
        province: '',
        country: '',
        itemPrice: 0
    });

    useEffect(() => {
        const fetchData = async () => {
            const token = Cookies.get('sessionToken');
            if (token) {
                const decodedToken = jwtDecode(token);
                try {
                    // Fetch user data
                    const userResponse = await AccountService.getUserByID(decodedToken.userId);
                    const userData = userResponse.data[0];

                    // Fetch item details
                    const itemResponse = await getItemById(itemId);
                    const itemData = itemResponse[0];

                    setUserDetails({
                        fName: userData.fName,
                        lName: userData.lName,
                        address: userData.address,
                        postal: userData.postal,
                        city: userData.city,
                        province: userData.province,
                        country: userData.country,
                        itemPrice: itemData.price,
                        shippingCost: itemData.shipcost,
                        expShipCost: itemData.expShipCost
                    });
                } catch (error) {
                    console.error('Error fetching data:', error);
                }
            }
        };

        fetchData();
    }, [itemId]);
    const totalPaid = userDetails.itemPrice + userDetails.shippingCost + (expediteFlag === '1' ? userDetails.expShipCost : 0);

    const handleGoToAuctions = () => {
        navigate('/items');
    };

    const handleLogout = () => {
        Cookies.remove('sessionToken'); // Or your logout logic
        navigate('/');
    };

    return (
        <div>
            <h2>Receipt</h2>
            <p>First Name: {userDetails.fName}</p>
            <p>Last Name: {userDetails.lName}</p>
            <p>Street: {userDetails.address}</p>
            <p>Postal Code: {userDetails.postal}</p>
            <p>City: {userDetails.city}</p>
            <p>Province: {userDetails.province}</p>
            <p>Country: {userDetails.country}</p>
            <p>Item Price: ${userDetails.itemPrice}</p>
            <p>Shipping Cost: ${userDetails.shippingCost}</p>
           {expediteFlag === '1' && <p>Expedited Shipping Cost: ${userDetails.expShipCost}</p>}
            <p>Total Paid: ${totalPaid}</p>
            <p>Item ID: {itemId}</p>
            <p>{expediteFlag === '1' ? 'Express Shipment: The Item will be shipped in 3 days.' : 'Normal Shipment: The Item will be shipped in 7 days.'}</p>
            <button onClick={handleGoToAuctions}>Go to Auctions</button>
            <button onClick={handleLogout}>Logout</button>
        </div>
    );
};

export default Receipt;
