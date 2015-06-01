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

import static org.jboss.fuse.eap.config.ConfigSupport.assertExists;
import static org.jboss.fuse.eap.config.ConfigSupport.findElementWithAttributeValue;
import static org.jboss.fuse.eap.config.ConfigSupport.findElementWithStartingAttributeValue;
import static org.jboss.fuse.eap.config.ConfigSupport.findProfileElements;
import static org.jboss.fuse.eap.config.LayerConfig.Type.INSTALLING;
import static org.jboss.fuse.eap.config.LayerConfig.Type.REQUIRED;

import java.util.Arrays;
import java.util.List;

import org.jboss.fuse.eap.config.ConfigEditor;
import org.jboss.fuse.eap.config.LayerConfig;

import de.pdark.decentxml.Document;
import de.pdark.decentxml.Element;
import de.pdark.decentxml.Text;

/**
 */
public class FuseIntegrationConfigEditor implements ConfigEditor
{

   @Override
   public void applyStandaloneConfigChange(boolean enable, Document doc)
   {
      updateSwitchyardModules(enable, doc);
   }

   @Override
   public void applyDomainConfigChange(boolean enable, Document doc)
   {
      applyStandaloneConfigChange(enable, doc);
   }

   public static void updateSwitchyardModules(boolean enable, Document doc)
   {
      List<Element> profiles = findProfileElements(doc);
      for (Element profile : profiles)
      {
         Element switchyard = findElementWithStartingAttributeValue(profile.getChildren("subsystem"), "xmlns", "urn:jboss:domain:switchyard:");
         assertExists(switchyard, "Did not find the switchyard subsystem");
         Element modules = switchyard.getChild("modules");
         assertExists(modules, "Did not find the <modules> element");

         updateSwitchyardModule(enable, modules, "org.fuse.integration.switchyard.component.bpm", "org.switchyard.component.bpm.deploy.BPMComponent");
         updateSwitchyardModule(enable, modules, "org.fuse.integration.switchyard.component.rules", "org.switchyard.component.rules.deploy.RulesComponent");
      }
   }

   private static void updateSwitchyardModule(boolean enable, Element modules, String id, String className)
   {
      Element module = findElementWithAttributeValue(modules.getChildren("module"), "identifier", id);
      if (enable && module == null)
      {
         modules.addNodes(
            new Text("    "),
            new Element("module")
               .addAttribute("identifier", id)
               .addAttribute("implClass", className),
            new Text("\n            ")
         );
      }
      if (!enable && module != null)
      {
         module.remove();
      }
   }

    @Override
    public List<LayerConfig> getLayerConfigs() {
        return Arrays.asList(
            new LayerConfig("fuse", REQUIRED, -10),
            new LayerConfig("bpms", INSTALLING, -9),
            new LayerConfig("soa", REQUIRED, -8),
                new LayerConfig("fuse-integration", INSTALLING, -7)

        );
    }
}
