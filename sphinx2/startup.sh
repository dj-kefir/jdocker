#!/bin/bash
set -e

echo "running startup.sh..."

SPHINX_MODE=${SPHINX_MODE:-}
SPHINX_BACKUP_DIR=${SPHINX_BACKUP_DIR:-"/tmp/backup"}
SPHINX_BACKUP_FILENAME=${SPHINX_BACKUP_FILENAME:-"backup.last.tar.gz"}
SPHINX_RESTORE=${SPHINX_RESTORE:-}
SPHINX_CHECK=${SPHINX_CHECK:-}
INDEX_NAME=${INDEX_NAME:-}
SPHINX_ROTATE_BACKUP=${SPHINX_ROTATE_BACKUP:-true}

sed -i "s~SPHINX_DATA_DIR~${SPHINX_DATA_DIR}~g" ${SPHINX_CONF}
sed -i "s~SPHINX_XML_SOURCE_DIR~${SPHINX_XML_SOURCE_DIR}~g" ${SPHINX_CONF}
sed -i "s~SPHINX_LOG_DIR~${SPHINX_LOG_DIR}~g" ${SPHINX_CONF}
sed -i "s~SPHINX_RUN~${SPHINX_RUN}~g" ${SPHINX_CONF}

if [[ ${SPHINX_MODE} == indexing ]]; then
 indexer --config ${SPHINX_CONF} --all
fi

# allow arguments to be passed to Sphinx search
if [[ ${1:0:1} = '-' ]]; then
  EXTRA_OPTS="$@"
  set --
fi

# default behaviour is to launch Sphinx search
if [[ -z ${1} ]]; then
  echo "Starting Sphinx search demon..."
  exec $(which searchd) --config ${SPHINX_CONF} --nodetach ${EXTRA_OPTS}
else
  exec "$@"
fi