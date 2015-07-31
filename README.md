Fuse BXMS Integration Repository
===============

This is a code repository for the Fuse and BxMS integration components.

It is an optional package that can be added to an existing Fuse 6.2 installation. It contains libraries/components that can interact with other integration modules that have not been added into the default Fuse 6.2 release. 

For the moment it provides the “glue” between Fuse and BRMS components/libraries. The fuse integration package gives the ability to install the components/libraries that have not been included in Fuse (for instance BRMS).

For the moment it contains:
  * Switchyard brms components: Rules-component and BPM component
  * Camel components used in kie:  kie-camel and  jbpm-workitem-camel components

Additionally a suite of quickstarts has been added as well as eap 6.4 and karaf support.

Execute mvn clean install

EAP 6.4 Installation
======================

1.  Download Jboss EAP 6.4.

2.  Download the fuse-eap installer:

      https://origin-repository.jboss.org/nexus/content/groups/ea/com/redhat/fuse/eap/fuse-eap-installer

3.  Install them over your EAP instance:


           java -jar fuse-eap-installer.jar ${eap.home}

4.  Install the fuse-integration installer:

        java -jar ${FUSE_BXMS_INTEG}/release/eap/installer/target/fuse-integration-eap-installer-1.1.0-SNAPSHOT.jar ${eap.home}

5.  All the modules and quickstarts will be installed on top of the eap.home provided. The fuse-integration quickstarts are placed:

     `     `${eap.home}/quickstarts/fuse-integration

    Quickstarts included:
    
    * switchyard-bpm-service
    * switchyard-demo-library
    * switchyard-rules-interview
    * switchyard-rules-interview-dtable
    * switchyard-demo-helpdesk
    * switchyard-rules-camel-cbr  
    * switchyard-rules-interview-container
    * switchyard-drools-channels
    * switchyard-rules-csv
    * switchyard-rules-multithreaded
    * switchyard-rules-loading

    For running the quickstarts:
    * Start EAP that contains fuse-integration installed
    * In the root of eap you can find a quickstarts/fuse-integration folder that contains the quickstarts
    * Follow the instructions of each quickstart. See the Readme of each one
    
    
    
Fuse 6.2 Installation
======================
1.  Download the Fuse distribution that is aligned with the version of fuse-integration:

     https://repository.jboss.org/nexus/content/groups/ea/org/jboss/fuse/jboss-fuse-full

2.  If Drools have not been added, it should. 

           features:addurl mvn:org.drools/drools-karaf-features/6.3.0-SNAPSHOT/xml/features

3.  Add the fuse-integration features file:  

           features:addurl mvn:org.jboss.integration.fuse/karaf-features/1.1.0-SNAPSHOT/xml/features
           
4.  Install the core features. You can install:  

           features:install 
            fuse-bxms-jbpm-workitems-camel
            fuse-bxms-kie-camel
            fuse-bxms-switchyard-bpm
            fuse-bxms-switchyard-rules
            
5.  Try our quickstarts:

           features:addurl mvn:org.jboss.integration.fuse.quickstarts/karaf-features/${FUSE_INTEGRATION_VERSION}/xml/features
           
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
