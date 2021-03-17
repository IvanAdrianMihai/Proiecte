package repository.page;

import domain.Eveniment;
import service.EvenimentService;

import java.util.List;

public class PageEvenimenteParticipare extends Page<Eveniment> {
    private EvenimentService service;
    private Long idUser;

    public PageEvenimenteParticipare(Integer size, EvenimentService service, Long idUser) {
        super(size);
        this.service = service;
        this.idUser = idUser;
        refreshPage();
    }

    @Override
    void nextPage() {
        List<Eveniment> list = service.getEvenimenteleParticip(idUser, size, pagina * size);
        if (list.size() != 0) {
            pagina++;
            elemente = list;
        }
    }

    @Override
    void previousPage() {
        if (pagina != 1) {
            pagina--;
            elemente = service.getEvenimenteleParticip(idUser, size, (pagina - 1) * size);
        }
    }

    @Override
    public void refreshPage() {
        elemente = service.getEvenimenteleParticip(idUser, size, (pagina - 1) * size);
    }
}
