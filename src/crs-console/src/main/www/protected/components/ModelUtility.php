<?php

class ModelUtility {
   
   static public function hasProperty($columns, &$object, $message = ''){
      foreach($columns as $key => $value) 
      {
         if(array_key_exists('resultfield', $value))
         {
            $property = $value['resultfield'];
            if(isset($property)) 
            {
               if(property_exists($object, $property) == false) {
                  Yii::log('Object has not property '.$property.' ('.$message.').'); 
                  $object->{$property} = null;
               }
            }
         }
      }
   }
}
?>
