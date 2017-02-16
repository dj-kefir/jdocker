FROM ubuntu:xenial
MAINTAINER dj-kefir <igor.ozol@eldorado.ru>

ENV OS_LOCALE="en_US.UTF-8" \
    OS_LANGUAGE="en_US:en" \
	SPHINX_LOG_DIR=/var/log/sphinxsearch \
	SPHINX_CONF=/etc/sphinxsearch/sphinx.conf \
	SPHINX_RUN=/run/sphinxsearch/searchd.pid \
	SPHINX_DATA_DIR=/var/lib/sphinxsearch/data \
	SPHINX_XML_SOURCE_DIR=/var/lib/sphinxsearch/source

# Set the locale
RUN locale-gen ${OS_LOCALE}
ENV LANG=${OS_LOCALE} \
	LANGUAGE=${OS_LANGUAGE} \
	LC_ALL=${OS_LOCALE}

COPY ./entrypoint.sh /sbin/entrypoint.sh
COPY ./startup.sh /sbin/startup.sh

RUN	buildDeps='software-properties-common python-software-properties' \
	&& apt-get update && apt-get install -y $buildDeps --no-install-recommends \
	&& add-apt-repository -y ppa:builds/sphinxsearch-rel22 \
	&& apt-get update \
	&& apt-get install -y sudo sphinxsearch \
	&& mv -f /etc/sphinxsearch/sphinx.conf /etc/sphinxsearch/origin.sphinx.conf \
	&& apt-get purge -y --auto-remove $buildDeps \
	&& rm -rf /var/lib/apt/lists/* \
    && chmod 755 /sbin/startup.sh \
	# Forward sphinx logs to docker log collector
	&& ln -sf /dev/stdout ${SPHINX_LOG_DIR}/searchd.log \
	&& ln -sf /dev/stdout ${SPHINX_LOG_DIR}/query.log

# копируем конфиг
COPY ./configs/* /etc/sphinxsearch/

# копируем xml для индексации
RUN mkdir -p ${SPHINX_XML_SOURCE_DIR}
COPY ./source/* /${SPHINX_XML_SOURCE_DIR}/

EXPOSE 9307 9306
VOLUME ["${SPHINX_DATA_DIR}"]
#ENTRYPOINT ["/sbin/entrypoint.sh"]
ENTRYPOINT ["/sbin/startup.sh"]