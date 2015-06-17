DESCRIPTION

It is an optional package that can be added to an existing Fuse 6.2 installation. It contains libraries/components that can interact with other integration modules that have not been added into the default Fuse 6.2 release. 

For the moment it provides the “glue” between Fuse and BRMS components/libraries. The fuse integration package gives the ability to install the components/libraries that have not been included in Fuse (for instance BRMS).

For the moment it contains:
Switchyard brms components: Rules-component and BPM component
Camel components used in kie:  kie-camel and  jbpm-workitem-camel components


REQUIREMENTS

SWITHYARD_VERSION --> 2.0.0-redhat or upper version
DROOLS_VERSION --> 6.2.0.Final or upper version

1º Download the Fuse distribution that is aligned with the version of fuse-integration:

   This is important. If you want to use the 6.2.0.redhat-117 (for instance) version, you need to install the fuse-integration features on top of fuse-6.2.0.redhat-117

2º Add the Remote Maven Repository that contains the fuse dependencies to your karaf instance:
    Edit the ${FUSE_HOME}/etc/org.ops4j.pax.url.mvn.cfg

3º If switchyard and drools have not been added, they should:
                   features:addurl mvn:org.switchyard.karaf/switchyard/${SWITHYARD_VERSION}/xml/features
                   features:addurl mvn:org.drools/drools-karaf-features/${DROOLS_VERSION}/xml/features

INSTALLATION

1º Add the fuse-integration features file:  
     features:addurl mvn:org.jboss.integration.fuse/karaf-features/1.0.0.redhat-620017/xml/features

2º Install the features. You can install:  
      features:install 
        fuse-bxms-switchyard-quickstart-remote-invoker              
        fuse-bxms-switchyard-quickstart-rules-camel-cbr             
        fuse-bxms-switchyard-quickstart-rules-interview             
        fuse-bxms-switchyard-quickstart-rules-interview-container   
        fuse-bxms-switchyard-quickstart-rules-interview-dtable
        fuse-bxms-switchyard-quickstart-bpm-service
        fuse-bxms-switchyard-demo-library

QUICKSTARTS

On the quickstarts/fuse-integration are included all the suite of quickstarts available in this distro version. Check directly the README file of 
the quickstart you are interested for further information about usage.

 
