package com.mastek.eElixir.product.schedule.dvo;

/** 
 * <p>Title:PRDListScheduleDVO</p>
 * <p>Description:Product Schedule List DVO Class for displaying result list.</p>
 * <p>Company: Mastek </p> 
 * @version 1.0 
 * @author Dipika Mulchandani
 * @Created On : 09/03/20099
 */ 

import java.io.Serializable;
import com.mastek.eElixir.common.dvo.BaseDVO;

public class PRDListScheduleDVO extends BaseDVO implements Serializable{

	private String strSchedNbr;  //Related to Column STRSchedNbr
	private String strSchType;  //Related to Column NSchType
	private String strSchFreq;  //Related to Column NSchFreq


	public String getSchedNbr(){
		return this.strSchedNbr;
	}

	public void setSchedNbr(String a_strSchedNbr){
		this.strSchedNbr = a_strSchedNbr;
	}


	public String getSchType(){
		return this.strSchType;
	}

	public void setSchType(String a_strSchType){
		this.strSchType = a_strSchType;
	}


	public String getSchFreq(){
		return this.strSchFreq;
	}

	public void setSchFreq(String a_strSchFreq){
		this.strSchFreq = a_strSchFreq;
	}

	//Start:04-07-2023||Rohit#22||TRM_MBF_URF24268_FIN_LTB_PROV_349||Added strIsLTB
	private String strIsLTB;
	public String getIsLTB() {
		return strIsLTB;
	}
	public void setIsLTB(String strIsLTB) {
		this.strIsLTB = strIsLTB;
	}
	//End:04-07-2023||Rohit#22||TRM_MBF_URF24268_FIN_LTB_PROV_349||Added strIsLTB

       public String toString(){
		StringBuffer sbToString = new StringBuffer();
		sbToString.append("Values in PRDListScheduleDVO\n");
		sbToString.append("strSchedNbr : ");
		sbToString.append(strSchedNbr);
		sbToString.append("\t");
		sbToString.append("strSchType : ");
		sbToString.append(strSchType);
		sbToString.append("\t");
		sbToString.append("strSchFreq : ");
		sbToString.append(strSchFreq);
		//Start:04-07-2023||Rohit#22||TRM_MBF_URF24268_FIN_LTB_PROV_349||Added strIsLTB
		sbToString.append("\t");
		sbToString.append("strIsLTB : ");
		sbToString.append(strIsLTB);
		//End:04-07-2023||Rohit#22||TRM_MBF_URF24268_FIN_LTB_PROV_349||Added strIsLTB
		return sbToString.toString();
	}





}