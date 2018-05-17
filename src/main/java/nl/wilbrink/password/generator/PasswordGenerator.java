package nl.wilbrink.password.generator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.concurrent.ThreadLocalRandom.current;

@Component
public class PasswordGenerator {

    private final int minPasswordLength;
    private final int maxPasswordLength;
    private final boolean useSpecialChar;
    private final boolean useUppercaseChar;
    private final boolean useNumericChar;

    private static final String LOWERCASE_CHARS = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE_CHARS = LOWERCASE_CHARS.toUpperCase();
    private static final String NUMERIC_CHARS = "0123456789";
    private static final String SPECIAL_CHARS = "!@#$%&*?.,";

    public PasswordGenerator(
        @Value("${password.minLength:16}") int minPasswordLength,
        @Value("${password.maxLength:32}") int maxPasswordLength,
        @Value("${password.specialChar:true}") boolean useSpecialChar,
        @Value("${password.uppercaseChar:true}") boolean useUppercaseChar,
        @Value("${password.numericChar:true}") boolean useNumericChar
    ) {

        this.minPasswordLength = minPasswordLength;
        this.maxPasswordLength = maxPasswordLength;
        this.useSpecialChar = useSpecialChar;
        this.useUppercaseChar = useUppercaseChar;
        this.useNumericChar = useNumericChar;
    }

    public String generatePassword() {
        Random random = current();

        String chars = LOWERCASE_CHARS;
        if (useUppercaseChar) chars = chars + UPPERCASE_CHARS;
        if (useNumericChar) chars = chars + NUMERIC_CHARS;
        if (useSpecialChar) chars = chars + SPECIAL_CHARS;

        char[] characters = chars.toCharArray();
        int passwordLength = current().nextInt(
            this.minPasswordLength,
            this.maxPasswordLength + 1
        );

        char[] buffer = new char[passwordLength];


        for (int i = 0; i < passwordLength; i++) {
            buffer[i] = characters[random.nextInt(characters.length)];
        }

        return new String(buffer);
    }
}
