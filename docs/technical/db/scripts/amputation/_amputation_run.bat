@echo ">>--------- amputation start";

mysql -e "source amputation_init.sql"
mysql -e "source amputation_sequence.sql"
mysql -e "source amputation_release.sql"

@echo ">>--------- amputation finished";
pause;