# Vaatimusmäärittely
## Sovelluksen tarkoitus
Sovellus toteuttaa yksinkertaisen luolapelin. Luolapeli pitää myös kirjaa pelaajien menestyksestä.
## Käyttäjät
Sovelluksella on yksi käyttäjärooli, _normaali pelaaja_. Sovellukseen voidaan lisätä tulevien laajennusten testaamista varten _debug-käyttäjä_
## Käyttöliittymäluonnos
![kuva](kayttoliittymaluonnos.jpg)

## Perusversion toiminnallisuus
- Sovellus näyttää karttanäkymän, jossa pelaaja voi liikkua nuolinäppäimillä.
    - Kartta näytetään graafisena ruudukkona JavaFX-rajapinnan avulla.
- Kartalla liikkuu myös tietokoneen ohjaamia vihollisia.
    - Vihollisia voi olla monenlaisia.
    - Viholliset on sijoitettu satunnaisiin paikkoihin.
    - Vihollisia voi syntyä lisää, mikä mahdollistaisi pelin vaikeuttamisen.
- Huomattuaan pelaajan (kun pelaaja saapuu vihollisen näkökenttään) vihollinen lähestyy pelaajaa ja alkaa hyökkäämään.
    - Pelaaja voi hyökätä liikkumalla vihollista kohti ollessaan tämän vieressä.
- Aluksi pelialueesta näkyy vain suoraan pelaajan näkökentässä oleva alue ("fog of war").
    - Nähty alue jää näkyville karttaan pysyvästi.
    - Nähty alue päivittyy vain pelaajan näkökentässä.
    - Myös vihollisen tieto pelaajasta perustuu pelaajan näkökenttään.
- Pelialue luodaan proseduraalisesti ja koostuu huoneista ja näitä yhdistävistä käytävistä.

## Jatkokehitysideoita
- Melumekaniikka ja reitinhaku
    - Vihollinen voi kutsua muita luokseen, jolloin tietyllä etäisyydellä olevat viholliset navigoivat lyhintä reittiä huoneeseen, jossa pelaaja sijaitsee.
        - Riittävän nopea voitto tai jonkinlainen yllätyshyökkäys voi taata, ettei vihollinen voi kutsua apuvoimia.
    - A\*-reitinhaku toteutettu aiemmassa projektissa, helppo kopioida ja siistiä ohjelman tarpeisiin
- Erilaisia aseita ja esineitä, jotka vaikuttavat pelaajan tai vihollisten toimintakykyihin.
- Pelitilanteen tallentaminen?
