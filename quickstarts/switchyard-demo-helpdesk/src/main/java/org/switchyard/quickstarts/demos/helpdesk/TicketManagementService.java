/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.switchyard.quickstarts.demos.helpdesk;


/**
 * The Interface TicketManagementService.
 *
 * @author David Ward &lt;<a
 *         href="mailto:dward@jboss.org">dward@jboss.org</a>&gt; &copy; 2012 Red
 *         Hat Inc.
 */
public interface TicketManagementService {

    /**
     * Open ticket.
     *
     * @param ticket the ticket
     * @return the ticket ack
     */
    public TicketAck openTicket(Ticket ticket);

    /**
     * Approve ticket.
     *
     * @param ticket the ticket
     */
    public void approveTicket(Ticket ticket);

    /**
     * Close ticket.
     *
     * @param ticket the ticket
     */
    public void closeTicket(Ticket ticket);

    /**
     * Request details.
     *
     * @param ticket the ticket
     */
    public void requestDetails(Ticket ticket);

    /**
     * Reject ticket.
     *
     * @param ticket the ticket
     */
    public void rejectTicket(Ticket ticket);

}
