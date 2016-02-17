Description
============
The Fuse Integration pack is an optional package that can be added to an existing Fuse 6.2 installation.  The integration package contains libraries/components that can interact with other integration modules that have not been added into the default Fuse 6.2 release. 

The purpose of the integration package is to provide the glue between Fuse and BRMS components/libraries.  The Fuse Integration Package gives the ability to install the components/libraries that have not been included in Fuse (Drools, BPM, etc).

Contents:
Switchyard BRMS components: rules-component and bpm-component
Camel components used in kie: kie-camel and jbpm-workitem-camel components


Requirements
============
Compatible Drools Version: ${version.org.kie}

1.  Download the Fuse distribution that is aligned with the version of fuse-integration. This version is aligned with fuse ${version.fuse.eap}
     `
2.  Add the Remote Maven Repository that contains the fuse dependencies to your karaf instance:
    `
  * Edit the ${FUSE_HOME}/etc/org.ops4j.pax.url.mvn.cfg
     `
3.  If the Drools features URL has not been added, it should be:
```
JBossFuse:karaf@root> features:addurl mvn:org.drools/drools-karaf-features/${version.org.kie}/xml/features
```


Installation
============

1.  Download the Fuse distribution that is aligned with the version of fuse-integration:

     https://repository.jboss.org/nexus/content/groups/ea/org/jboss/fuse/jboss-fuse-full

2.  If Drools have not been added, it should. 

           features:addurl mvn:org.drools/drools-karaf-features/${version.drools}/xml/features

3.  Add the fuse-integration features file:  

           features:addurl mvn:org.jboss.integration.fuse/karaf-features/${version.fuse.bxms-integration}/xml/features
           
4.  Install the core features. You can install:  

           features:install 
            fuse-bxms-jbpm-workitems-camel
            fuse-bxms-kie-camel
            fuse-bxms-switchyard-bpm
            fuse-bxms-switchyard-rules
            
5.  Try our quickstarts:

           features:addurl mvn:org.jboss.integration.fuse.quickstarts/karaf-features/${version.fuse.bxms-integration}/xml/features
           
           features:install    
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


Quickstarts
============
The quickstarts/fuse-integration directory contains the suite of quickstarts available in this distro version. Check directly the README file of the quickstart you are interested for further information about usage.


