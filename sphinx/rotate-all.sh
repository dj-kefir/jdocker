#!/bin/bash
set -e

/usr/local/bin/indexer --rotate --all
/usr/local/bin/searchd 