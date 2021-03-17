package domain.validators;

import domain.Utilizator;

/**
 * Relizeaza validarile entitatilor de tip utilizator
 */
public class UtilizatorValidator implements Validator<Utilizator> {
    @Override
    public void validate(Utilizator entity) throws ValidationException {
        String error = "";
        if (entity.getFirstName().isEmpty())
            error = error + "\tNumele nu trebuie sa fie vid\n";
        if (entity.getLastName().isEmpty())
            error = error + "\tPrenumele nu trebuie sa fie vid\n";
        if (!error.isEmpty())
            throw new ValidationException(error);
    }
}
