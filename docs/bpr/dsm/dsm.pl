#!/usr/bin/perl
# 
# Script to output SQL DML from CSV format of DSM Spec.
# This will need to be modified if the DB Schema changes, or the DSM spreadsheet structure changes, 
# but both should be fairly stable and the approach should be clear enough.
# Also - due to time constraints I've only indicated the points at which each INSERT statement can be
# output, but the data at each point should be available.
# 
# Author: Paul Delany
# Date: 2012-02-09

use strict;
use Text::CSV_XS;

my $csvfilename = "dsm.csv"; # filename of input DSM CSV file

# if dsm spec spreadsheet structure changes these may need to be altered
my $stateCol = 0; # col index for state id
my $validCol = 2; # col index for valid flag
my $holderCol = 4; # col index for holder type
my $renewalCol = 5; # col index for renewal mode
my $wipoCol = 6; # col index for wipo flag
my $customerCol = 7; # col index for customer type
my $nrpCol = 8; # col index for nrp status
my $ftc  = 10; # index of first transition column

my $csv = Text::CSV_XS->new ({ binary => 1 }) or
    die "Cannot use CSV: ".Text::CSV_XS->error_diag ();

open my $fh, "<:encoding(utf8)", $csvfilename or die "$csvfilename: $!";

my %events;
my $eventID = 0;

my %states;

my $transID = 0;
my $actionID = 0;
my %actionsByName;
my %actions;

my $trans_actionID = 0;
# main loop to process each CSV row
while (my $row = $csv->getline ($fh)) {

    if( $row->[$stateCol] =~ /EVENT/) {
	# we're processing the event header row...

	my $maxcols = scalar (@$row);
	for(my $i = $ftc; $i < $maxcols; $i+=3) {
	    next unless $row->[$i] =~ /\w+/;
	    $eventID++;
	    $events{$eventID} = $row->[$i];

	    # output your DSM_Event INSERT here....
	    print "Event Id: $eventID Name ".$events{$eventID}."\n";

	}}

    next unless $row->[$stateCol] =~ /^\d+$/; # state number
    next unless $row->[$validCol] =~ /^Y$/; # valid state

    my $stateID = $row->[$stateCol]; 
    my %stateData = ('holder' => $row->[$holderCol],
		     'renewal' => $row->[$renewalCol],
		     'wipo' => $row->[$wipoCol],
		     'customer' => $row->[$customerCol],
		     'nrp' => $row->[$nrpCol]);

    $states{$stateID} = \%stateData;

    # output your DSM_State INSERT here...
    print "State ID: $stateID Holder: ".$row->[$holderCol]." etc... \n";

    my $maxcols = scalar (@$row);
    for(my $i = $ftc; $i < $maxcols; $i+=3) {

	next unless $row->[$i] =~ /\d+/;

	$transID++;
	my $nextStateID = $row->[$i];

	# output your DSM_Transition INSERT here...
	print "Trans Id: $transID Begin_State: $stateID End_State: $nextStateID etc... \n";

	my $actionString = $row->[$i+1];
	next if $actionString =~ /^x$/; # no actions
	next if $actionString =~ /^$/;

	my @actionList = split /,/,  $actionString;
	my $order = 0;
	for my $action (@actionList) {
	    
	    # insert the action if it doesn't already exist
	    if(!exists $actionsByName{$action}) {
		$actionID++;
		$actions{$actionID} = $action;
		$actionsByName{$action} = $actionID;

		# output your DSM_Action INSERT statement here...
		print "Action ID: $actionID Name: $action etc...\n";
	    }

	    $trans_actionID++;
	    
	    # output your DSM_Transition_Action INSERT statement here...
	    print "Transition_Action ID: $trans_actionID Transition_Id: $transID Order $order Action_Id $actionID etc...\n";
	    $order++;
	}}
}

$csv->eof or $csv->error_diag ();
close $fh;

exit;
