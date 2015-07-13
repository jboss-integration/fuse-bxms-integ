Introduction
============
This quickstart demonstrates the usage of the drools channels from switchyard.

It demostrates the usage of the SwitchYardServiceChannel. It allows to redirect the output of a drools channel as an input of a Switchyard Service.

In the example there is a drools service that includes the input to 2 channels. One of them is implemented as a Java Service. The other one is implemented as another drools service.

It allows to show that 2 different KieSession can be executed together. In this example there is a KieSession(ChannelService) that calls another KieSession (WorkerB service).

Preqrequisites 
==============
Maven

Running the quickstart
======================

mvn clean install

Expected output:

service: Channel, payload: message
service: WorkerABean, payload: message.A
service: WorkerB, payload: message.B

