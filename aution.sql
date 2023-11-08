CREATE TABLE bids (
    bidId INT PRIMARY KEY,
    auctionId INT,
    userId INT,
    bidAmount DOUBLE
);

CREATE TABLE auctions (
    auctionId INT PRIMARY KEY,
    itemId INT,
    auctionType VARCHAR(255),
    initialPrice DOUBLE,
    currentPrice DOUBLE,
    startTimeOfAuction TIMESTAMP,
    endTimeOfAuction TIMESTAMP,
    auctionEnded BOOLEAN,
    soldToUserId INT
);
