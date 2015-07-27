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

import org.apache.karaf.features.Feature;
import org.apache.karaf.features.FeaturesService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.KieServices;
import org.kie.api.runtime.manager.RuntimeManagerFactory;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;
import org.jboss.integration.fuse.karaf.itest.KarafConfigUtil;
import static org.jboss.integration.fuse.karaf.itest.FeatureConstants.KIE_ARIES_BLUEPRINT_FEATURE_NAME;
import static org.jboss.integration.fuse.karaf.itest.FeatureConstants.KIE_CAMEL_FEATURE_NAME;
import static org.jboss.integration.fuse.karaf.itest.FeatureConstants.KIE_SPRING_FEATURE_NAME;
import org.ops4j.pax.exam.Configuration;

/**
 * Basic KIE-Camel smoke tests checking that the KIE features can be deployed into Karaf and that KieServices are available.
 */
@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class CamelFeatureInstallTest {
    
    @Inject
    FeaturesService fs;

    @Configuration
    public static Option[] configure() {
        return KarafConfigUtil.karafConfiguration();
    }
    
    @Test
    public void kieAriesBPFeatureInstalledTest() throws Exception {
        final Feature droolsFeature = fs.getFeature(KIE_ARIES_BLUEPRINT_FEATURE_NAME);
        assertTrue(fs.isInstalled(droolsFeature));
    }

    @Test
    public void kieSpringFeatureInstalledTest() throws Exception {
        final Feature droolsFeature = fs.getFeature(KIE_SPRING_FEATURE_NAME);
        assertTrue(fs.isInstalled(droolsFeature));
    }

    @Test
    public void kieCamelFeatureInstalledTest() throws Exception {
        final Feature droolsFeature = fs.getFeature(KIE_CAMEL_FEATURE_NAME);
        assertTrue(fs.isInstalled(droolsFeature));
    }

    @Test
    public void kieServicesAvailableTest() throws Exception {
        final KieServices ks = KieServices.Factory.get();
        assertNotNull(ks);
    }

    @Test
    public void runtimeManagerFactoryAvailableTest() throws Exception {
        final RuntimeManagerFactory runtimeManagerFactory = RuntimeManagerFactory.Factory.get();
        assertNotNull(runtimeManagerFactory);
    }
}
