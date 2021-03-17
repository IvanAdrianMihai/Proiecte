package repository.page;

import java.util.ArrayList;
import java.util.List;

public abstract class Page<T> {
    protected Integer pagina;
    protected Integer size;
    protected List<T> elemente;

    public Page(Integer size) {
        this.pagina = 1;
        this.size = size;
        elemente=new ArrayList<>();
    }

    public int getPagina() {
        return pagina;
    }

    public int getSize() {
        return size;
    }

    public List<T> getElemente() {
        return elemente;
    }

    public void pageFactory(Integer index) {
        if (pagina < index)
            nextPage();
        else if (pagina > index)
            previousPage();
    }

    abstract void nextPage();

    abstract void previousPage();

    public abstract void refreshPage();
}
