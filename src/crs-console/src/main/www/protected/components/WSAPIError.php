<?php

class WSAPIError {

   public static $errors = array();
   public static $isLoaded = false;

   static public function loadErrors() {
      $handle = fopen(MESSAGE_WSAPIERRORS, 'r');
      if ($handle) {
         while (($buffer = fgets($handle)) !== false) {
            $content = array();
            $content = split("=", $buffer, 2);
            if (count($content) == 2) {
               WSAPIError::$errors["" . $content[0] . ""] = "" . $content[1] . "";
            }
         }
         WSAPIError::$isLoaded = true;
         fclose($handle);
      } else {
         Yii::log('Can not open error messages.');
      }
   }

   static public function getErrors($error, $returnUrl = NULL) {
      if (isset($error)) {
         if (WSAPIError::$isLoaded == false) {
            WSAPIError::loadErrors();
         }

         if (is_array($error)) {
            foreach (WSAPIError::$errors as $key => $value) {
               for ($i = 0; $i < count($error); $i++) {
                  if (substr($error[$i], 0, strlen($key)) == $key) {
                     Yii::log('Error is array '.print_r($error,true));
                     return WSAPIError::printError($value);
                  }
               }
            }
            
            /* to cut SOAP Exception string*/
            $arrayError = split(":", $error[0]);
            $value = $arrayError[count($arrayError)-1];
            
            if(strpos($value, 'DECLINED') !== false) {
                return $returnUrl;
            }
            return WSAPIError::printError($value);
         } else if (is_string($error)) {
            Yii::log('WSAPI ERROR IS STRING');
            foreach (WSAPIError::$errors as $key => $value) {
               if (strlen(strstr($error, $key)) > 0) {
                  Yii::log('Error is string '.print_r($error,true));
                  if(strpos($error,'DECLINED') !== false) {
                     Yii::log('NOT FOUND = '.print_r($error,true));
                     return $returnUrl;
                  }
                  $message = WSAPIError::getErrorMessage($error);
                  if(isset($message)) {
                      Yii::log('WSAPI ERROR IS STRING WITH MESSAGE');
                      return WSAPIError::printError($message);
                  }
                  return WSAPIError::printError($value);
               }
            }
            /* if exception with short message */
            if (strlen(strstr($error, "SOAP")) > 0) {
               $content = array();
               $content = split(":", $error, 3);
               Yii::log('Error is string with soap to parse '.print_r($error,true));
               if(strpos($content[2],'DECLINED') !== false) {
                  return $returnUrl;
               }
               if(strpos($error,"is not a valid event") !== false) {
                   return WSAPIError::printError('Incorrect domain state');
               }
               return WSAPIError::printError($content[2]);
            }
         } else {
            Yii::log('Not parsed error!');
         }
      }
   }

   static private function getErrorMessage($error) {
       $content = split(":", $error);
       if (count($content) > 2) {
           return $content[2];
       } else {
           return null;
       }
   }

   static private function printError($message) {
       return '<span class="errorMessage">Error. ' . $message . ' </span><br>';
   }

   static public function getErrorsNotEmpty($error, $returnUrl = NULL) {
       $msg = WSAPIError::getErrors($error, $returnUrl);
       return isset($msg) ? $msg : $error;
   }
}

?>
