package logic.rozne;

import java.awt.*;

public abstract class ObiektMapy {
    public int x,y;
    public int width,height = 1;
    public boolean onMap;
    public int hp, maxHp;


    // Konstruktor dla żywych obiektów rozmiaru 1x1
    public ObiektMapy(int x,int y,int maxHp) {
        this.x = x;
        this.y = y;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.onMap = true;
    }

    // Konstrutkor do tworzenia obiektów większych niż 1x1 ( Mrowisko )
    public ObiektMapy(int x,int y,int maxHp, int width, int height) {
        this(x,y,maxHp);
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.width = width;
        this.height = height;
        this.onMap = true;
    }

    // konstruktor dla przedmiotów
    public ObiektMapy(int x, int y) {
        this.x = x;
        this.y = y;
        this.onMap = true;
    }

    public void dealDamage(int dmg) {
        hp -= dmg;
    }

    public void die() {
        if(hp<=0) {
            onMap = false;

        }
    }

    public boolean occupied(int dx, int dy) {
        // dx i dy oznacza środek jakiegoś obiektu
        // x i y oznacza środek naszego obiektu
        // width i height oznaczają szerokość i wysokość naszego obiektu
        // sprawdzamy czy nasz obiekt po przemieszczeniu będzie zachodził na inny obiekt
        return dx >= x && dx < x + width && dy >= y && dy < y + height;
    }

    // Rysuje obiekt na mapie
    public abstract void drawObject(Graphics g, int rozmiarPola);

    // Odpowiada za wywoływanie funkcji danego obiektu
    public abstract void update();
}
