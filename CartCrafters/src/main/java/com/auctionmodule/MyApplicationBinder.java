package com.auctionmodule;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class MyApplicationBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(DutchAuctionSearch.class).to(DutchAuctionSearch.class);
        bind(DutchAuctionUpdate.class).to(DutchAuctionUpdate.class);
        bind(DutchAuctionUpdate.class).to(DutchAuctionController.class);
        bind(DutchAuctionSearch.class).to(DutchAuctionController.class);
        
    }
}
