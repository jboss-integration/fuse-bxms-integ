Introduction
============
This quickstart demonstrates the usage of the rules and bpm components,
showing a rules component (SuggestionRules) which exposes business rules,
and bpm component (LoanProcess), which exposes a business process flow.
It is invoked through two SOAP gateway bindings.

The example is of a Library, which provides suggestions for books based on
a keyword, and a capability to give out and track loans of those books. The
rules component provides the book suggestions, and the bpm component provides
the loan tracking.

Please see library.jpg for a visual overview of the switchyard application,
and loan.jpg for the bpm process diagram.


Running the quickstart
======================

JBoss AS 7
----------
1. Start JBoss AS 7 in standalone mode:
```
        ${AS}/bin/standalone.sh
```

2. Build and deploy the Quickstart : 
```
        mvn install -Pdeploy
```

3. Submit multiple webservice requests to invoke the SOAP gateways by using the bundled client.
```
        mvn exec:java
```
  Check the expected output (below)


4. Undeploy the quickstart:
```
        mvn clean -Pdeploy
```


FUSE
----------
1. Start FUSE:

${FUSE_HOME}/bin/karaf


2. Ensure that the drools and switchyard compatible features URL files have been added to your Fuse instance. 
   In case they are not added then:

    features:addurl mvn:org.drools/drools-karaf-features/${version.org.kie}/${drools.karaf.features.classifier}


3. Add the features URL for the respective version of BXMS.

JBossFuse:karaf@root> features:addurl mvn:org.jboss.integration.fuse.quickstarts/karaf-features/${project.version}/xml/features


4. Install the feature for the Switchyard library Demo quickstart :

JBossFuse:karaf@root> features:install fuse-bxms-quickstart-switchyard-library

5. To submit a webservice request to invoke the SOAP gateway, run the quickstart client :
<br/>
```
mvn exec:java -Pkaraf
```
<br/>

6. Undeploy the quickstart:

JBossFuse:karaf@root> features:uninstall fuse-bxms-quickstart-switchyard-library




KARAF
----------
1. Start the Karaf server :

${KARAF_HOME}/bin/karaf


2. Ensure that the drools and switchyard compatible features URL files have been added to your Karaf instance. 
   In case they are not added then:

    features:addurl mvn:org.switchyard.karaf/switchyard/${version.switchyard}/xml/features
    features:addurl mvn:org.drools/drools-karaf-features/${version.org.kie}/${drools.karaf.features.classifier}


3. Add the features URL for the respective version of BXMS.

karaf@root> features:addurl mvn:org.jboss.integration.fuse.quickstarts/karaf-features/${project.version}/xml/features


4. Install the feature for the Switchyard library Demo quickstart :

karaf@root> features:install fuse-bxms-quickstart-switchyard-library

5. To submit a webservice request to invoke the SOAP gateway, run the quickstart client :
<br/>
```
mvn exec:java -Pkaraf
```
<br/>

6. Undeploy the quickstart:

karaf@root> features:uninstall fuse-bxms-quickstart-switchyard-library




Expected Output
-----------------

<br/>
Expected Client-Side Output:
```
Received suggestion for book: World War Z (isbn: 978-0-307-35193-7)
Attempting 1st loan for isbn: 978-0-307-35193-7
1st loan approved? true
Attempting 2nd loan for isbn: 978-0-307-35193-7
2nd loan approved? false
Returning 1st loan for isbn: 978-0-307-35193-7
1st loan return acknowledged? true
Re-attempting 2nd loan for isbn: 978-0-307-35193-7
Re-attempt of 2nd loan approved? true
Received suggestion for book: The Zombie Survival Guide (isbn: 978-1-4000-5-80-2)
Attempting 3rd loan for isbn: 978-1-4000-5-80-2
3rd loan approved? true
Returning 2nd loan for isbn: 978-0-307-35193-7
2nd loan return acknowledged? true
Returning 3rd loan for isbn: 978-1-4000-5-80-2
3rd loan return acknowledged? true
```

<br/>
Expected Server-Side Output:
```
Attempting Loan...
Loan approved? true
Attempting Loan...
Loan approved? false
Returning Loan...
Return acknowledged? true
Attempting Loan...
Loan approved? true
Attempting Loan...
Loan approved? true
Returning Loan...
Return acknowledged? true
Returning Loan...
Return acknowledged? true
```

## Further Reading

1. [Rules Service Documentation](https://docs.jboss.org/author/display/SWITCHYARD/Rules)
2. [BPM Service Documentation](https://docs.jboss.org/author/display/SWITCHYARD/BPM)

