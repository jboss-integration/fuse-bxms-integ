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

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

//import org.switchyard.component.common.label.EndpointLabel;
//import org.switchyard.component.resteasy.composer.RESTEasyContextMapper;
//import javax.inject.Inject;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.switchyard.component.bean.Reference;
//import org.switchyard.Context;
import org.switchyard.component.bean.Service;


/**
 * A WarehouseService implementation.
 *
 * @author Magesh Kumar B <mageshbk@jboss.com> (C) 2012 Red Hat Inc.
 */
@Service(WarehouseService.class)
public class WarehouseServiceImpl implements WarehouseService {

    /** The order decision. */
    @Inject
    @Reference
    private OrderDecision orderDecision;

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(WarehouseService.class);
    
    /** The Constant SUCCESS. */
    private static final String SUCCESS = "SUCCESS";
    
    /** The items. */
    private final ConcurrentMap<Integer, Item> items = new ConcurrentHashMap<Integer, Item>();

    @Override
    public Item getItem(Integer itemId) {
        System.out.println("++++++ getItem " + itemId);
        Item item = this.items.get(itemId);
        // TODO: Currently not possible to set property on return path for CDI
        // Beans
        /*
         * if (item == null) {
         * context.setProperty(RESTEasyContextMapper.HTTP_RESPONSE_STATUS,
         * 404).addLabels(new String[]{EndpointLabel.HTTP.label()}); }
         */
        return item;
    }

    @Override
    public String addItem(Item item) throws Exception {
        if (getItem(item.getItemId()) != null) {
            throw new RuntimeException("Item " + item.getItemId() + " already exists!");
        }

        this.items.put(item.getItemId(), item);
        orderDecision.addObject(item);
        LOGGER.info("Added item " + item.getItemId() + " with name " + item.getName());
        return SUCCESS;
    }

    @Override
    public String updateItem(Item item) throws Exception {
        Item item2 = this.items.get(item.getItemId());
        item2.setName(item.getName());
        LOGGER.info("Updated item " + item.getItemId() + " with name " + item.getName());
        return SUCCESS;
    }

    @Override
    public String removeItem(Integer itemId) throws Exception {
        if (getItem(itemId) == null) {
            throw new RuntimeException("Item " + itemId + " not found!");
        }
        this.items.remove(itemId);
        LOGGER.info("Removed item " + itemId);
        return SUCCESS;
    }

    @Override
    public Integer getItemCount() {
        System.out.println("++++++ getItemCount ");
        return this.items.size();
    }
}
