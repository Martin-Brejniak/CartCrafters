package com.usermodule;

/**
 * The class stores all information about a user.
 */
public class User {
	private String fName;
	private String lName;
	private String address;
	private String postal;
	private String city;
	private String country;
	private String province;
	private String username;
	private String password;
	private int userID;
	
    /**
    * Get the first name of user.
    *
    * @return         the user's first name
    */
	public String getfName() {
		return fName;
	}
	
    /**
    * Set the first name of user.
    *
    * @param  fName   the user's first name.
    */
	public void setfName(String fName) {
		this.fName = fName;
	}
	
    /**
    * Get the last name of user.
    *
    * @return         the user's last name
    */
	public String getlName() {
		return lName;
	}
	
    /**
    * Set the last name of user.
    *
    * @param  lName   the user's last name.
    */
	public void setlName(String lName) {
		this.lName = lName;
	}
	
    /**
    * Get the address of user.
    *
    * @return         the user's address
    */
	public String getAddress() {
		return address;
	}
	
    /**
    * Set the address of user.
    *
    * @param  address   the user's address.
    */
	public void setAddress(String address) {
		this.address = address;
	}
	
    /**
    * Get the postal code of user.
    *
    * @return         the user's postal code
    */
	public String getPostal() {
		return postal;
	}
	
    /**
    * Set the postal code of user.
    *
    * @param  postal   the user's postal code.
    */
	public void setPostal(String postal) {
		this.postal = postal;
	}
	
    /**
    * Get the city of user.
    *
    * @return         the user's city
    */
	public String getCity() {
		return city;
	}
	
    /**
    * Set the city of user.
    *
    * @param  city   the user's city.
    */
	public void setCity(String city) {
		this.city = city;
	}
	
    /**
    * Get the country of user.
    *
    * @return         the user's country
    */
	public String getCountry() {
		return country;
	}
	
    /**
    * Set the country of user.
    *
    * @param  country   the user's country.
    */
	public void setCountry(String country) {
		this.country = country;
	}
	
    /**
    * Get the province of user.
    *
    * @return         the user's province
    */
	public String getProvince() {
		return province;
	}
	
    /**
    * Set the province of user.
    *
    * @param  province   the user's province.
    */
	public void setProvince(String province) {
		this.province = province;
	}
	
    /**
    * Get the username of user.
    *
    * @return         the user's username
    */
	public String getUsername() {
		return username;
	}
	
    /**
    * Set the username of user.
    *
    * @param  username   the user's username.
    */
	public void setUsername(String username) {
		this.username = username;
	}
	
    /**
    * Get the password of user.
    *
    * @return         the user's password
    */
	public String getPassword() {
		return password;
	}
	
    /**
    * Set the password of user.
    *
    * @param  password   the user's password.
    */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	* Get the ID of user.
	*
	* @return         the user's ID
	*/
	public int getUserID() {
		return userID;
	}
	
	/**
	* Set the ID of user.
	*
	* @param  userID   the user's ID.
	*/
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
}
