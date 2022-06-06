package ru.helio.alfatest.giphy;


import com.google.gson.*;
import feign.FeignException;
import feign.Response;
import feign.codec.Decoder;
import org.springframework.context.annotation.Bean;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

class GiphyFeignConfig {


    @Bean
    public Decoder feignStringDecoder() {

        return new DecoderImpl();
    }

    private static class DecoderImpl implements Decoder{

        @Override
        public Object decode(Response response, Type type) throws IOException, FeignException {

            Reader json = response.body().asReader(StandardCharsets.UTF_8);
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(String.class, new GifUrlDeserializer())
                    .create();

            return gson.fromJson(json, String.class);
        }
    }

    private static class GifUrlDeserializer implements JsonDeserializer<String> {

        @Override
        public String deserialize(JsonElement json, Type typeOfT,
                                  JsonDeserializationContext context) throws JsonParseException {

            return json.getAsJsonObject()
                    .getAsJsonArray("data").get(0)
                    .getAsJsonObject()
                    .getAsJsonObject("images")
                    .getAsJsonObject("original")
                    .getAsJsonPrimitive("url").getAsString();
        }

    }


}

