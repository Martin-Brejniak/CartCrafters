<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Forward Auctions</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link rel="stylesheet" href="style.css">
</head>
<body>

<div id="auction-container">
    <div id="auction-details">
        <p id="item-id">Item Id: <span id="itemIdSpan"></span></p>
        <p id="current-price">Current Price: <span id="currentPriceSpan"></span></p>
        <p id="highest-bidder">Highest Bidder: <span id="highestBidderSpan"></span></p>
    </div>
    <div id="bid-section">
        <input type="number" id="bid-amount" placeholder="Enter your bid">
        <button onclick="placeBid()">Bid</button>
    </div>
</div>

<script>

    $(document).ready(function() {
        loadAuctionDetails();

        setInterval(loadAuctionDetails, 5000); 
    });

    function loadAuctionDetails() {
        var auctionId = 4;

        $.get("/auction/forward/details?auctionId=" + auctionId, function(auction) {
            $('#itemIdSpan').text(auction.itemId);
            $('#currentPriceSpan').text(auction.currentPrice);
            $('#highestBidderSpan').text(auction.highestBidderUserId);
        });
    }

    function placeBid() {
        var bidAmount = $('#bid-amount').val();
        var auctionId = 4; 
        var userId = 123;

        $.ajax({
            url: '/auction/forward/bid',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ auctionId: auctionId, userId: userId, bidAmount: bidAmount }),
            success: function(auction) {
                alert('Bid placed successfully');
                loadAuctionDetails();
            },
            error: function(response) {
                alert('Error placing bid: ' + response.responseText);
            }
        });
    }
</script>

</body>
</html>
