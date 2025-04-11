<%--
**Modification History
* -----------------------------------------------------------------------------------------------
* SR No. | Modified By   | Modification Date | Description
* ------------------------------------------------------------------------------------------------
* 1      | sunanthk       | 10/08/2009        |UI:  added CSS changes(removed &nbsp; to remove extended hyperlink)
--%>

<%-- 
/******************************************************************************* 
 * Title:PRDListSchedule
 * Description:Displaying result table for Product Schedule List. 
 * Company: Mastek 
 * Version 1.0 
 * Author Dipika Mulchandani
 ******************************************************************************/ 
 --%><%@ page import = "com.mastek.eElixir.common.jsp.util.JspUtil"%>
<%@ page import = "java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page import = "com.mastek.eElixir.common.helper.SearchListResult"%>
<%@ page import = "com.mastek.eElixir.product.schedule.dvo.PRDListScheduleDVO"%>
<%
if(oProdResult != null ){
	SearchListResult oSearchListResult = oProdResult.getSearchListResult();
	if(oSearchListResult != null && oSearchListResult.getResultList() != null && !oSearchListResult.getResultList().isEmpty()){
		ArrayList alResultList = (ArrayList)oSearchListResult.getResultList();
		PRDListScheduleDVO oPRDListScheduleDVO = null;
		for(int iIndex=0; iIndex<alResultList.size(); iIndex++){
			oPRDListScheduleDVO = (PRDListScheduleDVO)alResultList.get(iIndex);
%>
			         <TR>		           
			         <td WIDTH="10%" CLASS="firstselect">             
			         	<INPUT TYPE="radio"  ID="cbSchduleChk"  NAME="cbSchduleChk"  VALUE="<%=JspUtil.checkNull(oPRDListScheduleDVO.getSchedNbr())%>" ONCLICK="" />           
			         &nbsp;</td>           
			         <td WIDTH="30%" CLASS="midleft">     
			         	<A TITLE="Click here to view Schedule details" HREF="javascript:openDetails('<%=JspUtil.checkNull(oPRDListScheduleDVO.getSchedNbr())%>');">             
			         		<%=JspUtil.checkNull(oPRDListScheduleDVO.getSchedNbr())%>	     
			         	</A>  
							         
			         </td>        
			         <%--Start:04/07/2023||Rohit#22||TRM_MBF_URF24268_FIN_LTB_PROV_349||Changed WIDTH from 30% to 20%--%>
                     	<td WIDTH="20%" CLASS="midleft">
                     <%--End:04/07/2023||Rohit#22||TRM_MBF_URF24268_FIN_LTB_PROV_349||Changed WIDTH from 30% to 20%--%>
			         		<%=JspUtil.checkNull(oPRDListScheduleDVO.getSchType())%>         
			         &nbsp;</td>         
			         <%--Start:04/07/2023||Rohit#22||TRM_MBF_URF24268_FIN_LTB_PROV_349||Changed CLASS from lastleft to midleft,WIDTH from 30% to 20%--%>
                     <td WIDTH="20%" CLASS="midleft">
                     <%--End:04/07/2023||Rohit#22||TRM_MBF_URF24268_FIN_LTB_PROV_349||Changed CLASS from lastleft to midleft,WIDTH from 30% to 20%--%>
			         		<%=JspUtil.checkNull(oPRDListScheduleDVO.getSchFreq())%>         
			         &nbsp;</td>	 

			          <%--Start:04/07/2023||Rohit#22||TRM_MBF_URF24268_FIN_LTB_PROV_349||Added IsLTB--%>
                      <td WIDTH="20%" CLASS="lastleft">
                            <%=JspUtil.checkNull(oPRDListScheduleDVO.getIsLTB())%>
                      &nbsp;</td>
                      <%--End:04/07/2023||Rohit#22||TRM_MBF_URF24268_FIN_LTB_PROV_349||Added IsLTB--%>
			         	</TR>
 <%
		}
	%>
	
   </TABLE>
       <%@ include file="/includes/paging_footer.jsp"%>
	<% }
	else if (oSearchListResult != null){
%>
		  <tr><td  COLSPAN="8" class="norecords"><%=NO_RECORDS_FOUND_MESG%></TD>
	 </tr>
   </TABLE>
     

<%
	}
}
%>