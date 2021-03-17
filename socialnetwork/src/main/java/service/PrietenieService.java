package service;

import domain.Prietenie;
import domain.Tuple;
import domain.validators.RepositoryException;
import domain.validators.ServiceException;
import domain.validators.ValidationException;
import domain.validators.Validator;
import repository.Repository0;

/**
 * Se realizeaza functionalitatil necesare doar pentru a memora prietenii
 */

public class PrietenieService {
    private final Repository0<Tuple<Long, Long>, Prietenie> repo;
    private Validator<Prietenie> validator;
    private final UtilizatorService utilizatorService;


    /**
     * Metoda creaza un nou service pentru prietenii care foloseste un Repository de prietenii
     *
     * @param repo de tip Repository
     */
    public PrietenieService(Repository0<Tuple<Long, Long>, Prietenie> repo, Validator<Prietenie> validator,
                            UtilizatorService utilizatorService) {
        this.repo = repo;
        this.validator = validator;
        this.utilizatorService = utilizatorService;
    }

    /**
     * Metoda adauga noua prietenie in repo doar daca aceasta nu exista deja
     *
     * @param prietenie de tip Prietenie, not null
     * @throws ServiceException daca prietenia nu este valida,
     *                          daca prietenia exista deja
     */
    public void addPrietenie(Prietenie prietenie) throws ServiceException {
        try {
            validator.validate(prietenie);
            if (repo.findOne(new Tuple<>(prietenie.getId().getLeft(), prietenie.getId().getRight())) != null)
                throw new RepositoryException("\tCei doi sunt prieteni\n");
            repo.save(prietenie);
        } catch (ValidationException v) {
            throw new ServiceException(v.getMessage());
        } catch (RepositoryException r) {
            throw new ServiceException(r.getMessage());
        }
    }

    /**
     * Metoda elimina prietenia dintre utilizatorii cu id1 si id2, doar daca exista
     *
     * @param id1 de tip Long, trebuie sa fie a unui utilizator existent
     * @param id2 de tip Long, trebuie sa fie a unui utilizator existent
     * @return prietenie eliminata
     * @throws ServiceException daca nu exista prieteni intre cei doi,
     *                          daca nu exista cei doi utilizatori in repo
     */
    public Prietenie deletePrietenie(Long id1, Long id2) throws ServiceException {
        try {
            String eroare = "";
            if (utilizatorService.findUtilizator(id1) == null)
                eroare = eroare + "\tPrimul utilizator nu exista\n";
            if (utilizatorService.findUtilizator(id2) == null)
                eroare = eroare + "\tAl doilea utilizator nu exista\n";
            if (!eroare.isEmpty())
                throw new RepositoryException(eroare);

            Prietenie p = repo.findOne(new Tuple<>(id1, id2));
            if (p == null)
                throw new RepositoryException("\tCei doi nu sunt prieteni\n");
            repo.delete(p.getId());
            return p;
        } catch (RepositoryException r) {
            throw new ServiceException(r.getMessage());
        }
    }

    /**
     * @return toate entitatile prietenie
     */
    public Iterable<Prietenie> getAll() {
        return repo.findAll();
    }
}
