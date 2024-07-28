package utils;

import java.util.UUID;
import org.apache.commons.lang3.RandomStringUtils;

public class RandomDataUtil {

    public static String generateRandomName() {
        return "User" + RandomStringUtils.randomAlphabetic(5);
    }

    public static String generateRandomEmail() {
        return "qatest" + UUID.randomUUID().toString() + "@test.com";
    }

    public static String generateRandomGender() {
        String[] genders = {"Male", "Female"};
        return genders[(int) (Math.random() * genders.length)];
    }

    public static String generateRandomStatus() {
        String[] statuses = {"Active", "Inactive"};
        return statuses[(int) (Math.random() * statuses.length)];
    }
}
