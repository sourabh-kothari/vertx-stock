package com.example.sourabh.broker.watchlist;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

import java.util.HashMap;
import java.util.UUID;

public class PutWatchListHandler implements Handler<RoutingContext> {
  private final HashMap<UUID, WatchList> watchListPerAccount;

  public PutWatchListHandler(HashMap<UUID, WatchList> watchListPerAccount) {
    this.watchListPerAccount = watchListPerAccount;
  }

  @Override
  public void handle(RoutingContext context) {
      var accountId = WatchListRestApi.getAccountId(context);
      var json = context.getBodyAsJson();
      var watchlist = json.mapTo(WatchList.class);
      watchListPerAccount.put(UUID.fromString(accountId), watchlist);
      context.response().end(json.toBuffer());
    }
  }
