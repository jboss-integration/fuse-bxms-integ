DESCRIPTION

It is an optional package that can be added to an existing Fuse 6.2 installation. It contains libraries/components that can interact with other integration 
modules that have not been added into the default Fuse 6.2 release. 

For the moment it provides the “glue” between Fuse and BRMS components/libraries. The fuse integration package gives the ability to install the 
components/libraries that have not been included in Fuse (for instance BRMS).

For the moment it contains:
Switchyard brms components: Rules-component and BPM component
Camel components used in kie:  kie-camel and  jbpm-workitem-camel components


REQUIREMENTS

Previously needs to be installed the following modules:

    - Fuse

       1º Download the Fuse EAP Installer: 
		https://origin-repository.jboss.org/nexus/content/groups/ea/com/redhat/fuse/eap/fuse-eap-installer/${version.fuse.eap}
       2º java -jar fuse-eap-installer-${version.fuse.eap}.jar ${EAP-HOME}

INSTALLATION

    1º Download the Fuse Integration EAP Installer: 
		https://origin-repository.jboss.org/nexus/content/groups/ea/org/jboss/integration/fuse/fuse-integration-eap-installer/${project.version}
    2º java -jar fuse-integration-eap-installer-${project.version}.jar ${EAP-HOME}


QUICKSTARTS

On ${EAP_HOME}/quickstarts/fuse-integration are included all the suite of quickstarts available in this distro version. Check directly the README file of 
the quickstart you are interested for further information about usage.

 
