package ${package}.its;

import com.flowlogix.test.PayaraServerLifecycle;
import com.flowlogix.util.ShrinkWrapManipulator;
import org.eu.ingwar.tools.arquillian.extension.suite.annotations.ArquillianSuiteDeployment;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

@PayaraServerLifecycle
@ArquillianSuiteDeployment
class StarterIT {
    @Test
    void sanityCheck() {
        assertThat(true).isTrue();
    }

    @Deployment
    @SuppressWarnings("unused")
    static JavaArchive deploy() {
        return ShrinkWrapManipulator.createDeployment(JavaArchive.class);
    }
}
