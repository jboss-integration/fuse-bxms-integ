package org.switchyard.test.quickstarts.util;

import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.switchyard.test.ShrinkwrapUtil;

public abstract class BRMSArquillianUtil {

    public static final String QS_GID = "org.jboss.integration.bxms.switchyard.quickstarts";
    public static final String QS_DEMO_GID = "org.jboss.integration.bxms.switchyard.quickstarts.demos";

    /**
     * Create a SwitchYard Quickstart Demo Deployment.
     * <p/>
     * Uses "org.switchyard.quickstarts.demos" as the groupId and gets the
     * SwitchYard version from the mandatory SWITCHYARD_VERSION env property.
     *
     * @param artifactId
     *            Maven artifactId.
     * @return The Maven artifact archive.
     */
    public static JavaArchive createJarDemoDeployment(String artifactId) {
        return ShrinkwrapUtil.getSwitchYardJavaArchive(QS_DEMO_GID, artifactId);
    }

    /**
     * Create a SwitchYard Quickstart Deployment.
     * <p/>
     * Uses "org.switchyard.quickstarts" as the groupId and gets the SwitchYard
     * version from the mandatory SWITCHYARD_VERSION env property.
     *
     * @param artifactId
     *            Maven artifactId.
     * @return The Maven artifact archive.
     */
    public static JavaArchive createJarQSDeployment(String artifactId) {
        return ShrinkwrapUtil.getSwitchYardJavaArchive(QS_GID, artifactId);
    }

    /**
     * Create a SwitchYard Quickstart Demo Deployment.
     * <p/>
     * Uses "org.switchyard.quickstarts.demos" as the groupId and gets the
     * SwitchYard version from the mandatory SWITCHYARD_VERSION env property.
     *
     * @param artifactId
     *            Maven artifactId.
     * @return The Maven artifact archive.
     */
    public static WebArchive createWarDemoDeployment(String artifactId) {
        return ShrinkwrapUtil.getSwitchYardWebArchive(QS_DEMO_GID, artifactId);
    }

    /**
     * Create a SwitchYard Quickstart Deployment.
     * <p/>
     * Uses "org.switchyard.quickstarts" as the groupId and gets the SwitchYard
     * version from the mandatory SWITCHYARD_VERSION env property.
     *
     * @param artifactId
     *            Maven artifactId.
     * @return The Maven artifact archive.
     */
    public static WebArchive createWarQSDeployment(String artifactId) {
        return ShrinkwrapUtil.getSwitchYardWebArchive(QS_GID, artifactId);
    }

    /**
     * Create a SwitchYard Quickstart Deployment.
     * <p/>
     * Uses "org.switchyard.quickstarts" as the groupId and gets the SwitchYard
     * version from the mandatory SWITCHYARD_VERSION env property.
     *
     * @param artifactId
     *            Maven artifactId.
     * @return The Maven artifact archive.
     */
    public static EnterpriseArchive createEarQSDeployment(String artifactId) {
        return ShrinkwrapUtil.getSwitchYardEarArchive(QS_GID, artifactId);
    }

}
