import React, { useState, useEffect } from "react";
import TextField from "@mui/material/TextField";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import moment from "moment";

export const AuctionDetails = ({ auctionInfo }) => {
  const [timeLeft, setTimeLeft] = useState(getTimeLeft());

  useEffect(() => {
    const timer = setInterval(() => {
      setTimeLeft(getTimeLeft());
    }, 1000);

    return () => clearInterval(timer);
  }, [auctionInfo]);

  function getTimeLeft() {
    const now = new Date().getTime();
    const endTime = moment(auctionInfo.endTimeOfAuction, "YYYY-MM-DD-HH:mm").valueOf();
    const timeLeft = endTime - now;

    if (timeLeft < 0) {
      return "Auction has ended!";
    }

    const days = Math.floor(timeLeft / (1000 * 60 * 60 * 24));
    const hours = Math.floor((timeLeft % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
    const minutes = Math.floor((timeLeft % (1000 * 60 * 60)) / (1000 * 60));
    const seconds = Math.floor((timeLeft % (1000 * 60)) / 1000);

    return `Time Left: ${days}d ${hours}h ${minutes}m ${seconds}s`;
  }

  return (
    <Box
      component="form"
      style={{
        backgroundColor: "#f0edea",
        fontFamily: "poppins",
        fontSize: "28px",
        height: "450px",
        width: "700px",
        padding: "40px",
        marginTop: "10px",
        marginLeft: "15px",
        borderRadius: "10px",
      }}
      noValidate
      autoComplete="off"
    >
      <div>
        <h1 style={{ marginLeft: "5px", fontFamily: "poppins", fontWeight: "600", fontSize: "54px", color: "#61dafb" }}>
          Auction Details
        </h1>
      </div>
      <div>
        <Grid container spacing={4}>
          <Grid item xs={5.6}>
            <TextField
              style={{ width: "300px" }}
              id="filled-read-only-input"
              label="Auction ID"
              variant="filled"
              value={auctionInfo.auctionInfo.auctionId}
              InputProps={{ readOnly: true }}
            />
          </Grid>
          <Grid item xs={6}>
            <TextField
              style={{ width: "300px" }}
              id="filled-read-only-input"
              label="Auction Type"
              variant="filled"
              value={auctionInfo.auctionInfo.auctionType}
              InputProps={{ readOnly: true }}
            />
          </Grid>
        </Grid>
      </div>
      <div>
        <Grid container spacing={4}>
          <Grid item xs={5.6}>
            <TextField
              style={{ width: "300px" }}
              id="filled-read-only-input"
              label="Current Price"
              variant="filled"
              value={`$${auctionInfo.auctionInfo.currentPrice}`}
              InputProps={{ readOnly: true }}
            />
          </Grid>
          <Grid item xs={6}>
            <TextField
              style={{ width: "300px" }}
              id="filled-read-only-input"
              label="Current Highest Bidder"
              variant="filled"
              value={auctionInfo.auctionInfo.highestBidderUserId}
              InputProps={{ readOnly: true }}
            />
          </Grid>
        </Grid>
      </div>
      <div>
        <Grid container spacing={4}>
          <Grid item xs={5.6}>
            <TextField
              style={{ width: "300px" }}
              id="filled-read-only-input"
              label="Auction Start Time"
              variant="filled"
              value={auctionInfo.auctionInfo.startTimeOfAuction}
              InputProps={{ readOnly: true }}
            />
          </Grid>
          <Grid item xs={6}>
            <TextField
              style={{ width: "300px" }}
              id="filled-read-only-input"
              label="Auction End Time"
              variant="filled"
              value={auctionInfo.auctionInfo.endTimeOfAuction}
              InputProps={{ readOnly: true }}
            />
          </Grid>
        </Grid>
        <div
          id="countdown"
          style={{ fontSize: "28px", textAlign: "center", marginTop: "20px" }}
        >
          {timeLeft}
        </div>
      </div>
    </Box>
  );
};

export const ItemDetails = ({ itemInfo }) => {
  return (
    <Box
      component="form"
      style={{
        backgroundColor: "#f0edea",
        fontFamily: "poppins",
        fontSize: "28px",
        height: "450px",
        width: "700px",
        padding: "40px",
        marginTop: "10px",
        marginLeft: "5px",
        borderRadius: "10px",
      }}
      noValidate
      autoComplete="off"
    >
      <div>
        <h1 style={{ marginLeft: "5px", fontFamily: "poppins", fontWeight: "600", fontSize: "54px", color: "#61dafb" }}>
          Item Details
        </h1>
      </div>
      <div>
        <Grid container spacing={0}>
          <Grid item xs={5}>
            <TextField
              style={{ width: "250px" }}
              id="filled-read-only-input"
              label="Item ID"
              variant="filled"
              value={itemInfo.itemInfo.id}
              InputProps={{ readOnly: true }}
            />
          </Grid>
          <Grid item xs={6}>
            <TextField
              style={{ width: "350px" }}
              id="filled-read-only-input"
              label="Item Name"
              variant="filled"
              value={itemInfo.itemInfo.itemName}
              InputProps={{ readOnly: true }}
            />
          </Grid>
        </Grid>
      </div>
      <div>
        <Grid container spacing={4}>
          <Grid item xs={6}>
            <TextField
              style={{ width: "610px" }}
              multiline
              rows={6}
              id="filled-read-only-input"
              label="Item Description"
              variant="filled"
              value={itemInfo.itemInfo.description}
              InputProps={{ readOnly: true }}
            />
          </Grid>
        </Grid>
      </div>
    </Box>
  );
};
