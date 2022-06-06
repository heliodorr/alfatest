package ru.helio.alfatest.exchange;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        value = "exchangeFeignClient",
        configuration = ExchangeFeignConfig.class,
        url = "${exchange.url}"
)
interface ExchangeFeignClient {

    @GetMapping("/{date}.json")
    Double getRatio(
            @PathVariable String date,
            @RequestParam("app_id") String token,
            @RequestParam("base") String base,
            @RequestParam("symbols") String target
    );


}

