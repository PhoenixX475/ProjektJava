package logic.mrowki;

import graphics.MapaPanel;
import logic.rozne.Coordinates;
import logic.rozne.ObiektMapy;

import java.awt.*;
import java.util.*;
import java.util.List;


public class Mrowisko extends ObiektMapy {
    // Pola dotyczące samego mrowiska
    private int level;
    private int durability;
    private int maxDurability;
    public int stickCount;
    public int foodCount;
    private MapaPanel mapa;

    // Pola dotyczące mrówek danego mrowiska
    private int antCount;
    private int antMax;
    public List<Mrowka> mrowki;

    private static final Random random = new Random();
    private final Color kolor;
    private static final Set<Color> zajeteKolory = new HashSet<>();

    public Mrowisko(int x, int y, MapaPanel mapa) {
        super(x,y);

        this.level = 1;
        this.durability = 100;
        this.maxDurability = 100;
        this.stickCount = 0;
        this.foodCount = 50;

        this.mapa = mapa;
        this.antCount = 0;
        this.antMax = 3;
        this.mrowki = new ArrayList<>();

        this.kolor = UnikalnyKolor();
    }


    public void levelUp () {
        if(stickCount > 5*level) {
            stickCount -= 5*level;

            level++;
            antMax += 2;
            maxDurability += 0.2*maxDurability;


            System.out.println("[Mrowisko  zostalo ulepszone]");
        }
    }

    public void starvation() {
        if(foodCount == 0) {
            durability -= 1;
        }
    }

    public void defeat() {
        if(durability < 0 ) {
            onMap = false;
            for(Mrowka m: mrowki) {
                m.onMap = false;
            }
        }
    }

    public void foodDrain() {
        if (foodCount>0) foodCount -= 0.1*mrowki.size();
        if (foodCount<0) foodCount=0;
    }

    private void regeneration() {
        if(foodCount>0 && durability < maxDurability) durability += 5;
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
        if(antCount < antMax) {

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
        g.fillRect(x * rozmiarPola,y * rozmiarPola, rozmiarPola * 5 + level, rozmiarPola * 5 + level);
    }

    public void update() {
        if(onMap) {
            // aktualizujemy stan dla każdej mrówki
            for(Mrowka m: mrowki) {
                m.update();
            }
            foodDrain();
            levelUp();  // ulepszamy mrowisko jeśli można
            starvation();
            defeat();
            regeneration();
            System.out.println("food"+foodCount);
            System.out.println("hp"+durability);

        }
    }

}

