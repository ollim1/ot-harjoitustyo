package com.mycompany.unicafe;

import java.util.Random;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti != null);
    }

    @Test
    public void kortinSaldoAlussaOikein() {
        assertEquals(10, kortti.saldo());
    }

    @Test
    public void lataaminenToimiiOikein() {
        kortti.lataaRahaa(10);
        assertEquals(20, kortti.saldo());
    }

    @Test
    public void saldoVaheneeRahaaOtettaessa() {
        kortti.otaRahaa(5);
        assertEquals(5, kortti.saldo());
    }

    @Test
    public void saldoEiMuutuJosRahaaEiTarpeeksi() {
        kortti.otaRahaa(11);
        assertEquals(10, kortti.saldo());
    }

    @Test
    public void otaRahaaPalauttaaOikeanTotuusarvonJosRahatRiittavat() {
        assertEquals(true, kortti.otaRahaa(5));
    }

    @Test
    public void otaRahaaPalauttaaOikeanTotuusarvonJosRahatEivatRiita() {
        assertEquals(false, kortti.otaRahaa(11));
    }
    
    @Test
    public void toStringToimii() {
        Random rand = new Random();
        int r = rand.nextInt(10000) + 1;
        assertEquals("saldo: " + (r / 100) + "." + (r % 100),
                (new Maksukortti(r).toString()));
    }
}
