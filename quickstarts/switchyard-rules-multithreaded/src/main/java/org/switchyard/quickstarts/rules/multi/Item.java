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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Item for WarehouseService.
 *
 * @author Magesh Kumar B <mageshbk@jboss.com> (C) 2012 Red Hat Inc.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "item")
public class Item {

    /** The _item id. */
    @XmlElement(name = "itemId", required = true)
    private Integer _itemId;
    
    /** The _name. */
    @XmlElement(name = "name", required = false)
    private String _name;

    /** The _price. */
    @XmlElement(name = "price", required = true)
    private Integer _price;

    /**
     * Instantiates a new item.
     */
    public Item() {
    }

    /**
     * Instantiates a new item.
     *
     * @param itemId the item id
     * @param name the name
     * @param price the price
     */
    public Item(Integer itemId, String name, Integer price) {
        _itemId = itemId;
        _name = name;
        _price = price;
    }

    /**
     * Gets the item id.
     *
     * @return the item id
     */
    public Integer getItemId() {
        return _itemId;
    }

    /**
     * Sets the item id.
     *
     * @param itemId the new item id
     */
    public void setItemId(Integer itemId) {
        _itemId = itemId;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return _name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        _name = name;
    }

    /**
     * Gets the price.
     *
     * @return the price
     */
    public Integer getPrice() {
        return _price;
    }

    /**
     * Sets the price.
     *
     * @param _price the new price
     */
    public void setPrice(Integer _price) {
        this._price = _price;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{ItemId: ");
        builder.append(_itemId);
        builder.append(", name:");
        builder.append(_name);
        builder.append(", price:");
        builder.append(_price);
        builder.append("}");
        return builder.toString();
    }
}
