// Pages/dutchpage.jsx
import React from "react";
//
import DutchComponent from "../Components/dutchcomponent";
import { useLocation } from "react-router-dom";
import { useEffect } from "react";
import { useState } from "react";
import Cookies from "js-cookie";

const BASE_URL = "http://localhost:7070/";

function DutchAuctionPage() {
  const location = useLocation();
  const [auctionInformation, setAuctionInformation] = useState(
    location.state.auctionInfo
  );

  const [webSocket, setWebSocket] = useState(null);

  useEffect(() => {
    const url = `ws:${BASE_URL}/dutch/${auctionInformation.auctionId}/update`;
    const socket = new WebSocket(url);

    socket.onopen = function () {
      console.log("WebSocket connection established");
    };

    socket.onmessage = function (event) {
      const updatedAuctionInfo = JSON.parse(event.data);
      setAuctionInformation(updatedAuctionInfo);
      console.log("Received message:", updatedAuctionInfo);
    };

    socket.onerror = function (error) {
      console.error("WebSocket error:", error);
    };

    setWebSocket(socket);

    return () => {
      console.log("Closing WebSocket connection");
      socket.close();
    };
  }, [auctionInformation.auctionId]);

  return (
    <div style={{ overflow: "hidden" }}>
      <div>
       
      </div>

      <div style={{ position: "relative" }}>
    
        <div
          style={{
            position: "absolute",
            top: 2,
            left: 0,
            width: "100%",
            height: "100%",
          }}
        >
          <div style={{ position: "relative" }}>
            <DutchComponent
              auctionInfo={auctionInformation}
              itemInfo={location.state.itemInfo}
            />
          </div>
        </div>
      </div>
    </div>
  );
}

export default DutchAuctionPage;
