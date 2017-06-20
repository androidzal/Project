#!/bin/bash
sort -t, -k1 -n data.csv | sed '$!N; /^\(.*\)\n\1$/!P; D' | awk '{if(length($0)>20)print;}' > tmp.csv
sed -i 's/"/“/g' tmp.csv
python2.7 jiebaCut.py
rm tmp.csv
sed -i 's///g' result.csv

