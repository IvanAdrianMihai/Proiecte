package service;

import domain.Utilizator;
import domain.validators.RepositoryException;
import domain.validators.ServiceException;
import domain.validators.ValidationException;
import domain.validators.Validator;
import repository.database.UtilizatorRepositoryDB;

import java.util.List;

/**
 * Se realizeaza functionalitatil necesare doar pentru a memora utilizatori
 */

public class UtilizatorService {
    private final UtilizatorRepositoryDB repo;
    private Validator<Utilizator> validator;

    /**
     * Metoda creaza o noua entitate service pentru utilizatori
     *
     * @param repo de tip Repository<Long, Utilizator>
     */
    public UtilizatorService(UtilizatorRepositoryDB repo, Validator<Utilizator> validator) {
        this.repo = repo;
        this.validator = validator;
    }

    /**
     * Metoda incearca sa adauge utilizatorul in repository
     *
     * @param utilizator de tip Utilizator
     * @throws ServiceException – if the entity is not valid
     */
    public void addUtilizator(Utilizator utilizator) throws ServiceException {
        try {
            validator.validate(utilizator);
            repo.save(utilizator);
        } catch (ValidationException v) {
            throw new ServiceException(v.getMessage());
        }
    }

    /**
     * Metoda incearca sa stearga utilizatorul cu id-ul id din repository
     *
     * @param id de tip Long
     * @return the removed entity
     * @throws ServiceException – daca nu exista utilizatorul care se doreste sters
     */
    public Utilizator deleteUtilizator(Long id) throws ServiceException {
        try {
            Utilizator u = repo.findOne(id);
            if (u != null) {
                repo.delete(id);
                return u;
            }
            throw new RepositoryException("\tNu exista utilizatorul care trebuie sters\n");
        } catch (RepositoryException r) {
            throw new ServiceException(r.getMessage());
        }
    }

    public Utilizator update(Utilizator utilizator) throws ServiceException {
        try {
            validator.validate(utilizator);
            Utilizator u = repo.findOne(utilizator.getId());

            if (u != null) {
                repo.update(utilizator);
                return u;
            }
            throw new ServiceException("\tNu exista acest utilizator pentru a se face update\n");
        } catch (ValidationException v) {
            throw new ServiceException(v.getMessage());
        }
    }

    /**
     * @return toate entitatile utilizator
     */
    public Iterable<Utilizator> getAll() {
        return repo.findAll();
    }

    /**
     * Metoda returneaza utilizatorul cu id-ul id sau null daca acesta nu exista
     */
    public Utilizator findUtilizator(Long id) {
        return repo.findOne(id);
    }

    /**
     * @return numarul de utilizatori din repository sau null daca nu exista
     */
    public long size() {
        return repo.size();
    }

    public List<Utilizator> notYetFriends(Long id_user, Integer limit, String last, String first) {
        return repo.notYetFriends(id_user, limit, last, first);
    }

    public List<Utilizator> otherThanMe(Long id_user, Integer limit, String last, String first) {
        return repo.otherThanMe(id_user, limit, last, first);
    }

    public List<Utilizator> getFriends(Long id_user, Integer limit, Integer offset, String last, String first) {
        return repo.getFriends(id_user, limit, offset, last, first);
    }
}
