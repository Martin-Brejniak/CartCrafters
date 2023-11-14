-- Create the auctions table
CREATE TABLE auctions (
    auctionId INT PRIMARY KEY,
    itemId INT,
    auctionType VARCHAR(255),
    initialPrice DOUBLE,
    currentPrice DOUBLE,
    startTimeOfAuction TIMESTAMP,
    endTimeOfAuction TIMESTAMP,
    auctionEnded BOOLEAN,
    soldToUserId INT,
    highestBid DOUBLE,
    minimumPrice DOUBLE,
    decrement INT,
    highestBidderUserId INT
);
