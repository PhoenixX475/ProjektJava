package logic.mrowki;
import logic.rozne.Coordinates;
import java.util.Random;

/**
 * Bazowa klasa dla mrówek*/


public abstract class Mrowka {
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
        this.hp = hp;
        this.damage = damage;
        this.fights = null;
        this.coordinates = new Coordinates(x,y);
    }

    // Metody odpowiedzialne za zadawanie i otrzymywanie obrażeń przez mrówki
    public double getDamage() {
        return damage;
    }
    public void dealDamage(double dmg) {
        hp -= dmg;
    }


    public Coordinates randomMove() {
        // Losujemy zmianę pozycji mrówki o jedną jednostkę na każdej osi
        coordinates.x += randomMoveX.nextInt()%3 - 1;
        coordinates.y += randomMoveY.nextInt()%3 - 1;


        return coordinates;
    }


    public void checkEnemy() {
        // Znów trzeba zrobić listę wszystkich obiektów jakie są przechowywane na mapie
        // sprawdzamy koordynaty wokół mrówki ( pole 3x3 ) i jak tak to przypisujemy fights = przeciwinik

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++)
            {
                int x = coordinates.x - 1 + j;
                int y = coordinates.y - 1 + i;

                //Pole pole = map[x][y];

                //switch(pole){
                  //  case pole.typ = mrówka {

                    //}
                //}

            }
        }

    }



    public void returnToMrowisko() {
       while(true) {

       }
    }



    public int getHp() {
        return hp;
    }

}
