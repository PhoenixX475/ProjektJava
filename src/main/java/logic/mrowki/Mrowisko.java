package logic.mrowki;

import graphics.MapaPanel;
import logic.rozne.Coordinates;
import logic.rozne.ObiektMapy;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Mrowisko extends ObiektMapy {
    // Pola dotyczące samego mrowiska
    public int id;
    private int level;
    private int durability;
    public int stickCount;
    public int foodCount;
    private MapaPanel mapa;

    // Pola dotyczące mrówek danego mrowiska
    private int antCount;
    private int antMax;
    public List<Mrowka> mrowki;
    //public Coordinates coordinates;

    public Mrowisko(int x, int y, MapaPanel mapa) {
        super(x,y,3,3);

        //this.id = id;
        this.level = 1;
        this.durability = 100;
        this.stickCount = 0;
        this.foodCount = 30;

        this.mapa = mapa;
        this.antCount = 0;
        this.antMax = 5;
        this.mrowki = new ArrayList<>();
        //this.coordinates = new Coordinates(x,y);
    }


    public void levelUp () {
        if(stickCount > 5*level) {
            stickCount -= 5*level;

            level++;
            antMax += 2;
            durability += 0.2*durability;


            System.out.println("[Mrowisko "+id+" zostalo ulepszone]");
        }
    }



    public void foodDrain() {
        foodCount -= mrowki.size();
    }

    public int getLevel() {
        return level;
    }
    public int getDurability() {
        return durability;
    }
    public int getAntCount() {
        return antCount;
    }
    public int getAntMax() {
        return antMax;
    }




    public void createAnt(MapaPanel mapa) {
        if(antCount < antMax) {

            // losowanie miejsca gdzie mrówka się zespawni
            Random rnd = new Random();
            int rndX = (rnd.nextInt(3) -1 )*2;
            int rndY = (rnd.nextInt(3) - 1 )*2;


            // Zolnierz ma 1/5 szansy na stworzenie
            if(rnd.nextInt(5) == 0) {
                Zolnierz nowyZolnierz = new Zolnierz(x+rndX,y+rndY);
                mrowki.add(nowyZolnierz);
                mapa.listaObiektow.add(nowyZolnierz);

            }
            else {
                Robotnica nowaRobotnica = new Robotnica(x+rndX,y+rndY);
                mrowki.add(nowaRobotnica);
                mapa.listaObiektow.add(nowaRobotnica);
            }

            antCount++;


        }
    }
//System.out.println("Dodano mrowke do mrowiska " + antCount);



    public void destroyMrowisko() {
        // usuwamy mrowisko z listy która przechowywa wszystkie mrowiska znajdujące się na mapie
        // można byłoby przenieść tą funkcję jako ogólną do wszystkich obiektów w sumie
        // zależy od zapisywania obiektów na mapie
        // do zrobienia później pod koniec implementacji
    }


    public void wypiszMrowki() {
        int count = 0;
        for(Mrowka m: mrowki) {
            System.out.printf("[%d] %s %d\n",++count,"mrowka",m.getHp());
        }
    }

    public void mrowiskoInfo() {
        System.out.println("\n[Mrowisko" + id + " info]");
        System.out.println("Poziom: " + level);
        System.out.println("Wytrzymalosc: " + durability);
        System.out.println("Liczba mrowek: " + antCount);
        System.out.println("Maksymalna liczba mrowek: " + antMax);
        System.out.println("Liczba patykow: " + stickCount);
        System.out.println("Liczba pozywienia: " + foodCount);
    }




    public void drawObject(Graphics g, int rozmiarPola ) {
        g.setColor(Color.RED);
        g.fillRect(x * rozmiarPola,y * rozmiarPola, rozmiarPola * 5, rozmiarPola * 5 );
    }

    public void update() {
        for(Mrowka m: mrowki) {
            m.update();
        }
        foodDrain();
    }

}

