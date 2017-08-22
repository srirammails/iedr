#!/bin/sh

# Exit when an undeclared variable is used.
set -u

# Exit if a command teturns non-zero exit code.
set -e

command -v xsltproc >/dev/null 2>&1 || { echo >&2 "I require xsltproc but it's not installed.  Aborting."; exit 1; }
command -v xmllint >/dev/null 2>&1 || { echo >&2 "I require xmllint but it's not installed.  Aborting."; exit 1; }

for WSDL_FILE in *PortType.wsdl
do
  BASE=$(basename "$WSDL_FILE" PortType.wsdl)
  cat "$WSDL_FILE" > "$BASE.wsdl"
  rm "$WSDL_FILE"
done
for XSD_FILE in *PortType_schema1.xsd
do
  BASE=$(basename "$XSD_FILE" PortType_schema1.xsd)
  cat "$XSD_FILE" > "${BASE}_schema1.xsd"
  rm "$XSD_FILE"
done

OUTPUT_DIR=php
SERVICES=$(for sf in *.wsdl; do basename $sf .wsdl; done)
mkdir -p "$OUTPUT_DIR"

function xmltidy() {
  XML_FILE=$1
  if [ -s "$XML_FILE" ];
  then
    TEMPFILE=$(mktemp)
    cp "$XML_FILE" "$TEMPFILE" 
    xmllint --format "$TEMPFILE" > "$XML_FILE"
    rm "$TEMPFILE"
  fi
}

for SERVICE in $SERVICES
do
  echo "Processing service: $SERVICE"
  SERVICE_WSDL=${SERVICE}.wsdl
  SERVICE_API_XML=${SERVICE}_api.xml
  SERVICE_XSD=${SERVICE}_schema1.xsd
  SERVICE_PHP=$SERVICE.php

  xmltidy "$SERVICE_WSDL"
  xmltidy "$SERVICE_XSD"
  
  if [ -s "$SERVICE_XSD" ]
  then
    xsltproc --stringparam xsd_file "$SERVICE_XSD" wsdl_to_api.xslt "$SERVICE_WSDL" > "$SERVICE_API_XML"
    xmltidy "$SERVICE_API_XML"
  fi

  if [ -s "$SERVICE_API_XML" ];
  then
    xsltproc --stringparam namespace "$SERVICE" make_php_api_calls.xslt "$SERVICE_API_XML" | grep -v "xml version" > "$OUTPUT_DIR/$SERVICE_PHP"
  fi
done

