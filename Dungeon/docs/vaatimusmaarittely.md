# Vaatimusmäärittely
## Sovelluksen tarkoitus
Sovellus toteuttaa yksinkertaisen luolapelin. Luolapeli pitää myös kirjaa pelaajien menestyksestä.
## Käyttäjät
Sovelluksella on yksi käyttäjärooli, _normaali pelaaja_. Sovellukseen voidaan lisätä tulevien laajennusten testaamista varten _debug-käyttäjä_
## Käyttöliittymäluonnos

## Perusversion toiminnallisuus
- Sovellus näyttää karttanäkymän, jossa pelaaja voi liikkua nuolinäppäimillä.
    - Kartta näytetään graafisena ruudukkona JavaFX-rajapinnan avulla.
- Kartalla liikkuu myös tietokoneen ohjaamia vihollisia.
    - Vihollisia voi olla monenlaisia.
    - Viholliset on sijoitettu satunnaisiin paikkoihin.
    - Vihollisia voi syntyä lisää, mikä mahdollistaisi pelin vaikeuttamisen.
- Huomattuaan pelaajan (kun pelaaja saapuu vihollisen näkökenttään) vihollinen lähestyy pelaajaa ja alkaa hyökkäämään.
    - Pelaaja voi hyökätä liikkumalla vihollista kohti ollessaan tämän vieressä.


## Jatkokehitysideoita
- Melumekaniikka ja reitinhaku
    - Vihollinen voi kutsua muita luokseen, jolloin tietyllä etäisyydellä olevat viholliset navigoivat lyhintä reittiä huoneeseen, jossa pelaaja sijaitsee
    - A\*-reitinhaku toteutettu aiemmassa projektissa, helppo kopioida ja siistiä ohjelman tarpeisiin
- Erilaisia aseita ja esineitä, jotka vaikuttavat pelaajan tai vihollisten toimintakykyihin.
