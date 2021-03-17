package domain;

public class UtilizatorEveniment extends Utilizator{
    private NotificationType notificari;
    /**
     * Metoda creaza un utilizator avand firstName firstName si lastName lastName
     *
     * @param firstName de tip String
     * @param lastName  de tip String
     */
    public UtilizatorEveniment(String firstName, String lastName, NotificationType notificari) {
        super(firstName, lastName);
        this.notificari=notificari;
    }

    public NotificationType getNotificari() {
        return notificari;
    }

    public void setNotificari(NotificationType notificari) {
        this.notificari = notificari;
    }
}
