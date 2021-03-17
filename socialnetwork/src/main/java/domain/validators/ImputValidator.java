package domain.validators;

/**
 * Realizeaza validarile de imput
 */
public class ImputValidator {
    /**
     * Metoda verifica daca stringul se poate transforma in tipul de data long.
     *
     * @param string de tip String
     * @return true daca se poate transforma, altfel returneaza false
     */
    public static boolean isLong(String string) {
        try {
            Long.parseLong(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
