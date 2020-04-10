In deze module worden 4 zaken gedaan:

  1. Aanvragen Ubuntu servers
  2. Instellen Ubuntu server op netwerk
  3. Installeren MariaDB met laatste stabiele versie: 10.4
  4. Instellen Master-Master structuur tussen de Database servers
  
# 1 Aanvragen Ubuntu servers:

  - Navigeer naar skylab.windesheim.nl & log in
  - Onder het tabje 'Catalog' request 2 keer de 'Ubuntu Server 18.04'
  - Wacht tot de aanvrag is voltooid
  
# 2 Instellen Ubuntu server op netwerk:

  Openen ubuntu server:

    - Navigeer naar skylab.windesheim.nl & log in
    - Onder het tabje 'Deployments' klik op de aangevragde ubuntu server
    - Selecteer aan de linker kant het groene icoon.
    - Selecteer aan de linker kant het instellingen icoon.
    - Selecteer de 'Connect to Remote Console'.
    - Vul de volgende inlog gegevens in: username: student, password: Welkom01!
  
  Instellen hostname:
    
    - voordat de hostname veranderd kan worden moet er eerst een configuratie bestand worden aangepast van cloud-init, anders wordt de hostname overschreven bij een herstart van het systeem. dat kan door /etc/cloud/cloud.cfg aan te passen met: sudo nano /etc/cloud/cloud.cfg. Hier moet preserve_hostname op true worden gezet i.p.v. false.
    - Verander de hostname naar 'master1' of 'master2' met het volgende commando: sudo hostnamectl set-hostname master1 en voer het wachtwoord voor sudo in.
    - Verander de hostfile met het volgende commando: sudo nano /etc/hosts. Verander hier de hostname als de oude naam hier nog staat.
    - Herstart de server met het commando: sudo reboot
    
    Instellen statisch ip
    
    ## Statisch ip toewijzen
    Om een statisch ip toe te wijzen in Ubuntu 18.04 moet je de configuratie van netplan bijwerken.  
    Gebruik het ip en gateway uit het TO
    Het DNS-Server adres haal je op met `systemd-resolve --status`  

    Bewerken het netplan configuratie  
    `sudo nano /etc/netplan/50-cloud-init.yaml`

    Nu zal je een venster krijgen als volgt:

        network:
            ethernets:
                ens33:
                    dhcp4: yes
            version: 2

    Nu moet er het volgende veranderd worden:

        network:
            ethernets:
                ens33:
                    dhcp4: no
                    addresses: [x.x.x.x/24]
                    gateway4: x.x.x.x
                    nameservers:
                        addresses: [x.x.x.x]
            version: 2

    Wanneer je klaar ben met bewerken druk je op CTRL+X, Y, Enter.  
    Nu moet de configuratie nog toegepast worden. Dat gaat als volgt:  
    `sudo netplan apply`

  Wachtwoord veranderen
  
  voer het commando: passwd. voer het huidige wachtwoord in. Bevestig met twee keer het nieuwe gekozen wachtwoord in te vullen - voeg dit toe aan het wachtwoorden bestand!
  
  ## Updaten
        sudo apt update
        sudo apt full-upgrade -y
  
  ## Open ssh installeren
          sudo apt install openssh-server
          
  Herstart de server met het commando: 
          sudo reboot
  
  Er kan nu SSH worden gebruikt om een verbinding te maken met de webserver doormiddel van OpenVPN
  
  # 3 Installeren van MariaDB Server
  
  Dit zijn de commando's om de software repo van MariaDB toe te voegen:

      sudo apt-get install software-properties-common -y
      sudo apt-key adv --fetch-keys 'https://mariadb.org/mariadb_release_signing_key.asc'
      sudo add-apt-repository 'deb [arch=amd64,arm64,ppc64el] http://mirror.one.com/mariadb/repo/10.4/ubuntu bionic main'

  Nadat de Repository is toegevoegd, kan MariaDB Server geïnstalleerd worden:

      sudo apt update
      sudo apt install mariadb-server -y

  Om te controleren of mariadb nu geïnstalleerd is en ook is opgestart kan je `systemctl status mariadb.service` doen. (`q` voor exit)
  
  ## Shell toegang
