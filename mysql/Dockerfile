FROM ubuntu:14.04

RUN apt-get update \
    && DEBIAN_FRONTEND=noninteractive apt-get install -y mysql-server \
    && rm -rf /var/lib/mysql

ADD startup.sh /
RUN chmod +x /startup.sh

# ADD my.cnf /etc/mysql/
CMD [ "/startup.sh" ]

docker run \
    --name=sem_mysql \
    -v /ПАПКА/С/ДАННЫМИ:/var/lib/mysql \
    -p giza.dev.eldorado.ru:3306:3306 \
    -d jdocker/mysql

 docker run \
     --name=sem_mysql \
     -p  10.0.7.39:3307:3306 \
     -d jdocker/mysql


docker run --name=sem_mysql \
    -p 10.0.7.39:3307:3306 \
    -e MYSQL_ROOT_PASSWORD=123456 \
    -e MYSQL_DATABASE=sem \
    -e MYSQL_USER=sem_user \
    -e MYSQL_PASSWORD=sem123 \
    -d mysql:5.7