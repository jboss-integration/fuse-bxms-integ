/*
 * Copyright 2016 Red Hat Inc. and/or its affiliates and other contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,  
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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


/**
 * The Class DTableCSVTest.
 */
@RunWith(SwitchYardRunner.class)
@SwitchYardTestCaseConfig(config = SwitchYardTestCaseConfig.SWITCHYARD_XML, mixins = { CDIMixIn.class })
public class DTableCSVTest {

    /** The service. */
    @ServiceOperation("Service.invoke")
    private Invoker service;

    /**
     * Test d table csv.
     */
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
