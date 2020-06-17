import javafx.scene.image.Image;
import objects.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.FieldChecker;
import utils.HttpWrapper;
import utils.KeyGenerator;
import utils.Manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class ProjectTests {

    @Before
    public void init() {
    }

    @Test
    public void testHttpWrapper() {
        HttpWrapper httpWrapper = new HttpWrapper();

        String name = "acqua";
        List<Prodotto> productsPerName = httpWrapper.getProductsPerName(name);
        productsPerName.forEach(prodotto ->
                Assert.assertTrue(prodotto.getNome().toLowerCase().contains(name.toLowerCase()))
        );

        String brand = "ser";
        List<Prodotto> productsPerBrand = httpWrapper.getProductsPerBrand(brand);
        productsPerBrand.forEach(prodotto ->
                Assert.assertTrue(prodotto.getMarca().toLowerCase().contains(brand.toLowerCase()))
        );

        Categoria categoria = Categoria.FRUTTA_VERDURA;
        List<Prodotto> productByCategory = httpWrapper.getProductByCategory(categoria);
        productByCategory.forEach(prodotto ->
                Assert.assertEquals(categoria, prodotto.getCategoria())
        );

        CaratteristicheProdotto caratteristicheProdotto = CaratteristicheProdotto.BIOLOGICO;
        List<Prodotto> productByTag = httpWrapper.getProductByTag(caratteristicheProdotto);
        productByTag.forEach(prodotto ->
                Assert.assertEquals(caratteristicheProdotto, prodotto.getCaratteristiche())
        );

    }

    @Test
    public void testManager() throws FileNotFoundException {
        File image = new File(ClassLoader.getSystemResource("fidelity_card.png").getPath());
        Image fxImage = new Image(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResourceAsStream("fidelity_card.png")));
        StringBuilder base64String = new StringBuilder();
        Scanner scanner = new Scanner(new File(ClassLoader.getSystemResource("fidelity_card_base64.txt").getPath()));
        while (scanner.hasNext()) {
            base64String.append(scanner.next());
        }

        String encodedImage = Manager.encodeImage(image);
        Assert.assertEquals(encodedImage, base64String.toString());

        Image decodedImage = Manager.decodeImage(base64String.toString());
        Assert.assertEquals(decodedImage.getHeight() * decodedImage.getWidth(),
                fxImage.getHeight() * fxImage.getWidth(), 0);

        Prodotto prodotto = new Prodotto(8934, "test", "marca", CaratteristicheProdotto.VEGAN, Categoria.FRUTTA_VERDURA, 10f, 6, 8, "base64");
        String json = Manager.objectToJson(prodotto);
        Assert.assertTrue(json.matches("\\{.+}"));

        Prodotto jsonToObject = Manager.jsonToObject(json, Prodotto.class);
        Assert.assertEquals(jsonToObject, prodotto);

        String utenteClienteID = "UC-12345678";
        Manager.createIDFile(utenteClienteID);
        String uidFromFile1 = Manager.getUIDFromFile();
        Assert.assertEquals(utenteClienteID, uidFromFile1);
        Assert.assertTrue(Manager.isUserCliente());

        String utenteResponsabileID = "UR-98765432";
        Manager.createIDFile(utenteResponsabileID);
        String uidFromFile2 = Manager.getUIDFromFile();
        Assert.assertEquals(utenteResponsabileID, uidFromFile2);
        Assert.assertTrue(Manager.isUserResponsabile());

    }

    @Test
    public void testFieldChecker() {
        String empty = "";
        String something = "something";
        String number = "0123";
        String email1 = "mail.com";
        String email2 = "@mail.com";
        String email3 = "mail@com";
        String email4 = "some.thing@mail.com";
        String password1 = "Pass123";
        String password2 = "password1234";
        String password3 = "pass Word123";
        String password4 = "passWord1222";

        Assert.assertFalse(FieldChecker.validateEmail(empty));
        Assert.assertFalse(FieldChecker.validateEmail(email1));
        Assert.assertFalse(FieldChecker.validateEmail(email2));
        Assert.assertFalse(FieldChecker.validateEmail(email3));
        Assert.assertTrue(FieldChecker.validateEmail(email4));

        Assert.assertFalse(FieldChecker.validatePassword(empty));
        Assert.assertFalse(FieldChecker.validatePassword(password1));
        Assert.assertFalse(FieldChecker.validatePassword(password2));
        Assert.assertFalse(FieldChecker.validatePassword(password3));
        Assert.assertTrue(FieldChecker.validatePassword(password4));

        Assert.assertFalse(FieldChecker.validateNonEmptyString(empty));
        Assert.assertTrue(FieldChecker.validateNonEmptyString(something));

        Assert.assertFalse(FieldChecker.validateNumerableString(empty));
        Assert.assertFalse(FieldChecker.validateNumerableString(something));
        Assert.assertTrue(FieldChecker.validateNumerableString(number));
    }

    @Test
    public void testKeyGenerator() {
        int productKey = KeyGenerator.generateProductKey();
        Assert.assertTrue(productKey > 99999 && productKey < 1000000);

        String fidelityCardKey = KeyGenerator.generateFidelityCardKey();
        Assert.assertTrue(fidelityCardKey.matches("\\d{13}"));

        String userKey1 = KeyGenerator.generateUserKey(UtenteCliente.class);
        Assert.assertTrue(userKey1.matches("UC-\\d{8}"));

        String userKey2 = KeyGenerator.generateUserKey(UtenteResponsabile.class);
        Assert.assertTrue(userKey2.matches("UR-\\d{8}"));
    }

}
