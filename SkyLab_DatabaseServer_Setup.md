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
    
    - Verander de hostname naar 'master1' met het volgende commando: sudo hostnamectl set-hostname master1 en voer het wachtwoord voor sudo in.
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
  
  passwd [Gekozen wachtwoord] - voeg dit toe aan de github page!
  voer het huidige wachtwoord in
  
  ## Updaten
        sudo apt update
        sudo apt full-upgrade -y
  
  ## Open ssh ionstallenen
          sudo apt install openssh-server
          
  Er kan nu SSH worden gebruikt om een verbinding te maken met de webserver doormiddel van OpenVPN
  
  # 2 Installeren van MariaDB Server
  
  Dit zijn de commando's om de software repo van MariaDB toe te voegen:

      sudo apt-get install software-properties-common -y
      sudo apt-key adv --fetch-keys 'https://mariadb.org/mariadb_release_signing_key.asc'
      sudo add-apt-repository 'deb [arch=amd64,arm64,ppc64el] http://mirror.one.com/mariadb/repo/10.4/ubuntu bionic main'

  Nadat de Repository is toegevoegd, kan MariaDB Server geïnstalleerd worden:

      sudo apt update
      sudo apt install mariadb-server -y

  Om te controleren of mariadb nu geïnstalleerd is en ook is opgestart kan je `systemctl status mariadb.service` doen. (`q` voor exit)

