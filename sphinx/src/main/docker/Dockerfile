FROM ubuntu:14.04

RUN groupadd -r sphinx  && useradd -r -g sphinx sphinx

# add gosu for easy step-down from root
ENV GOSU_VERSION 1.9

RUN set -x \
  && apt-get update && apt-get install -y --no-install-recommends ca-certificates wget libmysqlclient18  unixodbc libpq5  && rm -rf /var/lib/apt/lists/* \
  && wget -O /usr/local/bin/gosu "https://github.com/tianon/gosu/releases/download/$GOSU_VERSION/gosu-$(dpkg --print-architecture)" \
  && wget -O /usr/local/bin/gosu.asc "https://github.com/tianon/gosu/releases/download/$GOSU_VERSION/gosu-$(dpkg --print-architecture).asc" \
  && export GNUPGHOME="$(mktemp -d)" \
  && gpg --keyserver ha.pool.sks-keyservers.net --recv-keys B42F6819007F00F88E364FD4036A9C25BF357DD4 \
  && gpg --batch --verify /usr/local/bin/gosu.asc /usr/local/bin/gosu \
  && rm -r "$GNUPGHOME" /usr/local/bin/gosu.asc \
  && chmod +x /usr/local/bin/gosu \
  && gosu nobody true \
  && wget -O /tmp/sphinxsearch.deb http://sphinxsearch.com/files/sphinxsearch_2.2.11-release-1~trusty_amd64.deb \
  && dpkg -i /tmp/sphinxsearch.deb \
  && apt-get purge -y --auto-remove ca-certificates wget \
  && mkdir -p /var/lib/sphinxsearch/data \
  && mkdir -p /var/log/sphinxsearch \
  && mkdir -p /var/run/sphinxsearch

EXPOSE 9312 9306
ADD entrypoint.sh /usr/local/bin/
CMD ["entrypoint.sh"]