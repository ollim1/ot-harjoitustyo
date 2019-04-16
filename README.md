# Ohjelmistotekniikka kevät 2019, harjoitustyö
## Projekti

## Dungeon
Luolapeli, joka on toteutettu JavaFX:ällä.

### Dokumentaatio
[Vaatimusmäärittely](/documentation/vaatimusmaarittely.md)

[Tyoaikakirjanpito](/documentation/tyoaikakirjanpito.md)

[Arkkitehtuuri](/documentation/arkkitehtuuri.md)

[Ohjeet](/documentation/ohjeet.md)

### Releaset
[Viikko 5](https://github.com/ollim1/ot-harjoitustyo/releases/tag/viikko5)

### Komentorivitoiminnot
#### Testaus
Testit suoritetaan komennolla

```
mvn test
```

Testikattavuusraportti luodaan komennolla

```
mvn jacoco:report
```

Kattavuusraportti tallentuu tiedostoon _Dungeon/target/site/jacoco/index.html_

#### Jarin generointi

```
mvn package
```
Komento luo jar-tiedoston hakemistoon target nimellä Dungeon-1.0-SNAPSHOT.jar

#### Checkstyle

Tiedostossa [checkstyle.xml](/Dungeon/checkstyle.xml) määritellyt tarkistukset ajetaan komennolla

```
mvn jxr:jxr checkstyle:checkstyle
```
Tulokset tallentuvat tiedostoon _Dungeon/target/site/checkstyle.html_
