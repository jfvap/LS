package pt.isel.ls.handlers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    /**
     * -Verifica se apenas contem letras minusculas, maisculas
     * e letras acentuadas da Língua Portuguesa
     * (NÃO inclui por exemplo o alfabeto cirilico, nem o grego...etc)
     *
     * @return retorna true se a condição anterior for válida
     */
    public boolean nameIsValid(String name) {
        name = name.replace(" ", "");
        if (name.length() < 2) {
            return false;
        }
        Pattern pattern = Pattern.compile("[a-zA-ZÀ-ü]+$");
        Matcher matcherLetters = pattern.matcher(name);
        return matcherLetters.matches();
    }

    /**
     * -Verifica se o primeiro e ultimo char antes de @ (Local) obedece ao pattern respetivo
     * -Verifica se o Local obedece ao pattern geral
     * -Verifica se o primeiro e ultimo char depois de @ (Domain) obedece ao pattern respetivo
     * -Verifica se o Domain obedece ao pattern geral
     *
     * @return retorna true se for valido (segundo MAIOR PARTE das convenções da estrutura de email)
     */
    public boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        if (!email.contains("@")) {
            return false;
        }
        String[] parts = email.split("@");
        if (parts.length > 2 || parts.length == 1) {
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

    /**
     * Verifica se pode ser cast para Integer e se é
     * maior que 0 e menor ou igual que 5
     *
     * @return retorna true se for valido a condicao anterior
     */
    public Integer isValidRating(String rating) {
        if (rating == null) {
            return null;
        }
        try {
            int rat = Integer.parseInt(rating);
            //return isPositiveInt(rating) && number <= 5;/
            return isPositiveInt(rat) && rat <= 5 ? rat : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public boolean isValidString(String name) {
        if (name == null) {
            return false;
        }
        Pattern numbers = Pattern.compile("[0-9]+$");
        Matcher matcherLetters = numbers.matcher(name);
        if (matcherLetters.matches()) { //Only contains numbers
            return false;
        }

        Pattern specialChars = Pattern.compile("[<>{}\\[\\]\"/|;:.,~!?@#$%^=&*()¿§«»ω⊙¤°℃℉€¥£¢¡®©_+]+$");
        Matcher matcherLetters2 = specialChars.matcher(name);
        return !matcherLetters2.matches();
    }

    /**
     * @return true se name nao excede o numero de carateres permitidos na BD ou false caso contrário
     */
    public boolean isValidSizeName(String name) {
        if (name == null) {
            return false;
        }
        int numMaxOfCharacters = 200;
        try {
            return name.length() >= 2 && name.length() <= numMaxOfCharacters;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * @return true se year contem 4 digitos ou false caso contrario
     */
    public boolean isValidSizeYear(String year) {
        if (year == null) {
            return false;
        }
        try {
            return year.length() == 4;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * @return true se name nao excede o numero de carateres permitidos na BD ( 80) ou false caso contrário
     */
    public boolean isValidSizeEmail(String email) {
        if (email == null) {
            return false;
        }
        int numMaxOfCharacters = 80;
        try {
            return email.length() >= 3 && email.length() < numMaxOfCharacters;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * @return true se obj é um inteiro ou false caso contrario
     */
    public Integer isValidInt(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return Integer.parseInt((String) obj);

        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * @return true se 'integer' dá para transformar em inteiro e se é maior que 0
     */
    public boolean isPositiveInt(Integer num) {
        if (num == null) {
            return false;
        }
        try {
            return num > 0;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    /**
     * @return true se reviewSummary nao excede o numero de carateres permitidos na BD ou false caso contrário
     */
    public boolean isValidSizeSummary(String reviewSummary) {
        if (reviewSummary == null) {
            return false;
        }
        try {
            return reviewSummary.length() <= 80;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * @return true se complete nao excede o numero de carateres permitidos na BD ou false caso contrário
     */
    public boolean isValidSizeCompleteReview(String complete) {
        if (complete == null) {
            return false;
        }
        try {
            return complete.length() <= 1024;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * @return true se valueParam dá para transformar em inteiro e se é maior ou igual que 0
     */
    public boolean isPositiveIntAndZero(Integer valueParam) {
        try {
            return valueParam >= 0;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    /**
     * @return true se average=highest ou average=lowest
     */
    public boolean isHighLow(String average) {
        try {
            if (average != null) {
                return average.equals("highest") || average.equals("lowest");
            }
        } catch (IllegalArgumentException e) {
            return false;
        }
        return false;
    }
}
