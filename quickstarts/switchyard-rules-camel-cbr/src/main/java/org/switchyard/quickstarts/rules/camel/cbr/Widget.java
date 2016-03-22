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
package org.switchyard.quickstarts.rules.camel.cbr;

import java.io.Serializable;


/**
 * The Class Widget.
 *
 * @author David Ward &lt;<a
 *         href="mailto:dward@jboss.org">dward@jboss.org</a>&gt; &copy; 2012 Red
 *         Hat Inc.
 */
@SuppressWarnings("serial")
public class Widget implements Serializable {

    /** The _id. */
    private String _id;

    /** The _box. */
    private Box _box;

    /**
     * Instantiates a new widget.
     */
    public Widget() {

    }

    /**
     * Instantiates a new widget.
     *
     * @param id the id
     */
    public Widget(String id) {
        _id = id;
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
     * Gets the id.
     *
     * @return the id
     */
    public String getId() {
        return _id;
    }

    /**
     * Gets the box.
     *
     * @return the box
     */
    public Box getBox() {
        return _box;
    }

    /**
     * Sets the box.
     *
     * @param box the new box
     */
    public void setBox(Box box) {
        _box = box;
    }

}
