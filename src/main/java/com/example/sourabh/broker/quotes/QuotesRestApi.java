package com.example.sourabh.broker.quotes;

import com.example.sourabh.broker.assets.Asset;
import com.example.sourabh.broker.assets.AssetsRestApi;
import io.vertx.ext.web.Router;
import io.vertx.sqlclient.Pool;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class QuotesRestApi {

  public static void attach(Router parent, Pool db) {
    final HashMap<String, Quote> cachedQuotes = new HashMap<>();
    AssetsRestApi.ASSETS.forEach(symbol-> {
      cachedQuotes.put(symbol, initRandomQuote(symbol));
    });

    parent.get("/quotes/:asset").handler(new GetQuoteHandler(cachedQuotes));
    parent.get("/pg/quotes/:asset").handler(new GetQuoteFromDatabaseHandler(db));

  }

  private static Quote initRandomQuote(String assetParam) {
    return Quote.builder()
      .asset(new Asset(assetParam))
      .ask(randomVale())
      .volume(randomVale())
      .bid(randomVale())
      .lastPrice(randomVale())
      .build();
  }

  private static BigDecimal randomVale() {
    return BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(1,100));
  }

}
