import axios from 'axios';

const BASE_URL = 'http://localhost:8080/user'; // Adjust if port changes @martin

const getAllUsers = () => axios.get(`${BASE_URL}/get-all`);
const getUserByID = (userID) => axios.get(`${BASE_URL}/get-id`, { params: { userID } });
const getUserByUsername = (username) => axios.get(`${BASE_URL}/get-username`, { params: { username } });
const authenticateUser = (username, password) => axios.get(`${BASE_URL}/authenticate`, { params: { username, password } });
const createUser = (user) => axios.post(BASE_URL, user);
const updateUser = (userID, user) => axios.put(BASE_URL, user, { params: { userID } });
const deleteUser = (userID) => axios.delete(BASE_URL, { params: { userID } });

export default {
  getAllUsers,
  getUserByID,
  getUserByUsername,
  authenticateUser,
  createUser,
  updateUser,
  deleteUser,
};
