package com.sekretowicz.feignexample.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="gif-client", url="${giphy.url}")
public interface GifClient {
    @GetMapping("/v1/gifs/random")
    public String getGif(@RequestParam String api_key, @RequestParam String tag);
}
