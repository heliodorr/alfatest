package ru.helio.alfatest.exchange;

import com.google.gson.*;
import feign.FeignException;
import feign.Response;
import feign.codec.Decoder;
import org.springframework.context.annotation.Bean;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

class ExchangeFeignConfig {

    @Bean
    public Decoder feignDoubleDecoder() {

        return new DecoderImpl();
    }

    private static class DecoderImpl implements Decoder{

        @Override
        public Object decode(Response response, Type type) throws IOException, FeignException {

            Reader json = response.body().asReader(StandardCharsets.UTF_8);
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Double.class, new RatioDeserializer())
                    .create();

            return gson.fromJson(json, Double.class);
        }
    }

    private static class RatioDeserializer implements JsonDeserializer<Double> {


        @Override
        public Double deserialize(JsonElement json, Type typeOfT,
                                  JsonDeserializationContext context) throws JsonParseException {

            return json
                    .getAsJsonObject()
                    .getAsJsonObject("rates").entrySet().iterator().next().getValue()
                    .getAsJsonPrimitive()
                    .getAsDouble();


        }
    }

}

