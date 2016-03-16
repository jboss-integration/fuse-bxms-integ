Drools-Spring-Camel Quickstart
==============================
This quickstart shows the integration between Spring, Camel and Drools decision tables. Sample decision table and DRL
file contain rules which are then used to create knowledge session, as part of the Spring's application context XML file. 

Camel routes are also defined inside the Spring's application context and they show different integration features like
passing (inserting) the Body of the message as a POJO to Drools engine or creating a Drools Command wrapping the Body
of the message with the help of the custom DroolsCommandHelperBean (included in this project), and passing this Drools
Command to the ruleset for execution.

FUSE
----------
1. Start FUSE:

${FUSE_HOME}/bin/karaf


2. Ensure that the drools and switchyard compatible features URL files have been added to your Fuse instance. 
   In case they are not added then:

    features:addurl mvn:org.drools/drools-karaf-features/${version.org.kie}/xml/features


3. Add the features URL for the respective version of BXMS.

JBossFuse:karaf@root> features:addurl mvn:org.jboss.integration.fuse.quickstarts/karaf-features/${project.version}/xml/features


4. Install the feature for the Blueprint camel drools decision table quickstart :

JBossFuse:karaf@root> features:install fuse-bxms-quickstart-camel-spring-drools-decision-table

5. Check the output. See output at the end of the document.

6. Undeploy the quickstart:

JBossFuse:karaf@root> features:uninstall fuse-bxms-quickstart-camel-spring-drools-decision-table



EAP
----------
1. Start EAP in standalone mode:

        ${AS}/bin/standalone.sh

2. Build and deploy the Quickstart : 

        mvn install -Pdeploy

3. Check the output. See output at the end of the document.

4. Undeploy the quickstart:

        mvn clean -Pdeploy
 


To see the results tail the JBoss Fuse log

    tail -f <JBoss Fuse Home> 
    
    2013-06-07 17:26:12,717 | INFO  | uteDecisionTable | Chilton   | 249 - org.apache.camel.camel-core - 2.10.3 | Cheese Stilton costs 10 EUR.
    2013-06-07 17:26:12,842 | INFO  | imer://testRoute | Home      | 249 - org.apache.camel.camel-core - 2.10.3 | Person Young Person is staying home
    2013-06-07 17:26:22,716 | INFO  | uteDecisionTable | Chilton   | 249 - org.apache.camel.camel-core - 2.10.3 | Cheese Stilton costs 10 EUR.
    2013-06-07 17:26:22,839 | INFO  | imer://testRoute | Bar       | 249 - org.apache.camel.camel-core - 2.10.3 | Person Old Person can go to the bar
