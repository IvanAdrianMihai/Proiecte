package repository.page;

import domain.Eveniment;
import service.EvenimentService;

import java.util.List;

public class PageEvenimenteCreate extends Page<Eveniment> {
    private EvenimentService service;
    private Long idUser;
    private boolean all;

    public PageEvenimenteCreate(Integer size, EvenimentService service, Long idUser) {
        super(size);
        all=false;
        this.service = service;
        this.idUser = idUser;
        refreshPage();
    }

    public boolean isAll() {
        return all;
    }

    public void setAll(boolean all) {
        this.all = all;
    }

    @Override
    void nextPage() {
        List<Eveniment> list = service.getEvenimenteleCreateUtilizator(idUser, all, size, pagina * size);
        if (list.size() != 0) {
            pagina++;
            elemente = list;
        }
    }

    @Override
    void previousPage() {
        if (pagina != 1) {
            pagina--;
            elemente = service.getEvenimenteleCreateUtilizator(idUser, all, size, (pagina - 1) * size);
        }
    }

    @Override
    public void refreshPage() {
        elemente = service.getEvenimenteleCreateUtilizator(idUser, all, size, (pagina - 1) * size);
    }
}
