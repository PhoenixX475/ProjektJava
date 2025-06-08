package logic.mrowki;

import graphics.MapaPanel;
import logic.rozne.ObiektMapy;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Zolnierz extends Mrowka{

    public List<ObiektMapy> fights = new ArrayList<>();
    private List<ObiektMapy> notFights = new ArrayList<>();
    //public ObiektMapy fights = null;
    private final Color kolor;

    public Zolnierz(int x, int y, Mrowisko mrowisko, MapaPanel mapa, Color kolor) {
        super(30,8,x,y,mrowisko);
        this.kolor = kolor.brighter().brighter();
    }


    // Zolnierz zadaje obrażenia obszarowe
    @Override
    protected void attackTarget() {
        if(fights == null) return;
        if(hp <= 0) return;
        if(!onMap) return;
        if(targeting == null) return;

        for(ObiektMapy o : fights) {
            if(o.onMap) {
                    // Jeśli w zasięgu
                    if (Math.abs(o.x - this.x) <= 5 && Math.abs(o.y - this.y) <= 5) {
                        o.dealDamage(damage);

                        if(o.hp <= 0) {
                            notFights.add(o);
                            o.onMap = false;
                        }
                    }
                }
                else {
                    notFights.add(o);
                }
            }
        for(ObiektMapy ob : notFights) {
            if(fights.contains(ob)) fights.remove(ob);
        }
        notFights.clear();


    }

    @Override
    public void drawObject(Graphics g, int rozmiarPola) {
        g.setColor(kolor);
        g.fillRect(x * rozmiarPola,y * rozmiarPola, rozmiarPola * 2, rozmiarPola * 2 );
    }

    @Override
    public void update( ) {
        // Ruch
        if (targeting != null) {
            moveToTarget();
        } else {
            randomMove();
        }

        // Sprawdź obiekty w zasięgu
        ObiektMapy obj = checkArea(MapaPanel.listaObiektow);

        // Jeśli napotkamy na mrówkę
        if(targeting == null && ( obj instanceof Mrowka || obj instanceof Mrowisko ) ) {
            targeting = obj;
        }

        if(targeting instanceof Mrowka || targeting instanceof Mrowisko) {
            fights.add(targeting);
            attackTarget();
        }
        die();
        regeneration();

    }
}
