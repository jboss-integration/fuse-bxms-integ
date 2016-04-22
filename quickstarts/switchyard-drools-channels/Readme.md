Introduction
============
This quickstart demonstrates the usage of the drools channels from switchyard.

It demonstrates the usage of the SwitchYardServiceChannel. It allows to redirect the output of a drools channel as an input of a Switchyard Service.

In the example there is a drools service that includes the input to 2 channels. One of them is implemented as a Java Service. The other one is implemented as another drools service.

It allows to show that 2 different KieSession can be executed together. In this example there is a KieSession(ChannelService) that calls another KieSession (WorkerB service).

Prerequisites 
==============
Maven

Running the quickstart
======================


EAP
----------
If installers have been previously installed on EAP skips the steps 1-4

1.  Download Jboss EAP 6.4 or upper


2.  Install them over your EAP instance:

           java -jar fuse-eap-installer.jar ${eap.home}

3.  Install the kie eap installer:

        java -jar kie-eap-installer.jar ${eap.home}

4.  Install the fuse-integration installer:

        java -jar fuse-integration-eap-installer-${version.fuse.bxms-integration}.jar ${eap.home}

5. Start EAP in standalone mode:

        ${AS}/bin/standalone.sh

6. Build and deploy the quickstart: 

        mvn install -Pdeploy

7. Submit a webservice request to invoke the SOAP gateway.  There are a number of ways to do this :
    - Submit a request with your preferred SOAP client - src/test/resources/xml contains 
      sample requests and the responses that you should see
    - Use the simple bundled SOAP client and the sample request XML e.g.
<br/>
```
            mvn exec:java
```
<br/>

Expected output:

service: Channel, payload: message
service: WorkerABean, payload: message.A
service: WorkerB, payload: message.B


8. Undeploy the quickstart:

mvn clean -Pdeploy


FUSE
----------
1. Start FUSE:

${FUSE_HOME}/bin/karaf


2. Ensure that the drools and switchyard compatible features URL files have been added to your Fuse Karaf instance. 
   In case they are not added then:

    features:addurl mvn:org.drools/drools-karaf-features/${version.org.kie}/xml/${drools.karaf.features.classifier}

3. Add the features URL for the respective version of BXMS.

JBossFuse:karaf@root> features:addurl mvn:org.jboss.integration.fuse.quickstarts/karaf-features/${project.version}/xml/features


4. Install the feature for the Rules Interview quickstart :

JBossFuse:karaf@root> features:install fuse-bxms-quickstart-switchyard-drools-channels

5. To submit a sca requestrun the quickstart client :
<br/>
```
mvn exec:java -Pkaraf
```
<br/>

Expected output:

service: Channel, payload: message
service: WorkerABean, payload: message.A
service: WorkerB, payload: message.B


6. Undeploy the quickstart:

JBossFuse:karaf@root> features:uninstall fuse-bxms-quickstart-switchyard-drools-channels



KARAF
----------
1. Start the Karaf server :

${KARAF_HOME}/bin/karaf


2. Ensure that the drools and switchyard compatible features URL files have been added to your Karaf instance. 
   In case they are not added then:

    features:addurl mvn:org.switchyard.karaf/switchyard/${version.switchyard}/xml/features
    features:addurl mvn:org.drools/drools-karaf-features/${version.org.kie}/xml/${drools.karaf.features.classifier}

3. Add the features URL for the respective version of BXMS.

karaf@root> features:addurl mvn:org.jboss.integration.fuse.quickstarts/karaf-features/${project.version}/xml/features


4. Install the feature for the Rules Interview quickstart:

karaf@root> features:install fuse-bxms-quickstart-switchyard-drools-channels

5. To submit a sca request run the quickstart client:
<br/>
```
mvn exec:java -Pkaraf
```

Expected output:

service: Channel, payload: message
service: WorkerABean, payload: message.A
service: WorkerB, payload: message.B
<br/>

6. Undeploy the quickstart:

karaf@root> features:uninstall fuse-bxms-quickstart-switchyard-drools-channels

======================

mvn clean install

Expected output:

service: Channel, payload: message
service: WorkerABean, payload: message.A
service: WorkerB, payload: message.B
