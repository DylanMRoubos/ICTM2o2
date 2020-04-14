# Loadbalancing webservers met de PfSense

Ga naar PfSense toe en login in. Vervolgens naar services / loadbalancer / Pools / edit. Daarna klik je op maak een nieuwe pool.

- Name: Webserver-http
- Mode: Load balance
- Port: 80
- Monitor: ICMP
- Server Ip Address: 172.16.0.191
- Current Pool Members: 

      172.16.0.190
    
      172.16.0.191
    
 Klik vervolgens op opslaan en ga daarna na Virtual Servers
 
 - Name: Webserver-http-vip
 - IP Address: 172.16.0.191
 - Port: 80
 - Virtual Server Pool: Webserver-http
 - Fall Back Pool: None
 
 Klik daarna op submit en Apple Changes

User toevoegen aan de Databaseservers

            CREATE USER 'haproxy_check'@'%';
            flush privileges;
            
Daarna de mysql-client installeren

            sudo apt install mysql-client haproxy
            
