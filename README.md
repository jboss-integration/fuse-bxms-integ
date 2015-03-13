fuse-bxms-integ
===============

A code repository for the Fuse and BxMS integration components.


SWITCHYARD
-------------------

This module contains all the swithyard brms components and quickstarts. 

The default install compiles them.

The deployment of switchyard BRMS modules can be done specifying the profile -Prelease.  Switchyard BRMS modules can be 
deployed in different environments:

Switchyard EAP Deployment
--------------------------
It requires an instance of EAP contains previously installed Switchyard core modules

          mvn clean install -Peap,release -Deap.home=${EAP.HOME}

Switchyard Karaf Deployment
----------------------------
          mvn clean install -Pkaraf,release


For executing the Switchyard Quickstarts enter inside of switchyard/quickstarts and check the README file that is included in every quickstart.
