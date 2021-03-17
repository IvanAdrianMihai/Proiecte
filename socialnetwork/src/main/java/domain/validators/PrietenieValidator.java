package domain.validators;

import domain.Prietenie;

/**
 * Relizeaza validarile entitatilor de tip prietenie
 */
public class PrietenieValidator implements Validator<Prietenie> {
    @Override
    public void validate(Prietenie entity) throws ValidationException {
        String error = "";
        Long f1 = entity.getId().getLeft(), f2 = entity.getId().getRight();
        if (f1 < 0)
            error = error + "\tPrimul prieten nu are un id numar natural >0\n";
        if (f2 < 0)
            error = error + "\tAl doilea prieten nu are un id numar natural >0\n";
        if (error.isEmpty() && f1 == f2)
            error = error + "\tNu exista prietenie a utilizatorului cu el insusi\n";
        if (!error.isEmpty())
            throw new ValidationException(error);
    }
}
