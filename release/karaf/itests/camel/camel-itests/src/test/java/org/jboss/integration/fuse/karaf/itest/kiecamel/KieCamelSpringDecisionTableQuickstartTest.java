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

package org.jboss.integration.fuse.karaf.itest.kiecamel;

import static org.jboss.integration.fuse.karaf.itest.FeatureConstants.INTEG_PACK_FEATURE_ARTIFACT_ID;
import static org.jboss.integration.fuse.karaf.itest.FeatureConstants.INTEG_PACK_QUICKSTARTS_FEATURE_GROUP_ID;
import static org.ops4j.pax.exam.CoreOptions.maven;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.features;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import junit.framework.Assert;
import org.jboss.integration.fuse.karaf.itest.KarafConfigUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;
import org.osgi.framework.BundleContext;

/**
 * Basic KIE-Camel with Blueprint and Decision tables functional tests running
 * in Apache Karaf.
 */
@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class KieCamelSpringDecisionTableQuickstartTest {

    private static String FEATURE_NAME = "fuse-bxms-quickstart-camel-spring-drools-decision-table";

    @Inject
    protected BundleContext bundleContext;

    @Configuration
    public static Option[] configure() {
        List<Option> options = new ArrayList<Option>();
        Option[] defaultOptions = KarafConfigUtil.karafConfiguration();
        options.addAll(Arrays.asList(defaultOptions));
        options.add(features(maven().groupId(INTEG_PACK_QUICKSTARTS_FEATURE_GROUP_ID).artifactId(INTEG_PACK_FEATURE_ARTIFACT_ID).versionAsInProject().type("xml")
                                 .classifier("features"), FEATURE_NAME));
        // options.add(org.ops4j.pax.exam.CoreOptions.mavenBundle().groupId("org.ops4j.pax.web.itest").artifactId("pax-web-itest-base").versionAsInProject());
        return options.toArray(new Option[1]);

    }


    @Test(timeout = 60000)
    public void test() throws Exception {
        // Empty tests to allow the Karaf server is started and the features installed.
        // Some checks should be included
        Assert.assertTrue(true);
    }
}
