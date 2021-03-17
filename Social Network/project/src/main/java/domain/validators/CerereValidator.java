package domain.validators;

import domain.Cerere;

import static domain.CerereType.*;

public class CerereValidator implements Validator<Cerere> {
    @Override
    public void validate(Cerere entity) throws ValidationException {
        String error = "";
        if (entity.getFrom().getId() == entity.getTo().getId())
            error = error + "\tNu se poate trimite cerere catre persoana care o trimite\n";
        if (entity.getType() != PENDING && entity.getType() != APPROVED && entity.getType() != REJECTED
                && entity.getType() != NOMOREFRIENDS)
            error = error + "\tTipul cererii este de tipul {PENDING, APPROVED, REJECTED, NOMOREFRIENDS}\n";
        if (!error.isEmpty())
            throw new ValidationException(error);
    }
}
