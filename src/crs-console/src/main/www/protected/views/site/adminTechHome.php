<p>
    <?php
    $userNH = Yii::app()->user->authenticatedUser->username;
    $userName = Yii::app()->user->nicHandle->name;
    echo 'Welcome to the IEDR Console, ' . $userName;
    ?>
</p>
<p>
    Depending on your role on a given domain name (whether you are an admin contact or technical contact) you will be able to perform the following actions here.
</p>
<ul>
    <li>Edit your account information (<a target="_parent" href="index.php?r=nichandles/viewnichandle&id=<?php echo $userNH?>">click here</a>)</li>
    <li>View your domains and submit requests to modify registration details (<a target="_parent" href="index.php?r=domainreports/alldomains">click here</a>) (available to admin contacts only)</li>
    <li>View the DNS information for your domains and submit updates as needed (<a target="_parent" href="index.php?r=nsreports/dnsserversearch">click here</a>)</li>
</ul>
<p>
    Please note, that when updating the DNS information on a .ie domain, you must ensure that there are always at least 2 nameservers listed,
    and any nameservers used must be configured to have authoritative zones set up, in accordance with RFC Protocols 1034 and 1035
    (<a target="_blank" href="http://www.ietf.org/rfc/rfc1034.txt">view</a>) When entering a nameserver, you will only be asked to enter glue records/IP addresses if the nameserver is based off the domain you are updating.
</p>
