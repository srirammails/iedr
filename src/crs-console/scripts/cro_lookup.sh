#!/bin/bash

NUM=409153

export http_proxy=127.0.0.1:8118

function extract_input_value() {
    VAL=$1
    EXTRACTED=`cat cro.html | tr '\r\n\t' ' ' | tr -s ' ' | tr '<>' '\n' | grep "^input.*${VAL}[^A-Z]" | tr ' ' '\n' | grep "^value" | tr '"' '\n' | grep "^......."`
    php -r "echo urlencode('$EXTRACTED');" 2>/dev/null
    }


UA="Mozilla/5.0 (X11; U; Linux x86_64; en-US) AppleWebKit/534.13 (KHTML, like Gecko) Ubuntu/10.10 Chromium/9.0.597.94 Chrome/9.0.597.94 Safari/534.13"
CKIE="--save-cookies cookies.txt --load-cookies cookies.txt --keep-session-cookies"
OPTS="-q "
REF="Referer: http://www.cro.ie/search/CompanySearch.aspx"

wget $OPTS -U "$UA" $CKIE -O cro.html "http://www.cro.ie/search/CompanySearch.aspx"

PD="__EVENTTARGET="
PD="${PD}&__EVENTARGUMENT="
PD="${PD}&__VIEWSTATE=`extract_input_value __VIEWSTATE`"
PD="${PD}&__SCROLLPOSITIONX=0"
PD="${PD}&__SCROLLPOSITIONY=220"
PD="${PD}&__VIEWSTATEENCRYPTED="
PD="${PD}&__EVENTVALIDATION=`extract_input_value __EVENTVALIDATION`"
PD="${PD}&radioSearchFor=Both"
PD="${PD}&textCompanyName="
PD="${PD}&radioSearchOptions=4"
PD="${PD}&textAddress="
PD="${PD}&textCompanyNum=${NUM}"
PD="${PD}&textAlphaSort="
PD="${PD}&radioExPrevious=Existing"
PD="${PD}&Button1=Search"

wget $OPTS -U "$UA" --header="$REF" $CKIE -O cro_resp.html --post-data="$PD" "http://www.cro.ie/search/CompanySearch.aspx"

cat cro_resp.html | sed -e "s|<table|<table border=\"1\"|" -e "s|><|>@<|g" | tr '@' '\n' | grep -A 90 "<table[^>]*GridView1" | grep -B 90 "thirdCol" | grep -B 90 "</table>" > x.html
links -width 200  -dump x.html

