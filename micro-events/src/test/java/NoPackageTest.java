import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.oath.micro.server.events.JobName;
import com.oath.micro.server.events.JobName.Types;

public class NoPackageTest {
    JobName one = Types.PACKAGE.getCreator();

    @Test
    public void testPackageNone() {
        assertThat(one.getType(NoPackageTest.class), equalTo("NoPackageTest"));
    }
}
