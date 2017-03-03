package com.ssc.ssgm.fx.refdata.dao;

import java.util.List;
import org.springframework.stereotype.Repository;


@Repository
public class RefDateDAO extends BaseDBUtil{

	public void clearBTBTraderMapping(final List<Object[]> btbTraderMappingList) {
        String deleteSql = "delete from FFODBA.ref_btb_trader_id_mapping where GMBH_WSS_ID = ? and GMBH_REUTERS_ID = ?" + 
        					" and SSBT_WSS_ID = ? and SSBT_REUTERS_ID = ? and CITY= ?";
        jdbcGFXTemplate.batchUpdate(deleteSql, btbTraderMappingList);
    }


}
