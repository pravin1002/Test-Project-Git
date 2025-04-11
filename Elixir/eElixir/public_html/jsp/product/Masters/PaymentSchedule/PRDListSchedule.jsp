<%--
*****************************************************************************************************
*  Module Name  :Produts
*  Description  :This screen enables the user to search for a specific Schedule detail.
*  Navigation   :On click of Schedule Nubmer link shows the Schedule Numer details 
*                for modification. On Click of New allows to define a new Schedule detail.
*  Copyright    :Copyright (c) 2002 Mastek Ltd
*  Date         :December 16, 2002
*  Author       :PRAVIN M BOGA
*  Programme Specification : PRD_PS_Claim_Payment Schedule.doc
*  PS Version   : 1.0
*  version      :(version controlling will be done by VSS) 
*****************************************************************************************************
--%>

<%-- including the workarea.jsp --%>
<%@include file="/includes/workarea.jsp" %>
<%--start:09/03/2009:MaybanPH1:dipikam:UI and Pagination --%>
<%@ include file="/includes/paging_include.jsp"%>
<%-- importing all the required java files --%>
<%@ page import="com.mastek.eElixir.product.util.ProductResult" %>
<%@ page import="com.mastek.eElixir.product.util.ProductConstants" %>

<%@ taglib uri="/WEB-INF/tld/HtmlTags.tld" prefix="EElixir" %>
<%@ taglib uri="/WEB-INF/tld/ProductTags.tld" prefix="EElixirBasic" %>

<!-- Start :dev1:mayban Ph1 : commented old code -->
<%//@ taglib uri="/WEB-INF/tld/xsl.tld" prefix="xsl" %>
<!-- End :dev1:mayban Ph1 : commented old code -->

<!-- Start :dev1:mayban Ph1 : Add for xalan-xerces issue -->
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jstl/xml" %>
<!-- End :dev1:mayban Ph1 : Add for xalan-xerces issue -->

<%
  ProductResult oProdResult = null;
  String strResult = null;
  String strXSLFile=ProductConstants.PRODUCT_XSL_FOLDER+"PRDScheduleList.xsl";
%>

<%
  oProdResult = (ProductResult) request.getAttribute("ResultObject");
%>


