package utils;

import objects.Utente;
import objects.UtenteCliente;

import java.util.Random;

public class KeyGenerator {

    private static final int USER_LENGTH = 8;
    private static final int FIDELITY_CARD_LENGTH = 13;

    public static int generateProductKey() {
        return Integer.parseInt(generateKey(6));
    }

    public static String generateUserKey(Class<? extends Utente> userType) {
        return (userType.equals(UtenteCliente.class) ? "UC-" : "UR-") + generateKey(USER_LENGTH);
    }

    public static String generateFidelityCardKey() {
        return generateKey(FIDELITY_CARD_LENGTH);
    }

    private static String generateKey(int length) {
        Random random = new Random();
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < length; i++) {
            key.append(random.nextInt(10));
        }
        return key.toString();
    }
}
