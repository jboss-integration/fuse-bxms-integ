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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;
import org.apache.camel.CamelContext;

import static org.junit.Assert.assertTrue;

import javax.inject.Inject;
import org.apache.camel.component.bean.PojoProxyHelper;
import org.jboss.integration.fuse.karaf.itest.KarafConfigUtil;
import org.jboss.integration.fuse.karaf.itest.kiecamel.model.Person;
import org.jboss.integration.fuse.karaf.itest.kiecamel.proxy.AgeVerificationService;
import org.jboss.integration.fuse.karaf.itest.kiecamel.tools.PersonFactory;
import org.ops4j.pax.exam.Configuration;

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class KieCamelBlueprintTest {
    
    @Inject
    private CamelContext camelContext;

    @Configuration
    public static Option[] configure() {
        return KarafConfigUtil.karafConfiguration();
    }
    
    @Test(timeout = 60000)
    public void testOldPerson() throws Exception {
        final AgeVerificationService service = getAgeVerificationProxy();
        final Person verifiedPerson = service.verifyAge(PersonFactory.createOldPerson());

        assertTrue(verifiedPerson.isCanDrink());
    }

    @Test(timeout = 60000)
    public void testYoungPerson() throws Exception {
        final AgeVerificationService service = getAgeVerificationProxy();
        final Person verifiedPerson = service.verifyAge(PersonFactory.createYoungPerson());

        assertTrue(verifiedPerson.isCanDrink()==false);
    }

    private AgeVerificationService getAgeVerificationProxy() throws Exception {
        // need to use PojoProxyHelper to avoid sending BeanInvocation object as payload
        return PojoProxyHelper.createProxy(camelContext.getEndpoint("direct://startAgeVerification"),
                AgeVerificationService.class);
    }
}
