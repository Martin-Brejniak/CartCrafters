import axios from 'axios';

const BASE_URL = 'http://localhost:9090/item'; // Adjust if necessary

const getAllItems = () => axios.get(`${BASE_URL}/get-all`);
const getItemByID = (itemID) => axios.get(`${BASE_URL}/get-id`, { params: { itemID } });
const searchItem = (term) => axios.get(`${BASE_URL}/search`, { params: { term } });
const createItem = (item) => axios.post(BASE_URL, item);
const updateItem = (itemID, item) => axios.put(`${BASE_URL}/update-all`, item, { params: { itemID } });
const updateItemWinner = (itemID, winner) => axios.put(`${BASE_URL}/update-winner`, null, { params: { itemID, winner } });
const deleteItem = (itemID) => axios.delete(BASE_URL, { params: { itemID } });

const getAllItemsWithAuctionType = () => axios.get(`${BASE_URL}/get-all-with-auction-type`);

export default {
  getAllItems,
  getItemByID,
  searchItem,
  createItem,
  updateItem,
  updateItemWinner,
  deleteItem,
  getAllItemsWithAuctionType,
};