<?php
      function getCrsVersion() {
         CRSInfo_service::getVersionInfo($result, $errs);
         if($result != null) {
            if(count($errs) == 0) {
               if(property_exists($result, 'formattedVersion')) {
                  return $result->formattedVersion;
               }
            }
         }
         return "CRS-WS Version";
		}
?>
