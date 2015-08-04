Introduction
============
This quickstart demonstrates the reloading feature of kie/drools and switchyard.

It shows how a switchyard application can be deployed and it uses a Container Release reference. This means that the drools files are located on a external maven artifact.

The Junit test initially is ignored. It is an arquillian test that require an EAP instance well configured being started.

But this important feature of switchyard can be tested executing the /release/eap/itest. There is a specific test that deploys an artifact on the local maven repo, and later redeploy the same artifact (with different drools rules).

Preqrequisites 
==============
Maven
EAP with Switchyard and Fuse-integration installed


