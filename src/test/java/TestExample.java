import org.junit.Test;
import static org.junit.Assert.*;
import utils.HttpWrapper;
import objects.*;
import utils.*;

import java.io.IOException;

public class TestExample {
    @Test
    public void testObjToJson() {
        Prodotto prodotto = new Prodotto("mela", "melinda",null,null,1,null); // MyClass is tested
        String json = Manager.objectToJson(prodotto);
        System.out.println(json);
        // assert statements
        assertEquals(json, "{\"nome\":\"mela\",\"marca\":\"melinda\",\"prezzo\":1.0}");
    }
    @Test
    public void test() throws IOException {
        HttpWrapper http = new HttpWrapper();
        http.getProductsPerName("pollo");
    }

}
