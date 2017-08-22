#!/usr/bin/perl 

use strict;
use Switch 'Perl5';
use Term::ANSIColor;

sub trim($) {
    my $string = shift;
    $string =~ s/^\s+//;
    $string =~ s/\s+$//;
    return $string;
}

sub match {
    my ($array,$value) = @_;
    my @a = @{$array};
    for(my $i=0;$i<$#a+1;$i++) {
	  if( $value =~ /$a[$i]/ ) {
		return $a[$i];
	  }
    }
    return '';
}

sub in_array {
    my ($array,$value) = @_;
    my @a = @{$array};
    for(my $i=0;$i<$#a+1;$i++) {
	  if($a[$i] eq $value) {
		return 1;
	  }
    }
    return 0;
}

my @modules = (
	  'CRSAuthenticationService',
	  'CRSDomainAppService',
	  'CRSNicHandleAppService',
	  'CRSPaymentAppService',
	  'CRSPermissionsAppService',
	  'CRSResellerAppService',
	  'CRSTicketAppService',
	  'CRSCommonAppService'
    );

my @CRSAuthenticationService = ();
my @CRSDomainAppService  = ();
my @CRSNicHandleAppService = ();
my @CRSPaymentAppService = ();
my @CRSPermissionsAppService = ();
my @CRSResellerAppService = ();
my @CRSTicketAppService = ();
my @CRSCommonAppService = ();

my @CRSAuthenticationServiceDiff = ();
my @CRSDomainAppServiceDiff  = ();
my @CRSNicHandleAppServiceDiff = ();
my @CRSPaymentAppServiceDiff = ();
my @CRSPermissionsAppServiceDiff = ();
my @CRSResellerAppServiceDiff = ();
my @CRSTicketAppServiceDiff = ();
my @CRSCommonAppServiceDiff = ();


my $debugNRC = 0;
my $debugWSAPI = 0;
my $fileNRC='nrclog.txt';
my $fileWSAPI='.txt';

#Read from NRC
my $pid = fork();
if ($pid == -1) {
    die;
} elsif ($pid == 0) {   
    exec "find /opt/crs/console/www/protected -name *.php -exec grep CRS* {} \\; > $fileNRC";
}

