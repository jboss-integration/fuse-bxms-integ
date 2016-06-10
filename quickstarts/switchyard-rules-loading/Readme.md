Introduction
============
This quickstart demonstrates the reloading feature of kie/drools and switchyard.

It shows how a switchyard application can be deployed and it uses a Container Release reference. This means that the drools files are located on a external maven artifact.

The Junit test initially is ignored. It is an arquillian test that require an EAP instance well configured being started.

But this important feature of switchyard can be tested executing the /release/eap/itest. There is a specific test that deploys an artifact on the local maven repo, and later redeploy the same artifact (with different drools rules).


Preqrequisites 
==============
Maven
EAP with Switchyard and Fuse-integration installed


EAP
----------
If installers have been previously installed on EAP skips the steps 1-4

1.  Download JBoss EAP 6.4 series

2.  Install Fuse over your EAP instance:

java -jar fuse-eap-installer.jar ${eap.home}

3.  Install KIE over your EAP instance:

java -jar kie-eap-installer.jar ${eap.home}

4.  Install the fuse-integration:

java -jar fuse-integration-eap-installer-${version.fuse.bxms-integration}.jar ${eap.home}

5. Start EAP in standalone mode:

${AS}/bin/standalone.sh

6. Build and deploy the quickstart:

mvn install -Pdeploy

7. Submit a webservice request to invoke the Client<br/>
```
mvn exec:java
```
<br/>

8. Undeploy the quickstart:

mvn clean -Pdeploy


