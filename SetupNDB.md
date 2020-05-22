# Opzetten NDB cluster

Dit bestand is grotendeels vertaald met google translate. De gebruike ip adressen en commands zijn aangepast voor onze situatie. Onderaan de tutorial staat de referantie naar het origenele artikel.

Het opzetten van een NDB cluster met 2 management nodes die apache, php en mysqld draaien. En 2 data nodes die de data opslaan. Het resultaat:
![infrastructure schematic](https://github.com/DylanMRoubos/ICTM2o2/blob/develop/Infrastructuur.png)

## Prerequisites

- 2 geconfigureerde webservers (apache2 & php) - (172.16.0.190, 172.16.0.191)
- 2 schone ubuntu 18.04 instanties - (172.16.0.158, 172.16.0.159)


## Stap 1 - De Cluster Manager installeren en configureren

Om de Cluster Manager te installeren, moeten we eerst het juiste .deb-installatiebestand ophalen van de officiële MySQL [downloadpagina](https://dev.mysql.com/downloads/cluster/).

Kies op deze pagina onder Selecteer besturingssysteem de optie Ubuntu Linux. Kies vervolgens onder Selecteer OS-versie Ubuntu Linux 18.04 (x86, 64-bit).

Blader naar beneden tot je DEB-pakket, NDB Management Server ziet en klik op de downloadlink voor degene die geen dbgsym bevat (tenzij je debug-symbolen nodig hebt). U wordt naar een pagina Beginnen met downloaden gebracht. Klik hier met de rechtermuisknop op Nee, bedankt, start gewoon mijn download. en kopieer de link naar het .deb-bestand.

Login met SSH bij de webservers en download dit .deb bestand:

```wget "url" ```

Installeer de ndb_mgmd

```sudo dpkg -i mysql-cluster-community-management-server_7.6.6-1ubuntu18.04_amd64.deb```

We moeten nu ndb_mgmd configureren voordat we het voor het eerst uitvoeren; juiste configuratie zorgt voor een correcte synchronisatie en verdeling van de belasting over de datanode.

De Cluster Manager moet het eerste onderdeel zijn dat in een MySQL-cluster wordt gestart. Het vereist een configuratiebestand, dat als argument is doorgegeven aan het uitvoerbare bestand. We maken en gebruiken het volgende configuratiebestand: /var/lib/mysql-cluster/config.ini.

Maak op de Cluster Manager de map / var / lib / mysql-cluster waar dit bestand zich zal bevinden:
```sudo mkdir /var/lib/mysql-cluster```

Maak en bewerk vervolgens het configuratiebestand met uw favoriete teksteditor:
```sudo nano /var/lib/mysql-cluster/config.ini```

Plak de volgende tekst in je editor:
```
NoOfReplicas=2  # Number of replicas
[NDB_MGMD DEFAULT]
datadir=/var/lib/mysql-cluster
[ndb_mgmd]
NodeId=1
hostname=172.16.0.190
[ndb_mgmd]
NodeId=2
hostname=172.16.0.191
[ndbd]
NodeId=3
hostname=172.16.0.158
[ndbd]
NodeId=4
hostname=172.16.0.159
[mysqld]
NodeId=5
hostname=172.16.0.190
[mysqld]
NodeId=6
hostname
```

Nadat u deze tekst heeft geplakt, moet u de bovenstaande hostnaamwaarden vervangen door de juiste IP-adressen van de druppels die u heeft geconfigureerd. Het instellen van deze hostnaamparameter is een belangrijke beveiligingsmaatregel die voorkomt dat andere servers verbinding kunnen maken met de Cluster Manager.

Sla het bestand op en sluit je teksteditor.

Dit is een minimalistisch, minimaal configuratiebestand voor een MySQL-cluster. U moet de parameters in dit bestand aanpassen aan uw productiebehoeften. Raadpleeg de MySQL Cluster-documentatie voor een voorbeeld van een volledig geconfigureerd ndb_mgmd-configuratiebestand.

In het bovenstaande bestand kunt u extra componenten zoals datanode (ndbd) of MySQL-datanode (mysqld) toevoegen door instanties aan de betreffende sectie toe te voegen.

We kunnen nu de manager starten door het ndb_mgmd binaire bestand uit te voeren en het configuratiebestand op te geven met de vlag -
```sudo ndb_mgmd -f /var/lib/mysql-cluster/config.ini```

Je zou de volgende output moeten zien:
```
MySQL Cluster Management Server mysql-5.7.22 ndb-7.6.6
2018-07-25 21:48:39 [MgmtSrvr] INFO     -- The default config directory '/usr/mysql-cluster' does not exist. Trying to create it...
2018-07-25 21:48:39 [MgmtSrvr] INFO     -- Successfully created config directory
```

Dit geeft aan dat de MySQL Cluster Management-server met succes is geïnstalleerd en nu op uw manager draait.

Idealiter willen we de Cluster Management-server automatisch starten bij het opstarten. Om dit te doen, gaan we een systemd-service maken en inschakelen.

Voordat we de service maken, moeten we de actieve server doden:
```sudo pkill -f ndb_mgmd```

Open en bewerk nu het volgende systemd Unit-bestand met uw favoriete editor:
```sudo nano /etc/systemd/system/ndb_mgmd.service```

Paste in the following code:
```
[Unit]
Description=MySQL NDB Cluster Management Server
After=network.target auditd.service

[Service]
Type=forking
ExecStart=/usr/sbin/ndb_mgmd -f /var/lib/mysql-cluster/config.ini
ExecReload=/bin/kill -HUP $MAINPID
KillMode=process
Restart=on-failure

[Install]
WantedBy=multi-user.target
```

Hier hebben we een minimale set opties toegevoegd die systemd instrueren over het starten, stoppen en herstarten van het ndb_mgmd-proces. Raadpleeg de systeemhandleiding voor meer informatie over de opties die in deze toestelconfiguratie worden gebruikt.

Sla het bestand op en sluit het.

Laad nu de managerconfiguratie van systemd opnieuw met daemon-reload:
```sudo systemctl daemon-reload```

We schakelen de service die we zojuist hebben gemaakt in zodat de MySQL Cluster Manager opnieuw opstart:
```sudo systemctl enable ndb_mgmd```

Eindelijk starten we de service:
```sudo systemctl start ndb_mgmd```

U kunt controleren of de NDB Cluster Management-service actief is:
```sudo systemctl status ndb_mgmd```

You should see the following output:
```
 ndb_mgmd.service - MySQL NDB Cluster Management Server
   Loaded: loaded (/etc/systemd/system/ndb_mgmd.service; enabled; vendor preset: enabled)
   Active: active (running) since Thu 2018-07-26 21:23:37 UTC; 3s ago
  Process: 11184 ExecStart=/usr/sbin/ndb_mgmd -f /var/lib/mysql-cluster/config.ini (code=exited, status=0/SUCCESS)
 Main PID: 11193 (ndb_mgmd)
    Tasks: 11 (limit: 4915)
   CGroup: /system.slice/ndb_mgmd.service
           └─11193 /usr/sbin/ndb_mgmd -f /var/lib/mysql-cluster/config.ini
```

Wat aangeeft dat de ndb_mgmd MySQL Cluster Management Server nu wordt uitgevoerd als een systemd service.

## Stap 2 - Installing and Configuring the Data Nodes


In deze stap installeren we de ndbd MySQL Cluster data node daemon en configureren we de nodes zodat ze kunnen communiceren met de Cluster Manager.

Om de binaire datanodes te installeren, moeten we eerst het juiste .deb-installatiebestand ophalen van de officiële [downloadpagina](https://dev.mysql.com/downloads/cluster/).

Kies op deze pagina onder Selecteer besturingssysteem de optie Ubuntu Linux. Kies vervolgens onder Selecteer OS-versie Ubuntu Linux 18.04 (x86, 64-bit).

Blader naar beneden tot je DEB-pakket, NDB-datanodes-binaries ziet en klik op de downloadlink voor degene die geen dbgsym bevat (tenzij je debug-symbolen nodig hebt). U wordt naar een pagina Beginnen met downloaden gebracht. Klik hier met de rechtermuisknop op Nee, bedankt, start gewoon mijn download. en kopieer de link naar het .deb-bestand.

Meld u nu aan bij uw eerste datanode en download dit .deb-bestand:
```wget https://dev.mysql.com/get/Downloads/MySQL-Cluster-7.6/mysql-cluster-community-data-node_7.6.6-1ubuntu18.04_amd64.deb```

Voordat we het binaire datanodes installeren, moeten we een afhankelijkheid, libclass-methodmaker-perl installeren:
```
sudo add-apt-repository universe
sudo apt update
sudo apt install libclass-methodmaker-perl
```

We kunnen nu de data note binary installeren met dpkg:
```sudo dpkg -i mysql-cluster-community-data-node_7.6.6-1ubuntu18.04_amd64.deb```

De datanodes halen hun configuratie van de standaardlocatie van MySQL, /etc/my.cnf. Maak dit bestand met uw favoriete teksteditor en begin met bewerken:
```sudo nano /etc/my.cnf```

Voeg de volgende configuratieparameter toe aan het bestand:
```
[mysql_cluster]
# Options for NDB Cluster processes:
ndb-connectstring=172.16.0.190,172.16.0.191  # location of cluster managers
```

Het opgeven van de locatie van het Cluster Manager-datanode is de enige configuratie die nodig is om ndbd te starten. De rest van de configuratie wordt rechtstreeks uit de manager gehaald.

Sla het bestand op en sluit het af.

In ons voorbeeld zal het datanode ontdekken dat de gegevensdirectory / usr / local / mysql / data is, volgens de configuratie van de manager. Voordat we de daemon starten, maken we deze map op het datanode:
```sudo mkdir -p /usr/local/mysql/data```

Nu kunnen we de datanode starten met de volgende opdracht:
```sudo ndbd```

Je zou ongeveer de volgende output moeten zien:
```
2018-07-18 19:48:21 [ndbd] INFO     -- Angel connected to '198.51.100.2:1186'
2018-07-18 19:48:21 [ndbd] INFO     -- Angel allocated nodeid: 2
```

De NDB-data node daemon is succesvol geïnstalleerd en draait nu op uw server.

Ten slotte willen we ook dat de data node daemon automatisch opstart wanneer de server opstart. We volgen dezelfde procedure die wordt gebruikt voor Cluster Manager en maken een systemd-service.

Voordat we de service maken, beëindigen we het lopende ndbd-proces:
```sudo pkill -f ndbd```

Now, open and edit the following systemd Unit file using your favorite editor:
```sudo nano /etc/systemd/system/ndbd.service```

Paste in the following code:
```
[Unit]
Description=MySQL NDB Data Node Daemon
After=network.target auditd.service

[Service]
Type=forking
ExecStart=/usr/sbin/ndbd
ExecReload=/bin/kill -HUP $MAINPID
KillMode=process
Restart=on-failure

[Install]
WantedBy=multi-user.target
```

Hier hebben we een minimale set opties toegevoegd die systemd instrueren over het starten, stoppen en herstarten van het ndbd-proces. Raadpleeg de systeemhandleiding voor meer informatie over de opties die in deze toestelconfiguratie worden gebruikt.

Sla het bestand op en sluit het.

Laad nu de managerconfiguratie van systemd opnieuw met daemon-reload:
```sudo systemctl daemon-reload```

We schakelen nu de service in die we zojuist hebben gemaakt, zodat de datanodedeamon begint bij het opnieuw opstarten:
```sudo systemctl enable ndbd```

Eindelijk starten we de service:
```sudo systemctl start ndbd```

U kunt controleren of de NDB Cluster Management-service actief is:
```sudo systemctl status ndbd```

Je zou de volgende output moeten zien:

``` 
ndbd.service - MySQL NDB Data Node Daemon
   Loaded: loaded (/etc/systemd/system/ndbd.service; enabled; vendor preset: enabled)
   Active: active (running) since Thu 2018-07-26 20:56:29 UTC; 8s ago
  Process: 11972 ExecStart=/usr/sbin/ndbd (code=exited, status=0/SUCCESS)
 Main PID: 11984 (ndbd)
    Tasks: 46 (limit: 4915)
   CGroup: /system.slice/ndbd.service
           ├─11984 /usr/sbin/ndbd
           └─11987 /usr/sbin/ndbd
```
Wat aangeeft dat de ndbd MySQL Cluster data node daemon nu draait als een systemd service. Uw datanode moet nu volledig functioneel zijn en verbinding kunnen maken met de MySQL Cluster Manager.

Als u klaar bent met het instellen van het eerste datanodes, herhaalt u de stappen in dit gedeelte op het andere datanodes.

## Stap 3 - Configuring and Starting the MySQL Server and Client

Een standaard MySQL-server, zoals degene die beschikbaar is in Ubuntu's APT-repository, ondersteunt de MySQL Cluster-engine NDB niet. Dit betekent dat we de aangepaste SQL-server moeten installeren die is verpakt met de andere MySQL Cluster-software die we in deze zelfstudie hebben geïnstalleerd.

We pakken opnieuw het MySQL Cluster Server-binaire bestand van de officiële MySQL [downloadpagina](https://dev.mysql.com/downloads/cluster/)..

Kies op deze pagina onder Selecteer besturingssysteem de optie Ubuntu Linux. Kies vervolgens onder Selecteer OS-versie Ubuntu Linux 18.04 (x86, 64-bit).

Blader naar beneden tot je DEB-bundel ziet en klik op de downloadlink (dit zou de eerste in de lijst moeten zijn). U wordt naar een pagina Beginnen met downloaden gebracht. Klik hier met de rechtermuisknop op Nee, bedankt, start gewoon mijn download. en kopieer de link naar het .tar-archief.

Meld u nu aan bij de Cluster Manager en download dit .tar-archief (onthoud dat we MySQL Server op hetzelfde datanodes als onze Cluster Manager installeren - in een productie-instelling moet u deze uitvoeren daemons op verschillende datanodes):
```wget https://dev.mysql.com/get/Downloads/MySQL-Cluster-7.6/mysql-cluster_7.6.6-1ubuntu18.04_amd64.deb-bundle.tar```

Voer de volgende commandos uit:
```
mkdir install
tar -xvf mysql-cluster_7.6.6-1ubuntu18.04_amd64.deb-bundle.tar -C install/
cd install
sudo apt update
sudo apt install libaio1 libmecab2
```

Nu moeten we de MySQL Cluster-afhankelijkheden installeren, gebundeld in het tar-archief dat we zojuist hebben uitgepakt:
```
sudo dpkg -i mysql-common_7.6.6-1ubuntu18.04_amd64.deb
sudo dpkg -i mysql-cluster-community-client_7.6.6-1ubuntu18.04_amd64.deb
sudo dpkg -i mysql-client_7.6.6-1ubuntu18.04_amd64.deb
sudo dpkg -i mysql-cluster-community-server_7.6.6-1ubuntu18.04_amd64.deb
```

Bij het installeren van mysql-cluster-community-server zou er een configuratieprompt moeten verschijnen waarin u wordt gevraagd om een ​​wachtwoord in te stellen voor het root-account van uw MySQL-database. Kies een sterk, veilig wachtwoord en druk op <Ok>. Voer dit root-wachtwoord opnieuw in wanneer daarom wordt gevraagd en druk nogmaals op <Ok> om de installatie te voltooien.

We kunnen nu de MySQL-server binair installeren met dpkg:
```sudo dpkg -i mysql-server_7.6.6-1ubuntu18.04_amd64.deb```

We now need to configure this MySQL server installation.

The configuration for MySQL Server is stored in the default /etc/mysql/my.cnf file.

Open this configuration file using your favorite editor:
```sudo nano /etc/mysql/my.cnf```

Je zou de volgende tekst moeten zien:
```
# Copyright (c) 2015, 2016, Oracle and/or its affiliates. All rights reserved.
#
# This program is free software; you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation; version 2 of the License.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program; if not, write to the Free Software
# Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA

#
# The MySQL Cluster Community Server configuration file.
#
# For explanations see
# http://dev.mysql.com/doc/mysql/en/server-system-variables.html

# * IMPORTANT: Additional settings that can override those from this file!
#   The files must end with '.cnf', otherwise they'll be ignored.
#
!includedir /etc/mysql/conf.d/
!includedir /etc/mysql/mysql.conf.d/
```

Voeg er de volgende configuratie aan toe:
```
[mysqld]
# Options for mysqld process:
ndbcluster                      # run NDB storage engine

[mysql_cluster]
# Options for NDB Cluster processes:
ndb-connectstring=172.16.0.190,172.16.0.191
```

Sla het bestand op en sluit het af.

Start de MySQL-server opnieuw op om deze wijzigingen door te voeren:
```
sudo systemctl restart mysql
sudo systemctl enable mysql
```
Op uw Cluster Manager / MySQL Server zou nu een SQL-server moeten draaien.


### Referentie: https://www.digitalocean.com/community/tutorials/how-to-create-a-multi-node-mysql-cluster-on-ubuntu-18-04
