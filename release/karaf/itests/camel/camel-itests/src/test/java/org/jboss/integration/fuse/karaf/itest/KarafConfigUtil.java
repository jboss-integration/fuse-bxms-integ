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
import java.util.ArrayList;
import java.util.List;

import static org.jboss.integration.fuse.karaf.itest.FeatureConstants.KARAF_FEATURES_CONFIG_FILE;
import static org.jboss.integration.fuse.karaf.itest.FeatureConstants.KARAF_BOOT_FEATURES_KEY;
import static org.jboss.integration.fuse.karaf.itest.FeatureConstants.CAMEL_FEATURE_ARTIFACT_ID;
import static org.jboss.integration.fuse.karaf.itest.FeatureConstants.CAMEL_FEATURE_GROUP_ID;
import static org.jboss.integration.fuse.karaf.itest.FeatureConstants.CAMEL_FEATURE_NAME;
import static org.jboss.integration.fuse.karaf.itest.FeatureConstants.CAMEL_FEATURE_SQL_NAME;
import static org.jboss.integration.fuse.karaf.itest.FeatureConstants.CAMEL_FEATURE_STREAM_NAME;
import static org.jboss.integration.fuse.karaf.itest.FeatureConstants.CAMEL_WORKITEM_FEATURE_NAME;
import static org.jboss.integration.fuse.karaf.itest.FeatureConstants.COMMONS_IO_ARTIFACT_ID;
import static org.jboss.integration.fuse.karaf.itest.FeatureConstants.COMMONS_IO_GROUP_ID;
import static org.jboss.integration.fuse.karaf.itest.FeatureConstants.DROOLS_DT_FEATURE_NAME;
import static org.jboss.integration.fuse.karaf.itest.FeatureConstants.DROOLS_FEATURE_ARTIFACT_ID;
import static org.jboss.integration.fuse.karaf.itest.FeatureConstants.DROOLS_FEATURE_GROUP_ID;
import static org.jboss.integration.fuse.karaf.itest.FeatureConstants.H2_FEATURE_NAME;
import static org.jboss.integration.fuse.karaf.itest.FeatureConstants.KIE_CI_FEATURE_NAME;
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
import static org.ops4j.pax.exam.CoreOptions.vmOption;
import static org.ops4j.pax.exam.CoreOptions.when;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.debugConfiguration;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.editConfigurationFileExtend;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.editConfigurationFilePut;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.karaf.options.KarafDistributionBaseConfigurationOption;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.configureConsole;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.features;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.karafDistributionConfiguration;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.keepRuntimeFolder;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.logLevel;
import org.ops4j.pax.exam.karaf.options.LogLevelOption;
import org.ops4j.pax.exam.options.DefaultCompositeOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KarafConfigUtil {

    /**
     * Path to file containing container binary archive.
     */
    public static final String PROP_KARAF_DISTRIBUTION_FILE = "karaf.dist.file";
    
    /**
     * Maximal size of perm gen memory. For example "512M". This property
     * is useful only in Java 7.
     */
    public static final String PROP_KARAF_MAXPERMSIZE = "karaf.maxpermsize";
    
    /**
     * Whether to keep pax-exam runtime folder after the test execution is completed.
     * It can be very useful for debugging to keep the content of runtime folder.
     */
    public static final String PROP_KEEP_RUNTIME_FOLDER = "karaf.keep.runtime.folder";

    /**
     * Specify port for remote debugger. If this property is defined, the remote debugger
     * can be attached to the specified port.
     */
    public static final String PROP_KARAF_DEBUG_CONFIGURATION_PORT = "karaf.debug.port";
    
    /**
     * Whether to install Camel to the container. Some Karaf based containers
     * (for example JBoss Fuse) have Camel installed. (Default value is true).
     */
    public static final String PROP_INSTALL_CAMEL = "karaf.install.camel";
    
    private static final String DROOLS_KARAF_FEATURES_CLASSIFIER = "drools.karaf.features.classifier";

    private static final transient Logger logger = LoggerFactory.getLogger(KarafConfigUtil.class);

    private static Option getKarafDistributionOption() {
        String karafVersion = getKarafVersion();
        logger.info("*** The karaf version is " + karafVersion + " ***");
        
        KarafDistributionBaseConfigurationOption karafDistributionConfiguration = karafDistributionConfiguration();
        
        /* Use default or custom container */
        if (System.getProperty(PROP_KARAF_DISTRIBUTION_FILE) == null) {
            karafDistributionConfiguration.frameworkUrl(maven().groupId("org.apache.karaf").artifactId("apache-karaf").type("tar.gz").versionAsInProject());
        } else {
            File fuseDistributionFile = new File(System.getProperty(PROP_KARAF_DISTRIBUTION_FILE));
            karafDistributionConfiguration.frameworkUrl("file:" + fuseDistributionFile.getAbsolutePath());
        }
        
        karafDistributionConfiguration.karafVersion(karafVersion)
            .name("Apache Karaf")
            .useDeployFolder(false).unpackDirectory(new File("target/paxexam/unpack/"));
        
        return new DefaultCompositeOption(karafDistributionConfiguration,
                localMavenRepoOption(),
                editConfigurationFilePut("etc/org.ops4j.pax.url.mvn.cfg", "org.ops4j.pax.url.mvn.repositories",
                        "http://repo1.maven.org/maven2@id=central,"
                        + "https://repository.jboss.org/nexus/content/groups/ea@id=jboss-ea,"
                        + "https://repository.jboss.org/nexus/content/groups/public@id=jboss-public,"
                        + "http://download.eng.bos.redhat.com/brewroot/repos/jb-fuse-6.2-build/latest/maven@id=fuse-brew,"
                        + "http://download.eng.bos.redhat.com/brewroot/repos/jb-bxms-6.4-build/latest/maven@id=fuse-brew-64"
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
        
        List<Option> options = new ArrayList<Option>();
        
        options.add(getKarafDistributionOption());
        
        /* Set maximal perm space size */
        if (System.getProperty(PROP_KARAF_MAXPERMSIZE) != null) {
            options.add(vmOption("-XX:MaxPermSize=" + System.getProperty(PROP_KARAF_MAXPERMSIZE)));
        }
        
        // It is really nice if the container sticks around after the test so you can check the contents
        // of the data directory when things go wrong.
        if (System.getProperty(PROP_KEEP_RUNTIME_FOLDER) != null) {
            options.add(keepRuntimeFolder());
        }
        
        // Don't bother with local console output as it just ends up cluttering the logs
        options.add(configureConsole().ignoreLocalConsole());
        
        // Force the log level to INFO so we have more details during the test.  It defaults to WARN.
        options.add(logLevel(LogLevelOption.LogLevel.INFO));
        
        String droolsClassifier = System.getProperty(DROOLS_KARAF_FEATURES_CLASSIFIER) != null ? System.getProperty(DROOLS_KARAF_FEATURES_CLASSIFIER) : "features";
        
        options.add(features(maven().groupId(DROOLS_FEATURE_GROUP_ID).artifactId(DROOLS_FEATURE_ARTIFACT_ID)
                                        .versionAsInProject().type("xml").classifier(droolsClassifier),
                                JNDI_FEATURE_NAME, H2_FEATURE_NAME, HIBERNATE_FEATURE_NAME, KIE_CI_FEATURE_NAME,
                                JBPM_FEATURE_NAME, DROOLS_DT_FEATURE_NAME, KIE_SPRING_FEATURE_NAME,
                                KIE_ARIES_BLUEPRINT_FEATURE_NAME));
        options.add(features(maven().groupId(INTEG_PACK_FEATURE_GROUP_ID).artifactId(INTEG_PACK_FEATURE_ARTIFACT_ID)
                                        .versionAsInProject().type("xml").classifier("features"),
                                KIE_CAMEL_FEATURE_NAME, CAMEL_WORKITEM_FEATURE_NAME));
        
        if (Boolean.parseBoolean(System.getProperty(PROP_INSTALL_CAMEL, "true"))) {
            options.add(features(maven().groupId(CAMEL_FEATURE_GROUP_ID).artifactId(CAMEL_FEATURE_ARTIFACT_ID)
                        .versionAsInProject().type("xml").classifier("features"),
                    CAMEL_FEATURE_NAME, CAMEL_FEATURE_SQL_NAME, CAMEL_FEATURE_STREAM_NAME));
        } else {
            options.add(editConfigurationFileExtend(KARAF_FEATURES_CONFIG_FILE, KARAF_BOOT_FEATURES_KEY, CAMEL_FEATURE_SQL_NAME));
            options.add(editConfigurationFileExtend(KARAF_FEATURES_CONFIG_FILE, KARAF_BOOT_FEATURES_KEY, CAMEL_FEATURE_STREAM_NAME));
        }
        
        options.add(bundle(mavenBundle().groupId(COMMONS_IO_GROUP_ID).artifactId(COMMONS_IO_ARTIFACT_ID)
                                .versionAsInProject().getURL()));

        if (System.getProperty(PROP_KARAF_DEBUG_CONFIGURATION_PORT) != null) {
            options.add(debugConfiguration(System.getProperty(PROP_KARAF_DEBUG_CONFIGURATION_PORT), true));
        }
        
        return options.toArray(new Option[1]);
    }
}
