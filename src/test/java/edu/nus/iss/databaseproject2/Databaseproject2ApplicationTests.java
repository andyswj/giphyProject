package edu.nus.iss.databaseproject2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.nus.iss.databaseproject2.services.GiphyService;

@SpringBootTest
class Databaseproject2ApplicationTests {

	@Autowired
	private GiphyService giphySvc;
	
	@Test
	void shouldLoad10Images() {
		List<String> gifs = giphySvc.getResult("dog");
		assertEquals(10, gifs.size(), "Default size 10");
	}

}
