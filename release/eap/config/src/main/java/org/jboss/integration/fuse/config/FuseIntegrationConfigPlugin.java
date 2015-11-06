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

import static org.wildfly.extension.camel.config.LayerConfig.Type.INSTALLING;
import static org.wildfly.extension.camel.config.LayerConfig.Type.REQUIRED;
import static org.wildfly.extension.camel.config.WildFlyCamelConfigPlugin.NS_DOMAIN;

import java.util.Arrays;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.Text;
import org.wildfly.extension.camel.config.ConfigContext;
import org.wildfly.extension.camel.config.ConfigPlugin;
import org.wildfly.extension.camel.config.ConfigSupport;
import org.wildfly.extension.camel.config.LayerConfig;

/**
 */
public class FuseIntegrationConfigPlugin implements ConfigPlugin {

    private static final Namespace NS_SWITCHYARD = Namespace.getNamespace("urn:jboss:domain:switchyard:1.0");
    
    @Override
    public String getConfigName() {
        return "bpms";
    }

    @Override
    public List<LayerConfig> getLayerConfigs() {
        return Arrays.asList(new LayerConfig("fuse", REQUIRED, -10), new LayerConfig("bpms", INSTALLING, -9), new LayerConfig("soa", REQUIRED, -8), new LayerConfig(
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

    public static void updateSwitchyardModules(boolean enable, ConfigContext context) {
        Document doc = context.getDocument();
        List<Element> profiles = ConfigSupport.findProfileElements(doc, NS_DOMAIN);
        for (Element profile : profiles) {
            Element switchyard = profile.getChild("subsystem", NS_SWITCHYARD);
            ConfigSupport.assertExists(switchyard, "Did not find the switchyard subsystem");
            Element modules = switchyard.getChild("modules", NS_SWITCHYARD);
            ConfigSupport.assertExists(modules, "Did not find the <modules> element");

            updateSwitchyardModule(enable, modules, "org.fuse.integration.switchyard.component.bpm", "org.switchyard.component.bpm.deploy.BPMComponent");
            updateSwitchyardModule(enable, modules, "org.fuse.integration.switchyard.component.rules", "org.switchyard.component.rules.deploy.RulesComponent");
        }
    }

    private static void updateSwitchyardModule(boolean enable, Element modules, String id, String className) {
        Element module = ConfigSupport.findElementWithAttributeValue(modules, "module", NS_SWITCHYARD, "identifier", id);
        if (enable && module == null) {
            modules.addContent(new Text("    "));
            modules.addContent(new Element("module", NS_SWITCHYARD).setAttribute("identifier", id).setAttribute("implClass", className));
            modules.addContent(new Text("    "));
        }
        if (!enable && module != null) {
            module.getParentElement().removeContent(module);
        }
    }
}
