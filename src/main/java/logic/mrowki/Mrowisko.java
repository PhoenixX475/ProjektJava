package logic.mrowki;

import graphics.MapaPanel;
import logic.rozne.ObiektMapy;

import java.awt.*;
import java.util.*;
import java.util.List;


public class Mrowisko extends ObiektMapy {
    // Pola dotyczące samego mrowiska
    private int level;
    public int stickCount;
    public int foodCount;
    private MapaPanel mapa;

    // Pola dotyczące mrówek danego mrowiska
    public int antCount;
    private int antMax;
    public List<Mrowka> mrowki;

    private static final Random random = new Random();
    private final Color kolor;
    private static final Set<Color> zajeteKolory = new HashSet<>();

    public Mrowisko(int x, int y, MapaPanel mapa) {
        super(x,y,100);

        this.level = 1;
        this.stickCount = 0;
        this.foodCount = 200;

        this.mapa = mapa;
        this.antCount = 0;
        this.antMax = 3;
        this.mrowki = new ArrayList<>();

        this.kolor = UnikalnyKolor();
    }


    public int getLevel(){
        return level;
    }

    public int getAntCount(){
        return antCount;
    }



    public void levelUp () {
        if(stickCount > 5*level) {
            stickCount -= 5*level;

            level++;
            antMax += 2;
            maxHp += 0.2*maxHp;


            //System.out.println("[Mrowisko  zostalo ulepszone]");
        }
    }



    public void starvation() {
        if(foodCount == 0) {
            hp -= 1;
        }
    }

    @Override
    public void die() {
        if(hp <= 0 ) {
            for(Mrowka m: mrowki) {
                m.onMap = false;
            }
            onMap = false;
        }
    }

    public void foodDrain() {
        if (foodCount>0) foodCount -= 0.1*mrowki.size();
        if (foodCount<0) foodCount=0;
    }

    private void regeneration() {
        if(foodCount>0 && hp < maxHp) hp += 5;
        if(hp > maxHp) hp = maxHp;
    }

    private Color UnikalnyKolor() {
        Color c;
        do {
            int r = random.nextInt(100);
            int g = random.nextInt(100);
            int b = random.nextInt(100);

            c = new Color(r,g,b);

        }while (zajeteKolory.contains(c));

        zajeteKolory.add(c);

        return c;
    }



    public void createAnt(MapaPanel mapa) {
        if(onMap && antCount < antMax) {

            // losowanie miejsca gdzie mrówka się zespawni
            Random rnd = new Random();
            int rndX = (rnd.nextInt(3) -1 )*2;
            int rndY = (rnd.nextInt(3) - 1 )*2;


            // Zolnierz ma 1/5 szansy na stworzenie
            if(rnd.nextInt(5) == 0) {
                Zolnierz nowyZolnierz = new Zolnierz(x+rndX,y+rndY,this,mapa,this.kolor);
                mrowki.add(nowyZolnierz);
                mapa.listaObiektow.add(nowyZolnierz);

            }
            else {
                Robotnica nowaRobotnica = new Robotnica(x+rndX,y+rndY,this,mapa,this.kolor);
                mrowki.add(nowaRobotnica);
                mapa.listaObiektow.add(nowaRobotnica);
            }

            antCount++;


        }
    }





    public void drawObject(Graphics g, int rozmiarPola ) {
        g.setColor(kolor);
        int size;
        if( level <= 4) size = level;
        else size = 2*level;
        g.fillRect(x * rozmiarPola,y * rozmiarPola, rozmiarPola * 5 + size, rozmiarPola * 5 + size);
    }

    public void update() {
        //System.out.println(this + " " + hp);


        if(onMap) {
            // aktualizujemy stan dla każdej mrówki
            for(Mrowka m: mrowki) {
                m.update();
            }
            foodDrain();
            levelUp();  // ulepszamy mrowisko jeśli można
            starvation();
            die();
            regeneration();
            //System.out.println("food"+foodCount);
            //System.out.println("hp"+hp);

        }
    }


}

