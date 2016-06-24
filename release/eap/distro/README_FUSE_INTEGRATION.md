Description
============
The Fuse Integration pack is an optional package that can be added to an existing Fuse 6.3 installation.  The integration package contains libraries/components that can interact with other integration modules that have not been added into the default Fuse 6.3 release. 

The purpose of the integration package is to provide the glue between Fuse and BRMS components/libraries.  The Fuse Integration Package gives the ability to install the components/libraries that have not been included in Fuse (Drools, BPM, etc).


Contents
============
Switchyard brms components: rules-component and BPM component
Camel components used in kie:  kie-camel and jbpm-workitem-camel components


Installation
============

Previously needs to be installed the following modules:

Fuse :

1.  Download and install EAP 6.4.x.    For the purposes of this document, we'll refer to the directory you installed into as ${EAP_HOME}.'

2.  Download the Fuse EAP Installer and install on top of EAP : 

    java -jar fuse-eap-installer-${version.fuse.eap}.jar ${EAP_HOME}

3.  Download the KIE EAP installer and install on top of EAP :

    java -jar kie-eap-installer-${version.org.kie}.jar ${EAP_HOME}

4.  Download the Fuse Integration EAP Installer and install on top of EAP : 

    java -jar fuse-integration-eap-installer-${project.version}.jar ${EAP_HOME}


Quickstarts
============

${EAP_HOME}/quickstarts/fuse-integration contains the full suite of quickstarts available in this distro version. Check the specific README file of the quickstart you are interested for further information about usage.

 
