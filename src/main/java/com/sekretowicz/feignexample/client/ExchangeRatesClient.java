package com.sekretowicz.feignexample.client;

import com.sekretowicz.feignexample.model.ExchangeRatesModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="exchange-rates-client", url="${exchange-rates.url}")
public interface ExchangeRatesClient {
    @GetMapping("/historical/{date}.json")
    public ExchangeRatesModel getExchangeRates (@PathVariable String date, @RequestParam String app_id);
}
