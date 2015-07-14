package com.example.switchyard.drools.channels;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.switchyard.component.test.mixins.cdi.CDIMixIn;
import org.switchyard.test.Invoker;
import org.switchyard.test.ServiceOperation;
import org.switchyard.test.SwitchYardRunner;
import org.switchyard.test.SwitchYardTestCaseConfig;


@RunWith(SwitchYardRunner.class)
@SwitchYardTestCaseConfig(mixins = CDIMixIn.class, config = SwitchYardTestCaseConfig.SWITCHYARD_XML)
public class ChannelsTest {

    @ServiceOperation("Channel")
	private Invoker service;

	@Test
	public void testProcess() throws Exception {
        String message = "message";
        service.operation("process").sendInOnly(message);
	}

}
