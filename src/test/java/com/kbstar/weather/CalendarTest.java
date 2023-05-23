package com.kbstar.weather;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;


@Slf4j
@SpringBootTest
class CalendarTest {


	@Test
	void contextLoads() {

		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH)+1;
		int day = cal.get(Calendar.DATE);

		String monthString = String.format("%02d", month);
		String today = Integer.toString(year)
				+ monthString
				+ Integer.toString(day) + "0600";
		log.info(today);






	}

}
