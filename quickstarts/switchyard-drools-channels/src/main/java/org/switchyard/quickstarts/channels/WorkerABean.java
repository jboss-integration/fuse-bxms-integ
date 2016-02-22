package org.switchyard.quickstarts.channels;

import org.switchyard.component.bean.Service;

@Service(WorkerA.class)
public class WorkerABean implements WorkerA {

	@Override
	public void process(String input) {
		System.out.println("service: WorkerABean, payload: " + input +".A");
	}

}
