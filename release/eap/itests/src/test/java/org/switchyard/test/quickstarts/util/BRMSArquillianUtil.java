package org.switchyard.test.quickstarts.util;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.switchyard.test.ShrinkwrapUtil;

public abstract class BRMSArquillianUtil {

    public static final String QS_GID = "org.jboss.integration.fuse.quickstarts";
    public static final String QS_DEMO_GID = "org.jboss.integration.fuse.quickstarts";

    
    private static final String LOCAL_MAVEN_REPO = System.getProperty("maven.repo.local") != null ? System.getProperty("maven.repo.local") : 
        (System.getProperty("user.home")+ File.separatorChar + ".m2"+ File.separatorChar + "repository");

    /** Current SwitchYard version test Environment variable. */
    public static final String SWITCHYARD_VERSION = "SWITCHYARD_VERSION";

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
    
    

    public static <A extends Archive> A getArchive(String groupId, String artifactId, String version, Class<A> archiveType, String fileExtension) {
        return getArchive(groupId, artifactId, version, archiveType, fileExtension, null);
    }

    /** Get a maven Artifact Archive.
     *
     * @param groupId Maven groupId
     * @param artifactId Maven artifactId.
     * @param version Artifact version.
     * @param archiveType The artifact type.
     * @param fileExtension The artifact file extension.
     * @return The Maven artifact archive.
     * @param <A> Archive type. */
    public static <A extends Archive> A getArchive(String groupId, String artifactId, String version, Class<A> archiveType, String fileExtension, String classifier) {
        Assert.assertNotNull("'groupId' argument is null.", groupId);
        Assert.assertNotNull("'artifactId' argument is null.", artifactId);
        Assert.assertNotNull("'version' argument is null.", version);
        File artifactFile = null;
        if (classifier == null) {
            artifactFile = new File(LOCAL_MAVEN_REPO, groupId.replace(".", "/") + "/" + artifactId + "/" + version + "/" + artifactId + "-" + version + "." + fileExtension);
        } else {
            artifactFile = new File(LOCAL_MAVEN_REPO, groupId.replace(".", "/") + "/" + artifactId + "/" + version + "/" + artifactId + "-" + version + "-" + classifier + "."
                                                      + fileExtension);
        }

        if (!artifactFile.isFile()) {
            String artifact = groupId + ":" + artifactId + ":" + version;
            Assert.fail("Failed to resolve artifact '" + artifact
                        + "'.  The artifact must be declared as a dependency in your POM, thereby making it available in your local repository.");
        }

        A archive = ShrinkWrap.create(ZipImporter.class, artifactFile.getName()).importFrom(convert(artifactFile)).as(archiveType);

        return archive;
    }

    // converts a file to a ZIP file
    private static ZipFile convert(File file) {
        try {
            return new ZipFile(file);
        } catch (ZipException e) {
            throw new RuntimeException("Unable to treat dependency artifact \"" + file.getAbsolutePath() + "\" as a ZIP file", e);
        } catch (IOException e) {
            throw new RuntimeException("Unable to access artifact file at \"" + file.getAbsolutePath() + "\".", e);
        }
    }

    /** Get the SwitchYard version being tested.
     * <p/>
     * Gets the SwitchYard version from the mandatory {@link #SWITCHYARD_VERSION} env property.
     *
     * @return The SwitchYard version being tested. */
    public static String getSwitchYardVersion() {
        String version = System.getenv(SWITCHYARD_VERSION);

        if (version == null) {
            Assert
                .fail("Test Environment variable '"
                      + SWITCHYARD_VERSION
                      + "' is not configured.  "
                      + "\n\t\t- If running the test in your IDE, set this Environment variable (in the test Run/Debug Configuration) to the current version of SwitchYard (maven artifact version)."
                      + "\n\t\t- If running the tests through Maven, make sure that the surefire plugin sets this Environment variable to the current version of SwitchYard (maven artifact version).");
        }
        version = version.trim();
        if (version.length() == 0) {
            Assert.fail("Test Environment variable '" + SWITCHYARD_VERSION
                        + "' not configured.  If running the test in your IDE, set this Environment variable to the current version od SwitchYard.");
        }

        return version;
    }
}
