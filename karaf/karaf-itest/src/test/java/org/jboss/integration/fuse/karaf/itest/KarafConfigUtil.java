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
package org.jboss.integration.fuse.karaf.itest;

import java.io.File;
import static org.jboss.integration.fuse.karaf.itest.FeatureConstants.CAMEL_FEATURE_ARTIFACT_ID;
import static org.jboss.integration.fuse.karaf.itest.FeatureConstants.CAMEL_FEATURE_GROUP_ID;
import static org.jboss.integration.fuse.karaf.itest.FeatureConstants.CAMEL_FEATURE_NAME;
import static org.jboss.integration.fuse.karaf.itest.FeatureConstants.CAMEL_WORKITEM_FEATURE_NAME;
import static org.jboss.integration.fuse.karaf.itest.FeatureConstants.COMMONS_IO_ARTIFACT_ID;
import static org.jboss.integration.fuse.karaf.itest.FeatureConstants.COMMONS_IO_GROUP_ID;
import static org.jboss.integration.fuse.karaf.itest.FeatureConstants.DROOLS_DT_FEATURE_NAME;
import static org.jboss.integration.fuse.karaf.itest.FeatureConstants.DROOLS_FEATURE_ARTIFACT_ID;
import static org.jboss.integration.fuse.karaf.itest.FeatureConstants.DROOLS_FEATURE_GROUP_ID;
import static org.jboss.integration.fuse.karaf.itest.FeatureConstants.H2_FEATURE_NAME;
import static org.jboss.integration.fuse.karaf.itest.FeatureConstants.HIBERNATE_FEATURE_NAME;
import static org.jboss.integration.fuse.karaf.itest.FeatureConstants.INTEG_PACK_FEATURE_ARTIFACT_ID;
import static org.jboss.integration.fuse.karaf.itest.FeatureConstants.INTEG_PACK_FEATURE_GROUP_ID;
import static org.jboss.integration.fuse.karaf.itest.FeatureConstants.JBPM_FEATURE_NAME;
import static org.jboss.integration.fuse.karaf.itest.FeatureConstants.JNDI_FEATURE_NAME;
import static org.jboss.integration.fuse.karaf.itest.FeatureConstants.KIE_ARIES_BLUEPRINT_FEATURE_NAME;
import static org.jboss.integration.fuse.karaf.itest.FeatureConstants.KIE_CAMEL_FEATURE_NAME;
import static org.jboss.integration.fuse.karaf.itest.FeatureConstants.KIE_SPRING_FEATURE_NAME;
import static org.ops4j.pax.exam.CoreOptions.bundle;
import static org.ops4j.pax.exam.CoreOptions.maven;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.when;
import org.ops4j.pax.exam.Option;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.configureConsole;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.editConfigurationFilePut;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.features;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.karafDistributionConfiguration;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.keepRuntimeFolder;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.logLevel;
import org.ops4j.pax.exam.karaf.options.LogLevelOption;
import org.ops4j.pax.exam.options.DefaultCompositeOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KarafConfigUtil {

    private static final transient Logger logger = LoggerFactory.getLogger(KarafConfigUtil.class);

    private static Option getKarafDistributionOption() {
        String karafVersion = getKarafVersion();
        logger.info("*** The karaf version is " + karafVersion + " ***");
        return new DefaultCompositeOption(karafDistributionConfiguration()
                .frameworkUrl(maven().groupId("org.apache.karaf").artifactId("apache-karaf").type("tar.gz").versionAsInProject())
                .karafVersion(karafVersion)
                .name("Apache Karaf")
                .useDeployFolder(false).unpackDirectory(new File("target/paxexam/unpack/")),
                localMavenRepoOption(),
                editConfigurationFilePut("etc/org.ops4j.pax.url.mvn.cfg", "org.ops4j.pax.url.mvn.repositories",
                        "http://repo1.maven.org/maven2@id=central,"
                        + "https://repository.jboss.org/nexus/content/groups/ea@id=jboss-ea"
                        + "https://repository.jboss.org/nexus/content/groups/public@id=jboss-public"
                ));
    }

    private static String getKarafVersion() {
        String karafVersion = System.getProperty("karafVersion");
        if (karafVersion == null) {
            // setup the default version of it
            karafVersion = "2.4.2";
        }
        return karafVersion;
    }
    
    public static Option localMavenRepoOption() {
        String localRepo = System.getProperty("maven.repo.local", "");
        if (localRepo.length() > 0) {
            logger.info("Using alternative local Maven repository in {}.", new File(localRepo).getAbsolutePath());
        }
        return when(localRepo.length() > 0).useOptions(
                //                systemProperty("org.ops4j.pax.url.mvn.localRepository").value(new File(localRepo).getAbsolutePath()));
                editConfigurationFilePut("etc/org.ops4j.pax.url.mvn.cfg",
                        "org.ops4j.pax.url.mvn.localRepository",
                        new File(localRepo).getAbsolutePath()));
    }

    public static Option[] karafConfiguration() {
        return new Option[]{
                // Install Karaf Container
                getKarafDistributionOption(),

                // It is really nice if the container sticks around after the test so you can check the contents
                // of the data directory when things go wrong.
                keepRuntimeFolder(),
                // Don't bother with local console output as it just ends up cluttering the logs
                configureConsole().ignoreLocalConsole(),
                // Force the log level to INFO so we have more details during the test.  It defaults to WARN.
                logLevel(LogLevelOption.LogLevel.INFO),

                // Option to be used to do remote debugging
                //  debugConfiguration("5005", true),
                
                features(maven().groupId(DROOLS_FEATURE_GROUP_ID).artifactId(DROOLS_FEATURE_ARTIFACT_ID)
                                        .versionAsInProject().type("xml").classifier("features"),
                                JNDI_FEATURE_NAME, H2_FEATURE_NAME, HIBERNATE_FEATURE_NAME,
                                JBPM_FEATURE_NAME, DROOLS_DT_FEATURE_NAME, KIE_SPRING_FEATURE_NAME,
                                KIE_ARIES_BLUEPRINT_FEATURE_NAME),
                
                // Install Camel feature into Apache Karaf
                features(maven().groupId(INTEG_PACK_FEATURE_GROUP_ID).artifactId(INTEG_PACK_FEATURE_ARTIFACT_ID)
                                        .versionAsInProject().type("xml").classifier("features"),
                                KIE_CAMEL_FEATURE_NAME, CAMEL_WORKITEM_FEATURE_NAME),
                
                features(maven().groupId(CAMEL_FEATURE_GROUP_ID).artifactId(CAMEL_FEATURE_ARTIFACT_ID)
                            .versionAsInProject().type("xml").classifier("features"),
                        CAMEL_FEATURE_NAME),
                
                // Install Apache Commons IO bundle into Apache Karaf
                bundle(mavenBundle().groupId(COMMONS_IO_GROUP_ID).artifactId(COMMONS_IO_ARTIFACT_ID)
                                .versionAsInProject().getURL())
        };
    }
}
