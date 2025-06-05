package logic.obiekty;

public class Patyk extends Przedmiot {
    private int upgradeContribution; // decyduje ile pojedynczy patyk daje do ulepszenia mrowiska

    // Kontruktor domyślny
    public Patyk() {
        this.upgradeContribution = 1;
    }
}