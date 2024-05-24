# Post Service

Tento projekt implementuje REST API pre správu príspevkov s funkciami ako pridanie príspevku, získanie príspevku podľa ID, získanie príspevkov podľa ID používateľa, aktualizácia príspevku a odstránenie príspevku. Projekt tiež obsahuje jednoduché webové rozhranie na testovanie týchto funkcií.

## Potrebné mať nainštalované a setupnuté

- JAVA 17 (odporúčam correto 17 - Amazon Correto 17.0.11 pre dokonalú kompatibilitu)
- docker
- maven
- git

v systémových premenných prostredia PATH:
- docker, maven, java, git
- JAVA_HOME ako samostatnú premennú


## Inštalácia

### 1. Klonovanie repozitára

Otvorte cmd a napíšte

>>> git clone https://github.com/pavolsvancarek/post-service
>>> cd post-service

### 2. Skompilovanie a zabalenie projektu

Použite Maven na vyčistenie, skompilovanie a zabalenie projektu:

>>> mvn clean package

### 3. Spustenie aplikácie

Máte dve možnosti:

a) Spustenie s Dockerom

>>> docker build -t post-service .
>>> docker-compose up

potom pri reštarte stačí už len
>>> docker-compose up --build
   
b) Spustenie bez Dockeru priamo cez maven
>>> mvn spring-boot:run

## Ako som postupoval

### 1. Setupol som si springboot projekt cez https://start.spring.io
### 2. Stiahol som si intelij ideu, docker, maven, javu a setupol premenné prostredia a veci podobné
### 3. Spravil som si základný layout toho ako projekt bude vyzerať, rozdelil classy do samostatných priečinkov atď
### 4. Vyriešil som konfliktné dependencie
### 5. Rozbehol som appku a začal som riešiť error handling a success handling
### 6. Ručne som si potestoval veci a upravil chyby
### 7. Napísal som základné postman testy
### 8. Pridal som button "Random" do UI na generovanie náhodných hodnôt ktorý slúži ale len na testovacie účely, taktiež aj endpoint na premazanie všetkých dát a endpoint na získanie všetkých dát.
### 9. Spravil som solidné UI rozhranie a pridal jednoduchý custom favicon 
### 10. Presunul projekt do docker kontajnera, cez ktorý som appku spúšťal aj ďalej
### 11. Napísal som základné jUnit testy 
### 12. Pokročilejší error handling
### 13. Spravil som readme súbor, skontroloval importy, popupratoval kód, ručne všetko popúšťal, pushol na git a poslal emailom späť

## Použitie

Máme 4 rôzne input údaje:

### id: integer 
- Zadajte číslo medzi 1 až 10 (1,2,3,....9,10)

### userId: integer
- Zadajte číslo medzi 1 až 100 (1,2,3,....99,100)

### title: string
- textový reťazec

### body: string
- textový reťazec


### 1. Po spustení aplikácie, bude dostupná na http://localhost:8080.
### 2. Pridajte príspevok. Zvolte User Id a nejaký text
### 3. Overte pridanie príspevku cez get post by user id
### 4. Pridajte ešte jeden príspevok ku tomu istému User ID
### 5. Skúste Get Posts by User ID na získanie všetkých príspevkov ku tomu User id
### 6. Jeden príspevok vymažte podľa ID
### 7. Updatnite text v príspevku cez update
### 8. Overte zmeny cez GET možnosť
### 9. Vymažte po jednom všetky príspevky a vyskúsajte GET či sú všetky vymazané

BONUSY:
### Využite tlačidlá Random
### Pri GET POST BY ID zadajte id ktoré ste nevytvorili a príde vám náhodný príspevok podľa zadania z https://jsonplaceholder.typicode.com/

## Ďalšie info
- na zbehnutie postman testov potrebujete mať nainštalovaný postman, importnutu kolekciu testov a aj custom enviroment na testovanie
- čas strávený na celej aplikácií a na všetkom okolo toho: 3 man-day

## Swagger
http://localhost:8080/swagger-ui/index.html#/

## Zadanie

Vytvor microservice v Jave, ktorý bude sprostredkovať RESTful API na manažovanie príspevkov používateľov. Formát príspevku je nasledovný:

- id: integer

- userId: integer

- title: string

- body: string


Funkčné požiadavky:

- Pridanie príspevku - potrebné validovať userID pomocou externej API

- Zobrazenie príspevku

    - na základe id alebo userId

    - ak sa príspevok nenájde v systéme, je potrebné ho dohľadať pomocou externej API a uložiť (platné iba pre vyhľadávanie pomocou id príspevku)

- Odstránenie príspevku

- Upravenie príspevku - možnosť meniť title a body



Externú API nájdeš na linku https://jsonplaceholder.typicode.com/ - používaj endpointy users a posts.



Všeobecné požiadavky:

- ReadMe s popisom inštalácie a prvého spustenia

- Dokumentácia API (napr. Swagger)

- Validácia vstupných dát

- Použitie ORM



Riešenie by malo demonštrovať schopnosti pracovať s (čím viac tým lepšie):

- ORM

- REST

- Práca s API tretích strán

- Validácia vstupov

- Error handling

- Rozumným štrukturovaním zdrojových kódov aplikácie



Použitie frameworku Spring Boot vítaná.



Voliteľné úlohy:

- neposkytovať iba API, ale poskytovať aj jednoduchý frontend podporujúci tieto funkcie

- kontajnerizácia (napr. cez Docker)


Pri kódení sa zameraj hlavne na čistotu kódu a využívanie správnych vzorov, štýlov, funkcií a princípov jazyka.

Zadanie zaves na svoj Git a pošli mi link, aby sme si to vedeli pozrieť. Zároveň si prosím poznač, koľko hodín si strávil na zadaní. Ak budeš mať s čímkoľvek problém, príp. ak ti nie je jasné zadanie, kľudne mi napíš konkrétne otázky a posuniem ich kolegovi, ktorý ti rád čokoľvek vysvetlí :)
