/*
 * Copyright 2012 Red Hat
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.jboss.integration.fuse.karaf.itest.kiecamel.tools;

import org.jboss.integration.fuse.karaf.itest.kiecamel.model.Cheese;


public class CheeseFactory {

    private CheeseFactory() {
    }

    public static Cheese createStilton() {
        Cheese cheese = new Cheese();
        cheese.setType("stilton");
        cheese.setPrice(10);
        return cheese;
    }

    public static Cheese createCheddar() {
        Cheese cheese = new Cheese();
        cheese.setType("cheddar");
        cheese.setPrice(50);
        return cheese;
    }    
}
