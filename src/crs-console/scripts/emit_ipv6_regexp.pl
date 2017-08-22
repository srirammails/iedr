#!/usr/bin/perl
#
# $HeadURL: https://svn.spodhuis.org/ksvn/spodhuis-tools/bin/emit_ipv6_regexp $
# $Id: emit_ipv6_regexp 304 2010-01-03 02:43:26Z pdp@SPODHUIS.ORG $
#
use warnings;
use strict;

my $IPV4_OCTET = qr/(?:25[0-5]|(?:[1-9]|1[0-9]|2[0-4])?[0-9])/;
my $IPV4_REGEXP = qr/(?:(?:${IPV4_OCTET}\.){3}${IPV4_OCTET})/o;
# Allow a.b.c.d/n a.b.c/n a.b/n a/n but not a.b.c./n (no trailing dot):
my $IPV4_NETBLOCK_REGEXP = qr{(?: (?:
        $IPV4_OCTET (?: \. $IPV4_OCTET){0,3}
        ) / (?:[1-9]|[12][0-9]|3[0-2])
        )}ox;
# {{{1IPv6RegexpCommentary
# RFC 3986 states:
#       IPv6address =                            6( h16 ":" ) ls32
#                   /                       "::" 5( h16 ":" ) ls32
#                   / [               h16 ] "::" 4( h16 ":" ) ls32
#                   / [ *1( h16 ":" ) h16 ] "::" 3( h16 ":" ) ls32
#                   / [ *2( h16 ":" ) h16 ] "::" 2( h16 ":" ) ls32
#                   / [ *3( h16 ":" ) h16 ] "::"    h16 ":"   ls32
#                   / [ *4( h16 ":" ) h16 ] "::"              ls32
#                   / [ *5( h16 ":" ) h16 ] "::"              h16
#                   / [ *6( h16 ":" ) h16 ] "::"
# 
#       ls32        = ( h16 ":" h16 ) / IPv4address
#                   ; least-significant 32 bits of address
# 
#       h16         = 1*4HEXDIG
#                   ; 16 bits of address represented in hexadecimal
#
# Note that we need to allow:
#   1:2:3:4:5:6:7:8   -- 7 colons
#   ::2:3:4:5:6:7:8   -- 8 colons, skips leading 0
#   1::3:4:5:6:7:8    -- 7 colons again; or fewer
#   1:2:3:4:5:6::8    -- 7 or fewer
#   1:2:3:4:5:6:7::   -- 8 colons, skips trailing 0
# so there can be 8 colons only if two are doubled and are an affix.
# Otherwise there's always 7 colons at most.
#
# RFC 4291: IPv6 Addressing Architecture
#   The use of "::" indicates one or more groups of 16 bits of zeros.
#   The "::" can only appear once in an address.  The "::" can also be
#   used to compress leading or trailing zeros in an address.
# That's "1 or more", not "2 or more", so in effect when it's an affix
# there's a degenerate case where a colon (:) just replaces a zero (0).
#
# Bugs encountered after original writing:
#  * Was missing two /o optimisations
#  * Had extra | at end of the last line, permitting empty string to match
#
# }}}1IPv6RegexpCommentary
        my $IPV6_H16 = qr/(?:[0-9a-fA-F]{1,4})/;
        my $IPV6_LS32 = qr/(?:(?:${IPV6_H16}:${IPV6_H16})|${IPV4_REGEXP})/o;
        my $IPV6_REGEXP = qr/(?:
  (?:(?:                                             (?:${IPV6_H16}:){6} )${IPV6_LS32}) |
  (?:(?:                                          :: (?:${IPV6_H16}:){5} )${IPV6_LS32}) |
  (?:(?: (?:                       ${IPV6_H16} )? :: (?:${IPV6_H16}:){4} )${IPV6_LS32}) |
  (?:(?: (?: (?:${IPV6_H16}:){0,1} ${IPV6_H16} )? :: (?:${IPV6_H16}:){3} )${IPV6_LS32}) |
  (?:(?: (?: (?:${IPV6_H16}:){0,2} ${IPV6_H16} )? :: (?:${IPV6_H16}:){2} )${IPV6_LS32}) |
  (?:(?: (?: (?:${IPV6_H16}:){0,3} ${IPV6_H16} )? ::    ${IPV6_H16}:     )${IPV6_LS32}) |
  (?:(?: (?: (?:${IPV6_H16}:){0,4} ${IPV6_H16} )? ::                     )${IPV6_LS32}) |
  (?:(?: (?: (?:${IPV6_H16}:){0,5} ${IPV6_H16} )? ::                     )${IPV6_H16} ) |
  (?:(?: (?: (?:${IPV6_H16}:){0,6} ${IPV6_H16} )? ::                     )            )
  )/ox;

( my $foo = $IPV6_REGEXP ) =~ s/\s+//g;
$foo =~ s/\Q(?x-ism:\E/(?:/g;
$foo =~ s/\Q(?-xism:\E/(?:/g;

sub run_tests;

if (defined $ARGV[0]) {
	if ($ARGV[0] eq '-s') {
		# No quotes in regex to worry about
		print "ipv6_regex='${foo}'\n";
	} elsif ($ARGV[0] eq 'test') {
		run_tests;
	}
} else {
	print "$foo\n";
}
exit 0;

sub run_tests
{
	require Test::More;
	import Test::More qw(no_plan);
	my $anchored = qr/^${IPV6_REGEXP}\z/o;
	foreach my $should (
		'::', '::1', 'fe02::1', '::ffff:192.0.2.1',
		'2001:DB8::42',
		'2001:DB8:1234:5678:90ab:cdef:0123:4567',
		'2001:DB8:1234:5678:90ab:cdef:0123::',
		'2001:DB8:1234:5678:90ab:cdef::0123',
		'2001:DB8:1234:5678:90ab:cdef:192.0.2.1',
		'2001:DB8:1234:5678:90ab:cdef:192.0.2.1',
	) {
		like($should, $anchored, "should be IPv6: [$should]");
	}
	foreach my $nope (
		'127.0.0.1', '', ' ', '192.0.2.1',
		'2001', '2001:DB8', '2001:DB8:', '2001:DB8::42::1',
		'2001:DB8:1234:5678:90ab:cdef:g123:4567',
		'2001:DB8:1234:5678:90ab:cdef:0123:4567:89',
		'2001:DB8:1234:5678:90ab:cdef:0123',
	) {
		unlike($nope, $anchored, "should not be IPv6: [$nope]");
	}

	exit(0);
}

# vim: set foldmethod=marker :

