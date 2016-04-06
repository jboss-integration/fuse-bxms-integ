Introduction
============
This quickstart demonstrates the sharing of the the same KSession on the server by different Threads calls.
Different Threads will populate the Knowledge Base Shared Session and then the Rules Service will return
different values depending on the Items inserted.
The rules are specified using a DRL resource in the manifest.
The input is the incoming message content (an "Applicant").
The output is a boolean, and mapped using the implicit "globals" Map.

It uses Rest Services to call the Rules Service.

There are 2 main Services:

OrderService --> get the best item from the items inserted. It calls the RulesComponent Service to get the best choice by price.

WarehouseService --> allow to insert/get/remove items.

In the example there are different threads running at the same time. As well, there are some Sleeps to see what happen. 



Preqrequisites 
==============
Maven

Running the quickstart
======================


EAP
----------
1. Start EAP in standalone mode:

        ${AS}/bin/standalone.sh

2. Build and deploy the quickstart: 

        mvn install -Pdeploy

3. Submit a webservice request to invoke the SOAP gateway.  There are a number of ways to do this :
    - Submit a request with your preferred SOAP client - src/test/resources/xml contains 
      sample requests and the responses that you should see
    - Use the simple bundled SOAP client and the sample request XML e.g.
<br/>
```
            mvn exec:java
```
<br/>
    - SOAP-UI : Use the wsdl for this project (src/main/resources/wsdl/OrderService.wsdl) to 
      create a soap-ui project. Use the sample request (src/test/resources/xml/soap-request-pass.xml) 
      as an example of a sample request.    See the "Expected Output" section for the expected results.


4. Undeploy the quickstart:

mvn clean -Pdeploy


FUSE
----------
1. Start FUSE:

${FUSE_HOME}/bin/karaf

2. Ensure that the drools and switchyard compatible features URL files have been added to your Fuse instance. 
   In case they are not added then:

    features:addurl mvn:org.drools/drools-karaf-features/${version.org.kie}/${drools.karaf.features.classifier}

3. Add the features URL for the respective version of BXMS.

JBossFuse:karaf@root> features:addurl mvn:org.jboss.integration.fuse.quickstarts/karaf-features/${project.version}/xml/features


4. Install the feature for the Rules Interview quickstart :

JBossFuse:karaf@root> features:install fuse-bxms-quickstart-switchyard-rules-multithread

5. To submit a rest request, run the quickstart client :
<br/>
```
mvn exec:java -Pkaraf
```
<br/>

6. Undeploy the quickstart:

JBossFuse:karaf@root> features:uninstall fuse-bxms-quickstart-switchyard-rules-multithread


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


4. Install the feature for the Rules Interview quickstart :

karaf@root> features:install fuse-bxms-quickstart-switchyard-rules-multithreaded

5. To submit a rest request, run the quickstart client :
<br/>
```
mvn exec:java -Pkaraf
```
<br/>

6. Undeploy the quickstart:

karaf@root> features:uninstall fuse-bxms-quickstart-switchyard-rules-interview


Possible Expected Output (As the thread execution is not sequencial)
===============
```
THREAD: 1 ITEM SELECTED: {"itemId":1,"price":400,"name":"DELLXX"}
THREAD: 0 ITEM SELECTED: {"itemId":1,"price":400,"name":"DELLXX"}
THREAD: 2 ITEM SELECTED: {"itemId":6,"price":380,"name":"LENOVO ZZ"}
THREAD: 3 ITEM SELECTED: {"itemId":9,"price":50,"name":"LENOVO XY"} 
THREAD: 4 ITEM SELECTED: {"itemId":9,"price":50,"name":"LENOVO XY"}



## Further Reading

1. [Configuration Documentation](https://docs.jboss.org/author/display/SWITCHYARD/Configuration)
2. [Rules Service Documentation](https://docs.jboss.org/author/display/SWITCHYARD/Rules)
