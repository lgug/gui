import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import utils.HttpWrapper;
import objects.*;
import utils.*;

import java.io.IOException;

public class TestExample {
    //@Test
    /*public void testObjToJson() {
        //Prodotto prodotto = new Prodotto("mela", "melinda",null,null,1,1,null); // MyClass is tested
        //String json = Manager.objectToJson(prodotto);
        System.out.println(json);
        // assert statements
        assertEquals(json, "{\"nome\":\"mela\",\"marca\":\"melinda\",\"prezzo\":1.0}");
    }*/
    @Test
    public void test() throws IOException {
        HttpWrapper http = new HttpWrapper();
        http.login("Pippo","Franco");
        http.availability("123");
        http.tag("vegan","123");
        http.remove("mela bella", "123");
    }

    @Test
    public void testUserCreation() {
        UtenteCliente utenteCliente = new UtenteCliente("12345", "Mario", "Rossi",
                new Indirizzo("Verdi", "34c", "37120", "Verona", "Verona", "Italia"),
                "+3902388384", "mariorossi@gmail.com", "Pa55word123", "28844838883;06/22", FormaDiPagamento.CARTA_CREDITO,
                new TesseraFedelta("234567875", System.currentTimeMillis(), 45));
        HttpWrapper httpWrapper = new HttpWrapper();
        boolean response = httpWrapper.sendNewUser(utenteCliente);
        Assert.assertTrue(response);
    }

}
