package com.auctionmodule;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.NoSuchElementException;

import jakarta.servlet.ServletContext;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/auction/dutch")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class DutchAuctionController {

    private DutchAuctionSearch dutchAuctionSearch;
    private DutchAuctionUpdate dutchAuctionUpdate;

    @Inject
    public DutchAuctionController(DutchAuctionSearch dutchAuctionSearch, DutchAuctionUpdate dutchAuctionUpdate) {
        this.dutchAuctionSearch = dutchAuctionSearch;
        this.dutchAuctionUpdate = dutchAuctionUpdate;
    }



    @GET
    @Path("/get-all")
    public List<Auction> getAllDutchAuctions() {
        return dutchAuctionSearch.getAllDutchAuctions();
    }

    @GET
    @Path("/get-all-open")
    public List<Auction> getAllOpenDutchAuctions() {
        return dutchAuctionSearch.getAllOpenDutchAuctions();
    }

    @GET
    @Path("/details")
    public Auction getDutchAuctionDetails(@QueryParam("auctionId") int auctionId) {
        return dutchAuctionSearch.getDutchAuctionDetails(auctionId);
    }

    @GET
    @Path("/user/winner")
    public boolean isUserWinner(@QueryParam("auctionId") int auctionId, @QueryParam("userId") int userId) {
        return dutchAuctionSearch.isUserWinner(auctionId, userId);
    }

    @POST
    @Path("/search/items")
    public List<Auction> searchDutchAuctionsByItems(List<String> itemIds) {
        try {
            return dutchAuctionSearch.searchDutchAuctionsByItems(itemIds);
        } catch (NoSuchElementException e) {
            throw new NotFoundException(e.getMessage());
        }
    }

	/*
	 * @POST
	 * 
	 * @Path("/reset") public String resetDutchAuctions() {
	 * DutchAuctionUpdate.resetDutchAuctions(); return
	 * "Dutch auctions reset successfully"; }
	 */

    @POST
    @Path("/decrement")
    public Auction decrementPrice(@QueryParam("auctionId") int auctionId) {
        return dutchAuctionUpdate.decrementPrice(auctionId);
    }

    @POST
    @Path("/close")
    public Auction closeAuction(@QueryParam("auctionId") int auctionId) {
        return dutchAuctionUpdate.closeAuction(auctionId);
    }

    @POST
    @Path("/buy")
    public Auction buyAuction(DutchBuy buy) {
        try {
            return dutchAuctionUpdate.buyAuction(buy.getAuctionId(), buy.getUserId());
        } catch (AccessDeniedException e) {
            throw new ForbiddenException("Could not buy the auction.");
        }
    }
}
