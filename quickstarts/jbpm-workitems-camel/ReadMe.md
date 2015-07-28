Fuse Integration: Quickstarts: jBPM : Work-items camel example
==============================================================

To build this project use

    mvn install

To deploy this project into :

[JBoss Fuse](http://access.redhat.com/downloads) 

Start JBoss Fuse

    <JBoss Fuse Home>/bin/fuse

In the console, use the following commands

    features:addurl mvn:org.jboss.integration.fuse.quickstarts/karaf-features/${FUSE_BXMS_VERSION}/xml/features
    features:install jbpm-workitems-camel-quickstart

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