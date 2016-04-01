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
package org.switchyard.quickstarts.rules.interview;


/**
 * The Class Applicant.
 *
 * @author David Ward &lt;<a
 *         href="mailto:dward@jboss.org">dward@jboss.org</a>&gt; &copy; 2012 Red
 *         Hat Inc.
 */
public class Applicant {

    /** The name. */
    private String name;

    /** The age. */
    private int age;

    /**
     * Instantiates a new applicant.
     */
    public Applicant() {
        this("", 0);
    }

    /**
     * Instantiates a new applicant.
     *
     * @param name the name
     * @param age the age
     */
    public Applicant(String name, int age) {
        setName(name);
        setAge(age);
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the age.
     *
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the age.
     *
     * @param age the new age
     */
    public void setAge(int age) {
        this.age = age;
    }

}
