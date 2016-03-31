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
 * The Class Ticket.
 *
 * @author David Ward &lt;<a
 *         href="mailto:dward@jboss.org">dward@jboss.org</a>&gt; &copy; 2012 Red
 *         Hat Inc.
 */
@SuppressWarnings("serial")
public class Ticket implements Serializable {

    /** The _id. */
    private String _id;
    
    /** The _status. */
    private String _status;
    
    /** The _details. */
    private String _details;

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
     * @param id the new id
     */
    public void setId(String id) {
        _id = id;
    }

    /**
     * Gets the status.
     *
     * @return the status
     */
    public String getStatus() {
        return _status;
    }

    /**
     * Sets the status.
     *
     * @param status the new status
     */
    public void setStatus(String status) {
        _status = status;
    }

    /**
     * Gets the details.
     *
     * @return the details
     */
    public String getDetails() {
        return _details;
    }

    /**
     * Sets the details.
     *
     * @param details the new details
     */
    public void setDetails(String details) {
        _details = details;
    }

}