<HTML>

  <HEAD>

  <SCRIPT SRC="<%=EELIXIR_HOME%>/jsp/product/scripts/ProductMessages.js" LANGUAGE="JavaScript"></SCRIPT>

    
  <SCRIPT LANGUAGE="JavaScript">
  <!--
    var len;
    var BUTTON_PALLETE;
    var oForm;
			
    /* It is called on body onload. Do all initializations here.	*/		
    function pageInit()
    {
      oForm = document.frmSchedListing;
      BUTTON_PALLETE = getButtonPallete();
      BUTTON_PALLETE.workareaLoaded('ScheduleList');
      return;
    }

    /**  
      PURPOSE     : SUBMIT THE SEARCH PAGE AND GO TO THE DEFINITION DETAILS PAGE.
      CALLED FROM : ON CLICK OF ANY OF THE DISCOUNT TYPE.
      PARAMETERS  : _strSchedNbr - Schedule Number
    */
    function openDetails(_strSchedNbr)
    {
      oForm.strSchedNbr.value = _strSchedNbr;
      BUTTON_PALLETE.changeAction("ScheduleSearch","frmSchedListing");
      return;
    }

    function validateSchedDel()
    {

      if(oForm.cbSchduleChk && oForm.cbSchduleChk.length)
      {
        len = document.frmSchedListing.cbSchduleChk.length;
        if( isAnyChecked("frmSchedListing","cbSchduleChk") )
        {
          for(var i=0; i <= len; i++)
          {
            if(document.frmSchedListing.cbSchduleChk[i].checked)
            {
              if(confirm(getPRDErrorMsg(103,"Payment Schedule")))
              {
                oForm.strSchedNbr.value = oForm.cbSchduleChk[i].value;
                BUTTON_PALLETE.changeAction("ScheduleDelete","frmSchedListing");
                return;
              }
              else
              {
                return false;
              }
            }
          }
        }
        else
        {
          alert(getPRDErrorMsg(36));
          return false;
        }
      }
      else
      {
        if(oForm.cbSchduleChk)
        {
          if(oForm.cbSchduleChk.checked)
          {
            if(confirm(getPRDErrorMsg(103,"Payment Schedule")))
            {
              oForm.strSchedNbr.value = oForm.cbSchduleChk.value;
              BUTTON_PALLETE.changeAction("ScheduleDelete","frmSchedListing");
              return;
            }
            else
            {
              return false;
            }
          }
          else
          {
            alert(getPRDErrorMsg(104,"Payment Schedule"));
            return false;
          }
        }
        else
        {
          alert(getPRDErrorMsg(67,"Schedule Listing"));
          return false;
        }
      }
    }
	
  -->				
  </SCRIPT>
     
  </HEAD>
    <BODY CLASS="content">
		  <FORM NAME="frmSchedListing" METHOD="post">
	    	  <INPUT TYPE="hidden" ID="actionevent" NAME="actionevent">
	      	  <INPUT TYPE="hidden" ID="strSchedNbr" NAME="strSchedNbr">
	          <TABLE BORDER="0" CELLPADDING="0" CELLSPACING="0" WIDTH="100%">
	          	<TR> 
	            	<TD ALIGN="middle">
			            <jsp:include page="/includes/header.jsp">
		                <jsp:param name="eelixir_home" value="<%=EELIXIR_HOME%>"/>
	                    <jsp:param name="header" value="Payment Schedule Listing"/>
	            	   <jsp:param name="module" value="<%=Constants.PRODUCT%>"/>
	                   </jsp:include>
	       	       </TD>
	          </TR>
	        </Table>
	        <%@ include file="/includes/paging_content.jsp"%>
	            <DIV ID = "ResultArea" STYLE="HEIGHT:440px; OVERFLOW: AUTO; WIDTH:100%"> 
		              <TABLE ID = "results" BORDER="0" CELLSPACING="0" CELLPADDING="0" WIDTH="100%"  CLASS="datatable sortable">
		                 <TR  style="position: relative; top: expression(this.offsetParent.scrollTop-2);">
		                  <TH ts_nosort ="y" CLASS = "first" WIDTH="10%" >
		                    Delete
		                  </TH>
		                  <%-- Start:29/06/2009:rev_code_cleanup:shweta:Sorting --%>
		                  <TH  ts_type="string" CLASS = "mid sort" WIDTH="30%"  >
		                     Schedule Number
		                  </TH>
		                  <%-- End:29/06/2009:rev_code_cleanup:shweta:Sorting --%>
		                  <%--Start:11/01/2008:MaybanPH1:tanujam:<TaskID>: --%>
		                  <%--Start:04/07/2023||Rohit#22||TRM_MBF_URF24268_FIN_LTB_PROV_349||Changed WIDTH from 30% to 20%--%>
                          <TH CLASS = "mid sort" WIDTH="20%">
                          <%--End:04/07/2023||Rohit#22||TRM_MBF_URF24268_FIN_LTB_PROV_349||Changed WIDTH from 30% to 20%--%>
		                     Schedule Type
		                  </TH>
		                  <%--Start:04/07/2023||Rohit#22||TRM_MBF_URF24268_FIN_LTB_PROV_349:Changed CLASS from last sort to mid sort,WIDTH from 30% to 20%--%>
                          <TH CLASS = "mid sort" WIDTH="20%" >
                          <%--End:04/07/2023||Rohit#22||TRM_MBF_URF24268_FIN_LTB_PROV_349:Changed CLASS from last sort to mid sort,WIDTH from 30% to 20%--%>
		                     Schedule Frequency
		                  </TH>
		                  <%--End:11/01/2008:MaybanPH1:tanujam:<TaskID>: --%>
		                  <%--Start:04/07/2023||Rohit#22||TRM_MBF_URF24268_FIN_LTB_PROV_349||Added IsLTB--%>
                          <TH CLASS = "last sort" WIDTH="20%" >
                            LTB Applicable
                          </TH>
                          <%--End:04/07/2023||Rohit#22||TRM_MBF_URF24268_FIN_LTB_PROV_349||Added IsLTB--%>
		                </TR>
		 				<%@ include file="PRDListScheduleSL_Results.jsp"%>
		
		<%--end:09/03/2009:MaybanPH1:dipikam:UI and Pagination --%>
		            </DIV>
		   
		</FORM>
    </BODY>
 </HTML>
                 
