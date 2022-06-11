package com.sekretowicz.feignexample.model;

import java.util.Map;
/*
    Модель JSON'а с курсами валют, который мы получаем с сайта
 */
public class ExchangeRatesModel {
    String disclaimer, license, base;
    long timestamp;
    Map<String, Long> rates;

    public ExchangeRatesModel() {}

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, Long> getRates() {
        return rates;
    }

    public void setRates(Map<String, Long> rates) {
        this.rates = rates;
    }
}
