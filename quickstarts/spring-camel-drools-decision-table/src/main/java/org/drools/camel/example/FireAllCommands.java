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
package org.drools.camel.example;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.drools.core.command.impl.GenericCommand;
import org.drools.core.command.runtime.BatchExecutionCommandImpl;
import org.drools.core.command.runtime.rule.FireAllRulesCommand;
import org.drools.core.command.runtime.rule.InsertObjectCommand;

import java.util.List;


/**
 * The Class FireAllCommands.
 */
public class FireAllCommands {

    /**
     * Generate command.
     *
     * @param exchange the exchange
     * @throws Exception the exception
     */
    public void generateCommand(Exchange exchange) throws Exception {
        System.out.println(">> We will fire all rules commands");
        FireAllRulesCommand fireAllRulesCommand = new FireAllRulesCommand();
        exchange.getIn().setBody(fireAllRulesCommand);
    }

    /**
     * Insert and fire all.
     *
     * @param exchange the exchange
     */
    public void insertAndFireAll(Exchange exchange) {
        final Message in = exchange.getIn();
        final Object body = in.getBody();

        BatchExecutionCommandImpl command = new BatchExecutionCommandImpl();
        final List<GenericCommand<?>> commands = command.getCommands();
        commands.add(new InsertObjectCommand(body, "obj1"));
        commands.add(new FireAllRulesCommand());

        in.setBody(command);
    }
}
