import React, { useState } from 'react';
import { createItem, getItemIdByName, createAuction } from '../Services/sellservice';

const SellerPage = () => {
    const [formData, setFormData] = useState({
        name: '',
        description: '',
        price: '',
        auctionType: 'forward', // Default auction type
        endTime: '',
        minimumPrice: '',
        decrement: '',
    });

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            // Step 1: Create Item
            await createItem({
                name: formData.name,
                description: formData.description,
                price: formData.price,
                auctionType: formData.auctionType,
                endTime: formData.endTime,
            });

            // Step 2: Get Item ID by Name
            const itemId = await getItemIdByName(formData.name);

            // Step 3: Create Auction
            await createAuction(formData, itemId);

            alert('Item and auction created successfully');
            // Reset form or navigate to another page
        } catch (error) {
            console.error('Error in creating item or auction:', error);
            alert('Failed to create item or auction');
        }
    };

    return (
        <div>
            <h2>Sell an Item</h2>
            <form onSubmit={handleSubmit}>
                <input type="text" name="name" value={formData.name} onChange={handleChange} placeholder="Item Name" /><br/>
                <textarea name="description" value={formData.description} onChange={handleChange} placeholder="Description"></textarea><br/>
                <input type="number" name="price" value={formData.price} onChange={handleChange} placeholder="Starting Bid Price" /><br/>
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
        </div>
    );
};

export default SellerPage;
