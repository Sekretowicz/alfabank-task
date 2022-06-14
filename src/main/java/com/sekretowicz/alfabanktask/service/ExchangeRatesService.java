package com.sekretowicz.alfabanktask.service;

import com.sekretowicz.alfabanktask.client.ExchangeRatesClient;
import com.sekretowicz.alfabanktask.model.ExchangeRatesModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class ExchangeRatesService {

    @Value("${exchange-rates.api-key}")
    private String APP_KEY;

    @Autowired
    ExchangeRatesClient client;

    /*
    Модели с курсами всех валют. Чтобы не посылать каждый раз новый запрос, если, например,
    понадобится несколько раз смотреть курсы по разным валютам, все курсы будут загружаться
    один раз с помощью Feign-клиента, и при последующих запросах курсы валют будут браться оттуда.
     */
    private Map<String, ExchangeRatesModel> ratesMap = new HashMap<>();

    //Получение курсов всех валют на выбранную дату
    public double getRates(Date date, String currency) {

        ExchangeRatesModel rates = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        //Если JSON с курсами уже был сохранен, берем его
        if (ratesMap.containsKey(sdf.format(date))) {
            rates = ratesMap.get(sdf.format(date));
        }
        else {
            rates = client.getExchangeRates(sdf.format(date), APP_KEY);
            ratesMap.put(sdf.format(date), rates);
        }

        if (rates.getRates().containsKey(currency)) {
            return rates.getRates().get(currency);
        } else {
            //Если введен неверный код валюты, вернется 0, который потом обработается контроллером
            return 0;
        }
    }
}
