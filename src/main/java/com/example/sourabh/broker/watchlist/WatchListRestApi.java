package com.example.sourabh.broker.watchlist;

import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.sqlclient.Pool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.UUID;

public class WatchListRestApi {
  private static final Logger LOG = LoggerFactory.getLogger(WatchListRestApi.class);

  public static void attach(Router parent, Pool db) {
    final HashMap<UUID, WatchList> watchListPerAccount = new HashMap<UUID, WatchList>();
    String path = "/account/watchlist/:accountId";
    parent.get(path).handler(new GetWatchListHandler(watchListPerAccount));

    parent.put(path).handler(new PutWatchListHandler(watchListPerAccount));

    parent.delete(path).handler(new DeleteWatchListHandler(watchListPerAccount));

    String pgPath = "/pg/account/watchlist/:accountId";
    parent.get(pgPath).handler(new GetWatchListFromDatabaseHandler(db));
    parent.put(pgPath).handler(new PutWatchListDatabaseHandler(db));
    parent.delete(pgPath).handler(new DeleteWatchListDatabaseHandler(db));

  }

  static String getAccountId(RoutingContext context) {
    var accountId = context.pathParam("accountId");
    LOG.debug("{} for account {}", context.normalizedPath(), accountId);
    return accountId;
  }
}
