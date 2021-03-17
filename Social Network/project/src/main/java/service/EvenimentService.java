package service;

import domain.Eveniment;
import domain.NotificationType;
import domain.Utilizator;
import domain.UtilizatorEveniment;
import domain.validators.RepositoryException;
import domain.validators.ServiceException;
import domain.validators.ValidationException;
import domain.validators.Validator;
import repository.database.EvenimentRepositoryBD;
import repository.database.UtilizatorRepositoryDB;

import java.util.List;

import static domain.NotificationType.OFF;
import static domain.NotificationType.ON;

public class EvenimentService {
    private final EvenimentRepositoryBD repo;
    private Validator<Eveniment> validator;
    private final UtilizatorRepositoryDB utilizatori;

    public EvenimentService(EvenimentRepositoryBD repo, Validator<Eveniment> validator, UtilizatorRepositoryDB utilizatori) {
        this.repo = repo;
        this.validator = validator;
        this.utilizatori = utilizatori;
    }

    public void addEveniment(Eveniment eveniment) throws ServiceException {
        try {
            validator.validate(eveniment);
            repo.save(eveniment);
        } catch (ValidationException v) {
            throw new ServiceException(v.getMessage());
        }
    }

    public Eveniment deleteEveniment(Long id, Long id_gazda) throws ServiceException {
        try {
            Eveniment e = repo.findOne(id);
            if (e != null) {
                if (e.getGazda().getId() == id_gazda) {
                    repo.delete(id);
                    return e;
                }
                throw new RepositoryException("\tNu aveti dreptul sa stergeti acest eveniment\n");
            }
            throw new RepositoryException("\tNu exista evenimentul care trebuie sters\n");
        } catch (RepositoryException r) {
            throw new ServiceException(r.getMessage());
        }
    }

    public void abonareEveniment(Long id_eveniment, Long id_user, NotificationType note) throws ServiceException {
        try {
            Eveniment e = repo.findOne(id_eveniment);
            Utilizator u = utilizatori.findOne(id_user);
            String error = "";
            if (e == null)
                error = error + "\tNu exista acest eveniment\n";
            if (u == null)
                error = error + "\tNu exista acest utilizator\n";
            if (note == null)
                error = error + "\tTipul notificarii nu trebuie sa fie null\n";
            if (u != null && e != null && e.getGazda().getId() == id_user)
                error = error + "\tNu puteti sterge un eveniment pe care nu l-ati creat\n";

            if (error.isEmpty())
                repo.abonare(id_eveniment, id_user, note);
            else
                throw new RepositoryException(error);
        } catch (RepositoryException r) {
            throw new ServiceException(r.getMessage());
        }
    }

    public void dezabonareEveniment(Long id_eveniment, Long id_user) throws ServiceException {
        try {
            Eveniment e = repo.findOne(id_eveniment);
            Utilizator u = utilizatori.findOne(id_user);
            String error = "";
            if (e == null)
                error = error + "\tNu exista acest eveniment\n";
            if (u == null)
                error = error + "\tNu exista acest utilizator\n";
            if (e != null && u != null) {
                boolean ok = false;
                for (UtilizatorEveniment ue : e.getParticipanti())
                    if (ue.getId() == id_user) {
                        ok = true;
                        break;
                    }
                if (!ok)
                    error = error + "\tNu sunteti inregistrat la acest eveniment\n";
            }
            if (error.isEmpty())
                repo.dezabonare(id_eveniment, id_user);
            else
                throw new RepositoryException(error);
        } catch (RepositoryException r) {
            throw new ServiceException(r.getMessage());
        }
    }

    public void notificariOn(Long id_eveniment, Long id_user) throws ServiceException {
        try {
            Eveniment e = repo.findOne(id_eveniment);
            Utilizator u = utilizatori.findOne(id_user);
            String error = "";
            if (e == null)
                error = error + "\tNu exista acest eveniment\n";
            if (u == null)
                error = error + "\tNu exista acest utilizator\n";
            if (error.isEmpty()) {
                boolean ok = false, typeOff = false;
                for (UtilizatorEveniment uv : e.getParticipanti())
                    if (uv.getId() == id_user) {
                        ok = true;
                        if (uv.getNotificari() == OFF)
                            typeOff = true;
                        break;
                    }
                if (!ok)
                    error = error + "\tAcest utilizator nu participa la acest eveniment\n";
                else if (!typeOff)
                    error = error + "\tSunt deja notificarile pornite\n";
            }

            if (error.isEmpty())
                repo.notificationOn(id_eveniment, id_user);
            else
                throw new RepositoryException(error);
        } catch (RepositoryException r) {
            throw new ServiceException(r.getMessage());
        }
    }

    public void notificariOff(Long id_eveniment, Long id_user) throws ServiceException {
        try {
            Eveniment e = repo.findOne(id_eveniment);
            Utilizator u = utilizatori.findOne(id_user);
            String error = "";
            if (e == null)
                error = error + "\tNu exista acest eveniment\n";
            if (u == null)
                error = error + "\tNu exista acest utilizator\n";
            if (error.isEmpty()) {
                boolean ok = false, typeOn = false;
                for (UtilizatorEveniment uv : e.getParticipanti())
                    if (uv.getId() == id_user) {
                        ok = true;
                        if (uv.getNotificari() == ON)
                            typeOn = true;
                        break;
                    }
                if (!ok)
                    error = error + "\tAcest utilizator nu participa la acest eveniment\n";
                else if (!typeOn)
                    error = error + "\tSunt deja notificarile oprite\n";
            }

            if (error.isEmpty())
                repo.notificationOff(id_eveniment, id_user);
            else
                throw new RepositoryException(error);
        } catch (RepositoryException r) {
            throw new ServiceException(r.getMessage());
        }
    }


    public Eveniment findEveniment(Long id) {
        return repo.findOne(id);
    }

    public List<Eveniment> getAll() {
        return repo.findAll();
    }

    public void trimiteNotificari() {
        repo.sendNotifications();
    }

    public long size() {
        return repo.size();
    }

    public List<Eveniment> getEvenimenteleCreateUtilizator(Long id_utilizator, boolean all, Integer limit, Integer offset) {
        return repo.getEvenimenteleCreateUtilizator(id_utilizator, all, limit, offset);
    }

    public List<Eveniment> getEvenimenteleParticip(Long id_utilizator, Integer limit, Integer offset) {
        return repo.getEvenimenteleParticip(id_utilizator, limit, offset);
    }

    public List<Eveniment> getEvenimetePublice(Long id_utilizator, Integer limit, Integer offset) {
        return repo.getEvenimetePublice(id_utilizator, limit, offset);
    }
}
