FROM ubuntu:14.04

RUN apt-get update \
    && DEBIAN_FRONTEND=noninteractive apt-get install -y mysql-server \
    && rm -rf /var/lib/mysql

ADD start.sh /
RUN chmod +x /start.sh

CMD [ "/startup.sh" ]