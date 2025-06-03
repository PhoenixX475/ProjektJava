package logic.obiekty;

public class Lisc {
    private int foodContribution; // decyduje ile jeden lisc daje jedzenia (foodCount mrowiska)
    public boolean onGround; // jeśli true to mrówka może go podnieść


    // Kontruktor domyślny
    public Lisc() {
        this.foodContribution = 5;
        this.onGround = true;
    }
}

