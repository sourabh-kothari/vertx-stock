package com.example.sourabh.broker;

import com.example.sourabh.broker.config.ConfigLoader;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VersionInfoVerticle extends AbstractVerticle {
  private static final Logger LOG = LoggerFactory.getLogger(VersionInfoVerticle.class);

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    ConfigLoader.load(vertx)
      .onFailure(startPromise::fail)
      .onSuccess(configuration -> {
        LOG.info("Current Application version is:{}", configuration.getVersion());
        startPromise.complete();
      });
  }
}
