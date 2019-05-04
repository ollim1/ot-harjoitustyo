# Testausdokumentti
Ohjelman testit ovat muuttuneet kehityksen aikana. Testeistä yksikkötestejä sisältävät lähinnä pohjaluokat, joista muut riippuvat. Loput testit ovat integraatiotestejä, sillä projektin aikataulun puitteissa ei ole ollut mahdollista kirjoittaa korvaavia valeluokkia kaikille riippuvuuksille. Sovelluksen käyttökokemusta on paranneltu manuaalisella testauksella.
Käyttöliittymäluokat on jätetty testauksen ulkopuolelle.

## Yksikkö- ja integraatiotestaus
### Logiikka


### DAO
Valtaosa DAO-luokkien testeistä kirjoitettiin silloin, kun DAO-osuus oli erillisessä testiprojektissa. DAO-luokat käyttävät testitietokantaa Dungeon/savedGames/TestDatabase.

### Apuluokat (domain)
Domain-luokat riippuvat osin logiikkaluokista, joten perustoiminnallisuuden valmistuttua alustavien yksikkötestien avulla testit on muutettu integraatiotesteiksi aitojen luokkien kanssa.

### Testauskattavuus
Sovelluksessa testattujen pakkausten testien rivikattavuus on 90% ja haarautumakattavuus 78%. Kaikelle ei ehditty kirjoittaa testejä.

[jacoco](jacoco.png)

## Järjestelmätestaus

### Toiminnallisuudet
Määrittelydokumentin toiminnallisuudet on käyty läpi käyttämällä sovellusta. Rajatapaukset on pyritty testaamaan.


