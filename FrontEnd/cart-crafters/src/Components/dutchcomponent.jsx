import React from "react";
import Button from "@mui/material/Button";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import { getPaymentPage } from "../Services/paymentservice";
import { useNavigate } from "react-router-dom";
import { dutchBuy } from "../Services/auctionservice";

// Placeholder components
const AuctionDetails = ({ auctionInfo }) => (
  <div>
    <strong>Auction Details:</strong> {auctionInfo.auctionId}
  </div>
);

const ItemDetails = ({ itemInfo }) => (
  <div>
    <strong>Item Details:</strong> {itemInfo.itemId}
  </div>
);


const DutchComponent = (info) => {
  const navigate = useNavigate();

  const handlePayNow = async (event) => {
    try {
      const result1 = await dutchBuy(info.auctionInfo.auctionId);
      console.log(result1);
      const result = await getPaymentPage(info.auctionInfo.auctionId);
      console.log(result);
      if (result.userInfo.username === result.auctionInfo.soldToUserId) {
        navigate("/payment", {
          state: {
            result,
          },
        });
      } else {
        navigate("/home");
      }
    } catch (e) {}
  };

  return (
    <div>
      <Grid container spacing={2}>
        <Grid item xs={6} sm={6}>
          <AuctionDetails auctionInfo={info.auctionInfo} />
        </Grid>
        <Grid item xs={6} sm={6}>
          <ItemDetails itemInfo={info.itemInfo} />
        </Grid>
      </Grid>
      <div>
        <Box
          display="flex"
          justifyContent="center"
          alignItems="center"
          sx={{ height: "100vh", marginTop: 10 }}
        >
          <Button
            type="submit"
            variant="contained"
            onClick={handlePayNow}
            sx={{
              mt: 3,
              mb: 120,
              fontSize: "24px",
              fontFamily: "poppins",
              fontWeight: "bold",
              padding: "20px 40px",
              height: "60px",
              backgroundColor: "pink",
              color: "#ffffff",
              width: "30%",
            }}
          >
            Pay Now!
          </Button>
        </Box>
      </div>
    </div>
  );
};

export default DutchComponent;
