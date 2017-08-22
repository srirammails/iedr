Select Order_ID, Domain_Name, Invoice_ID, Total_Net_Amount, Total_Vat_Amount, Total_Cost  from Transaction t, Reservation r where t.ID=r.Transaction_ID and Invoice_ID='4'
