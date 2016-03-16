Introduction
============
This quickstart demonstrates the usage of the bpm component.
This example shows a bpm component, ProcessOrder, which exposes a business process flow.

The incoming messageId is mapped from the SwitchYard Context and into a process variable
using an MVEL expression, and printed out via a BPMN onEntry-script of the Inventory service.

This example is invoked through a SOAP gateway binding.

If you would like to log the process execution, uncomment these lines in
src/main/resources/META-INF/switchyard.xml:
```
<listeners>
    <listener class="org.drools.event.DebugProcessEventListener"/>
</listeners>
```

![BPM Service Quickstart](https://github.com/jboss-switchyard/quickstarts/raw/master/bpm-service/bpm-service.jpg)


Warning and Error Logs
======================
You may see some Warning and Error logs the first time the application is deployed. 

You ll see this logs when the app is deployed:

        ERROR [stderr] (MSC service thread 1-8) ScriptEngineManager providers.next(): javax.script.ScriptEngineFactory: Provider bsh.engine.BshScriptEngineFactory not found
        ERROR [stderr] (MSC service thread 1-8) ScriptEngineManager providers.next(): javax.script.ScriptEngineFactory: Provider org.codehaus.groovy.jsr223.GroovyScriptEngineFactory not found
        ERROR [stderr] (MSC service thread 1-8) ScriptEngineManager providers.next(): javax.script.ScriptEngineFactory: Provider org.python.jsr223.PyScriptEngineFactory not found
        ERROR [stderr] (MSC service thread 1-8) ScriptEngineManager providers.next(): javax.script.ScriptEngineFactory: Provider com.google.code.scriptengines.js.javascript.RhinoScriptEngineFactory not found
        ERROR [stderr] (MSC service thread 1-8) ScriptEngineManager providers.next(): javax.script.ScriptEngineFactory: Provider com.google.code.scriptengines.js.javascript.EmbeddedRhinoScriptEngineFactory not found

This is caused by:

        https://issues.jboss.org/browse/WFCORE-719
        https://issues.jboss.org/browse/ENTESB-3765

Once these issues are solved and released, then the workaround is to uncomment this line on the pom.xml of this project:

         <!--Dependencies>org.apache.camel.script.jruby, org.apache.camel.script.rhino, org.apache.camel.script.python, org.apache.camel.script.groovy, org.beanshell</Dependencies-->


As well you can see other annoying logs:

         ERROR | yard Extender: 3 | ExtensibleXmlParser              | 290 - org.drools.core - 6.2.0.20141207-0957 | (null: 2, 674): cvc-elt.1.a: Cannot find the declaration of element 'bpmn2:definitions'.
         WARN  | yard Extender: 3 | ExtensibleXmlParser              | 290 - org.drools.core - 6.2.0.20141207-0957 | (null: 3, 100): schema_reference.4: Failed to read schema document 'BPMN20.xsd', because 1) could not find the document; 2) the document could not be read; 3) the root element of the document is not <xsd:schema>.
         WARN  | yard Extender: 3 | ExtensibleXmlParser              | 290 - org.drools.core - 6.2.0.20141207-0957 | (null: 4, 70): schema_reference.4: Failed to read schema document 'BPMN20.xsd', because 1) could not find the document; 2) the document could not be read; 3) the root element of the document is not <xsd:schema>.
         WARN  | yard Extender: 3 | ExtensibleXmlParser              | 290 - org.drools.core - 6.2.0.20141207-0957 | (null: 5, 63): schema_reference.4: Failed to read schema document 'BPMN20.xsd', because 1) could not find the document; 2) the document could not be read; 3) the root element of the document is not <xsd:schema>.
         WARN  | yard Extender: 3 | ExtensibleXmlParser              | 290 - org.drools.core - 6.2.0.20141207-0957 | (null: 6, 108): schema_reference.4: Failed to read schema document 'BPMN20.xsd', because 1) could not find the document; 2) the document could not be read; 3) the root element of the document is not <xsd:schema>.

There is a jira related to this error on JBPM side:

         https://issues.jboss.org/browse/JBPM-3716


