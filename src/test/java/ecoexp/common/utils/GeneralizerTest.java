package ecoexp.common.utils;

import org.junit.Test;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.Assert.assertEquals;

public class GeneralizerTest {
	@Test public void regionTest() {
		assertEquals("서울특별시",Generalizer.region("서울특별시"));
		assertEquals("평창",Generalizer.region("평창군"));
		assertEquals("평창",Generalizer.region("평창"));
		assertEquals("양주",Generalizer.region("양주시"));

	}
}
