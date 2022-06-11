package com.sekretowicz.feignexample.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sekretowicz.feignexample.client.GifClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GifService {

    @Value("${giphy.api-key}")
    private String API_KEY;

    @Autowired
    private GifClient client;

    /*
    Объект для парсинга JSON. Так как его инициализация довольно дорогостоящая,
    он будет инициализироваться при первом запросе и использоваться в последующих.
     */
    private ObjectMapper objectMapper = null;

    public String getGif(String tag) {
        /*
        Запрашиваем JSON с информацией о картинке через апи giphy.com
         */
        String json = client.getGif(API_KEY, tag);

        //Вытаскиваем из него URL картинки
        String url = "";
        try {
            //Инициализируем ObjectMapper
            if (objectMapper==null) {
                objectMapper = new ObjectMapper();
            }
            JsonNode parent = objectMapper.readTree(json);
            url = parent.at("/data/images/original/url").asText();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return url;
    }
}
