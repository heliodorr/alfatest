package ru.helio.alfatest.giphy;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        configuration = GiphyFeignConfig.class,
        name = "giphyClient",
        url = "${giphy.url}"
)
interface GiphyFeignClient{

    @GetMapping
    String getGifUrl(
            @RequestParam(name = "api_key") String token,
            @RequestParam(name = "q") String query,
            @RequestParam(name = "limit") int limit,
            @RequestParam(name = "offset") int offset
    );


}

