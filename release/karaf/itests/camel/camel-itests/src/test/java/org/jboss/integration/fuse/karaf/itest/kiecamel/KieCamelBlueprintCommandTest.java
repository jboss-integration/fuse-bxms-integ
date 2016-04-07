/*
 * Copyright 2015 Red Hat Inc. and/or its affiliates and other contributors.
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

package org.jboss.integration.fuse.karaf.itest.kiecamel;

import org.apache.camel.CamelContext;
import org.apache.camel.component.bean.PojoProxyHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.runtime.ExecutionResults;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;

import javax.inject.Inject;
import org.jboss.integration.fuse.karaf.itest.KarafConfigUtil;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.jboss.integration.fuse.karaf.itest.kiecamel.model.Person;
import org.jboss.integration.fuse.karaf.itest.kiecamel.proxy.CommandExecutionService;
import org.jboss.integration.fuse.karaf.itest.kiecamel.tools.PersonFactory;
import org.ops4j.pax.exam.Configuration;

/**
 * Basic KIE-Camel with Blueprint KIE Command execution tests running in Apache Karaf.
 */
@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class KieCamelBlueprintCommandTest {

    @Inject
    private CamelContext camelContext;

    @Configuration
    public static Option[] configure() {
        return KarafConfigUtil.karafConfiguration();
    }

    @Test(timeout = 60000)
    public void testOldPerson() throws Exception {
        final Person person = PersonFactory.createOldPerson();
        assertFalse(person.isCanDrink());

        final Person verifiedPerson = executeCommand(person);

        assertNotNull(verifiedPerson);
        assertTrue(verifiedPerson.isCanDrink());
    }

    @Test(timeout = 60000)
    public void testYoungPerson() throws Exception {
        final Person person = PersonFactory.createYoungPerson();
        assertFalse(person.isCanDrink());

        final Person verifiedPerson = executeCommand(person);

        assertNotNull(verifiedPerson);
        assertFalse(verifiedPerson.isCanDrink());
    }

    private Person executeCommand(final Person person) throws Exception {
        final CommandExecutionService service = getCommandExecutionProxy();
        final ExecutionResults results = service.executeCommandWithPerson(person);

        assertNotNull(results);
        return (Person) results.getValue("person");
    }

    private CommandExecutionService getCommandExecutionProxy() throws Exception {
        // need to use PojoProxyHelper to avoid sending BeanInvocation object as payload
        return PojoProxyHelper.createProxy(camelContext.getEndpoint("direct://ruleOnCommand"),
                CommandExecutionService.class);
    }
}
