package com.example.sourabh.broker.quotes;

import com.example.sourabh.broker.db.DbResponse;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Optional;

public class GetQuoteHandler implements Handler<RoutingContext> {
  private static final Logger LOG = LoggerFactory.getLogger(GetQuoteHandler.class);
  final HashMap<String, Quote> cachedQuotes;

  public GetQuoteHandler(HashMap<String, Quote> cachedQuotes) {
    this.cachedQuotes = cachedQuotes;
  }


  @Override
  public void handle(RoutingContext context) {
      final String assetParam = context.pathParam("asset");
      LOG.debug("Asset Param {}", assetParam);
      var maybeQuote = Optional.ofNullable(cachedQuotes.get(assetParam));
      if(maybeQuote.isEmpty()) {
        DbResponse.notFound(context,"Failed to get quote for asset " + assetParam + " from db!");
        return;
      }
      final JsonObject response = maybeQuote.get().toJsonObject();
      LOG.info("Path {} responds with {}", context.normalizedPath(), response.encode());
      context.response().end(response.toBuffer());
    }
  }
