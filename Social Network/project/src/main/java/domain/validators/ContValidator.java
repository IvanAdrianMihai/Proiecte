package domain.validators;

import domain.Cont;

public class ContValidator implements Validator<Cont> {

    @Override
    public void validate(Cont entity) throws ValidationException {
        String error = "";
        char[] mail = entity.getMail().toCharArray();
        char[] password = entity.getPassword().toCharArray();

        if (entity.getMail().isEmpty())
            error = error + "\tMailul nu poate fi vid\n";
        else {
            int nr = 0;
            for (int i = 1; i < mail.length - 1; i++)
                if (mail[i] == '@')
                    nr++;
            if (nr != 1)
                error = error + "\tMailul nu poate fi considerat un mail valid\n";
        }

        boolean ok = true;
        if (entity.getPassword().isEmpty())
            error = error + "\tParola nu poate fi vida\n";
        else {
            for (int i = 0; i < password.length && ok; i++)
                if ((password[i] < 'a' || password[i] > 'z') && (password[i] < 'A' || password[i] > 'Z') && (password[i] < '0' || password[i] > '9'))
                    ok = false;
            if (!ok)
                error = error + "\tParola este formata doar din litere mici/mari si/sau cifre\n";
        }

        if (!error.isEmpty())
            throw new ValidationException(error);
    }
}
