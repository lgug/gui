import org.junit.Test;
import utils.HttpWrapper;

import java.io.IOException;

public class TestExample {
    @Test
    public void test() throws IOException {
        HttpWrapper http = new HttpWrapper();
        http.login("Pippo","Franco");
    }

}
