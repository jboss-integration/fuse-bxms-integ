Fuse Integration: Quickstarts: jBPM : Work-items camel example
==============================================================

INTRODUCE HERE SOME DESCRIPTION



FUSE
----------
1. Start FUSE:

${FUSE_HOME}/bin/karaf


2. Ensure that the drools and switchyard compatible features URL files have been added to your Fuse instance. 
   In case they are not added then:

    features:addurl mvn:org.drools/drools-karaf-features/${version.org.kie}/xml/features


3. Add the features URL for the respective version of BXMS.   Replace {FUSE_BXMS_VERSION}
with the version of Fuse BXMS Integration that you are using (ex. 1.0.0): 

JBossFuse:karaf@root> features:addurl mvn:org.jboss.integration.fuse.quickstarts/karaf-features/${FUSE_BXMS_VERSION}/xml/features


4. Install the feature for the Blueprint camel drools decision table quickstart :

JBossFuse:karaf@root> features:install jbpm-workitems-camel-quickstart

5. Check the output. See output at the end of the document.

6. Undeploy the quickstart:

JBossFuse:karaf@root> features:uninstall jbpm-workitems-camel-quickstart




To see the results display files "acceptedApplications.txt" and "rejectedApplications.txt" in fuse tmp
directory (<JBoss Fuse Home>/data/tmp).

    cat data/tmp/acceptedApplications.txt
    
    Application number 1 - repayment is 1224
    Application number 5 - repayment is 1335
    Application number 6 - repayment is 3056
    Application number 7 - repayment is 2477
    Application number 10 - repayment is 484

    cat data/tmp/rejectedApplications.txt
    
    Application 2 was rejected
    Application 3 was rejected
    Application 4 was rejected
    Application 8 was rejected
    Application 9 was rejected
