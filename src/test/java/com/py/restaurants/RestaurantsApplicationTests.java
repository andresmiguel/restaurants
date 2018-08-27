package com.py.restaurants;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestaurantsApplicationTests {

	static {
		System.setProperty("PY_CLIENT_ID", "PY_CLIENT_ID");
		System.setProperty("PY_CLIENT_SECRET", "PY_CLIENT_SECRET");
	}

	@Test
	public void contextLoads() {
	}

}
