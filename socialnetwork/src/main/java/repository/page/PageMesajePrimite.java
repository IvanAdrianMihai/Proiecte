package repository.page;

import domain.Eveniment;
import domain.Message;
import service.MesajService;

import java.util.List;

public class PageMesajePrimite extends Page<Message> {
    private MesajService service;
    private Long idUser;

    public PageMesajePrimite(Integer size, MesajService service, Long idUser) {
        super(size);
        this.service = service;
        this.idUser = idUser;
        refreshPage();
    }

    @Override
    void nextPage() {
        List<Message> list = service.mesajePrimite(idUser, size, pagina * size);
        if (list.size() != 0) {
            pagina++;
            elemente = list;
        }
    }

    @Override
    void previousPage() {
        if (pagina != 1) {
            pagina--;
            elemente = service.mesajePrimite(idUser, size, (pagina - 1) * size);
        }
    }

    @Override
    public void refreshPage() {
        elemente = service.mesajePrimite(idUser, size, (pagina - 1) * size);
    }
}
