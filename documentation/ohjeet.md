# Käyttöohje

## Ohjelman suorittaminen
**Sijoita sovelluksen jar-tiedosto Dungeon-hakemiston juureen ennen ajamista.** Sovellus olettaa, että tiedosto tileset.png on suoritushakemistossa polun resources/tileset.png päässä.

Sovellus käynnistetään komennolla

```
java -jar Dungeon.jar
```
, missä Dungeon.jar -tiedostonimeä voidaan joutua täydentämään sovelluksen versionumerolla.

## Peliohjeet 

Pelin alussa on mahdollista valita vaikeusaste ja karttakoko. Vaikeusaste vaikuttaa hirviöiden yleiseen esiintymistiheyteen kartalla ja eri hirviötyyppien esiintymisen todennäköisyyksiin. Karttakokoa valittaessa on hyvä huomioida, että suurella kartalla on kaikkiaan enemmän hirviöitä, joita voi kertyä pakomatkan varrella.

Pelissä liikutaan nuolinäppäimillä tai H-, J-, K- ja L-näppäimillä. H-näppäin siirtää pelihahmoa länteen, J-näppäin etelään, K-näppäin pohjoiseen ja L-näppäin itään. Pelihahmoa voi myös liikkua luoteeseen Y-näppäimellä, koilliseen U-näppäimellä, lounaaseen N-näppäimellä ja kaakkoon M-näppäimellä. Myös hirviöt voivat liikkua väli-ilmansuuntiin, joten on suositeltavaa opetella käyttämään HJKL-näppäimiä. .-näppäin pitää pelaajaa paikallaan. Tämä on hyödyllistä turvapaikoissa pysyttäessä.

Shift-näppäimen painaminen mahdollistaa kahden ilmansuunnan syöttämisen kerralla nuolinäppäimillä. Näin myös nuolinäppäimillä voi liikkua väli-ilmansuuntiin.


![alku](start.png)
![näkökenttä](lineofsight.png)

Pelialueesta näkyy vain pelihahmon näkökenttään kuuluvat alueet. Aiemmin nähdyt alueet sisältöineen näkyvät varjostettuina. Pelaajan terveyspisteet näkyvät ruudun vasemmassa yläkulmassa.

![vihollinen havaittu](enemyseen.png)

Hirviöt osaavat hakeutua pelaajan lähelle minkä tahansa esteiden ympäri niin kauan kuin reitti pelaajan lähelle on olemassa. Hirviöt ovat kuitenkin tietoisia vain pelaajan viimeisimmästä sijainnista eivätkä näe pelaajaa aivan tämän näkökentän rajalla, joten etene varovaisesti. Hirviöt osaavat nostaa hälytyksen nähdessään pelaajan, jolloin muut hirviöt hälytyksen säteellä alkavat hakeutumaan pelaajan viimeisintä havaittua sijaintia kohti.

![taistelu](combat.png)

Hyökkääminen tapahtuu painamalla suuntanäppäintä kohteen suuntaan silloin, kun pelaajahahmo on sen vierellä.

Toistaiseksi hirviöitä ei ole ohjelmoitu liikkumaan muulloin kuin niiden hyökätessä tai paetessa, joten paikat, joissa yksikään näkyvä ruutu ei ole sellainen, jossa hirviö on viimeksi nähnyt pelaajan ovat aina turvallisia.

Peli laskee pisteitä pelaajan tappaessa hirviöitä. Jos pelaajan pisteet riittävät, niin peli kysyy pelaajalta tämän nimeä.

![pisteytys](score.png)

![nimi](nameinput.png)

![pistetilastot](highscores.png)

Hirviöitä on toistaiseksi kolmenlaisia:

### Örkki (orc), 100 pistettä
![örkki](orc.png)

Heikoin hirviö. Pakenee parin osuman jälkeen eikä kovin tehokkaasti.

### Ihmishyeena (gnoll), 150 pistettä
![ihmishyeena](gnoll.png)

Jonkin verran vahvempi kuin örkki. Taistelee aggressiivisesti.

### Lohikäärme (dragon) 200 pistettä
![lohikäärme](dragon.png)

Vahvin hirviö. Ei nosta hälytystä eikä reagoi muiden hirviöiden hälytyksiin. Osaa paeta suhteellisen älykkäästi.
