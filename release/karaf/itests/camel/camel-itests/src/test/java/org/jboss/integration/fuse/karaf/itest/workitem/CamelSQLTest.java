package org.jboss.integration.fuse.karaf.itest.workitem;

import org.apache.camel.CamelContext;
import org.h2.jdbcx.JdbcDataSource;
import org.jboss.integration.fuse.karaf.itest.KarafConfigUtil;
import org.jbpm.process.workitem.camel.CamelHandler;
import org.jbpm.process.workitem.camel.request.RequestPayloadMapper;
import org.jbpm.process.workitem.camel.response.ResponsePayloadMapper;
import org.jbpm.process.workitem.camel.uri.SQLURIMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.ProcessInstance;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;
import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class CamelSQLTest {

    private KieServices kieServices;
    private KieSession kieSession;

    @Inject
    CamelContext camelContext;

    @Configuration
    public static Option[] configure() {
        return KarafConfigUtil.karafConfiguration();
    }

    @Before
    public void prepare() {
        this.kieServices = KieServices.Factory.get();
        final KieContainer kieContainer = this.kieServices.newKieClasspathContainer(this.getClass().getClassLoader());
        this.kieSession = kieContainer.newKieSession("camel-workitem-ksession");
        assertNotNull(this.kieSession);
    }

    @Test
    public void testSingleSQLProcess() throws Exception {

        JdbcDataSource dataSource = (JdbcDataSource) camelContext.getRegistry().lookupByName("dataSource");
        Connection connection = dataSource.getConnection();

        try {
            // Prepare database table
            PreparedStatement statement = connection.prepareStatement("CREATE TABLE test (KEY VARCHAR(255), VALUE INTEGER)");
            statement.execute();
            // Create row in row in database table
            statement = connection.prepareStatement("INSERT INTO test VALUES ('myKey',5)");
            statement.execute();

            // Verify the value was correctly set
            statement = connection.prepareStatement("SELECT * FROM test WHERE KEY = 'myKey'");
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int value = resultSet.getInt("VALUE");
            assertTrue(value == 5);

            // Change the value in table using workitem camel
            CamelHandler handler = new CamelHandler(new SQLURIMapper(), new RequestPayloadMapper("payload"), new ResponsePayloadMapper(), camelContext);
            kieSession.getWorkItemManager().registerWorkItemHandler("CamelSQL", handler);

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("dataSourceVar", "dataSource");
            params.put("queryVar", "UPDATE test SET VALUE = 6 WHERE KEY='myKey'");

            ProcessInstance pi = kieSession.startProcess("CamelSQLProcess", params);
            ProcessInstance result = kieSession.getProcessInstance(pi.getId());

            // Verify the value was changed
            resultSet = statement.executeQuery();
            resultSet.next();
            value = resultSet.getInt("VALUE");
            assertTrue(value == 6);
        } finally {
            connection.close();
        }
    }

    @After
    public void cleanup() {
        if (this.kieSession != null) {
            this.kieSession.dispose();
        }
    }
}
