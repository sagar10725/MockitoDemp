package com.info.mock;

import static org.mockito.Mockito.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.info.mock.Math.CalculatorService;
import com.info.mock.Math.MathAppication;

@RunWith(MockitoJUnitRunner.class)
public class MathApplicationTester {

	@InjectMocks
	private MathAppication application = new MathAppication();

	// creating a mock object
	@Mock
	private CalculatorService calculatorService;

	@Test(expected = RuntimeException.class)
	public void testAdd() {

		doThrow(new RuntimeException("Error on adding ")).when(calculatorService).add(10.0, 20.0);

		// test the add functionality
		Assert.assertEquals(application.add(10.0, 20.0), 30.0, 0);
		/*
		 * Assert.assertEquals(application.add(10.0, 30.0), 40.0, 0);
		 * Assert.assertEquals(application.add(10.0, 40.0), 30.0, 0);
		 */

		verify(calculatorService, times(3)).add(10.0, 20.0);

	}

	@Test
	public void testSubstract() {
		Mockito.when(calculatorService.subtract(20.0, 10.0)).thenReturn(10.0);
		Assert.assertEquals(application.subtract(20.0, 10.0), 10.0, 0);

		verify(calculatorService).subtract(20.0, 10.0);
	}

	@Test
	public void testmultiplication() {
		Mockito.when(calculatorService.multiply(20.0, 10.0)).thenReturn(100.0);
		Assert.assertEquals(application.multiply(20.0, 10.0), 100.0, 0);

		verify(calculatorService, times(1)).multiply(20.0, 10.0);
	}
}
