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

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.kie.api.KieServices;
import org.kie.api.command.BatchExecutionCommand;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;

import java.util.ArrayList;
import java.util.List;

public class DroolsCommandHelper {
    
    public void insertAndFireAll(Exchange exchange) {
        final Message in = exchange.getIn();
        final Object body = in.getBody();

        final KieCommands kieCommands = KieServices.Factory.get().getCommands();

        final List<Command> commands = new ArrayList<Command>();
        // since KIE commands are JAXB-annotated, the body has to be JAXB annotated class as well
        commands.add(kieCommands.newInsert(body, "person"));
        commands.add(kieCommands.newFireAllRules());

        BatchExecutionCommand command = kieCommands.newBatchExecution(commands);
        in.setBody(command);
    }
}
