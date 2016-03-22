/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
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
package org.switchyard.quickstarts.demos.helpdesk;

import static org.switchyard.quickstarts.demos.helpdesk.HelpDeskUserGroupCallback.USERS_GROUPS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.kie.api.task.model.TaskSummary;
import org.switchyard.component.bpm.runtime.BPMTaskService;
import org.switchyard.component.bpm.runtime.BPMTaskServiceRegistry;


/**
 * The Class HelpDesk.
 *
 * @author David Ward &lt;<a
 *         href="mailto:dward@jboss.org">dward@jboss.org</a>&gt; &copy; 2012 Red
 *         Hat Inc.
 */
@ManagedBean(name = "helpDesk")
@SessionScoped
public class HelpDesk {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(HelpDesk.class);
    
    /** The Constant TICKET. */
    private static final String TICKET = "ticket";
    
    /** The Constant EN_UK. */
    private static final String EN_UK = "en-UK";

    /** The _task service. */
    private final BPMTaskService _taskService;
    
    /** The _user tasks. */
    private final List<TaskSummary> _userTasks;
    
    /** The _user tickets. */
    private final Map<Long, Ticket> _userTickets;
    
    /** The _user id. */
    private String _userId = "krisv";

    /**
     * Instantiates a new help desk.
     */
    public HelpDesk() {
        _taskService = BPMTaskServiceRegistry.getTaskService(new QName(null, "helpdesk"), new QName(
                "urn:switchyard-quickstart-demo:helpdesk:0.1.0", "HelpDeskService"));
        _userTasks = Collections.synchronizedList(new ArrayList<TaskSummary>());
        _userTickets = Collections.synchronizedMap(new LinkedHashMap<Long, Ticket>());
    }

    /**
     * Gets the user id.
     *
     * @return the user id
     */
    public String getUserId() {
        return _userId;
    }

    /**
     * Sets the user id.
     *
     * @param userId the new user id
     */
    public void setUserId(String userId) {
        _userId = userId;
    }

    /**
     * Gets the group id.
     *
     * @return the group id
     */
    public String getGroupId() {
        List<String> groups = USERS_GROUPS.get(_userId);
        return (groups != null && groups.size() > 0) ? groups.get(0) : null;
    }

    /**
     * Gets the users groups.
     *
     * @return the users groups
     */
    public Map<String, String> getUsersGroups() {
        Map<String, String> usersGroups = new LinkedHashMap<String, String>();
        for (Map.Entry<String, List<String>> entry : USERS_GROUPS.entrySet()) {
            String key = entry.getKey();
            usersGroups.put(key + " (" + entry.getValue().get(0) + ")", key);
        }
        return usersGroups;
    }

    /**
     * Gets the user tasks.
     *
     * @return the user tasks
     */
    public List<TaskSummary> getUserTasks() {
        return _userTasks;
    }

    /**
     * Gets the user tickets.
     *
     * @return the user tickets
     */
    public Map<Long, Ticket> getUserTickets() {
        return _userTickets;
    }

    /**
     * Select user.
     *
     * @param vce the vce
     */
    public void selectUser(ValueChangeEvent vce) {
        setUserId((String) vce.getNewValue());
        fetchTasks();
    }

    /**
     * Fetch tasks.
     */
    private void fetchTasks() {
        synchronized (_userTasks) {
            _userTasks.clear();
            _userTickets.clear();
            List<TaskSummary> tasks = _taskService.getTasksAssignedAsPotentialOwner(_userId, EN_UK);
            for (TaskSummary task : tasks) {
                _userTasks.add(task);
                Map<String, Object> params = _taskService.getTaskContent(task.getId());
                Ticket ticket = (Ticket) params.get(TICKET);
                _userTickets.put(task.getProcessInstanceId(), ticket);
            }
        }
    }

    /**
     * Complete tasks.
     */
    private void completeTasks() {
        synchronized (_userTasks) {
            if (_userTasks.size() > 0) {
                for (TaskSummary task : _userTasks) {
                    _taskService.claim(task.getId(), _userId);
                    _taskService.start(task.getId(), _userId);
                    Map<String, Object> results = new HashMap<String, Object>();
                    Ticket ticket = _userTickets.get(task.getProcessInstanceId());
                    results.put(TICKET, ticket);
                    _taskService.complete(task.getId(), _userId, results);
                }
            }
        }
    }

    /**
     * Submit.
     */
    public void submit() {
        try {
            completeTasks();
            fetchTasks();
        } catch (Throwable t) {
            StringBuilder sb = new StringBuilder();
            sb.append("Problem processing tasks: ");
            sb.append(t.getClass().getName());
            String m = t.getMessage();
            if (m != null) {
                sb.append("(");
                sb.append(m);
                sb.append(")");
            }
            String msg = sb.toString();
            LOGGER.error(msg, t);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
        }
    }

}
