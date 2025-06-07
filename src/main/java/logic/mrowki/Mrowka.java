package logic.mrowki;
import graphics.MapaPanel;
import logic.rozne.Coordinates;
import logic.rozne.ObiektMapy;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

/**
 * Bazowa klasa dla mrówek*/


public abstract class Mrowka extends ObiektMapy {
    // Pola mrówki
    protected int hp;  // Punkty życia mrówki jak 0 to umiera
    protected double damage;
    public Mrowka fights;
    protected Mrowisko myMrowisko;

    // Pola mrówki odpowiedzialne za położenie na mapie
    public Coordinates coordinates;
    public Random randomMoveX = new Random();
    public Random randomMoveY = new Random();



    public Mrowka(int hp, double damage, int x,int y) {
        super(x,y);
        this.hp = hp;
        this.damage = damage;
        this.fights = null;
        //this.coordinates = new Coordinates(x,y);
    }

    // Metody odpowiedzialne za zadawanie i otrzymywanie obrażeń przez mrówki
    public double getDamage() {
        return damage;
    }
    public void dealDamage(double dmg) {
        hp -= dmg;
    }


    public void randomMove() {
        // Losujemy zmianę pozycji mrówki o jedną jednostkę na każdej osi
        x += randomMoveX.nextInt(3) - 1;
        y += randomMoveY.nextInt(3) - 1;
        if(x<0) x=1;
        if(x>100) x=99;
        if(y<0) y=1;
        if(y>100) y=99;
    }

    public ObiektMapy checkArea(LinkedList<ObiektMapy> listaObiektow) {
        // Dla wszystkich obiektów na mapie
        for(ObiektMapy obj:listaObiektow) {
            if(obj instanceof Mrowka) {
                // Sprawdzamy czy są w polu tej mrówki
                for(int i = -1; i<2; i++) {
                    for( int j = -1; j<2; j++) {
                        if(obj.x == i && obj.y == j) {
                            System.out.println("Mrowka trafila na "+obj);
                            return obj;
                        }
                    }
                }
            }
        }
        return null;
    }

    public int getHp() {
        return hp;
    }


}
