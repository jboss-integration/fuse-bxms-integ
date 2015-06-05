Drools-Spring-Camel Quickstart
==============================

To build this project use

    mvn install

To deploy this project into :

[JBoss Fuse](http://access.redhat.com/downloads) 

Start JBoss Fuse

    <JBoss Fuse Home>/bin/fuse

In the console, use the following commands

    features:addurl mvn:org.jboss.integration.fuse.quickstarts/karaf-features/${FUSE_BXMS_VERSION}/xml/features
    features:install fuse-bxms-camel-spring-drools-decision-table-camel

To see the results tail the JBoss Fuse log

    tail -f <JBoss Fuse Home> 
    
    2013-06-07 17:26:12,717 | INFO  | uteDecisionTable | Chilton   | 249 - org.apache.camel.camel-core - 2.10.3 | Cheese Stilton costs 10 EUR.
    2013-06-07 17:26:12,842 | INFO  | imer://testRoute | Home      | 249 - org.apache.camel.camel-core - 2.10.3 | Person Young Person is staying home
    2013-06-07 17:26:22,716 | INFO  | uteDecisionTable | Chilton   | 249 - org.apache.camel.camel-core - 2.10.3 | Cheese Stilton costs 10 EUR.
    2013-06-07 17:26:22,839 | INFO  | imer://testRoute | Bar       | 249 - org.apache.camel.camel-core - 2.10.3 | Person Old Person can go to the bar
