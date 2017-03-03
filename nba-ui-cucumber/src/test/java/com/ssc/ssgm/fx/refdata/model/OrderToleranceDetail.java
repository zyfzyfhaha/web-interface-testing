package com.ssc.ssgm.fx.refdata.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ssc.ssgm.fx.refdata.base.BaseTestUtil;

public class OrderToleranceDetail {
	protected static final Logger logger = Logger.getLogger(OrderToleranceDetail.class);
    
	private String title;
	private String ccy1;
	private String ccy2;
	private String ccy1Minimun;
	private String ccy2Minimun;
	private List<String[]> tier1;
	private List<String[]> tier2;
		
	public OrderToleranceDetail(){}
	
	public OrderToleranceDetail(String title, String ccy1, String ccy2,
			String ccy1Minimun, String ccy2Minimun, List<String[]> tier1,
			List<String[]> tier2) {
		this.title = title;
		this.ccy1 = ccy1;
		this.ccy2 = ccy2;
		this.ccy1Minimun = ccy1Minimun;
		this.ccy2Minimun = ccy2Minimun;
		this.tier1 = tier1;
		this.tier2 = tier2;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCcy1() {
		return ccy1;
	}
	public void setCcy1(String ccy1) {
		this.ccy1 = ccy1;
	}
	public String getCcy2() {
		return ccy2;
	}
	public void setCcy2(String ccy2) {
		this.ccy2 = ccy2;
	}
	public String getCcy1Minimun() {
		return ccy1Minimun;
	}
	public void setCcy1Minimun(String ccy1Minimun) {
		this.ccy1Minimun = ccy1Minimun;
	}
	public String getCcy2Minimun() {
		return ccy2Minimun;
	}
	public void setCcy2Minimun(String ccy2Minimun) {
		this.ccy2Minimun = ccy2Minimun;
	}
	public List<String[]> getTier1() {
		return tier1;
	}
	public void setTier1(List<String[]> tier1) {
		this.tier1 = tier1;
	}
	public List<String[]> getTier2() {
		return tier2;
	}
	public void setTier2(List<String[]> tier2) {
		this.tier2 = tier2;
	}
	
	public void setTier(String index, List<Map<String, String>> values){
		List<String[]> tier = new ArrayList<String[]>();
		for (int i = 0; i < values.size(); i++) {
			String[] row = new String[3];
			row[0] = values.get(i).get("Range Start");
			row[1] = values.get(i).get("Range End");
			row[2] = values.get(i).get("Factor");
			tier.add(row);
		}
		if (index.equals("CCY1")) {
			this.tier1 = tier;
		}else if (index.equals("CCY2")){
			this.tier2 = tier;
		}
	}
	
	@Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OrderToleranceDetail anotherObj = (OrderToleranceDetail) obj;

        return BaseTestUtil.isEqualValue(ccy1Minimun, anotherObj.ccy1Minimun, "CCY1_MINIMUM")
                && BaseTestUtil.isEqualValue(ccy2Minimun, anotherObj.ccy2Minimun,"CCY2_MINIMUM")
                && BaseTestUtil.isEqualValue(tier1, anotherObj.tier1,"CCY1_Tiers")
                && BaseTestUtil.isEqualValue(tier2, anotherObj.tier2,"CCY2_Tiers");
    }
}
