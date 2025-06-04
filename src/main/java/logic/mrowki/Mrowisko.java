package logic.mrowki;

import logic.rozne.Coordinates;

import java.util.ArrayList;
import java.util.List;


public class Mrowisko {
    // Pola dotyczące samego mrowiska
    public int id;
    private int level;
    private int durability;
    public int stickCount;
    public int foodCount;


    // Pola dotyczące mrówek danego mrowiska
    private int antCount;
    private int antMax;
    public List<Mrowka> mrowki;
    public Coordinates coordinates;

    public Mrowisko(int x, int y) {
        //this.id = id;
        this.level = 1;
        this.durability = 100;
        this.stickCount = 0;
        this.foodCount = 30;


        this.antCount = 0;
        this.antMax = 5;
        this.mrowki = new ArrayList<>();
        this.coordinates = new Coordinates(x,y);
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




    public void createAnt() {
        if(antCount < antMax) {
            Robotnica nowaRobotnica = new Robotnica(coordinates.x, coordinates.y);
            antCount++;
            mrowki.add(nowaRobotnica);
            //System.out.println("Dodano mrowke do mrowiska " + antCount);


        }
    }



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





}

