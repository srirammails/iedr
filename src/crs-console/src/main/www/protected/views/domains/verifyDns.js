function verifyDNS(formId, domains, nameservers) {
  var DNS_CHECK_NOTICE_ID = "DNSCheckNotificationResult";
  var formData = $.param({
      YII_CSRF_TOKEN: $("#"+formId+" input[name=YII_CSRF_TOKEN]").val(),
      domains: domains,
      nameservers: nameservers});
  $("#"+DNS_CHECK_NOTICE_ID).remove();
  var flash = function(flashType, message, additionalData) {
     var flashDiv = $("<div>").attr('id', DNS_CHECK_NOTICE_ID).addClass("flash-" + flashType).text(message);
     if (additionalData) flashDiv.append(additionalData);
     flashDiv.prepend($("<a>").text("Close").css("float", "right").click(function(e){
        e.preventDefault();
        $("#"+DNS_CHECK_NOTICE_ID).remove();
     }));
     $("#content > h2:first").after(flashDiv);
  };
  $.ajax({
     type: 'POST',
     url: "<?php $host = Yii::app()->request->hostInfo; $host .= '/index.php?r=domains/verifyDns'; echo $host; ?>",
     data: formData,
     success: function(response) {
        if (response == "") {
            flash("notice", "DNS Check passed");
        } else {
            var dnsFailure = response.indexOf('FATAL') >= 0;
            if (dnsFailure) {
                flash("error", "DNS Check failed", $('<pre>').css({maxHeight: "150px", overflow:"auto"}).text(response));
            } else {
                flash("error", response);
            }
        }
     }
  });
}
