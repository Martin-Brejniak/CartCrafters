import React, { useState } from 'react';
import { createItem, getItemIdByName, createAuction } from '../Services/sellservice';
import { useNavigate } from 'react-router-dom';

const SellerPage = () => {
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        name: '',
        description: '',
        price: '',
        auctionType: 'forward', // Default auction type
        endTime: '2011-12-15T04:44:00.000Z', // Default end time
        minimumPrice: '',
        decrement: '',
        shipcost: '',
        expShipCost: ''
    });

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {

          // Convert endTime to Date object
          let date = new Date(formData.endTime);

          date.setHours(date.getHours());

          // Convert back to ISO string and add 'Z' to denote UTC time
          let formattedEndTime = date.toISOString();
            // Step 1: Create Item
            await createItem({
                name: formData.name,
                description: formData.description,
                price: formData.price,
                auctionType: formData.auctionType,
                endTime: formattedEndTime, // Use the formatted endTime with seconds
                shipcost: formData.shipcost, // Include shipping cost
                expShipCost: formData.expShipCost, // Include expedited shipping cost
            });

            setFormData({
            ...formData,
            endTime: formattedEndTime
        });

            // Step 2: Get Item ID by Name
            const itemId = await getItemIdByName(formData.name);

            // Step 3: Create Auction
            await createAuction({
            ...formData,
            endTime: formattedEndTime // Use the formatted endTime directly
        }, itemId);

            alert('Item and auction created successfully');
            window.location.reload();
        } catch (error) {
            console.error('Error in creating item or auction:', error);
            alert('Failed to create item or auction');
        }
    };

    const handleLogout = () => {
        // Clear any stored authentication data
        localStorage.removeItem('userToken');
        // Or clear cookies if you're using cookies for auth

        // Redirect to homepage
        navigate('/');
    };

    return (
        <div>
            <h2>Sell an Item</h2>
            <form onSubmit={handleSubmit}>
                <input type="text" name="name" value={formData.name} onChange={handleChange} placeholder="Item Name" /><br/>
                <textarea name="description" value={formData.description} onChange={handleChange} placeholder="Description"></textarea><br/>
                <input type="number" name="price" value={formData.price} onChange={handleChange} placeholder="Starting Bid Price" /><br/>
                <input type="number" name="shipcost" value={formData.shipcost} onChange={handleChange} placeholder="Shipping Cost" /><br/>
                <input type="number" name="expShipCost" value={formData.expShipCost} onChange={handleChange} placeholder="Expedited Shipping Cost" /><br/>
                <select name="auctionType" value={formData.auctionType} onChange={handleChange}>
                    <option value="forward">Forward Auction</option>
                    <option value="dutch">Dutch Auction</option>
                </select><br/>
                <input type="datetime-local" name="endTime" value={formData.endTime} onChange={handleChange} placeholder="End Time" /><br/>
                {formData.auctionType === 'dutch' && (
                    <>
                        <input type="number" name="minimumPrice" value={formData.minimumPrice} onChange={handleChange} placeholder="Minimum Price" /><br/>
                        <input type="number" name="decrement" value={formData.decrement} onChange={handleChange} placeholder="Decrement Amount" /><br/>
                    </>
                )}
                <button type="submit">Submit</button>
            </form>
            <button onClick={handleLogout}>Logout</button> {}
        </div>
    );
};

export default SellerPage;
