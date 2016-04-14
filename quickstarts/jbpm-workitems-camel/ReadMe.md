Fuse Integration: Quickstarts: jBPM : Work-items camel example
==============================================================

Purpose of this quickstart is to show how to easily write into file using jBPM workitems and Camel endpoint.
The base of the quickstart is simple process which can decide validity of mortgage application and potentially
calculates repayments. The bundle activator creates few mortgage applications and runs the process for each of these
applications. The results are saved into "acceptedApplications.txt" and "rejectedApplications.txt" files. These files
are located in server temporary folder.

FUSE
----------
1. Start FUSE:

${FUSE_HOME}/bin/karaf


2. Ensure that the drools and switchyard compatible features URL files have been added to your Fuse instance. 
   In case they are not added then:

    features:addurl mvn:org.drools/drools-karaf-features/${version.org.kie}/${drools.karaf.features.classifier}


3. Add the features URL for the respective version of BXMS. 

JBossFuse:karaf@root> features:addurl mvn:org.jboss.integration.fuse.quickstarts/karaf-features/${project.version}/xml/features


4. Install the feature for the Blueprint camel drools decision table quickstart :

JBossFuse:karaf@root> features:install jbpm-workitems-camel-quickstart

5. Make an Http Get Request:

        mvn exec:java 

6. Check the output. See output at the end of the document. The output is placed on ${FUSE_HOME}/data/tmp directory and also is 
displayed as the output of the previous maven command;

6. Undeploy the quickstart:

JBossFuse:karaf@root> features:uninstall jbpm-workitems-camel-quickstart


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

6. Build and deploy the Quickstart : 

        mvn install -Pdeploy

7. Make an Http Get Request:

        mvn exec:java -Peap 

8. Check the output. See output at the end of the document. The output is placed on /tmp directory and also is 
displayed as the output of the previous maven command;

9. Undeploy the quickstart:

        mvn clean -Pdeploy
 



To see the results display files "acceptedApplications.txt" and "rejectedApplications.txt" in tmp directory:

    cat acceptedApplications.txt
    
    Application number 1 - repayment is 1224
    Application number 5 - repayment is 1335
    Application number 6 - repayment is 3056
    Application number 7 - repayment is 2477
    Application number 10 - repayment is 484

    cat rejectedApplications.txt
    
    Application 2 was rejected
    Application 3 was rejected
    Application 4 was rejected
    Application 8 was rejected
    Application 9 was rejected
