package com.sekretowicz.alfabanktask.controller;

import com.sekretowicz.alfabanktask.service.ExchangeRatesService;
import com.sekretowicz.alfabanktask.service.GifService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
public class MainController {
    //Сервис для запросов курсов валют
    @Autowired
    ExchangeRatesService exchangeRatesService;
    //Сервис для запроса GIF
    @Autowired
    GifService gifService;

    @GetMapping("/rates")
    public String compareRates(@RequestParam String currency, Model model) {
        //Сегодняшний и вчерашний курсы заданной валюты
        long currentTime = System.currentTimeMillis();
        double todayRate = exchangeRatesService.getRates(new Date(currentTime), currency);
        double yesterdayRate = exchangeRatesService.getRates(new Date(currentTime-24*60*60*1000), currency);

        //Тэг, по которому будет искаться подходящая картинка. Если курс не изменился, будет картинка с тегом "nothing"
        String tag = "nothing";

        if (todayRate==0) {
            tag = "error";
        } else if (todayRate>yesterdayRate) {
            tag = "rich";
        } else if (todayRate<yesterdayRate) {
            tag = "broke";
        }

        //Запрашиваем ссылку на GIF
        String gifUrl = gifService.getGif(tag);

        //Кладем оба курса и ссылку на GIF в модель и отображаем при помощи Thymeleaf
        model.addAttribute("currency", currency);
        model.addAttribute("today", todayRate);
        model.addAttribute("yesterday", yesterdayRate);
        model.addAttribute("src", gifUrl);

        return "image";
    }
}
