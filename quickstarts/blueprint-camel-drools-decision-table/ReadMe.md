Drools-Blueprint-Camel Quickstart
=================================

This quickstart shows integration between Blueprint, Camel and Drools. Decision table and DRL file contain simple rules
which are used to create knowledge session via Blueprint configuration file. Camel routes, defined via Blueprint
as well, are then used to e.g. pass (insert) the Body of the message as a POJO to Drools engine for execution.

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

JBossFuse:karaf@root> features:install fuse-bxms-camel-blueprint-drools-decision-table

5. Check the output. See output at the end of the document.

6. Undeploy the quickstart:

JBossFuse:karaf@root> features:uninstall fuse-bxms-camel-blueprint-drools-decision-table




    
    2013-06-07 17:26:12,717 | INFO  | uteDecisionTable | Chilton   | 249 - org.apache.camel.camel-core - 2.10.3 | Cheese Stilton costs 10 EUR.
    2013-06-07 17:26:12,842 | INFO  | imer://testRoute | Home      | 249 - org.apache.camel.camel-core - 2.10.3 | Person Young Person is staying home
    2013-06-07 17:26:22,716 | INFO  | uteDecisionTable | Chilton   | 249 - org.apache.camel.camel-core - 2.10.3 | Cheese Stilton costs 10 EUR.
    2013-06-07 17:26:22,839 | INFO  | imer://testRoute | Bar       | 249 - org.apache.camel.camel-core - 2.10.3 | Person Old Person can go to the bar
