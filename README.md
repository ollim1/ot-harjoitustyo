# Ohjelmistotekniikka kevät 2019, harjoitustyö
## Projekti

## Dungeon
Luolapeli, joka on toteutettu JavaFX:ällä.

### Dokumentaatio
[Vaatimusmäärittely](/documentation/vaatimusmaarittely.md)

[Tyoaikakirjanpito](/documentation/tyoaikakirjanpito.md)

[Arkkitehtuuri](/documentation/arkkitehtuuri.md)

[Ohjeet](/documentation/ohjeet.md)

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

Kattavuusraportti tallentuu tiedostoon __Dungeon/target/site/jacoco/index.html__

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
Tulokset tallentuvat tiedostoon __Dungeon/target/site/checkstyle.html__
