DELETE FROM NBADBA.XREF_FUND_INST_HEDGE WHERE XREF_FUND_INSTRUMENT_DETAIL_ID IN (SELECT XREF_FUND_INSTRUMENT_DETAIL_ID FROM NBADBA.XREF_FUND_INSTRUMENT_DETAIL WHERE INSTRUMENT_DETAIL_ID IN (SELECT INSTRUMENT_DETAIL_ID FROM NBADBA.INSTRUMENT_DETAIL WHERE INSTRUMENT_PROFILE_ID IN (SELECT INSTRUMENT_PROFILE_ID FROM NBADBA.INSTRUMENT_PROFILE WHERE INSTRUMENT_NAME LIKE INSTRUMENT_NAME_DETAIL)));#
DELETE FROM NBADBA.XREF_FUND_INSTRUMENT_DETAIL WHERE CUST_ID IN (CUST_ID_DETAIL) OR INSTRUMENT_DETAIL_ID IN (SELECT INSTRUMENT_DETAIL_ID FROM NBADBA.INSTRUMENT_DETAIL WHERE INSTRUMENT_PROFILE_ID IN (SELECT INSTRUMENT_PROFILE_ID FROM NBADBA.INSTRUMENT_PROFILE WHERE INSTRUMENT_NAME LIKE INSTRUMENT_NAME_DETAIL));#
DELETE FROM NBADBA.INSTRUMENT_DETAIL WHERE INSTRUMENT_PROFILE_ID IN (SELECT INSTRUMENT_PROFILE_ID FROM NBADBA.INSTRUMENT_PROFILE WHERE INSTRUMENT_NAME LIKE INSTRUMENT_NAME_DETAIL);#
DELETE FROM NBADBA.INSTRUMENT_PROFILE WHERE INSTRUMENT_NAME LIKE INSTRUMENT_NAME_DETAIL;#
DELETE FROM NBADBA.FUND_HEDGE_BOOKING_CODE WHERE CUST_ID IN (CUST_ID_DETAIL) ;#
DELETE FROM NBADBA.FUND_PROFILE WHERE CUST_ID IN (CUST_ID_DETAIL)