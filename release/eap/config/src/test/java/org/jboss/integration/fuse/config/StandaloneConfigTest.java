/*
 * Copyright 2015 JBoss Inc
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
package org.jboss.integration.fuse.config;

import java.net.URL;
import java.nio.file.Paths;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.junit.Assert;
import org.junit.Test;
import org.wildfly.extras.config.ConfigContext;
import org.wildfly.extras.config.ConfigPlugin;
import org.wildfly.extras.config.ConfigSupport;
import org.wildfly.extras.config.NamespaceRegistry;

import static org.jboss.integration.fuse.config.FuseIntegrationConfigPlugin.NS_SWITCHYARD;
import static org.wildfly.extras.config.NamespaceConstants.NS_DOMAIN;

public class StandaloneConfigTest {

    @Test
    public void testStandaloneConfig() throws Exception {

        URL resurl = StandaloneConfigTest.class.getResource("/standalone.xml");
        SAXBuilder jdom = new SAXBuilder();
        Document doc = jdom.build(resurl);

        NamespaceRegistry registry = new NamespaceRegistry();
        ConfigPlugin plugin = new FuseIntegrationConfigPlugin(registry);

        Namespace[] domain = registry.getNamespaces(NS_DOMAIN);
        Namespace[] switchyard = registry.getNamespaces(NS_SWITCHYARD);

        ConfigContext context = ConfigSupport.createContext(null, Paths.get(resurl.toURI()), doc);
        plugin.applyStandaloneConfigChange(context, true);

        // Verify extension
        Element element = ConfigSupport.findElementWithAttributeValue(doc.getRootElement(), "extension", "module", "org.switchyard", domain);
        Assert.assertNotNull("Extension not null", element);

        List<Element> profiles = ConfigSupport.findProfileElements(doc, domain);

        // Verify SwitchYard
        element = ConfigSupport.findChildElement(profiles.get(0), "subsystem", switchyard);
        Assert.assertNotNull("switchyard not null", element);

        // Verify bxms switchyard modules
        Element modules = ConfigSupport.findChildElement(element, "modules", switchyard);
        Element moduleBpm = ConfigSupport.findElementWithAttributeValue(modules, "module", "identifier", "org.fuse.integration.switchyard.component.bpm", switchyard);
        Assert.assertNotNull("BPM module not null", moduleBpm);

        Element moduleRules = ConfigSupport.findElementWithAttributeValue(modules, "module", "identifier", "org.fuse.integration.switchyard.component.rules", switchyard);
        Assert.assertNotNull("Rules module not null", moduleRules);

        XMLOutputter output = new XMLOutputter();
        output.setFormat(Format.getRawFormat());
        System.out.println(output.outputString(doc));
    }
}
