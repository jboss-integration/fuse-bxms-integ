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

public class FeatureConstants {

    public static final String KARAF_FEATURES_CONFIG_FILE = "etc/org.apache.karaf.features.cfg";
    public static final String KARAF_BOOT_FEATURES_KEY = "featuresBoot";
    
    public static final String CAMEL_FEATURE_GROUP_ID = "org.apache.camel.karaf";
    public static final String CAMEL_FEATURE_ARTIFACT_ID = "apache-camel";
    public static final String CAMEL_FEATURE_NAME = "camel";
    public static final String CAMEL_FEATURE_SQL_NAME = "camel-sql";
    public static final String CAMEL_FEATURE_STREAM_NAME = "camel-stream";

    public static final String DROOLS_FEATURE_GROUP_ID = "org.drools";
    public static final String DROOLS_FEATURE_ARTIFACT_ID = "drools-karaf-features";

    public static final String DROOLS_MODULE_FEATURE_NAME = "drools-module";
    public static final String DROOLS_JPA_FEATURE_NAME = "drools-jpa";
    public static final String DROOLS_DT_FEATURE_NAME = "drools-decisiontable";

    public static final String JBPM_FEATURE_NAME = "jbpm";
    public static final String JBPM_HUMAN_TASK_FEATURE_NAME = "jbpm-human-task";

    public static final String KIE_CI_FEATURE_NAME = "kie-ci";

    public static final String KIE_ARIES_BLUEPRINT_FEATURE_NAME = "kie-aries-blueprint";
    public static final String KIE_SPRING_FEATURE_NAME = "kie-spring";

    // Integration Pack features
    public static final String INTEG_PACK_FEATURE_GROUP_ID = "org.jboss.integration.fuse";
    public static final String INTEG_PACK_FEATURE_ARTIFACT_ID = "karaf-features";

    public static final String KIE_CAMEL_FEATURE_NAME = "kie-camel";
    public static final String CAMEL_WORKITEM_FEATURE_NAME = "jbpm-workitems-camel";

    // auxiliary features
    public static final String HIBERNATE_FEATURE_NAME = "droolsjbpm-hibernate";
    public static final String H2_FEATURE_NAME = "h2";
    public static final String JNDI_FEATURE_NAME = "jndi";

    public static final String PAX_CDI_FEATURE_GROUP_ID = "org.ops4j.pax.cdi";
    public static final String PAX_CDI_FEATURE_ARTIFACT_ID = "pax-cdi-features";

    public static final String PAX_CDI_FEATURE_NAME = "pax-cdi-1.1-weld";
    
    public static final String COMMONS_IO_GROUP_ID = "commons-io";
    public static final String COMMONS_IO_ARTIFACT_ID = "commons-io";
}
