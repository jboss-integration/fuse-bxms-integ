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
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;

import javax.inject.Inject;
import org.jboss.integration.fuse.karaf.itest.KarafConfigUtil;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.jboss.integration.fuse.karaf.itest.kiecamel.model.Cheese;
import org.jboss.integration.fuse.karaf.itest.kiecamel.proxy.CheeseAssessmentService;
import org.jboss.integration.fuse.karaf.itest.kiecamel.tools.CheeseFactory;
import org.ops4j.pax.exam.Configuration;

/**
 * Basic KIE-Camel with Blueprint and Decision tables functional tests running
 * in Apache Karaf.
 */
@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class KieCamelBlueprintDTTest {

    @Inject
    private CamelContext camelContext;

    @Configuration
    public static Option[] configure() {
        return KarafConfigUtil.karafConfiguration();
    }

    @Test(timeout = 60000)
    public void testNonFavouriteCheese() throws Exception {
        final CheeseAssessmentService service = getCheeseAssessmentProxy();
        final Cheese assessedCheese = service.assessCheese(CheeseFactory.createStilton());

        assertFalse(assessedCheese.isFavourite());
    }

    @Test(timeout = 60000)
    public void testFavouriteCheese() throws Exception {
        final CheeseAssessmentService service = getCheeseAssessmentProxy();
        final Cheese assessedCheese = service.assessCheese(CheeseFactory.createCheddar());

        assertTrue(assessedCheese.isFavourite());
    }

    private CheeseAssessmentService getCheeseAssessmentProxy() throws Exception {
        // need to use PojoProxyHelper to avoid sending BeanInvocation object as payload
        return PojoProxyHelper.createProxy(camelContext.getEndpoint("direct://startCheeseAssessment"),
                CheeseAssessmentService.class);
    }
}
