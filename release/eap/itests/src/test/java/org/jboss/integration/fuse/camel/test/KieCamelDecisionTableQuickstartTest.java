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
package org.jboss.integration.fuse.camel.test;

import junit.framework.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.switchyard.test.quickstarts.util.BRMSArquillianUtil;



@RunWith(Arquillian.class)
public class KieCamelDecisionTableQuickstartTest {

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return BRMSArquillianUtil.getArchive(BRMSArquillianUtil.QS_GID, 
                                             "spring-camel-drools-decision-table", 
                                             BRMSArquillianUtil.getSwitchYardVersion(),
                                             WebArchive.class, "war","eap");
    }

    @Test
    public void test() throws Exception {
        // Only testing that deployment is successful at this point.
        Assert.assertTrue(true);
    }
}
