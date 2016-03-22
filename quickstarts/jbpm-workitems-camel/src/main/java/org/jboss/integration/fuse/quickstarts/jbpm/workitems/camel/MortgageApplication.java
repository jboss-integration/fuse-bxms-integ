/*
 * Copyright 2016 Red Hat Inc. and/or its affiliates and other contributors.
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
package org.jboss.integration.fuse.quickstarts.jbpm.workitems.camel;


/**
 * The Class MortgageApplication.
 */
public class MortgageApplication {

    /** The id. */
    private int id;
    
    /** The income. */
    private int income; // applicants income
    
    /** The ammount. */
    private int ammount; // the ammount of mortgage
    
    /** The accepted. */
    private boolean accepted; // is application accepted

    /**
     * Gets the id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the income.
     *
     * @return the income
     */
    public int getIncome() {
        return income;
    }

    /**
     * Sets the income.
     *
     * @param income the new income
     */
    public void setIncome(int income) {
        this.income = income;
    }

    /**
     * Gets the ammount.
     *
     * @return the ammount
     */
    public int getAmmount() {
        return ammount;
    }

    /**
     * Sets the ammount.
     *
     * @param ammount the new ammount
     */
    public void setAmmount(int ammount) {
        this.ammount = ammount;
    }

    /**
     * Checks if is accepted.
     *
     * @return true, if is accepted
     */
    public boolean isAccepted() {
        return accepted;
    }

    /**
     * Sets the accepted.
     *
     * @param accepted the new accepted
     */
    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}
