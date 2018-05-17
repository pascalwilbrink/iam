package nl.wilbrink.password.generator;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PasswordGeneratorTest {

    private PasswordGenerator sut;

    @Test
    public void itShouldGenerateALowercasePassword() {
        sut = new PasswordGenerator(8, 16, false, false, false);

        String generatedPassword = sut.generatePassword();

        assertThat(generatedPassword).containsPattern("[a-z]+");
        assertThat(generatedPassword.length()).isBetween(8, 16);
    }

    @Test
    public void itShouldGenerateALowerAndUppercasePassword() {
        sut = new PasswordGenerator(8, 16, false, true, false);

        String generatedPassword = sut.generatePassword();

        assertThat(generatedPassword).containsPattern("[a-zA-Z]+");
        assertThat(generatedPassword.length()).isBetween(8, 16);
    }

    @Test
    public void itShouldGenerateSpecialCharacterPassword() {
        sut = new PasswordGenerator(8, 16, true, true, true);

        String generatedPassword = sut.generatePassword();

        assertThat(generatedPassword).containsPattern("[a-zA-Z0-9]+");
        assertThat(generatedPassword.length()).isBetween(8, 16);
    }
}
