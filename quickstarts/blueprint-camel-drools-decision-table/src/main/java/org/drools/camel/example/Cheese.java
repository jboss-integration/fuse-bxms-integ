/*
 * Copyright 2005 JBoss Inc
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.camel.example;


/**
 * The Class Cheese.
 */
public class Cheese {

    /** The type. */
    private String type;

    /** The price. */
    private int price;

    /**
     * Instantiates a new cheese.
     */
    public Cheese() {

    }

    /**
     * Instantiates a new cheese.
     *
     * @param type the type
     * @param price the price
     */
    public Cheese(final String type, final int price) {
        super();
        this.type = type;
        this.price = price;
    }

    /**
     * Gets the price.
     *
     * @return the price
     */
    public int getPrice() {
        return this.price;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public String getType() {
        return this.type;
    }

    /**
     * Sets the price.
     *
     * @param price the new price
     */
    public void setPrice(final int price) {
        this.price = price;
    }

    /**
     * Sets the type.
     *
     * @param type the new type
     */
    public void setType(String type) {
        this.type = type;
    }

}
