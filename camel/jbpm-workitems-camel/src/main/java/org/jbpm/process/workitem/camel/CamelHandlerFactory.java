/*
 * Copyright 2016 Red Hat Inc. and/or its affiliates and other contributors.
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
package org.jbpm.process.workitem.camel;

import org.jbpm.process.workitem.camel.request.FTPRequestPayloadMapper;
import org.jbpm.process.workitem.camel.request.RequestPayloadMapper;
import org.jbpm.process.workitem.camel.uri.CXFURIMapper;
import org.jbpm.process.workitem.camel.uri.FTPURIMapper;
import org.jbpm.process.workitem.camel.uri.FileURIMapper;
import org.jbpm.process.workitem.camel.uri.GenericURIMapper;
import org.jbpm.process.workitem.camel.uri.JMSURIMapper;
import org.jbpm.process.workitem.camel.uri.SQLURIMapper;
import org.jbpm.process.workitem.camel.uri.XSLTURIMapper;

public class CamelHandlerFactory {

    public static CamelHandler sftpHandler() {
        return new CamelHandler(new FTPURIMapper("sftp"), new FTPRequestPayloadMapper("payload"));
    }

    public static CamelHandler ftpHandler() {
        return new CamelHandler(new FTPURIMapper("ftp"), new FTPRequestPayloadMapper("payload"));
    }

    public static CamelHandler ftpsHandler() {
        return new CamelHandler(new FTPURIMapper("ftps"), new FTPRequestPayloadMapper("payload"));
    }

    public static CamelHandler cxfHandler() {
        return new CamelHandler(new CXFURIMapper(), new RequestPayloadMapper("payload"));
    }

    public static CamelHandler fileHandler() {
        return new CamelHandler(new FileURIMapper(), new RequestPayloadMapper("payload"));
    }

    public static CamelHandler xsltHandler() {
        return new CamelHandler(new XSLTURIMapper(), new RequestPayloadMapper("payload"));
    }

    public static CamelHandler jmsHandler() {
        return new CamelHandler(new JMSURIMapper(), new RequestPayloadMapper("payload"));
    }

    public static CamelHandler sqlHandler() {
        return new CamelHandler(new SQLURIMapper(), new RequestPayloadMapper("payload"));
    }

    public static CamelHandler genericHandler(String schema, String pathLocation) {
        return new CamelHandler(new GenericURIMapper(schema, pathLocation), new RequestPayloadMapper("payload"));
    }

}