while (wait() != -1) {
    open FILE, "< $fileNRC" or die('Could not open file!');
    my @lines = <FILE>;
    close FILE;

    my @callFunctions;
    my @object;
    my @objectWihout_;
    my @functions;
    my @originalObject;

    for(my $i=0;$i<$#lines+1;$i++) {
	  if( $lines[$i] =~ "::" ) {
	     if($lines[$i] =~ /\bCRS/ && $lines[$i] !~ /\/\//) {
		@callFunctions = split(/::/,$lines[$i]);
 		@object = split(/ /,$callFunctions[0]);
		@functions = split(/\(/,$callFunctions[1]); 
		@callFunctions = split(/ /, $functions[0]);
		$functions[0] = $callFunctions[0];
		$functions[0] =~ s/[\n:;)(.']//g;
		$functions[0] = trim($functions[0]);
		@functions = split(/ /,$functions[0]);

		@objectWihout_ = split(/_/,$object[$#object]); 
		
		    switch (match(\@modules,trim($objectWihout_[0]))) 
		    {
			  case 'CRSAuthenticationService' {
				if(!in_array(\@CRSAuthenticationService, $functions[0])) {
				    push(@CRSAuthenticationService, $functions[0]);    
				    @CRSAuthenticationService = sort(@CRSAuthenticationService);
				}
			  }
			  case 'CRSDomainAppService' {
				if(!in_array(\@CRSDomainAppService, $functions[0])){
				    push(@CRSDomainAppService, $functions[0]);
				    @CRSDomainAppService = sort(@CRSDomainAppService);
				}
			  }
			  case 'CRSNicHandleAppService' {
				if(!in_array(\@CRSNicHandleAppService, $functions[0])) {
				    push(@CRSNicHandleAppService, $functions[0]);
				    @CRSNicHandleAppService = sort(@CRSNicHandleAppService);
				}
			  }
			  case 'CRSPaymentAppService' {
				if(!in_array(\@CRSPaymentAppService, $functions[0])) {
				    push(@CRSPaymentAppService, $functions[0]);
				    @CRSPaymentAppService = sort(@CRSPaymentAppService);
				}
			  }
			  case 'CRSPermissionsAppService' {
				if(!in_array(\@CRSPermissionsAppService, $functions[0])) {
				    push(@CRSPermissionsAppService, $functions[0]);
				    @CRSPermissionsAppService = sort(@CRSPermissionsAppService);
				}
			  }
			  case 'CRSResellerAppService' {
				if(!in_array(\@CRSResellerAppService, $functions[0])) {
				    push(@CRSResellerAppService, $functions[0]);
				    @CRSResellerAppService = sort(@CRSResellerAppService);
				}
			  }
			  case 'CRSTicketAppService' {
				if(!in_array(\@CRSTicketAppService, $functions[0])) {
				    push(@CRSTicketAppService, $functions[0]);
				    @CRSTicketAppService = sort(@CRSTicketAppService);
				}
			  }
			  case 'CRSCommonAppService' {
				if(!in_array(\@CRSCommonAppService, $functions[0])) {
				    push(@CRSCommonAppService, $functions[0]);
				    @CRSCommonAppService = sort(@CRSCommonAppService);
				}
			  }
		    }
		}
	  }
    }

    #Show from NRC function
    if($debugNRC) {
	  print_array(\@CRSAuthenticationService, "CRSAuthenticationService");
	  print_array(\@CRSDomainAppService, "CRSDomainAppService");
	  print_array(\@CRSNicHandleAppService, "CRSNicHandleAppService");
	  print_array(\@CRSPaymentAppService, "CRSPaymentAppService");
	  print_array(\@CRSPermissionsAppService, "CRSPermissionsAppService");
	  print_array(\@CRSResellerAppService, "CRSResellerAppService");
	  print_array(\@CRSTicketAppService, "CRSTicketAppService");
	  print_array(\@CRSCommonAppService, "CRSCommonAppService");
    }

    #Read from wsapi
    for(my $i=0;$i<$#modules+1;$i++) {
	  my $pid = fork();
	  if ($pid == -1) {
		die;
	  } elsif ($pid == 0) {   	  
		exec "find /opt/crs/console/wsapi/wsdl_from_svc -name ".$modules[$i].".php -exec grep function {} \\; > $modules[$i]$fileWSAPI ";
	  }
	  while( wait() != -1) {
		open FILE_DIFF, "< $modules[$i]$fileWSAPI" or die('Could not open file!');
		my @linesDiff = <FILE_DIFF>;
		close FILE_DIFF;
		my @functionDiff = ();
		my @functionOriginal = ();
		for(my $j=0;$j<$#linesDiff+1;$j++) {
		    if( $linesDiff[$j] =~ "static" ) {
			  @functionDiff = split(/ /,trim($linesDiff[$j]));
			  $functionDiff[3] = trim($functionDiff[3]);
			  @functionOriginal = split(/\(/,$functionDiff[3]);

			  switch ($modules[$i]) 
			  {
				case '' {
				    #printf "  ".$i." obj=".trim($objectWihout_[0])." fun=".$functionsOriginal[0]."\n";
				}
				case 'CRSAuthenticationService' {
				    if(!in_array(\@CRSAuthenticationServiceDiff, $functionOriginal[0])) {
					  push(@CRSAuthenticationServiceDiff, $functionOriginal[0]);    	
					  @CRSAuthenticationServiceDiff = sort(@CRSAuthenticationServiceDiff);
				    }
				}
				case 'CRSDomainAppService' {
				    if(!in_array(\@CRSDomainAppServiceDiff, $functionOriginal[0])){
					  push(@CRSDomainAppServiceDiff, $functionOriginal[0]);
					  @CRSDomainAppServiceDiff = sort(@CRSDomainAppServiceDiff);
				    }
				}
				case 'CRSNicHandleAppService' {
				    if(!in_array(\@CRSNicHandleAppServiceDiff, $functionOriginal[0])) {
					  push(@CRSNicHandleAppServiceDiff, $functionOriginal[0]);
					  @CRSNicHandleAppServiceDiff = sort(@CRSNicHandleAppServiceDiff);
				    }
				}
				case 'CRSPaymentAppService' {
				    if(!in_array(\@CRSPaymentAppServiceDiff, $functionOriginal[0])) {
					  push(@CRSPaymentAppServiceDiff, $functionOriginal[0]);
					  @CRSPaymentAppServiceDiff = sort(@CRSPaymentAppServiceDiff);
				    }
				}
				case 'CRSPermissionsAppService' {
				    if(!in_array(\@CRSPermissionsAppServiceDiff, $functionOriginal[0])) {
					  push(@CRSPermissionsAppServiceDiff, $functionOriginal[0]);
					  @CRSPermissionsAppServiceDiff = sort(@CRSPermissionsAppServiceDiff);
				    }
				}
				case 'CRSResellerAppService' {
				    if(!in_array(\@CRSResellerAppServiceDiff, $functionOriginal[0])) {
					  push(@CRSResellerAppServiceDiff, $functionOriginal[0]);
					  @CRSResellerAppServiceDiff = sort(@CRSResellerAppServiceDiff);
				    }
				}
				case 'CRSTicketAppService' {
				    if(!in_array(\@CRSTicketAppServiceDiff, $functionOriginal[0])) {
					  push(@CRSTicketAppServiceDiff, $functionOriginal[0]);
					  @CRSTicketAppServiceDiff = sort(@CRSTicketAppServiceDiff);
				    }
				}
				case 'CRSCommonAppService' {
				    if(!in_array(\@CRSCommonAppServiceDiff, $functionOriginal[0])) {
					  push(@CRSCommonAppServiceDiff, $functionOriginal[0]);
					  @CRSCommonAppServiceDiff = sort(@CRSCommonAppServiceDiff);
				    }
				}
			  }
		    }
		} 

		#Show from WSAPI function
		if($debugWSAPI) {
		    print_array(\@CRSAuthenticationServiceDiff, "CRSAuthenticationServiceDiff");
		    print_array(\@CRSDomainAppServiceDiff, "CRSDomainAppServiceDiff");
		    print_array(\@CRSNicHandleAppServiceDiff, "CRSNicHandleAppServiceDiff");
		    print_array(\@CRSPaymentAppServiceDiff, "CRSPaymentAppServiceDiff");
		    print_array(\@CRSPermissionsAppServiceDiff, "CRSPermissionsAppServiceDiff");
		    print_array(\@CRSResellerAppServiceDiff, "CRSResellerAppServiceDiff");
		    print_array(\@CRSTicketAppServiceDiff, "CRSTicketAppServiceDiff");
		    print_array(\@CRSCommonAppServiceDiff, "CRSCommonAppServiceDiff");
		}
	  }
    }
	  
		#Check differences between NRC and WSPI
		my $counter = 0;
		$counter = diff_array(\@CRSAuthenticationService , \@CRSAuthenticationServiceDiff, "CRSAuthenticationService"); 
 		$counter += diff_array(\@CRSDomainAppService , \@CRSDomainAppServiceDiff, "CRSDomainAppService"); 
		$counter += diff_array(\@CRSNicHandleAppService , \@CRSNicHandleAppServiceDiff, "CRSNicHandleAppService"); 
		$counter += diff_array(\@CRSPaymentAppService , \@CRSPaymentAppServiceDiff, "CRSPaymentAppService"); 
		$counter += diff_array(\@CRSPermissionsAppService , \@CRSPermissionsAppServiceDiff, "CRSPermissionsAppService"); 
		$counter += diff_array(\@CRSResellerAppService , \@CRSResellerAppServiceDiff, "CRSResellerAppService"); 
		$counter += diff_array(\@CRSTicketAppService , \@CRSTicketAppServiceDiff, "CRSTicketAppService"); 
		$counter += diff_array(\@CRSCommonAppService , \@CRSCommonAppServiceDiff, "CRSCommonAppService"); 
		print colored( "NOT FOUND ".$counter." ELEMENTS IN WSAPI!\n", 'yellow' );
		if(!unlink $fileNRC) {
		    printf "Could not remove file log ".$fileNRC."\n";
		}
		for(my $i=0;$i<$#modules+1;$i++) {
		    if(!unlink $modules[$i].$fileWSAPI) {
			  printf "Could not remove file log ".$modules[$i]."".$fileWSAPI."\n";
		    }
		}

}

sub diff_array() {
    my ($array, $value, $msg) = @_;
    my @nrc = @{$array};
    my @wsapi = @{$value};
    my $true = 0;
    my $count = 0;
    for(my $i=0;$i<$#nrc+1;$i++) {
	  for(my $j=0;$j<$#wsapi+1;$j++) {
		if($nrc[$i] eq $wsapi[$j]) {
		    $true = 1;
		}
	  }
	  if($true == 0) {
		print colored( $msg, 'green' );
		printf " element (".colored($nrc[$i],'red').") not found in WSAPI.\n";	
		$count++;
	  }
	  $true = 0;
    }
    return $count;
}

sub print_array() {
    my ($array, $msg) = @_;
    my @a = @{$array};
    for(my $i=0;$i<$#a+1;$i++) {
	  printf "$msg ".$a[$i]."\n";
    }
}
