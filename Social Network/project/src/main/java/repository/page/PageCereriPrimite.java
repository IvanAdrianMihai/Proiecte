package repository.page;

import domain.Cerere;
import service.CerereService;

import java.util.List;

public class PageCereriPrimite extends Page<Cerere> {
    private CerereService service;
    private Long idUser;

    public PageCereriPrimite(Integer size, CerereService service, Long idUser) {
        super(size);
        this.service = service;
        this.idUser = idUser;
        refreshPage();
    }

    @Override
    void nextPage() {
        List<Cerere> list = service.cereriPrimite(idUser, size, pagina * size);
        if (list.size() != 0) {
            pagina++;
            elemente = list;
        }
    }

    @Override
    void previousPage() {
        if (pagina != 1) {
            pagina--;
            elemente = service.cereriPrimite(idUser, size, (pagina - 1) * size);
        }
    }

    @Override
    public void refreshPage() {
        elemente = service.cereriPrimite(idUser, size, (pagina - 1) * size);
    }
}
