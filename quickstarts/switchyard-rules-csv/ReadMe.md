Introduction
============
This quickstart demonstrates the usage of a rules service using as a source a csv file
The rules are specified using a DTABLE resource (and CSV resourceDetail) in the manifest.


Preqrequisites 
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

7. Submit a webservice request to invoke the Client<br/>
```
        mvn exec:java
```
<br/>

8. Undeploy the quickstart:

        mvn clean -Pdeploy


FUSE
----------
1. Start Fuse:

${FUSE_HOME}/bin/karaf

2. Ensure that the drools and switchyard compatible features URL files have been added to your Fuse instance. 
   In case they are not added then:

JBossFuse:karaf@root> features:addurl mvn:org.drools/drools-karaf-features/${version.org.kie}/xml/${drools.karaf.features.classifier}

3. Add the Karaf BRMS assemblies as well :

JBossFuse:karaf@root> features:addurl mvn:org.apache.karaf.assemblies.features/brms-features/${version.org.apache.karaf}/xml/features

4. Add the features URL for the respective version of BXMS.

JBossFuse:karaf@root> features:addurl mvn:org.jboss.integration.fuse.quickstarts/karaf-features/${project.version}/xml/features


5. Install the feature for the Rules CSV quickstart :

JBossFuse:karaf@root> features:install fuse-bxms-quickstart-switchyard-rules-csv

6. To submit a webservice request to invoke the SOAP gateway, run the quickstart client :
<br/>
```
mvn exec:java -Pkaraf
```
<br/>

7. Undeploy the quickstart:

JBossFuse:karaf@root> features:uninstall fuse-bxms-quickstart-switchyard-rules-csv




KARAF
----------
1. Start the Karaf server :

${KARAF_HOME}/bin/karaf

2. Ensure that the drools and switchyard compatible features URL files have been added to your Karaf instance. 
   In case they are not added then:

    features:addurl mvn:org.switchyard.karaf/switchyard/${version.switchyard}/xml/features
    features:addurl mvn:org.drools/drools-karaf-features/${version.org.kie}/xml/${drools.karaf.features.classifier}
    features:addurl mvn:org.apache.karaf.assemblies.features/brms-features/${version.org.apache.karaf}/xml/features

3. Add the features URL for the respective version of BXMS.

karaf@root> features:addurl mvn:org.jboss.integration.fuse.quickstarts/karaf-features/${project.version}/xml/features


4. Install the feature for the Rules CSV quickstart :

karaf@root> features:install fuse-bxms-quickstart-switchyard-rules-csv

5. To submit a webservice request to invoke the SOAP gateway, run the quickstart client :
<br/>
```
mvn exec:java -Pkaraf
```
<br/>

6. Undeploy the quickstart:

karaf@root> features:uninstall fuse-bxms-quickstart-switchyard-rules-csv





## Further Reading

1. [Configuration Documentation](https://docs.jboss.org/author/display/SWITCHYARD/Configuration)
2. [Rules Service Documentation](https://docs.jboss.org/author/display/SWITCHYARD/Rules)
