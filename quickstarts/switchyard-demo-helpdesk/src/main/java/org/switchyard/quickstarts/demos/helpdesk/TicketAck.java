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

import java.io.Serializable;


/**
 * The Class TicketAck.
 *
 * @author David Ward &lt;<a
 *         href="mailto:dward@jboss.org">dward@jboss.org</a>&gt; &copy; 2012 Red
 *         Hat Inc.
 */
@SuppressWarnings("serial")
public class TicketAck implements Serializable {

    /** The _id. */
    private String _id;
    
    /** The _received. */
    private boolean _received;

    /**
     * Gets the id.
     *
     * @return the id
     */
    public String getId() {
        return _id;
    }

    /**
     * Sets the id.
     *
     * @param id the id
     * @return the ticket ack
     */
    public TicketAck setId(String id) {
        _id = id;
        return this;
    }

    /**
     * Checks if is received.
     *
     * @return true, if is received
     */
    public boolean isReceived() {
        return _received;
    }

    /**
     * Sets the received.
     *
     * @param received the received
     * @return the ticket ack
     */
    public TicketAck setReceived(boolean received) {
        _received = received;
        return this;
    }

}
