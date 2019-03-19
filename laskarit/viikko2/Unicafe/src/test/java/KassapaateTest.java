/*
 * @author olli m
 */

import com.mycompany.unicafe.Kassapaate;
import com.mycompany.unicafe.Maksukortti;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class KassapaateTest {

    private Kassapaate k;

    @Before
    public void setUp() {
        k = new Kassapaate();
    }

    @Test
    public void kassapaateAlustettuOikein() {
        assertEquals(100000, k.kassassaRahaa());
        assertEquals(0, k.maukkaitaLounaitaMyyty());
        assertEquals(0, k.edullisiaLounaitaMyyty());
    }

    @Test
    public void edullinenKateisostoRiittavallaMaksulla() {
        assertEquals(0, k.syoEdullisesti(240));
        assertEquals(1, k.edullisiaLounaitaMyyty());
        assertEquals(100240, k.kassassaRahaa());
    }

    @Test
    public void edullinenKateisostoVajaallaMaksulla() {
        assertEquals(100, k.syoEdullisesti(100));
        assertEquals(0, k.edullisiaLounaitaMyyty());
        assertEquals(100000, k.kassassaRahaa());
    }

    @Test
    public void maukasKateisostoRiittavallaMaksulla() {
        assertEquals(0, k.syoMaukkaasti(400));
        assertEquals(1, k.maukkaitaLounaitaMyyty());
        assertEquals(100400, k.kassassaRahaa());
    }

    @Test
    public void maukasKateisostoVajaallaMaksulla() {
        assertEquals(100, k.syoMaukkaasti(100));
        assertEquals(0, k.maukkaitaLounaitaMyyty());
        assertEquals(100000, k.kassassaRahaa());
    }

    @Test
    public void edullinenOstoMaksukortillaRiittavaSaldo() {
        Maksukortti m = new Maksukortti(241);
        assertTrue(k.syoEdullisesti(m));
        assertEquals(1, k.edullisiaLounaitaMyyty());
    }

    @Test
    public void edullinenOstoMaksukorttiVajaaSaldo() {
        Maksukortti m = new Maksukortti(100);
        assertFalse(k.syoEdullisesti(m));
        assertEquals(0, k.edullisiaLounaitaMyyty());
    }

    @Test
    public void maukasOstoMaksukortillaRiittavaSaldo() {
        Maksukortti m = new Maksukortti(401);
        assertTrue(k.syoMaukkaasti(m));
        assertEquals(1, k.maukkaitaLounaitaMyyty());
    }

    @Test
    public void maukasOstoMaksukortillaVajaaSaldo() {
        Maksukortti m = new Maksukortti(100);
        assertFalse(k.syoMaukkaasti(m));
        assertEquals(0, k.maukkaitaLounaitaMyyty());
    }

    @Test
    public void rahanLatausKortille() {
        Maksukortti m = new Maksukortti(100);
        k.lataaRahaaKortille(m, 1100);
        assertEquals(1200, m.saldo());
        assertEquals(101100, k.kassassaRahaa());
    }

    @Test
    public void rahanLatausKortilleKunMaaraNollaTaiPienempi() {
        Maksukortti m = new Maksukortti(100);
        k.lataaRahaaKortille(m, -1);
        assertEquals(100, m.saldo());
        assertEquals(100000, k.kassassaRahaa());
    }
}
