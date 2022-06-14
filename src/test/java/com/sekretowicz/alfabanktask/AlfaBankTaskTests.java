package com.sekretowicz.alfabanktask;

import com.sekretowicz.alfabanktask.client.ExchangeRatesClient;
import com.sekretowicz.alfabanktask.model.ExchangeRatesModel;
import com.sekretowicz.alfabanktask.service.ExchangeRatesService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan("sekretowicz.com")
class AlfaBankTaskTests {
	@MockBean
	private ExchangeRatesClient exchangeRatesClient;

	@Autowired
	private ExchangeRatesService exchangeRatesService;

	@Test
	void testExchangeRates() {
		/*
		Создадим тестовые модели с сегодняшним и вчерашним курсами, которые будет возвращать заглушка.
		Положим туда три валюты - одна выросла, другая упала, третья не изменилась.
		 */
		ExchangeRatesModel todayRates = new ExchangeRatesModel();
		todayRates.setRates(new HashMap<>());
		todayRates.getRates().put("CURR1", 100.0);
		todayRates.getRates().put("CURR2", 100.0);
		todayRates.getRates().put("CURR3", 100.0);

		ExchangeRatesModel yesterdayRates = new ExchangeRatesModel();
		yesterdayRates.setRates(new HashMap<>());
		yesterdayRates.getRates().put("CURR1", 90.0);
		yesterdayRates.getRates().put("CURR2", 110.0);
		yesterdayRates.getRates().put("CURR3", 100.0);

		//Определим поведение заглушки
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long currentTime = System.currentTimeMillis();
		Date todayDate = new Date(currentTime);
		Date yesterdayDate = new Date(currentTime-24*60*60*1000);
		String todayFormat = sdf.format(todayDate);
		String yesterdayFormat = sdf.format(yesterdayDate);

		Mockito.when(exchangeRatesClient.getExchangeRates(Mockito.eq(todayFormat), Mockito.anyString()))
				.thenReturn(todayRates);
		Mockito.when(exchangeRatesClient.getExchangeRates(Mockito.eq(yesterdayFormat), Mockito.anyString()))
				.thenReturn(yesterdayRates);

		Assert.assertTrue(
				exchangeRatesService.getRates(todayDate, "CURR1")>
						exchangeRatesService.getRates(yesterdayDate, "CURR1"));
		Assert.assertTrue(
				exchangeRatesService.getRates(todayDate, "CURR2")<
						exchangeRatesService.getRates(yesterdayDate, "CURR2"));
		Assert.assertTrue(
				exchangeRatesService.getRates(todayDate, "CURR3")==
						exchangeRatesService.getRates(yesterdayDate, "CURR3"));

	}

}
