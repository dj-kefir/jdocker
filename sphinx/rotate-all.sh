#!/bin/bash
set -e

/usr/local/bin/indexer.sh --rotate --all
/usr/local/bin/searchd.sh