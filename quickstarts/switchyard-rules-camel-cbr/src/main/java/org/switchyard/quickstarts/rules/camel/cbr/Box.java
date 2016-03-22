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
 * The Class Box.
 *
 * @author David Ward &lt;<a
 *         href="mailto:dward@jboss.org">dward@jboss.org</a>&gt; &copy; 2012 Red
 *         Hat Inc.
 */
@SuppressWarnings("serial")
public class Box implements Serializable {

    /** The _widget. */
    private Widget _widget;

    /** The _destination. */
    private String _destination;

    /**
     * Instantiates a new box.
     */
    public Box() {

    }

    /**
     * Instantiates a new box.
     *
     * @param widget the widget
     */
    public Box(Widget widget) {
        _widget = widget;
        _widget.setBox(this);
    }

    /**
     * Sets the widget.
     *
     * @param widget the new widget
     */
    public void setWidget(Widget widget) {
        _widget = widget;
        _widget.setBox(this);
    }

    /**
     * Gets the widget.
     *
     * @return the widget
     */
    public Widget getWidget() {
        return _widget;
    }

    /**
     * Gets the destination.
     *
     * @return the destination
     */
    public String getDestination() {
        return _destination;
    }

    /**
     * Sets the destination.
     *
     * @param destination the new destination
     */
    public void setDestination(String destination) {
        _destination = destination;
    }

}
