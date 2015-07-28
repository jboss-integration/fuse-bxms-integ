package org.switchyard.test.quickstarts;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.switchyard.test.quickstarts.util.BRMSArquillianUtil;

@RunWith(Arquillian.class)
public class RulesChannelsQuickstartTest {

    @Deployment(testable = false)
    public static JavaArchive createDeployment() {
        return BRMSArquillianUtil.createJarQSDeployment("switchyard-drools-channels");
    }

    @Test
    public void testDeployment() {
        // Only testing that deployment is successful at this point.
        // AS this test check the channels, and the channels are void methods,
        // then it is not possible to obtain a response from the server
        // Only thing can be done is to chekc the logs and see that the
        // deployment is correct.
        Assert.assertTrue(true);
    }
}
