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
            
De naam van de originele configuration file veranderen met het volgende commando            
            
            mv /etc/haproxy/haproxy.cfg{,.org}
            
Maak daarna een nieuwe aan 

            nano /etc/haproxy/haproxy.cfg
            
Vul de volgende gegevens in

            global
                        log 127.0.0.1 local0 notice
                        user haproxy
                        group haproxy
                        
            defaults 
                        log global
                        retries 2
                        timeout connect 3000
                        timeout server 5000
                        timeout client 5000
                        
            listen mysql-cluster
                        bind 127.0.0.1:3306
                        mode tcp
                        option mysql-check user haproxy_check
                        balance roundrobin
                        server master1 172.16.0.158:3306 check
                        server master2 172.16.0.158:3306 check
                        
            listen stats
                        bind 0.0.0.0:8080
                        mode http
                        stats enable
                        stats uri /
                        stats realm Strictly\ private
                        stats auth admin:password
                        
                        
