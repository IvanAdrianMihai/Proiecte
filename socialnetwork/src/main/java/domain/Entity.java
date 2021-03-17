package domain;

import java.io.Serializable;

public class Entity<ID> implements Serializable {

    private static final long serialVersionUID = 7331115341259248461L;
    private ID id;

    /**
     * @return id-ul de tip ID al entitatii
     */
    public ID getId() {
        return id;
    }

    /**
     * Metoda seteaza id-ul entitatii ca fiind id
     *
     * @param id de tip ID
     */
    public void setId(ID id) {
        this.id = id;
    }
}