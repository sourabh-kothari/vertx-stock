package com.example.sourabh.broker.assets;


import com.example.sourabh.broker.AbstractRestApi;
import com.example.sourabh.broker.MainVerticle;
import com.example.sourabh.broker.config.ConfigLoader;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpHeaders;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(VertxExtension.class)
public class TestAssestsRestApi extends AbstractRestApi {
  private static final Logger LOG = LoggerFactory.getLogger(TestAssestsRestApi.class);


  @Test
  void returns_all_assets(Vertx vertx, VertxTestContext testContext) throws Throwable {
    var client = WebClient.create(vertx, new WebClientOptions().setDefaultPort(TEST_SERVER_PORT));
    client.get("/assets")
      .send()
      .onComplete(testContext.succeeding(response -> {
        var json= response.bodyAsJsonArray();
        LOG.info("Response: {}", json);
        assertEquals("[{\"name\":\"AAPL\"},{\"name\":\"AMZN\"},{\"name\":\"FB\"},{\"name\":\"GOOG\"},{\"name\":\"NFLX\"},{\"name\":\"TSLA\"}]", json.encode());
        assertEquals(200, response.statusCode());
        assertEquals(HttpHeaderValues.APPLICATION_JSON.toString(),
          response.getHeader(HttpHeaders.CONTENT_TYPE.toString()));
        assertEquals("my-value", response.getHeader("my-header"));
        testContext.completeNow();
      }));
  }
}
