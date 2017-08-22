create or replace view vjm_acc_nic_role_dom AS 
    select a.A_Number AS A_Number,n.Nic_Handle AS Nic_Handle,n.NH_Name AS NH_Name,c.Type AS Type,d.D_Reg_Dt AS D_Reg_Dt,d.D_Name AS D_Name
    from (((Account a join NicHandle n) join Contact c) join Domain d)
    where ((a.A_Number = n.A_Number)
        and (d.A_Number = a.A_Number)
        and (c.Contact_NH = n.Nic_Handle)
        and (c.D_Name = d.D_Name));

create or replace view vjm_dom_acc_role_nic AS 
    select d.D_Name AS D_Name,d.A_Number AS A_Number,ca.Contact_NH AS Nic_A,cb.Contact_NH AS Nic_B,cc.Contact_NH AS Nic_C,ct.Contact_NH AS Nic_T
    from ((((Domain d
        left join Contact ca on(((ca.Type = _latin1'A') and (d.D_Name = ca.D_Name))))
        left join Contact cb on(((cb.Type = _latin1'B') and (d.D_Name = cb.D_Name))))
        left join Contact cc on(((cc.Type = _latin1'C') and (d.D_Name = cc.D_Name))))
        left join Contact ct on(((ct.Type = _latin1'T') and (d.D_Name = ct.D_Name))))
    where (d.A_Number > 1);

create or replace view vjm_dom_nic_acc AS 
    select d.D_Name AS D_Name,d.D_Reg_Dt AS D_Reg_Dt,d.D_Holder AS D_Holder,a.A_Number AS A_Number,n.Nic_Handle AS Nic_Handle,c.Type AS Type
    from (((Domain d join Account a) join NicHandle n) join Contact c)
    where ((d.A_Number = a.A_Number)
        and (n.A_Number = a.A_Number)
        and (c.Contact_NH = n.Nic_Handle)
        and (c.D_Name = d.D_Name));

create or replace view vjm_dom_role_nic_acc AS 
    select d.D_Name AS D_Name,c.Type AS Type,n.Nic_Handle AS Nic_Handle,n.A_Number AS A_Number
    from ((Domain d join Contact c) join NicHandle n)
    where ((d.D_Name = c.D_Name)
    and (c.Contact_NH = n.Nic_Handle));


INSERT INTO user VALUES ('%','root',password('rootpass'),'Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','','','','',0,0,0,0);
INSERT INTO user VALUES ('%','crsuser',password('crspassword'),'Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','','','','',0,0,0,0);

INSERT INTO user VALUES ('127.0.0.1','root',password('rootpass'),'Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','','','','',0,0,0,0);
INSERT INTO user VALUES ('127.0.0.1','crsuser',password('crspassword'),'Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','','','','',0,0,0,0);

INSERT INTO user VALUES ('localhost','root',password('rootpass'),'Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','','','','',0,0,0,0);
INSERT INTO user VALUES ('localhost','crsuser',password('crspassword'),'Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','','','','',0,0,0,0);

