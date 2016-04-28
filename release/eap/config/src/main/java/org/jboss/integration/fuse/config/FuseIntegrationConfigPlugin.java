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

import static org.wildfly.extras.config.LayerConfig.Type.INSTALLING;
import static org.wildfly.extras.config.LayerConfig.Type.REQUIRED;
import static org.wildfly.extras.config.NamespaceConstants.NS_DOMAIN;

import java.util.Arrays;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.Text;
import org.wildfly.extras.config.ConfigContext;
import org.wildfly.extras.config.ConfigPlugin;
import org.wildfly.extras.config.ConfigSupport;
import org.wildfly.extras.config.LayerConfig;
import org.wildfly.extras.config.NamespaceRegistry;

public class FuseIntegrationConfigPlugin implements ConfigPlugin {

    public static final String NS_SWITCHYARD = "urn:jboss:domain:switchyard";

    private NamespaceRegistry registry;

    public FuseIntegrationConfigPlugin() {
        registerNamespaceVersions(new NamespaceRegistry());
    }

    public FuseIntegrationConfigPlugin(NamespaceRegistry registry) {
        registerNamespaceVersions(registry);
    }

    @Override
    public String getConfigName() {
        return "fuse-integration";
    }

    @Override
    public List<LayerConfig> getLayerConfigs() {
        return Arrays.asList(new LayerConfig("fuse", REQUIRED, -10), new LayerConfig("bpms", REQUIRED, -9), new LayerConfig("soa", REQUIRED, -8), new LayerConfig(
                "fuse-integration", INSTALLING, -7)
        );
    }

    @Override
    public void applyDomainConfigChange(ConfigContext context, boolean enable) {
    }

    @Override
    public void applyStandaloneConfigChange(ConfigContext context, boolean enable) {
        updateSwitchyardModules(enable, context);
    }

    private void updateSwitchyardModules(boolean enable, ConfigContext context) {
        Document doc = context.getDocument();
        Namespace[] domainNamespaces = registry.getNamespaces(NS_DOMAIN);
        Namespace[] switchYardNamespaces = registry.getNamespaces(NS_SWITCHYARD);

        List<Element> profiles = ConfigSupport.findProfileElements(doc, domainNamespaces);
        for (Element profile : profiles) {
            Element switchyard = ConfigSupport.findChildElement(profile, "subsystem", switchYardNamespaces);
            ConfigSupport.assertExists(switchyard, "Did not find the switchyard subsystem");
            Element modules = ConfigSupport.findChildElement(switchyard, "modules", switchYardNamespaces);
            ConfigSupport.assertExists(modules, "Did not find the <modules> element");

            updateSwitchyardModule(enable, modules, "org.fuse.integration.switchyard.component.bpm", "org.switchyard.component.bpm.deploy.BPMComponent");
            updateSwitchyardModule(enable, modules, "org.fuse.integration.switchyard.component.rules", "org.switchyard.component.rules.deploy.RulesComponent");
        }
    }

    private void updateSwitchyardModule(boolean enable, Element modules, String id, String className) {
        Namespace[] switchYardNamespaces = registry.getNamespaces(NS_SWITCHYARD);
        Element module = ConfigSupport.findElementWithAttributeValue(modules, "module", "identifier", id, switchYardNamespaces);
        if (enable && module == null) {
            modules.addContent(new Text("    "));
            modules.addContent(new Element("module", modules.getNamespace()).setAttribute("identifier", id).setAttribute("implClass", className));
            modules.addContent(new Text("\n    "));
        }
        if (!enable && module != null) {
            module.getParentElement().removeContent(module);
        }
    }

    private void registerNamespaceVersions(NamespaceRegistry registry) {
        this.registry = registry;
        registry.registerNamespace(NS_DOMAIN, "1.8");
        registry.registerNamespace(NS_DOMAIN, "1.7");
        registry.registerNamespace(NS_SWITCHYARD, "1.0");
    }
}
