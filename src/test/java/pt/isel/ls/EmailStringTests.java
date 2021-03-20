package pt.isel.ls;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EmailStringTests {

    @Test
    public void checkEmails() {
        assertFalse(isValidEmail(".sandro@gmai.com"));
        assertFalse(isValidEmail("sandro.@gmai.com."));
        assertFalse(isValidEmail("Abc.example.com"));
        assertFalse(isValidEmail("A@b@c@example.com"));
        assertFalse(isValidEmail("1234567890123456789012345678901234567890123456789012345678901234+x@example.com"));

        assertTrue(isValidEmail("jsmith@[192.168.2.1]"));
        assertTrue(isValidEmail("jsmith@[IPv6:2001:db8::1]"));
        assertTrue(isValidEmail("sandro@gmai.com"));
        assertTrue(isValidEmail("me@gmail.com"));
        assertTrue(isValidEmail("me@me.co.uk"));
        assertTrue(isValidEmail("me@yahoo.com"));
        assertTrue(isValidEmail("me-100@me.com"));
    }

    public boolean isValidSizeEmail(Object email) {
        int numMaxOfCharacters = 80;
        try {
            return ((String) email).length() < 50 || ((String) email).length() >= numMaxOfCharacters;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * @param emailParam Converts into string (if possible) then:
     *                   -Verifies if the first and last char before @ (Username/Local part) obeys with the
     *                      respective pattern
     *                   -Verifies if Username/Local part obeys with the respective pattern
     *                   -Verifies if the first and last char after @ (Domain part) obeys with the respective pattern
     *                   -Verifies if Domain part obeys with the respective pattern
     * @return returns true if is valid (this method only covers a big MAJORITY of email adresses, not all)
     */
    public boolean isValidEmail(Object emailParam) {
        if (!isValidSizeEmail(emailParam)) {
            return false;
        }
        String email;
        try {
            email = (String) emailParam;
        } catch (IllegalArgumentException e) {
            return false;
        }
        if (!email.contains("@")) {
            return false;
        }
        String[] parts = email.split("@");
        if (parts.length > 2) {
            return false;
        }
        String local = parts[0];
        if (local.length() > 64) {
            return false;
        }
        String domain = parts[1];

        String firstAndLastChar = local.charAt(0) + "" + local.charAt(local.length() - 1);

        Pattern firstAndLastcharLocal = Pattern.compile("[a-zA-Z0-9]+$");
        Matcher firstAndLastcharLocalMatcher = firstAndLastcharLocal.matcher(firstAndLastChar);

        Pattern emailPattern = Pattern.compile("[a-zA-Z0-9!-/]+$");
        Matcher matcherLocal = emailPattern.matcher(local);

        firstAndLastChar = domain.charAt(0) + "" + domain.charAt(domain.length() - 1);
        Pattern firstAndLastcharDomain = Pattern.compile("[a-zA-Z0-9\\[\\]]+$");
        Matcher firstAndLastcharMatcherDomain = firstAndLastcharDomain.matcher(firstAndLastChar);

        Pattern domainPattern = Pattern.compile("[a-zA-Z0-9:.\\[\\]()]+$");
        Matcher matcherD = domainPattern.matcher(domain);

        return firstAndLastcharLocalMatcher.matches()
                && matcherLocal.matches()
                && firstAndLastcharMatcherDomain.matches()
                && matcherD.matches();
    }
}
