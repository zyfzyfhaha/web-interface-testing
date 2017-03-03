package com.ssc.ssgm.fx.refdata.model;


import java.util.Map;
import org.apache.log4j.Logger;

/**
 * Model class for SAS reporting.
 * @author e580217
 *
 */
public class OrderTolerance {
    protected static final Logger logger = Logger.getLogger(OrderTolerance.class);
      
    private String GROUP_ID;
    private String CCY1;
    private String CCY2;
    private String PRICING_SERVICE;
    private String CCY1_MINIMUM; 
    private String CCY2_MINIMUM; 
    private String CCY1_NORMAL_SIZE; 
    private String CCY2_NORMAL_SIZE; 
    private String IM;
    private String FUND;
    private String DEADLINE;
    private String errorMsg;
    

    public String getDeadline() {
        return DEADLINE;
    }

    public void setDeadline(String deadline) {
        DEADLINE = deadline;
    }

    public String getGroupID() {
        return GROUP_ID;
    }

    public void setGroupID(String group_id) {
        GROUP_ID = group_id;
    }
    
    public String getCCY1() {
        return CCY1;
    }

    public void setCCY1(String ccy1) {
        CCY1 = ccy1;
    }

    public String getCCY2() {
        return CCY2;
    }

    public void setCCY2(String ccy2) {
        CCY2 = ccy2;
    }

    public String getPricingService() {
        return PRICING_SERVICE;
    }

    public void setPricingServiceID(String pricingService) {
        PRICING_SERVICE = pricingService;
    }

    public String getCCY1_MINIMUM() {
        return CCY1_MINIMUM;
    }

    public void setCCY1_MINIMUM(String ccy1_minimum) {
        CCY1_MINIMUM = ccy1_minimum;
    }

    public String getCCY2_MINIMUM() {
        return CCY2_MINIMUM;
    }

    public void setCCY2_MINIMUM(String ccy2_minimum) {
        CCY2_MINIMUM = ccy2_minimum;
    }
    
    public String getCCY1NORMALSIZE() {
        return CCY1_NORMAL_SIZE;
    }

    public void setCCY1NORMALSIZE(String ccy1_normal_size) {
        CCY1_NORMAL_SIZE = ccy1_normal_size;
    }

    public String getCCY2NORMALSIZE() {
        return CCY2_NORMAL_SIZE;
    }

    public void setCCY2NORMALSIZE(String ccy2_normal_size) {
        CCY2_NORMAL_SIZE = ccy2_normal_size;
    }
    
    public String getIM() {
        return IM;
    }

    public void setIM(String im) {
        IM = im;
    }
    public String getFund() {
        return FUND;
    }

    public void setFund(String fund) {
        FUND = fund;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public OrderTolerance(){}
    
    public OrderTolerance(Map<String, Object> values){
        this.PRICING_SERVICE = stringValue(values.get("PRICING_SERVICE"));
        this.CCY1 = stringValue(values.get("CCY1"));
        this.CCY2 = stringValue(values.get("CCY2"));    
        this.CCY1_MINIMUM = stringValue(values.get("CCY1_MINIMUM"));
        CCY1_MINIMUM = CCY1_MINIMUM.replace(",", "");
        this.CCY2_MINIMUM = stringValue(values.get("CCY2_MINIMUM"));
        CCY2_MINIMUM = CCY2_MINIMUM.replace(",", "");
        this.IM = stringValue(values.get("IM"));
        this.FUND = stringValue(values.get("FUND"));
        this.CCY1_NORMAL_SIZE = stringValue(values.get("CCY1_NORMAL_SIZE"));
        CCY1_NORMAL_SIZE = CCY1_NORMAL_SIZE.replace(",", "");
        this.CCY2_NORMAL_SIZE = stringValue(values.get("CCY2_NORMAL_SIZE"));
        CCY2_NORMAL_SIZE = CCY2_NORMAL_SIZE.replace(",", "");
        this.DEADLINE = stringValue(values.get("DEADLINE"));
        this.errorMsg = stringValue(values.get("__errorMsg"));
    }
    private String stringValue(Object value){
        return value == null? "" : value.toString();
    }
      
    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OrderTolerance anotherObj = (OrderTolerance) obj;

        return isEqualValue(CCY1, anotherObj.CCY1, "CCY1")
                && isEqualValue(CCY2, anotherObj.CCY2,"CCY2")
                && isEqualValue(PRICING_SERVICE, anotherObj.PRICING_SERVICE, "PRICING_SERVICE")
                && isEqualValue(CCY1_MINIMUM, anotherObj.CCY1_MINIMUM, "CCY1_MINIMUM")
                && isEqualValue(CCY2_MINIMUM, anotherObj.CCY2_MINIMUM, "CCY2_MINIMUM")
                && isEqualValue(IM, anotherObj.IM, "IM")
                && isEqualValue(FUND, anotherObj.FUND, "FUND")
                && isEqualValue(CCY1_NORMAL_SIZE, anotherObj.CCY1_NORMAL_SIZE, "CCY1_NORMAL_SIZE")
                && isEqualValue(CCY2_NORMAL_SIZE, anotherObj.CCY2_NORMAL_SIZE, "CCY2_NORMAL_SIZE")
                && isEqualValue(DEADLINE, anotherObj.DEADLINE, "DEADLINE");
    }

    private boolean isEqualValue(String value1, String value2, String name) {
        if (value1 == null || value1.isEmpty()) {
            if (value2 != null && !value2.isEmpty()) {
                logger.error("check equals: " + name + " is null");
                return false;
            }
        } else if (!value1.equals(value2)) {
            logger.error("check equals: " + name + " is " + value1 + " when another is " + value2);
            return false;
        }
        return true;
    }

	@Override
	public String toString() {
		return "OrderTolerance [GROUP_ID=" + GROUP_ID + ", CCY1=" + CCY1
				+ ", CCY2=" + CCY2 + ", PRICING_SERVICE=" + PRICING_SERVICE
				+ ", CCY1_MINIMUM=" + CCY1_MINIMUM + ", CCY2_MINIMUM="
				+ CCY2_MINIMUM + ", CCY1_NORMAL_SIZE=" + CCY1_NORMAL_SIZE
				+ ", CCY2_NORMAL_SIZE=" + CCY2_NORMAL_SIZE + ", IM=" + IM
				+ ", FUND=" + FUND + ", DEADLINE=" + DEADLINE + ", errorMsg="
				+ errorMsg + "]";
	}
    
    
}
