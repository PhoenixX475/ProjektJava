package logic.mrowki;

import graphics.MapaPanel;
import logic.obiekty.Lisc;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class RobotnicaTest {
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
}
