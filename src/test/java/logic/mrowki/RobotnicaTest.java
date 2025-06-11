package logic.mrowki;

import graphics.MapaPanel;
import logic.obiekty.Lisc;
import logic.rozne.ObiektMapy;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class RobotnicaTest {
    @Test
    void testgrab() {
        MapaPanel mapa = new MapaPanel(1,1,1,1,1,true);
        Mrowisko mrowisko = new Mrowisko(1,1,mapa);
        Robotnica r = new Robotnica(10,10,mrowisko,mapa, Color.BLACK);
        Lisc l = new Lisc(10,10);
        r.targeting = l;
        r.grab();
        assertNotNull(r.holding);
        assertTrue(r.holding instanceof Lisc);
    }

    @Test
    void testChecking() {
        MapaPanel mapa = new MapaPanel(1,1,1,1,1,true);
        Mrowisko mrowisko = new Mrowisko(1,1,mapa);
        Robotnica r = new Robotnica(10,10,mrowisko,mapa, Color.BLACK);
        Mrowisko mrowisko1 = new Mrowisko(40,40,mapa);
        Robotnica r1 = new Robotnica(12,12,mrowisko1,mapa, Color.BLACK);
        LinkedList<ObiektMapy> listaobiektow = new LinkedList<>();
        listaobiektow.add(mrowisko);
        listaobiektow.add(mrowisko1);
        listaobiektow.add(r);
        listaobiektow.add(r1);

        ObiektMapy obj = r.checkArea(listaobiektow);
        assertEquals(obj,r1);

    }
}