Toegang to de sql shell van mariadb kan op 2 manieren:
``` bash
mysql -u root -p
```
of
``` bash
sudo mysql -u root
```
Je komt de shell uit door ``` quit; ``` te gebruiken.

***

## Externe toegang
MariaDB staat standaard geconfigureerd op alleen luisteren op localhost. Je kan dan geen verbinding leggen vanaf een andere computer.
Om MariaDB te laten luisteren moet er een setting veranderd worden in ``` /etc/mysql/my.cnf ```

Bewerk de configuratie file van mariadb:
``` bash
sudo nano /etc/mysql/my.cnf
```
Vind de line die aangeeft: ``` bind-address            = 127.0.0.1 ```  
Comment die line door er ``` # ``` voor te zetten: ``` #bind-address            = 127.0.0.1 ```  
Save met CTRL+X, Y, Enter  
Herstart mariadb om de nieuwe configuratie te laden
``` bash
sudo systemctl restart mariadb
```

## Gebruiker voor externe toegang
De server luistert nu overal, maar er is nog geen root gebruiker die ook vanaf buiten mag inloggen. Die gaan we nu aanmaken.

``` bash
sudo mysql -u root
```
Nu in de SQL-shell:
``` sql
create user 'root'@'%' identified by 'insert_password_here';
grant all on *.* to 'root'@'%';
flush privileges;
quit;
```
Nu kan je inloggen op de mariadb servers vanaf de mysql client op je computer.

# 4 Instellen Master-Master structuur tussen de Database servers
 
Voor het configureren van de Master:
``` bash
sudo nano /etc/mysql/my.cnf
```
Hierin zoek je het ```server-id``` op.
Vanuit hier verander je de configuratie naar het volgende:

``` bash
server-id = 1
report_host = master1
 
log_bin = /var/log/mysql/mariadb-bin
log_bin_index = /var/log/mysql/mariadb-bin.index
 
relay_log = /var/log/mysql/relay-bin
relay_log_index = /var/log/mysql/relay-bin.index
 
replicate-do-db = wideworldimporters
 
Wanneer je klaar ben met bewerken druk je op CTRL+X, Y, Enter.
Om de configuratie toe te passen voer je het volgende in:
sudo systemctl restart mariadb
 
```
Voor de configuratie van de tweede master doe je hetzelfde. 
Alleen verandert het server- id en de report_host naar:

```bash
server-id = 2
report_host = master2
```
Nu start je bij beide masters MariaDB op en login met root.
 
``` bash
sudo service mysql start
sudo mariadb -uroot -p
```
 
Maak nu op beide een gebruiker aan met de benodigde privileges.  
Deze gebruiker zal gebruikt worden voor het repliceren van de databaseservers.
 
``` bash
MariaDB [(none)]> create user 'replusr'@'%' identified by 'replusr';
MariaDB [(none)]> grant replication slave on *.* to 'replusr'@'%';
```
  
Om de status van de master te bekijken voer je het volgende in:
``` bash
MariaDB [(none)]> show master status;
```
Hieruit moet je de de ```File``` en de ```Position``` van de Masters onthouden voor de volgende stap.

Voor het starten van de replicatie voer je het volgende in op de tweede Master:
``` bash
MariaDB [(none)]> CHANGE MASTER TO MASTER_HOST='master1', MASTER_USER='replusr',
-> MASTER_PASSWORD='replusr', MASTER_LOG_FILE='file', MASTER_LOG_POS=Position;
 
MariaDB [(none)]> START SLAVE;
```
Je vult dan de ```File``` en ```Position``` in van de eerste Master.

Nu ga je de status van de tweede Master bekijken.
``` bash
MariaDB [(none)]> SHOW SLAVE STATUS\G
```
Als Read_Master_Log_Pos en Exec_Master_Log_Pos dezelfde waarde hebben, betekent dit dat de masters sychroon lopen.

Nu werkt de replicatie van de eerste Master naar de tweede Master.
Voor de replicatie van de tweede naar de eerste Master doe je hetzelfde. 
Je werkt nu alleen vanuit de eerste Master en je verandert de MASTER_HOST, MASTER_LOG_FILE en MASTER_LOG_POS naar die van de tweede Master.
