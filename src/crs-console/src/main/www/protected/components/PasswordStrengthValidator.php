<?php
class PasswordStrengthValidator
extends CValidator
{

	function validateAttribute($object,$attribute) {
		$passwd = $object->$attribute;
		$rules = array('/.*[a-z]+.*/', '/.*[A-Z]+.*/', '/.*[0-9]+.*/', '/.*[^a-zA-Z0-9]+.*/');

		foreach ($rules as $r) {
	  		if (0 == preg_match($r, $passwd)) {
	  			$object->addError($attribute, "Password must contain uppercase letter, lowercase letter, number, non-alphanumeric [:_-.#@|!$%^&*].");
	  			return false;
	  			
	  		}
		}
		
		return true;
	}
}
?>