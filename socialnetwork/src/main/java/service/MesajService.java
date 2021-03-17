package service;

import domain.Cerere;
import domain.Message;
import domain.validators.ServiceException;
import domain.validators.ValidationException;
import domain.validators.Validator;
import repository.database.MesajRepositoryBD;

import java.util.List;

public class MesajService {
    private MesajRepositoryBD repo;
    private Validator<Message> validator;

    public MesajService(MesajRepositoryBD repo, Validator<Message> validator) {
        this.repo = repo;
        this.validator = validator;
    }

    /**
     * @param message este de tip mesaj si nu trebuie sa fie null
     * @throws ServiceException daca mesajul nu este valid
     */
    public void addMesaj(Message message) throws SecurityException {
        try {
            validator.validate(message);
            repo.save(message);
        } catch (ValidationException v) {
            throw new ServiceException(v.getMessage());
        }
    }

    /**
     * @return toate mesajele ordonate cronologic
     */
    public Iterable<Message> findAll() {
        return repo.findAll();
    }

    /**
     * @param id de tip Long reprezinta id-ul Mesajului si nu trebuie sa fie null
     * @return mesajul cu id-ul id
     * sau null daca mesajul cu acest id nu exista
     */
    public Message findOne(Long id) {
        return repo.findOne(id);
    }

    /**
     * @return numarul de elemente din repository
     */
    public long size() {
        return repo.size();
    }

    /**
     * Metoda sterge mesajele retinute de system, dar care nu mai sunt importante pentru ceilalti utilizatori din aplicatie
     */
    public void deleteMesjeSystem() {
        repo.deleteMesjeSystem();
    }

    public List<Message> mesajeTrimise(Long id_user, Integer limit, Integer offset) {
        return repo.mesajeTrimise(id_user, limit, offset);
    }

    public List<Message> mesajePrimite(Long id_user, Integer limit, Integer offset) {
        return repo.mesajePrimite(id_user, limit, offset);
    }
}
