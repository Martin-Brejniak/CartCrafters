<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dutch Auction Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }

        form {
            display: flex;
            flex-direction: column;
            max-width: 300px;
            margin-bottom: 20px;
        }

        label {
            margin-bottom: 5px;
        }

        button {
            padding: 10px;
            background-color: #4caf50;
            color: white;
            border: none;
            cursor: pointer;
        }
    </style>
</head>
<body>
    <h2>Dutch Auction Page</h2>

    <form id="searchForm">
        <label for="auctionId">Auction ID:</label>
        <input type="text" id="auctionId" name="auctionId" required>
        <button type="button" onclick="searchAuction()">Search Auction</button>
    </form>

    <button type="button" onclick="buyAuction()">Buy Now</button>

    <script>
        function searchAuction() {
            var auctionId = document.getElementById('auctionId').value;

            // Use Fetch API to make a GET request to your backend
            fetch('/auction/dutch/details?auctionId=' + auctionId)
                .then(response => response.json())
                .then(data => {
                    // Update the page with auction details
                    alert('Auction Details: ' + JSON.stringify(data));
                })
                .catch(error => {
                    // Handle errors
                    alert('Error: ' + error.message);
                });
        }

        function buyAuction() {
            var auctionId = document.getElementById('auctionId').value;

            // Use Fetch API to make a POST request to your backend
            fetch('/auction/dutch/buy', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ auctionId: auctionId, userId: 1 }), // Replace 1 with the actual user ID
            })
                .then(response => response.json())
                .then(data => {
                    // Update the page with the result or handle errors
                    alert('Buy Result: ' + JSON.stringify(data));
                })
                .catch(error => {
                    // Handle errors
                    alert('Error: ' + error.message);
                });
        }
    </script>
</body>
</html>
