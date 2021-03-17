package repository.page;

import domain.Cerere;
import domain.Eveniment;
import service.CerereService;

import java.util.List;

public class PageCereriTrimise extends Page<Cerere> {
    private CerereService service;
    private Long idUser;

    public PageCereriTrimise(Integer size, CerereService service, Long idUser) {
        super(size);
        this.service = service;
        this.idUser = idUser;
        refreshPage();
    }

    @Override
    void nextPage() {
        List<Cerere> list = service.cereriTrimise(idUser, size, pagina * size);
        if (list.size() != 0) {
            pagina++;
            elemente = list;
        }
    }

    @Override
    void previousPage() {
        if (pagina != 1) {
            pagina--;
            elemente = service.cereriTrimise(idUser, size, (pagina - 1) * size);
        }
    }

    @Override
    public void refreshPage() {
        elemente = service.cereriTrimise(idUser, size, (pagina - 1) * size);
    }
}
