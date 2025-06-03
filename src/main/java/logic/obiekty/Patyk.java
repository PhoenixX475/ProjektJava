package logic.obiekty;

class Patyk extends Przedmiot {
    private int upgradeContribution; // decyduje ile pojedynczy patyk daje do ulepszenia mrowiska

    // Kontruktor domyślny
    public Patyk() {
        this.upgradeContribution = 1;
    }
}