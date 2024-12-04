package com.example.sourabh.broker.quotes;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.vertx.core.json.JsonObject;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.math.BigDecimal;


@Data
public class QuoteEntity {
  String asset;
  BigDecimal bid;
  BigDecimal ask;
  @JsonProperty("last_price")
  BigDecimal lastPrice;
  BigDecimal volume;

  public JsonObject toJsonObject() {
    return JsonObject.mapFrom(this);
  }
}
