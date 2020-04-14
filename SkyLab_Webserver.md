In deze module worden 4 zaken gedaan:

  1. Aanvragen Ubuntu servers
  2. Instellen Ubuntu server op netwerk
  3. Installeren Apache
  4. Installeren PHP
  
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
  
## Instellen hostname:

voordat de hostname veranderd kan worden moet er eerst een configuratie bestand worden aangepast van cloud-init, anders        wordt de hostname overschreven bij een herstart van het systeem. 
Dit kan door /etc/cloud/cloud.cfg aan te passen met: 
`sudo nano /etc/cloud/cloud.cfg.`

Hier moet preserve_hostname op true worden gezet i.p.v. false.
Verander de hostname naar web1' of 'web2' met het volgende commando: 
`sudo hostnamectl set-hostname web1` 

voer het wachtwoord voor sudo in.
Verander de hostfile met het volgende commando: 
  `sudo nano /etc/hosts.`
  Verander hier de hostname als de oude naam hier nog staat.
Herstart de server met het commando: sudo reboot

## Instellen statisch ip
    
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

# 3 Apache installeren
Installeren van Apache met behulp van de Ubuntu package manager, `apt`:

      sudo apt update
      
      sudo apt install apache2

# 4 PHP installeren

PHP installeer je met het volgende commando

      sudo apt install php7.2 libapache2-mod-php7.2 php7.2-mysql
      
Normaal kijkt Apache eerst naar een file genaamd index.html. Wij willen dat Apache eerst kijkt naar php files. Dus we moeten ervoor zorgen dat Apache kijkt naar index.php. 

Typ het volgende commando

      cd /var/www\html
      ls

Verwijder vervolgens index.html

      sudo remove index.html

Voeg daarna index.php toe

      sudo nano index.php
      
Typ vervolgens in index.php het volgende

      <? php
      phpinfo();
      ?>
      
Om ervoor te zorgen dat je geen sudo meer hoeft te gebruiken doe je het volgende

    sudo usermod -a -G www-data student

Vervolgens moet je eerst naar

    cd/var/www
    
Dan de volgende commandos

    sudo chown www-data:www-data -R html
    sudo chmod 775 html 
    cd html



