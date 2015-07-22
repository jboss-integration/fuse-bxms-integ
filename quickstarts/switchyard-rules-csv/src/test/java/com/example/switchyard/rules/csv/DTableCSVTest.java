package com.example.switchyard.rules.csv;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.switchyard.Message;
import org.switchyard.component.test.mixins.cdi.CDIMixIn;
import org.switchyard.quickstart.rules.csv.Person;
import org.switchyard.test.Invoker;
import org.switchyard.test.ServiceOperation;
import org.switchyard.test.SwitchYardRunner;
import org.switchyard.test.SwitchYardTestCaseConfig;

@RunWith(SwitchYardRunner.class)
@SwitchYardTestCaseConfig(config = SwitchYardTestCaseConfig.SWITCHYARD_XML, mixins = {CDIMixIn.class})
public class DTableCSVTest {


	@ServiceOperation("Service.invoke")
	private Invoker service;

	@Test
	public void testDTableCSV() {
		Person person = new Person();
		person.setId(0);
		Message message = service.sendInOut(person);
		Assert.assertNotNull(message.getContent());
		Assert.assertEquals(1, ((Person) message.getContent()).getId());
		System.out.println(message);
	}
}
