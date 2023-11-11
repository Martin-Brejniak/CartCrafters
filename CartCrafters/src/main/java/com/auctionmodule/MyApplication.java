package com.auctionmodule;

import org.glassfish.jersey.server.ResourceConfig;

public class MyApplication extends ResourceConfig {
    public MyApplication() {
    	packages("com.auctionmodule");
        register(new MyApplicationBinder());
        register(DutchAuctionController.class);
    }
}