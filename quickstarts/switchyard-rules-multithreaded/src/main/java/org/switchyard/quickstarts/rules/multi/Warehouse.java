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
package org.switchyard.quickstarts.rules.multi;


/**
 * Interface for Warehouse.
 *
 * @author Magesh Kumar B <mageshbk@jboss.com> (C) 2012 Red Hat Inc.
 */
public interface Warehouse {

    /**
     * Gets the item.
     *
     * @param itemId the item id
     * @return the item
     */
    Item getItem(Integer itemId);

    /**
     * Adds the item.
     *
     * @param item the item
     * @return the string
     * @throws Exception the exception
     */
    String addItem(Item item) throws Exception;

    /**
     * Update item.
     *
     * @param item the item
     * @return the string
     * @throws Exception the exception
     */
    String updateItem(Item item) throws Exception;

    /**
     * Removes the item.
     *
     * @param itemId the item id
     * @return the string
     * @throws Exception the exception
     */
    String removeItem(Integer itemId) throws Exception;

    /**
     * Gets the item count.
     *
     * @return the item count
     */
    Integer getItemCount();
}
