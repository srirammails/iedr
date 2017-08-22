if(typeof String.prototype.trim !== 'function') {
   String.prototype.trim = function() {
      return this.replace(/^\s+|\s+$/g, "");
   }
}

if(typeof Array.prototype.indexOf !== 'function') {
   Array.prototype.indexOf = function(element, index) {
      if (index === undefined) 
         index = 0;
      if (index < 0) 
         index += this.length;
      if (index < 0) 
         index = 0;
      for (var i = this.length; index < i; index++)
         if (index in this && this[index] === element)
            return index;
      return -1;
   }
}

function applyErrorsForAllFields(form, attribute, data, hasError){
    var settings=form.data("settings");
    var attributes=settings.attributes;
    $.each(attributes,function(index,attribute){
        $.fn.yiiactiveform.updateInput(attribute,data, form);
    })
}

// fix problem  with slow backend validation methods
function applyErrorForField(form, attribute, data, hasError){
    $.fn.yiiactiveform.updateInput(attribute,data, form);
}
