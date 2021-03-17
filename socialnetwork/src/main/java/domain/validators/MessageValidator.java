package domain.validators;

import domain.Message;
import domain.Utilizator;

public class MessageValidator implements Validator<Message> {
    @Override
    public void validate(Message entity) throws ValidationException {
        String error = "";
        if(entity.getMessage().isEmpty())
            error=error+"\tMesajul nu poate fi gol\n";
        if(entity.getTo().size()==0)
            error=error+"\tMesajul trebuie trimis la cel putin o persoana\n";
        for(Utilizator u: entity.getTo())
            if(u.getId()==entity.getFrom().getId())            {
                error=error+"\tMesajul nu poate fi trimis persoanei care il trimite\n";
                break;
            }
        for(Utilizator t:entity.getTo())
            for(Utilizator j:entity.getTo())
                if(t!=j&& t.getId()==j.getId()){
                    error=error+"\tMesajul nu poate fi trimis de doua ori aceleiasi persoane\n";
                    break;
                }
        if (!error.isEmpty())
            throw new ValidationException(error);
    }
}
