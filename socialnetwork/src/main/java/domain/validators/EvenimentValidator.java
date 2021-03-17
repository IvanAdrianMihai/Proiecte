package domain.validators;

import domain.Eveniment;
import domain.UtilizatorEveniment;

public class EvenimentValidator implements Validator<Eveniment> {
    @Override
    public void validate(Eveniment entity) throws ValidationException {
        String error = "";
        if(!entity.getCreateDate().isBefore(entity.getEvenimentDate()))
            error=error+"\tNu se poate crea un eveniment in trecut\n";
        if(entity.getDenumire().isEmpty())
            error=error+"\tDenumirea evenimentului nu trebuie sa fie vida\n";
        if(entity.getDescriere().isEmpty())
            error=error+"\tDescrierea evenimentului nu trebuie sa fie vida\n";
        if (!error.isEmpty())
            throw new ValidationException(error);
    }
}
