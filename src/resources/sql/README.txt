0000-schema-baseline.sql         The latest production schema only - NO DATA!
0100-import-settings.sql         Optional file to hold temporary settings before loading the data
1000-triggers.proc               Stored procedures and triggers - run with delimiter //
2000-test-data-bulk.sql          Important: TEST DATA - hardcoded tests depend on it
5000-schema-migrations.sql       Schema changes to be applied to production schema - Before first release
5???-releaseName-release.sql     Release specif data / schema changes to apply on PRODUCTION and TEST environments - eg.: 5001-Beetle-release.sql
5???-releaseName-test-data.sql   Release specif data / schema changes to apply on TEST environments only - eg.: 5002-Beetle-test-data.sql
9999-cleanup.sql                 Final steps before tests will run (e.g. revert import time settings)
README.txt
