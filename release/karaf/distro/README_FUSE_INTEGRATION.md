Description
============
The Fuse Integration pack is an optional package that can be added to an existing Fuse 6.2 installation.  The integration package contains libraries/components that can interact with other integration modules that have not been added into the default Fuse 6.2 release. 

The purpose of the integration package is to provide the â€œglueâ€ between Fuse and BRMS components/libraries.  The Fuse Integration Package gives the ability to install the components/libraries that have not been included in Fuse (Drools, BPM, etc).

Contents:
Switchyard BRMS components: rules-component and bpm-component
Camel components used in kie: kie-camel and jbpm-workitem-camel components


Requirements
============
DROOLS_VERSION --> 6.2.0.Final-redhat-9

1.  Download the Fuse distribution that is aligned with the version of fuse-integration:
    `
  * This is important. If you want to use the 6.2.1.redhat-137 (for instance) version of Fuse, you need to install the fuse-integration features on top of fuse-6.2.1.redhat-137
     `
2.  Add the Remote Maven Repository that contains the fuse dependencies to your karaf instance:
    `
  * Edit the ${FUSE_HOME}/etc/org.ops4j.pax.url.mvn.cfg
     `
3.  If the Drools features URL has not been added, it should be:
```
JBossFuse:karaf@root> features:addurl mvn:org.drools/drools-karaf-features/${DROOLS_VERSION}/xml/features
```


Installation
============
1.   Add the fuse-integration quickstarts features file: 
```
JBossFuse:karaf@root> features:addurl mvn:org.jboss.integration.fuse.quickstarts/karaf-features/${FUSE_INTEGRATION_VERSION}/xml/features
```
2.  Install the features. You can install: 
```
JBossFuse:karaf@root> features:install    
        fuse-bxms-switchyard-quickstart-rules-camel-cbr             
        fuse-bxms-switchyard-quickstart-rules-interview          
        fuse-bxms-switchyard-quickstart-rules-dtable      
        fuse-bxms-switchyard-quickstart-rules-interview-container   
        fuse-bxms-switchyard-quickstart-bpm-service
        fuse-bxms-switchyard-demo-library
        fuse-bxms-switchyard-demo-helpdesk
        fuse-bxms-jbpm-workitems-camel
        fuse-bxms-camel-spring-drools-decision-table
        fuse-bxms-camel-blueprint-drools-decision-table
        fuse-bxms-switchyard-quickstart-rules-csv
        fuse-bxms-switchyard-quickstart-rules-multithread
```

Quickstarts
============
The quickstarts/fuse-integration directory contains the suite of quickstarts available in this distro version. Check directly the README file of the quickstart you are interested for further information about usage.




