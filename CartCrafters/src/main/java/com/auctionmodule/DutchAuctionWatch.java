package com.offerup.auctionservice.dutchAuctionServices;

import com.offerup.auctionservice.dtos.DutchAuction;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Singleton
@Startup
public class DutchMonitor {

    private DutchAuctionDB dutchAuctionDB;
    private QueryDutchService queryDutchService;
    private UpdateDutchService updateDutchService;

    // No need for @Autowired or @Inject in Jakarta EE

    // No need for @Scheduled, instead use javax.ejb.Schedule
    @Schedule(hour = "*", minute = "*", second = "*/10", persistent = false)
    public void monitorDecrement() {
        try {
            List<DutchAuction> allAuctions = queryDutchService.getAllOpen();
            for (DutchAuction auction : allAuctions) {
                updateDutchService.decrement(auction.getAuctionId().toString());
            }
        } catch (Exception e) {
            System.out.println("Unable to decrement auction");
        }
    }

    @Schedule(hour = "0", minute = "*", second = "0", persistent = false)
    public void monitorClosure() {
        try {
            List<Auction> allAuctions = DutchAuctionSearch.getAllOpenDutchAuctions();
            Date endDate;
            Date timeNow;
            for (DutchAuction auction : allAuctions) {
                timeNow = new Date();

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH:mm");
                String formattedDate = formatter.format(timeNow);

                timeNow = formatter.parse(formattedDate);
                endDate = auction.DateEndTime();
                if (timeNow.getTime() >= endDate.getTime()) {
                    System.out.println(timeNow);
                    System.out.println(endDate);
                    DutchAuctionUpdate.closeAuction(auction.getAuctionId().toString());
                }
            }
        } catch (Exception e) {
            System.out.println("Unable to end auction");
        }
    }
}
