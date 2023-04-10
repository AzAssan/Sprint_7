package api;

import java.util.Random;

public class CourierGenerator {

    public static Courier getRandom() {
        Random random = new Random();
        String login = "Login " + random.nextInt(1_000_000_000);
        String password = "Password " + random.nextInt(1_000_000_000);
        String firstName = "FirstName " + random.nextInt(1_000_000_000);
        return new Courier(login, password, firstName);
    }
}
