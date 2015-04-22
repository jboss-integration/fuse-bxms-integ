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
package org.jboss.integration.fuse.xslt;

import java.io.File;
import java.net.URLDecoder;

/**
 * Main class that executes the xslt fuse integration transformations on the EAP
 * configuration files
 *
 * @author David Virgil Naranjo 2015 Red Hat Inc.
 */
public class ConfigMain {

    /**
     * The main method.
     *
     * @param args
     *            the arguments
     * @throws Exception
     *             the exception
     */
    public static void main(String[] args) throws Exception {
        if(args.length!=1){
            System.out.println("XSLTransformerMain [disable/enable]");
        } else {
            XSLTransformer transformer=new XSLTransformer();
            String path = ConfigMain.class.getProtectionDomain().getCodeSource().getLocation().getPath();

            String decodedPath = URLDecoder.decode(path, "UTF-8");
            String containingFolder = decodedPath.substring(0, decodedPath.lastIndexOf(File.separator));
            if (containingFolder.endsWith("bin")) {
                String jbossHome = containingFolder.substring(0, containingFolder.lastIndexOf(File.separator));
                String standalonePath = jbossHome + "/standalone/configuration";
                String domainPath = jbossHome + "/domain/configuration";
                File stanaloneFile = new File(standalonePath);
                File domainFile = new File(domainPath);
                if (stanaloneFile.exists() && domainFile.exists()) {
                    if (args[0].equals("enable")) {
                        transformer.applyXSLT(true, jbossHome);
                    } else if (args[0].equals("disable")) {
                        transformer.applyXSLT(false, jbossHome);
                    } else {
                        System.out.println("\tXSLTransformerMain [disable/enable]");
                    }
                } else {
                    System.out.println("\t The execution is not correct. This jar should be placed inside of ${JBOSS_HOME}/bin");
                }
            }


        }
    }



}
