<?php
/**
 * Description of JavaScriptUtils
 *
 * @author Artur Kielak
 */
class JavaScriptUtils extends CWidget {
   public $model;
   
   public function run() {
      $this->render('application.widgets.js.utils');
   }
}

?>
