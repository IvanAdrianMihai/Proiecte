package repository.page;

import domain.Message;
import domain.Utilizator;
import service.UtilizatorService;

import java.util.List;

public class PagePrieteni extends Page<Utilizator> {
    private UtilizatorService service;
    private Long idUser;
    private String s1, s2;

    public PagePrieteni(Integer size, UtilizatorService service, Long idUser) {
        super(size);
        this.service = service;
        this.idUser = idUser;
        refreshPage();
    }

    public void setS1(String s1) {
        this.s1 = s1;
    }

    public void setS2(String s2) {
        this.s2 = s2;
    }

    @Override
    void nextPage() {
        List<Utilizator> list = service.getFriends(idUser, size, pagina * size, s1, s2);
        if (list.size() != 0) {
            pagina++;
            elemente = list;
        }
    }

    @Override
    void previousPage() {
        if (pagina != 1) {
            pagina--;
            elemente = service.getFriends(idUser, size, (pagina - 1) * size, s1, s2);
        }
    }

    @Override
    public void refreshPage() {
        elemente = service.getFriends(idUser, size, (pagina - 1) * size, s1, s2);
    }
}
