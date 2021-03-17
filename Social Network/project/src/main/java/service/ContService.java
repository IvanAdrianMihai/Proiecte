package service;

import com.google.common.hash.Hashing;
import domain.Cont;
import domain.validators.RepositoryException;
import domain.validators.ServiceException;
import domain.validators.ValidationException;
import domain.validators.Validator;
import repository.database.ContRepositoryBD;
import util.EmailSender;
import util.Util;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class ContService {
    private ContRepositoryBD repo;
    private Validator<Cont> validator;
    private Integer connectionAttempts = 3;
    private Long startTimeToWait = null;

    public Long getStartTimeToWait() {
        return startTimeToWait;
    }

    public void setStartTimeToWait(Long startTimeToWait) {
        this.startTimeToWait = startTimeToWait;
    }

    public ContService(ContRepositoryBD repo, Validator<Cont> validator) {
        this.repo = repo;
        this.validator = validator;
    }

    public Cont addCont(Cont cont) throws ServiceException {
        try {
            validator.validate(cont);
            Cont c = repo.findOne(cont.getMail());

            if (c != null)
                throw new RepositoryException("\tExista deja un cont creat cu acest mail\n");

            Cont contInsert = new Cont(cont.getMail(),
                    Hashing.sha256().hashString(cont.getPassword(), StandardCharsets.UTF_8).toString());
            Cont rezult = repo.save(contInsert);
            cont.setId(rezult.getId());
            return cont;
        } catch (ValidationException v) {
            throw new ServiceException(v.getMessage());
        } catch (RepositoryException r) {
            throw new ServiceException(r.getMessage());
        }
    }

    public void updateCont(String lastMail, Cont cont) throws ServiceException {
        try {
            validator.validate(cont);
            Cont c = repo.findOne(lastMail);

            if (c == null)
                throw new RepositoryException("\tNu exista acest cont caruia sa i se faca update\n");
            Cont contInsert = new Cont(cont.getMail(),
                    Hashing.sha256().hashString(cont.getPassword(), StandardCharsets.UTF_8).toString());
            contInsert.setId(c.getId());
            repo.update(contInsert);
        } catch (ValidationException v) {
            throw new ServiceException(v.getMessage());
        } catch (RepositoryException r) {
            throw new ServiceException(r.getMessage());
        }
    }

    public void deleteCont(String mail) throws ServiceException {
        try {
            Cont c = repo.findOne(mail);
            if (c == null)
                throw new RepositoryException("\tNu exista acest cont\n");
            repo.delete(c.getId());
        } catch (RepositoryException r) {
            throw new ServiceException(r.getMessage());
        }
    }

    public Cont tryToConnect(Cont cont) throws ServiceException {
        try {
            Cont c = repo.findOne(cont.getMail());
            if (c == null)
                throw new RepositoryException("\tMail incorect\n");
            if (!c.getPassword().equals(Hashing.sha256().hashString(cont.getPassword(), StandardCharsets.UTF_8).toString())) {
                if (connectionAttempts != 1) {
                    connectionAttempts--;
                    throw new RepositoryException("\tParola incorecta\nMai aveti " + connectionAttempts + " incercari\n");
                } else {
                    connectionAttempts = 3;
                    startTimeToWait=System.currentTimeMillis();
                    throw new RepositoryException("\tParola incorecta\nTrebuie sa asteptati 30 de secunde sa va logati din nou\n");
                }
            }
            connectionAttempts = 3;
            cont.setId(c.getId());
            return cont;
        } catch (RepositoryException r) {
            throw new ServiceException(r.getMessage());
        }
    }

    public void recoverMailPassword(String mail) throws ServiceException {
        String code = Util.numbersCode(6);
        Cont contUpdate = repo.findOne(mail);
        if (contUpdate == null)
            throw new ServiceException("\tNu exista acest mail\n");
        contUpdate.setCode(code);
        contUpdate.setDate(LocalDateTime.now());
        repo.update(contUpdate);
        EmailSender.send(mail, "Recuperare parola", "Codul tau pentru recuperarea parolei este: " +
                code + "\nCodul va fi valabil 30 de minute.\n\nNota: Daca ai trimis mai multe mailuri de recuperare" +
                " a parolei, ultimul mail primit reprezinta codul valid!");
    }

    public Cont findCont(String mail) {
        return repo.findOne(mail);
    }
}
