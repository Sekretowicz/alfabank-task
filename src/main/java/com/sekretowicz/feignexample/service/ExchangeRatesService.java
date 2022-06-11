package com.sekretowicz.feignexample.service;

import com.sekretowicz.feignexample.client.ExchangeRatesClient;
import com.sekretowicz.feignexample.model.ExchangeRatesModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

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
    private ExchangeRatesModel todayRates = null;
    private ExchangeRatesModel yesterdayRates = null;

    //Получение курсов всех валют на выбранную дату
    private ExchangeRatesModel getAllRatesByDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return client.getExchangeRates(sdf.format(date), APP_KEY);
    }

    //Получение сегодняшнего курса
    public float getTodayRates(String currency) {
        if (todayRates==null) {
            todayRates = getAllRatesByDate(new Date(System.currentTimeMillis()));
        }

        return todayRates.getRates().get(currency);
    }

    //Получение вчерашнего курса
    public float getYesterdayRates(String currency) {
        if (yesterdayRates==null) {
            yesterdayRates = getAllRatesByDate(new Date(System.currentTimeMillis()-24*60*60*1000*7));
        }

        return yesterdayRates.getRates().get(currency);
    }
}
