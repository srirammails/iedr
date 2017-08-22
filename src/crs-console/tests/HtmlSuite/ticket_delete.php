<tr>
	<td>open</td>
	<td>/index.php?r=tickets/viewticket&amp;id=399784</td>
	<td></td>
</tr>
<tr>
	<td>clickAndWait</td>
	<td>link=Tickets</td>
	<td></td>
</tr>
<tr>
	<td>clickAndWait</td>
	<td>link=399784</td>
	<td></td>
</tr>
<tr>
	<td>click</td>
	<td>link=Delete Ticket</td>
	<td></td>
</tr>
<tr>
	<td>assertConfirmation</td>
	<td>Are you sure you want to delete the ticket for newexample1.ie?</td>
	<td></td>
</tr>
<tr>
	<td>assertText</td>
	<td>//div[@id='content']/div[1]/div</td>
	<td>Success : Ticket for domain newexample1.ie deleted!</td>
</tr>

