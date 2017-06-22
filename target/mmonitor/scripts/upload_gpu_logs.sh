cd /var/www/html/ul
ftp -n <<EOF
open ftp.atw.hu
user barbarminer Astra16i
put gpuinfos.csv
EOF
