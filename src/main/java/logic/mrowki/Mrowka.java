package logic.mrowki;
import logic.rozne.Coordinates;
import logic.rozne.ObiektMapy;

import java.awt.*;
import java.sql.SQLOutput;
import java.util.LinkedList;
import java.util.Random;

/**
 * Bazowa klasa dla mrówek*/


public abstract class Mrowka extends ObiektMapy {
    // Pola mrówki
    protected int hp;  // Punkty życia mrówki jak 0 to umiera
    protected double damage;
    public Mrowka fights;
    public Mrowisko myMrowisko;

    // Pola mrówki odpowiedzialne za położenie na mapie
    public Coordinates coordinates;
    public Random randomMoveX = new Random();
    public Random randomMoveY = new Random();

    public ObiektMapy targeting;


    public Mrowka(int hp, double damage, int x,int y,Mrowisko mrowisko) {
        super(x,y);
        this.hp = hp;
        this.damage = damage;
        this.fights = null;
        this.myMrowisko = mrowisko;
        this.targeting = null;
        //this.coordinates = new Coordinates(x,y);
    }

    // Metody odpowiedzialne za zadawanie i otrzymywanie obrażeń przez mrówki
    public double getDamage() {
        return damage;
    }
    public void dealDamage(double dmg) {
        hp -= dmg;
    }


    // Losowe poruszanie się mrówek
    public void randomMove() {
        // Losujemy zmianę pozycji mrówki o jedną jednostkę na każdej osi
        if(targeting == null) {
            x += randomMoveX.nextInt(3) - 1;
            y += randomMoveY.nextInt(3) - 1;

            if(x<0) x=1;
            if(x>100) x=99;
            if(y<0) y=1;
            if(y>100) y=99;

        }

    }


    public void moveToTarget() {
        if (targeting == null) return;

        int dx = Integer.compare(targeting.x, this.x);
        int dy = Integer.compare(targeting.y, this.y);

        // Przesuwamy tylko o 1 krok w danym kierunku
        this.x += dx;
        this.y += dy;
    }



    public ObiektMapy checkArea(LinkedList<ObiektMapy> listaObiektow) {
        for (ObiektMapy obj : listaObiektow) {
            if (obj == this) continue; // pomiń samą siebie
            if (!obj.onMap) continue;  // pomiń obiekty, które już zostały zabrane
            // Sprawdź zasięg 3x3 (lub większy)
            if(obj == this.myMrowisko) continue;
            if (Math.abs(obj.x - this.x) <= 5 && Math.abs(obj.y - this.y) <= 5) {
                return obj;
            }
        }
        return null;
    }


    public int getHp() {
        return hp;
    }


}