These logs are not causing any error. The quickstart works as expected.

Running the quickstart
======================


EAP
----------
1. Start EAP in standalone mode:

        ${AS}/bin/standalone.sh

2. Build and deploy the Quickstart : 

        mvn install -Pdeploy

3. Submit a webservice request to invoke the SOAP gateway.  There are a number of ways to do this :
    - Submit a request with your preferred SOAP client - src/test/resources/xml contains sample 
      requests and the responses that you should see
    - Use the simple bundled SOAP client and the sample request XML e.g.
<br/>
```
            mvn exec:java
```
<br/>
    - SOAP-UI : Use the wsdl for this projects (src/main/resources/wsdl/) to create a soap-ui 
      project.  Use the sample request (src/test/resources/xml/soap-request.xml) as an example 
      of a sample request.   See the "Expected Output" section for the expected results.

4. Undeploy the quickstart:

        mvn clean -Pdeploy


FUSE
----------
1. Start FUSE:

${FUSE_HOME}/bin/karaf


2. Ensure that the drools and switchyard compatible features URL files have been added to your Fuse instance. 
   In case they are not added then:

    features:addurl mvn:org.drools/drools-karaf-features/${version.org.kie}/xml/features


3. Add the features URL for the respective version of BXMS.

JBossFuse:karaf@root> features:addurl mvn:org.jboss.integration.fuse.quickstarts/karaf-features/${project.version}/xml/features


4. Install the feature for the bpm-service quickstart :

JBossFuse:karaf@root> features:install fuse-bxms-quickstart-switchyard-bpm-service

5. To submit a webservice request to invoke the SOAP gateway, run the quickstart client :
<br/>
```
mvn exec:java -Pkaraf
```
<br/>

6. Undeploy the quickstart:

JBossFuse:karaf@root> features:uninstall fuse-bxms-quickstart-switchyard-bpm-service



KARAF
----------
1. Start the Karaf server :

${KARAF_HOME}/bin/karaf


2. Ensure that the drools and switchyard compatible features URL files have been added to your Karaf instance. 
   In case they are not added then:

    features:addurl mvn:org.switchyard.karaf/switchyard/${version.switchyard}/xml/features
    features:addurl mvn:org.drools/drools-karaf-features/${version.org.kie}/xml/features


3. Add the features URL for the respective version of BXMS. 

karaf@root> features:addurl mvn:org.jboss.integration.fuse.quickstarts/karaf-features/${project.version}/xml/features


4. Install the feature for the bpm-service quickstart :

karaf@root> features:install fuse-bxms-quickstart-switchyard-bpm-service

5. To submit a webservice request to invoke the SOAP gateway, run the quickstart client :
<br/>
```
mvn exec:java -Pkaraf
```
<br/>

6. Undeploy the quickstart:

karaf@root> features:uninstall fuse-bxms-quickstart-switchyard-bpm-service



Expected Output:
================

```
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
<SOAP-ENV:Header/>
<SOAP-ENV:Body>
<ns2:submitOrderResponse xmlns:ns2="urn:switchyard-quickstart:bpm-service:1.0">
<orderId>test1</orderId>
<accepted>true</accepted>
<status>Thanks for your order, it has been shipped!</status>
</ns2:submitOrderResponse>
</SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```


Test Properties:
================
This quickstart also shows (albeit commented out) how one could write a test where test properties within configurations are programmatically overridden.  In switchyard.xml, you see that the binding.soap element's socketAddr port is a dynamic property, like so:
```
        <socketAddr>localhost:${soapPort:18001}</socketAddr>
```
In WebServiceTest.java, you can see that by uncommenting the commented code, you could set the value of the soapPort property to 18002, and that will be used instead of the default port of 18001.  One could also run the test with a system property overriding the value, which takes highest precedence. (i.e.: -DsoapPort=18003)

Additionally, you can see how a domain property (userName) is retrieved from a system property (${user.name}), which is then mapped into a process instance variable (userName), which can then be used inside the process itself.

## Further Reading

1. [BPM Service Documentation](https://docs.jboss.org/author/display/SWITCHYARD/BPM)
