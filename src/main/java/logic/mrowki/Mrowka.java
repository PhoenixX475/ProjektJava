package logic.mrowki;
import logic.rozne.ObiektMapy;

import java.awt.*;
import java.sql.SQLOutput;
import java.util.LinkedList;
import java.util.Random;

/**
 * Bazowa klasa dla mrówek*/


public abstract class Mrowka extends ObiektMapy {
    // Pola mrówki
    protected int damage;
    public ObiektMapy fights;
    public Mrowisko myMrowisko;

    // Pola mrówki odpowiedzialne za położenie na mapie
    public Random randomMoveX = new Random();
    public Random randomMoveY = new Random();
    public int zabiteMrowki = 0; // liczba zabitych mrówek przez tę mrówkę
    public ObiektMapy targeting;


    public Mrowka(int hp, int damage, int x,int y,Mrowisko mrowisko) {
        super(x,y,hp);
        this.damage = damage;
        this.fights = null;
        this.myMrowisko = mrowisko;
        this.targeting = null;
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


    protected void moveToTarget() {
        if (targeting == null) return;
        if(!targeting.onMap) {
            targeting = null;
            return;
        }

        int dx = Integer.compare(targeting.x, this.x);
        int dy = Integer.compare(targeting.y, this.y);

        // Przesuwamy tylko o 1 krok w danym kierunku
        this.x += dx;
        this.y += dy;
    }



    protected ObiektMapy checkArea(LinkedList<ObiektMapy> listaObiektow) {
        for (ObiektMapy obj : listaObiektow) {
            if (obj == this) continue; // pomiń samą siebie
            if (!obj.onMap) continue;  // pomiń obiekty, które już zostały usunięte
            if(obj == this.myMrowisko) continue; // pomiń własne mrowisko
            if(this.myMrowisko.mrowki.contains(obj)) continue; // pomiń przyjazne mrówki


            // jeśli obiekt jest w zasięgu to zwróć ten obiekt
            if (Math.abs(obj.x - this.x) <= 5 && Math.abs(obj.y - this.y) <= 5) {
                return obj;
            }
        }
        return null;
    }

    protected void attackTarget() {
        if(fights == null) return;
        if(hp <= 0) return;
        if(!onMap) return;
        if(targeting == null) return;

        if (Math.abs(targeting.x - this.x) <= 3 && Math.abs(targeting.y - this.y) <= 3) {
            //System.out.println(this +  " atakuje " +  fights + "fights hp: " + fights.hp);
            fights.dealDamage(damage);
            if(fights.hp <= 0) {
                //System.out.println(fights  + " umarla");
                fights.onMap = false;
            }
        }

    }



    @Override
    public void die() {
        if(hp<=0) {
            onMap = false;
            targeting = null;
            fights = null;
            myMrowisko.antCount--;
        }
        if(myMrowisko == null || myMrowisko.hp <= 0) onMap = false;
    }

    protected void regeneration() {
        if(fights == null && hp<maxHp) {
            hp++;
        }
    }



}
