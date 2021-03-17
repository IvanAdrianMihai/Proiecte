package domain.validators;

public interface Validator<T> {
    /**
     * Metoda valideaza entitatea entity. Daca nu este valida se arunca exceptie
     *
     * @param entity de tip T
     * @throws ValidationException daca entitatea nu este valida
     */
    void validate(T entity) throws ValidationException;
}