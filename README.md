# Ohjelmistotekniikka kevät 2019, harjoitustyö
## Projekti

## Dungeon
Luolapeli, joka on toteutettu JavaFX:ällä.

### Dokumentaatio
[Käyttöohje](/documentation/ohjeet.md)

[Vaatimusmäärittely](/documentation/vaatimusmaarittely.md)

[Arkkitehtuuri](/documentation/arkkitehtuuri.md)

[Testausdokumentti](/documentation/testausdokumentti.md)

[Tyoaikakirjanpito](/documentation/tyoaikakirjanpito.md)

### Releaset
[Viikko 5](https://github.com/ollim1/ot-harjoitustyo/releases/tag/viikko5)

[Viikko 6](https://github.com/ollim1/ot-harjoitustyo/releases/tag/viikko6)

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

Kattavuusraportti tallentuu tiedostoon _target/site/jacoco/index.html_

#### JavaDoc
JavaDoc generoidaan komennolla

```
mvn javadoc:javadoc
```
JavaDocia voi tarkastella avaamalla selaimella tiedosto _target/site/apidocs/index.html_

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
Tulokset tallentuvat tiedostoon _target/site/checkstyle.html_
