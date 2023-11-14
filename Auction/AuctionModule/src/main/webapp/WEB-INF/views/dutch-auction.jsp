<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Dutch Auction</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>

<div id="auction-container">
    <div id="auction-details">
        <p id="item-id">Item Id: <span id="itemIdSpan"></span></p>
        <p id="current-price">Current Price: <span id="currentPriceSpan"></span></p>
    </div>
    <div id="buy-section">
        <button onclick="buyAuction()">Buy Now</button>
    </div>
</div>

<script>

    $(document).ready(function() {
        loadAuctionDetails();

        
        setInterval(loadAuctionDetails, 5000); 
    });

    function loadAuctionDetails() {
        var auctionId = 1; 

        $.get("/auction/dutch/details?auctionId=" + auctionId, function(auction) {
            if (auction) {
                $('#itemIdSpan').text(auction.itemId);
                $('#currentPriceSpan').text(auction.currentPrice);
            } else {
                $('#auction-details').html('<p>Auction details not found.</p>');
            }
        }).fail(function() {
            $('#auction-details').html('<p>Error loading auction details.</p>');
        });
    }

    function buyAuction() {
        var auctionId = 1; 
        var userId = 123; 

        $.ajax({
            url: '/auction/dutch/buy',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ auctionId: auctionId, userId: userId }),
            success: function(response) {
                alert('Auction purchased successfully.');
                loadAuctionDetails();
            },
            error: function(response) {
                alert('Error purchasing auction: ' + response.responseText);
            }
        });
    }
</script>

</body>
</html>
