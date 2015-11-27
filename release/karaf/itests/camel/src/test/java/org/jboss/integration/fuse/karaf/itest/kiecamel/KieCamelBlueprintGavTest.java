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

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jboss.integration.fuse.karaf.itest.kiecamel.proxy.AgeVerificationServiceGav;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.KieBase;
import org.kie.api.definition.type.FactType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;
import org.apache.camel.CamelContext;

import javax.inject.Inject;
import org.apache.camel.component.bean.PojoProxyHelper;
import org.jboss.integration.fuse.karaf.itest.KarafConfigUtil;
import org.jboss.integration.fuse.karaf.itest.kiecamel.proxy.AgeVerificationService;
import org.jboss.integration.fuse.karaf.itest.kiecamel.tools.PersonFactoryGav;
import org.ops4j.pax.exam.Configuration;
import org.osgi.service.blueprint.container.BlueprintContainer;

/**
 * Test scenarion: Insert facts and execute rules locally in Fuse using a Camel route.
 * Rule definitions are resolved as KIE artifacts using Maven.
 */
@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class KieCamelBlueprintGavTest {

    @Inject
    private CamelContext camelContext;

    @Inject
    private KieBase kieBase;

    @Configuration
    public static Option[] configure() {
        return KarafConfigUtil.karafConfiguration();
    }

    @Test(timeout = 60000)
    public void testOldPerson() throws Exception {
        FactType personType = kieBase.getFactType("org.jboss.integration.fuse.karaf.itest.kiecamel.module.drl", "Person");
        final AgeVerificationServiceGav service = getAgeVerificationProxy();
        final Object verifiedPerson = service.verifyAge(PersonFactoryGav.createOldPerson(personType));

        boolean canDrink = (Boolean) personType.get(verifiedPerson, "canDrink");

        assertTrue(canDrink);
    }

    @Test(timeout = 60000)
    public void testYoungPerson() throws Exception {
        FactType personType = kieBase.getFactType("org.jboss.integration.fuse.karaf.itest.kiecamel.module.drl", "Person");
        final AgeVerificationServiceGav service = getAgeVerificationProxy();
        final Object verifiedPerson = service.verifyAge(PersonFactoryGav.createYoungPerson(personType));

        Boolean canDrink = (Boolean) personType.get(verifiedPerson, "canDrink");

        assertFalse(canDrink);
    }

    private AgeVerificationServiceGav getAgeVerificationProxy() throws Exception {
        // need to use PojoProxyHelper to avoid sending BeanInvocation object as payload
        return PojoProxyHelper.createProxy(camelContext.getEndpoint("direct://startAgeVerificationGav"),
                AgeVerificationServiceGav.class);
    }
}
