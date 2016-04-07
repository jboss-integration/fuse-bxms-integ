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

import org.kie.api.definition.type.FactType;

public class PersonFactoryGav {

    private PersonFactoryGav() {
    }

    public static Object createOldPerson(FactType personType) throws IllegalAccessException, InstantiationException {

        Object person = personType.newInstance();
        personType.set(person, "name", "Old person");
        personType.set(person, "age", 21);

        return person;
    }

    public static Object createYoungPerson(FactType personType) throws IllegalAccessException, InstantiationException {
        Object person = personType.newInstance();
        personType.set(person, "name", "Young person");
        personType.set(person, "age", 18);

        return person;
    }
}
