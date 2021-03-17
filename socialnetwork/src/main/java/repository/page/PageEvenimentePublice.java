package repository.page;

import domain.Eveniment;
import service.EvenimentService;

import java.util.List;

public class PageEvenimentePublice extends Page<Eveniment> {
    private EvenimentService service;
    private Long idUser;

    public PageEvenimentePublice(Integer size, EvenimentService service, Long idUser) {
        super(size);
        this.service = service;
        this.idUser = idUser;
        refreshPage();
    }

    @Override
    void nextPage() {
        List<Eveniment> list = service.getEvenimetePublice(idUser, size, pagina * size);
        if (list.size() != 0) {
            pagina++;
            elemente = list;
        }
    }

    @Override
    void previousPage() {
        if (pagina != 1) {
            pagina--;
            elemente = service.getEvenimetePublice(idUser, size, (pagina - 1) * size);
        }
    }

    @Override
    public void refreshPage() {
        elemente = service.getEvenimetePublice(idUser, size, (pagina - 1) * size);
    }
}
