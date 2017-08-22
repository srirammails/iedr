Alter Table BillStatus
ADD( DetailedDescription tinytext,
     Colour char(12));

UPDATE billstatus SET DetailedDescription='Yes Billable',  Colour='blue' WHERE Status='0'
UPDATE billstatus SET DetailedDescription='Non-Charitable Non-Billable',  Colour='black' WHERE Status='1'

