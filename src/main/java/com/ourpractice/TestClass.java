package com.ourpractice;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest ({MyStatic.class})
public class TestClass {
	
	ClassUnderTest classUnderTest = new ClassUnderTest();

	@Test
	public void something() {
		PowerMockito.mockStatic(MyStatic.class);
		
		Service service = Mockito.mock(Service.class);
		Mockito.when(service.reverseString("something")).thenReturn("somethingelse");
		Mockito.when(MyStatic.getLength("somethingelse")).thenReturn(5);
		classUnderTest.setService(service);
		
		String str = classUnderTest.letsDoSomething("something");
		Assert.assertEquals("aaaaa", str);
	}
}
