<%--/**
 *--------------------
 *  Change History:
 * -----------------------------------------------------------------------------------------------
 * SR No. | Modified By   | Modification Date  | Description
 * ------------------------------------------------------------------------------------------------
 * 1      |Anil L Gupta   | 02/01/2008(dd/mm/yy|<TaskID>:added Field schType ,schFreq
 * 2      |Sonalb         | 14/07/2009         |defect resolution: removed readonly for sched type and frequency
 * 3      | Sonalb        |01/08/2009          |It-defect 4763: modified validation for duplicate record
 * 4	  |Mrunal Vaidya  | 10/12/2010		   |commented code to allow 0 as period innput value
 * 5      |Rajesh P       | 19/04/2012          | Changes made for periodic payment
 * 6      |Rohit#22       | 04/07/2023         |TRM_MBF_URF24268_FIN_LTB_PROV_349|| Added isLTB field
 **/ --%>
<%--
********************************************************************************
*  Module Name  :Produts
*  Description  :This screen captures the Product-Event-Benefit SA Definition details.
*  Navigation   :Submit - On Click the same page is refreshed 
*  Copyright    :Copyright (c) 2002 Mastek Ltd
*  Date         :December 16, 2002
*  Author       :PRAVIN M BOGA
*  Programme Specification : PRD_PS_Claim_Payment Schedule.doc
*  PS Version   : 1.0
*  version      :(version controlling will be done by VSS)
********************************************************************************
--%>
<%-- including the workarea.jsp --%>
<%@include file="/includes/workarea.jsp" %>
<!-- start:6/03/2009:MaybanPH1:dev1:UI:changed for UI and pagination -->
<%@ include file="/includes/paging_include.jsp"%>
<%-- importing all the required  --%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.mastek.eElixir.product.util.ProductConstants"%>
<%@ page import="com.mastek.eElixir.common.util.Constants"%>
<%@ page import="com.mastek.eElixir.product.util.ProductResult" %>
<%@ page import="com.mastek.eElixir.product.masters.premium.util.PremiumMasterConstants" %>
<%@ page import="com.mastek.eElixir.product.schedule.dvo.Schedule" %>
<%@ page import="com.mastek.eElixir.product.schedule.dvo.ScheduleHeader" %>

<%@ taglib uri="/WEB-INF/tld/ProductTags.tld" prefix="EElixirBasic" %>
<%@ taglib uri="/WEB-INF/tld/HtmlTags.tld" prefix="EElixir" %>


<%
  ProductResult oProdResult = null;
  Collection colSchdDtl = null;
  Iterator itSchdDtl = null;
  ScheduleHeader oSchHeader = null;
  String strSchedNoAttrib = "READONLY";
  String strScheduleNo = "";
  int cnt = 0;
//Start:07/04/2009:MaybanPH1:dipikam:UI:added for pagination and sorting dynamically
  String strClassName = "sortable";
  String strClassSort = "sort";
  boolean bIsNew = false;
// End:07/04/2009:MaybanPH1:dipikam:UI:added for pagination and sorting dynamically
%>

<%
  oProdResult = (ProductResult) request.getAttribute("ResultObject");

  if(oProdResult != null)
  {
    if(oProdResult.getScheduleHeader() != null)
    {
      oSchHeader = oProdResult.getScheduleHeader();
    }
    colSchdDtl = oProdResult.getSchedule();
    if(colSchdDtl == null)
    {
      colSchdDtl = new ArrayList();
      strSchedNoAttrib = "";
      //Start:07/04/2009:MaybanPH1:dipikam:UI:added for pagination and sorting dynamically
		strClassName = "";
		strClassSort = "";
		bIsNew = true;
	//End:07/04/2009:MaybanPH1:dipikam:UI:added for pagination and sorting dynamically
    }
    else
    {
      if(colSchdDtl.size() > 0)
      {
        strScheduleNo = ((Schedule)colSchdDtl.iterator().next()).getSchedNbr();
        if(Constants.STATUS_INSERT.equals(((Schedule)colSchdDtl.iterator().next()).getStatus()))
        {
          strSchedNoAttrib = "";
          //Start:07/04/2009:MaybanPH1:dipikam:UI:added for pagination and sorting dynamically
			strClassName = "";
			strClassSort = "";
			bIsNew = true;
		//End:07/04/2009:MaybanPH1:dipikam:UI:added for pagination and sorting dynamically
        }
      }
    }

  }
  else
  {
      colSchdDtl = new ArrayList();
      strSchedNoAttrib = "";
      //Start:07/04/2009:MaybanPH1:dipikam:UI:added for pagination and sorting dynamically
		strClassName = "";
		strClassSort = "";
		bIsNew = true;
	//End:07/04/2009:MaybanPH1:dipikam:UI:added for pagination and sorting dynamically
  }
   
%>

<%@page import="com.mastek.eElixir.common.util.EElixirUtils"%>
<%@page import="com.mastek.eElixir.common.jsp.util.JspUtil"%>
<HTML>
  <%-- Including Product Module specific messages java script file. --%>
  <SCRIPT SRC="<%=EELIXIR_HOME%>/jsp/product/scripts/ProductMessages.js" LANGUAGE="JavaScript"></SCRIPT>
  <%-- The common javascript functions --%>
  <SCRIPT SRC="<%=EELIXIR_HOME%>/jsp/product/scripts/ProductScript.js" LANGUAGE="JavaScript"></SCRIPT>

  <HEAD>


    <SCRIPT LANGUAGE='JavaScript'>
    <!--//
    var oSchdTableBody;  <%-- /*This is the table body object*/ --%>
    var totalRowsInSchd; <%-- // This will count the number of rows added --%>
    var noOfCols = 6;  <%-- // Number of columns in the row. --%>
    var BUTTON_PALLETE;
    var oForm;

    function pageInit()
    {
      BUTTON_PALLETE = getButtonPallete();
      BUTTON_PALLETE.workareaLoaded('ScheduleDetails');

      oSchdTableBody = getTableBody('results');
      totalRowsInSchd = <%=colSchdDtl.size()%>;
      oForm = document.frm_schedule;

<%if(colSchdDtl.size() == 0)
  {%> 
      addSABenefit(oSchdTableBody,"frm_schedule");
      oForm.strSchedNbr.focus();
<%}
  else		// else part added by sanju ..to set focus
  {%>
      eval("document.frm_schedule.nInsttNbr"+0).focus();	  

<%}%>
    //Start:02/01/2008:anilg:added to change status for schedule frequency for schedule type
    //<!--09-07-2018 | Pawan Annuity Start here -->
     	var schFreq    = eval("frm_schedule" +".schFreq");
       	var schType    = eval("frm_schedule" +".schType");				       	
       	var strNperiodNbr    = eval("frm_schedule" +".strNperiodNbr");
       	var strNperiodUnit    = eval("frm_schedule" +".strNperiodUnit");				       	
			       if((schType.value=="2" || schType.value=="3"))
			       { 
			           strNperiodNbr.value = "";      //<!--09-07-2018 | Pawan Annuity Start here -->
			           strNperiodUnit.value = "";
				       strNperiodNbr.disabled = true ;	//<!--09-07-2018 | Pawan Annuity Start here --> 
				       strNperiodUnit.disabled = true ;
			       }
      return;
    }
    //<!--09-07-2018 | Pawan Annuity End here -->
    <%-- /* Clears the form elements */ --%>
    function clearForm()
    {
      if(confirm(getPRDErrorMsg(101)))
      {
        document.frm_schedule.reset();
        oForm.strSchedNbr.value = oForm.strSchNbr0.value;
      }
      return;
    }     
    <%--
    /*  This is called on clicking of add button. It creates an array of cells to be added in the row. 
        It then calls the function to actually create a row with all the required parameters.
        Adds information for Minimum/Maximu Age Entry*/ --%>
    function addSABenefit(oSchdTableBody, formName)
    {
      var cellArray = new Array(); // Array of cell contents
      var cellArrayWidth = new Array();  //Array of cell width
      
      cellArray[0]= '<CENTER>'
                    +'<INPUT TYPE="checkbox" ID="strDelStatus'+totalRowsInSchd
                    +'" NAME="strDelStatus'+totalRowsInSchd+'" CLASS="firstleft">'
                    +'<INPUT TYPE="hidden" ID="strDVOStatus'+totalRowsInSchd
                    +'" NAME="strDVOStatus'+totalRowsInSchd
                    +'" VALUE="<%=Constants.STATUS_INSERT%>">'
                    +'<INPUT TYPE="hidden" ID="lSchedDtlSeq'+totalRowsInSchd
                    +'" NAME= "lSchedDtlSeq'+totalRowsInSchd+'"'
                    +' VALUE="0">'
                    +'<INPUT TYPE="hidden" ID="strSchNbr'+totalRowsInSchd
                    +'" NAME= "strSchNbr'+totalRowsInSchd+'"'
                    +' VALUE="">'
                    +'</CENTER>';
      cellArrayWidth[0]= '10%';
        
      cellArray[1]= '<CENTER>'
                    +'<INPUT TYPE="text" ID="nInsttNbr'+totalRowsInSchd+'"'
                    +' NAME="nInsttNbr'+totalRowsInSchd+'" '
                    +' SIZE="6" MAXLENGTH="3">'
                    +'</CENTER>';
      cellArrayWidth[1] = '12%';
              
      cellArray[2]= '<CENTER>'
                    +'<INPUT TYPE="text" ID="nTermFrom'+totalRowsInSchd+'"'
                    +' NAME="nTermFrom'+totalRowsInSchd+'" '
                    +' SIZE="6" MAXLENGTH="3">'
                    +'</CENTER>';
      cellArrayWidth[2] = '12%';
      
      cellArray[3]= '<CENTER>'
                    +'<INPUT TYPE="text" ID="nTermTo'+totalRowsInSchd+'"'
                    +' NAME="nTermTo'+totalRowsInSchd+'" '
                    +' SIZE="6" MAXLENGTH="3">'
                    +'</CENTER>';
      cellArrayWidth[3] = '12%';
       
      cellArray[4]= '<CENTER>'
                    +'<INPUT TYPE="text" ID="nPerd'+totalRowsInSchd
                    +'" NAME="nPerd'+totalRowsInSchd+'" '
                    +' SIZE="6" MAXLENGTH="3">'
                    +'</CENTER>';
      cellArrayWidth[4] = '12%';
     
      cellArray[5]= '<CENTER>'
                    +'<EElixir:Select type="s5505" name="nPerdUnit'+totalRowsInSchd+'" mastertype="<%=Constants.SYSTEM_DATA%>" />'
                    +'</CENTER>';
      cellArrayWidth[5] = '12%';
       
       //Start :RajeshP : 10-Apr-2012 : CR_306 : Changes for Periodic payments
      cellArray[6]= '<CENTER>'
                    +'<EElixir:Select type="s5503" name="nPmtMode'+totalRowsInSchd+'" mastertype="<%=Constants.SYSTEM_DATA%>" />'
                    +'</CENTER>';
      cellArrayWidth[6] = '12%';
     
      cellArray[7]= '<CENTER>'
                    +'<EElixir:Select type="s1029" name="nPmtDoneAfterDt'+totalRowsInSchd+'" mastertype="<%=Constants.SYSTEM_DATA%>" onChange="disableNoOfDays(this,'+totalRowsInSchd+' );return false;"/>'
                    +'</CENTER>';
      cellArrayWidth[7] = '12%';
      
      cellArray[8]= '<CENTER>'
                    +'<INPUT TYPE="text" ID="noOfDays'+totalRowsInSchd
                    +'" NAME="noOfDays'+totalRowsInSchd+'" '
                    +' SIZE="5" MAXLENGTH="3" VALUE="0" CLASS="numeric">'
                    +'</CENTER>';
      cellArrayWidth[8] = '13%';
     
      cellArray[9]= '<CENTER>'
                    +'<INPUT TYPE="text" ID="dRate'+totalRowsInSchd
                    +'" NAME="dRate'+totalRowsInSchd+'" '
                    +' SIZE="25" MAXLENGTH="20" CLASS="numeric">'
                    +'</CENTER>';
      cellArrayWidth[9] = '13%';
      	  	  
      //End :RajeshP : 10-Apr-2012 : CR_306 : Changes for Periodic payments      	  	  
      addRow(oSchdTableBody,(totalRowsInSchd-1),cellArray,cellArrayWidth);
      eval("document.frm_schedule.nInsttNbr"+totalRowsInSchd).focus();
      totalRowsInSchd++;
        // start:06/04/2009:MaybanPH1:dipikam:UI:added for pagination 
			<%if(!bIsNew){%>
			resetPagingAfterRowAdd(pager);
		<%}%>
	  // End:06/04/2009:MaybanPH1:dipikam:UI:added for pagination
    }

    <%--/** 
       PURPOSE     : CALLS NECESSARY FUNCTIONS TO VALIDATE THE ENTRIES MADE ON THE PAGE.
       CALLED FROM : PRDButton.jsp 
       RETURN TYPE : BOOLEAN - RETURN TRUE IF ALL FIELDS ARE VALID, ELSE RETURNS
                     FALSE. 
     */--%>
    function validateForm()
    {
      //START:24-09-2008:Mayban Ph1:<HP:Defectid 342>Aakritij:Schedule Frequency to be Mandatory	
      var schType    = eval("frm_schedule" +".schType");      			 			       	
			       if((schType.value=="2"))
			       { 			
				   if(isEmpty(document.frm_schedule.schFreq))
					{
	 				alert('Please select a Schedule Fequency');
          			document.frm_schedule.schFreq.focus();
          			return false;
					}	 				
			       }
	  //END:24-09-2008:Mayban Ph1:<HP:Defectid 342>Aakritij:Schedule Frequency to be Mandatory
	  //start:sunil:23/05/2012:def-6070:uncommented to allow validation on submit if verified and authorized
      if(document.frm_schedule.dtVerifyUpdated.value != "")
      {
        if(document.frm_schedule.dtAuthorizeUpdated.value != "")
        {
          alert(getPRDErrorMsg(111,"Cannot Submit Data. Payment Schedule Table"));
          return false;
        }
        else
        {
          alert(getPRDErrorMsg(109,"Cannot Submit Data. Payment Schedule Table"));
          return false;
        }
      }
      //end:sunil:23/05/2012:def-6070:uncommented to allow validation on submit if verified and authorized
     //START:29-01-2008:Mayban Ph1:Schedule Type to be Mandatory
	if(isEmpty(document.frm_schedule.schType))
	{
	 alert('Please select a Schedule Type');
          document.frm_schedule.schType.focus();
          return false;
	}
// <!--09-07-2018 | Pawan Annuity Start here -->	
	if(!isNumber(oForm.strNperiodNbr,"Period"))
         {
           oForm.strNperiodNbr.focus();
           return false;
         }
	
//<!--09-07-2018 | Pawan Annuity End here -->	
	
	 //END:29-01-2008:Mayban Ph1:Schedule Type to be Mandatory
      <%--/** check to see if any rows are added (if table is blank) */--%>
      if( cntRows(totalRowsInSchd,"frm_schedule","Payment Schedule") )
      {
        if( validateTabValues() )  
        {
          if( chkTableArray() )
          {
            if(confirm(getPRDErrorMsg(100)))
            {
              return true;
            }
            else
            {
              return false;
            }
          }
          else
            return false;
        }
        else
          return false;
      }
      else
      {
        alert(getPRDErrorMsg(99));
        return false;
      }
      
      return true;
    }

    <%--/** 
       PURPOSE     : BASIC VALIDATION - EMPTY, NUMBER, DECIMAL...
       CALLED FROM : validateForm() 
       RETURN TYPE : BOOLEAN - RETURN TRUE IF ALL FIELDS ARE VALID, ELSE RETURNS
                     FALSE. 
     */--%>
    function validateTabValues()
    {
      oForm = document.frm_schedule;

      if(!validateField(oForm.strSchedNbr,"Schedule Number",false,false))
      {
        oForm.strSchedNbr.focus();
        return false;
      }
      if(hasSpecialChar(oForm.strSchedNbr,"Schedule Number"))
      {
        return false;
      }
      var strSchNbr = oForm.strSchedNbr.value;
      if(strSchNbr.indexOf("-") != -1)
      {
        alert(getPRDErrorMsg(114,"Schedule Number","-"));
        oForm.strSchedNbr.focus();
        return false;
      }
      if(strSchNbr.indexOf("_") != -1)
      {
        alert(getPRDErrorMsg(114,"Schedule Number","_"));
        oForm.strSchedNbr.focus();
        return false;
      }

      var dRateTotal=0;

      <%--/** Looping through the details in the table for basic validations */--%>
      for(var i=0; i< totalRowsInSchd; i++)
      {
        if( !(document.getElementById("strDelStatus"+i).checked) )
        {
          var oInsttNbr = eval('document.frm_schedule.nInsttNbr'+i);
          var oFrom = eval('document.frm_schedule.nTermFrom'+i);
          var oTo = eval('document.frm_schedule.nTermTo'+i);
          var oPerd = eval('document.frm_schedule.nPerd'+i);
          var oPerdUnit = eval('document.frm_schedule.nPerdUnit'+i);
          var oPmtDoneAfterDt = eval('document.frm_schedule.nPmtDoneAfterDt'+i);
          var oRate = eval('document.frm_schedule.dRate'+i);
          //Start :RajeshP : 10-Apr-2012 : CR_306 : Changes for Periodic payments
          var oNoOfDays = eval('document.frm_schedule.noOfDays'+i);
          var oPmtMode = eval('document.frm_schedule.nPmtMode'+i);
		  //End :RajeshP : 10-Apr-2012 : CR_306 : Changes for Periodic payments
          dRateTotal = parseFloat(oRate.value) + dRateTotal;

          <%--/** Call to validateField function to make basic validations. */--%>
          if(validateField(oInsttNbr,"Installment Number",true,false))
          {
            if(parseInt(oInsttNbr.value,10) == 0)
            {
              alert(getPRDErrorMsg(81,"Installment Number","zero"));
              oInsttNbr.focus();
              return false;
            }
          }
          else
          {
            return false;
          }

          if(validateField(oFrom,"Term From",true,false))
          {
            if( !(parseInt(oFrom.value,10) >= 1) )
            {
              alert(getPRDErrorMsg(81,"Term From","zero"));
              oFrom.focus();
              return false;
            }
          }
          else
          {
            return false;
          }
          if(validateField(oTo,"Term To",true,false))
          {
            if( !(parseInt(oTo.value,10) >= 1) )
            {
              alert(getPRDErrorMsg(81,"Term To","zero"));
              oTo.focus();
              return false;
            }
          }
          else
          {
            return false;
          }

          if(parseInt(oFrom.value,10) > parseInt(oTo.value,10))
          {
            alert(getPRDErrorMsg(31,"Term"));
            oFrom.focus();
            return false;
          }

          if(validateField(oPerd,"Period",true,false))
          {
          <%--start:10/12/2010:Internal Defect:mrunal:commented code to allow 0 as period innput value--%>
            <%--if( !(parseInt(oPerd.value,10) >= 1) )
            {
              alert(getPRDErrorMsg(81,"Period","zero"));
              oPerd.focus();
              return false;
            }--%>
          <%--end:10/12/2010:Internal Defect:mrunal:commented code to allow 0 as period innput value--%>
		      }
		      else
		      {
            return false;
          }
          if(oPerdUnit.value == "<%=ProductConstants.DRP_DWN_SELECT%>")
          {
            alert(getPRDErrorMsg(3,"Period Unit"));
            oPerdUnit.focus();
            return false;
          }

          if(oPmtDoneAfterDt.value == "<%=ProductConstants.DRP_DWN_SELECT%>")
          {
            alert(getPRDErrorMsg(3,"Payment Done After Date"));
            oPmtDoneAfterDt.focus();
            return false;
          }
          //Start :RajeshP : 10-Apr-2012 : CR_306 : Changes for Periodic payments
          if(oNoOfDays && oNoOfDays.value!='')
          if(!isNumber(oNoOfDays,'Number Of Days',false))
          {
          	oNoOfDays.focus();
          	return false;
          }
		 //End :RajeshP : 10-Apr-2012 : CR_306 : Changes for Periodic payments
          if(validateField(oRate,"Rate",false,false))
          {
            if( !(parseFloat(oRate.value,10) >= 0) )
            {
              alert(getPRDErrorMsg(81,"Rate","zero"));
              oRate.focus();
              return false;
            }
          }
          else
          {
            return false;
          }

          if(!(oRate.value.indexOf('-')==-1))
          {
            alert(getPRDErrorMsg(55,"Rate"));
            return false;
          }

          if(!isDecimalWithPrecision(oRate,"Rate",20,17,false))
          {
            oRate.focus();
            return false;
          }

        
          <%--/** End of calls to validateField function. */--%>
        }
      }  <%-- /** End of For loop */ --%>
    
    
      return true;

    }  <%--/** End of function validateTabValues */ --%>

    <%--/** 
       PURPOSE     : VALIDATES ALL TERM VALUES OF ENTRY AGE VALUES PRESENT IN THE TABLE.
       CALLED FROM : validateForm.
       RETURN TYPE : BOOLEAN.
     */--%>
    function chkTableArray()
    {
      var tabValArray = getTabArray();  
      var j=0;
      for(var i=0; i<tabValArray.length; i++)
      {
        j=i+1;
        if(j<tabValArray.length)
        {         
          if(parseInt(tabValArray[i][1],10) >= parseInt(tabValArray[j][0],10))
          {
            if((parseInt(tabValArray[i][0],10) == parseInt(tabValArray[j][0],10))  
               && (parseInt(tabValArray[i][1],10) == parseInt(tabValArray[j][1],10)) )
            {
              if(parseInt(tabValArray[i][2],10) == parseInt(tabValArray[j][2],10))
              {
                alert(getPRDErrorMsg(57));
                document.getElementById("nInsttNbr" + getIndex(tabValArray[j][0])).focus();
                return false;
              }
            }
            else
            {
              alert(getPRDErrorMsg(32));
              document.getElementById("nTermFrom" + getIndex(tabValArray[j][0])).focus();
              return false;
            }
          }
          else if(parseInt(tabValArray[i][1],10) != parseInt(tabValArray[j][0],10)-1 ) 
          {       
            alert(getPRDErrorMsg(13,"Term",eval(tabValArray[i][1])+1));
            document.getElementById("nTermFrom" + getIndex(tabValArray[j][0])).focus();
            return false;
          }
          //start:01/08/2009:defect-4763 IT|sonalb:added to check for intallment number for unique record
          if(parseInt(tabValArray[i][0]) == parseInt(tabValArray[j][0]) &&
             parseInt(tabValArray[i][1]) == parseInt(tabValArray[j][1]) &&
             parseInt(tabValArray[i][3]) == parseInt(tabValArray[j][3]) &&
             parseInt(tabValArray[i][4]) == parseInt(tabValArray[j][4]) &&
             parseInt(tabValArray[i][5]) == parseInt(tabValArray[j][5]) &&
             parseInt(tabValArray[i][2]) == parseInt(tabValArray[j][2])
            )
          {
         
            alert(getPRDErrorMsg(80,"Payment Schedule Details"));
            return false;
          }
            //end:01/08/2009:defect-4763 IT|sonalb:
        }
      }
      
     return true;
       
    }  <%-- /** End of function chkTableArray. */ --%>

    <%--/** 
       PURPOSE     : POPULATES A ARRAY WITH THE FROM - TO FIELDS BASED ON IF 
                     THE CHECK BOX IS CHECKED.   
       CALLED FROM : getTabArray 
       RETURN TYPE : ARRAY.
     */--%>
    function getTabArray()
    {
      var tabArray = new Array();
      for(var i=0,j=0; j < totalRowsInSchd; j++ )
      {
        if( !(document.getElementById("strDelStatus"+j).checked) )
        {
          tabArray[i] = new Array();
          tabArray[i][0] = eval("document.frm_schedule.nTermFrom"+ j).value;
          tabArray[i][1] = eval("document.frm_schedule.nTermTo"+j).value;
          tabArray[i][2] = eval("document.frm_schedule.nInsttNbr"+j).value;
          tabArray[i][3] = eval("document.frm_schedule.nPerd"+j).value;
          tabArray[i][4] = eval("document.frm_schedule.nPerdUnit"+j).value;
          tabArray[i][5] = eval("document.frm_schedule.nPmtDoneAfterDt"+j).value;      
          i++;  
        }
        
      }
      return (sort(tabArray));            
    }

    <%--/** 
       PURPOSE     : POPULATES A ARRAY WITH THE FROM - TO FIELDS BASED ON FROM 
                     VALUES IN ASCENDING ORDER. 
       CALLED FROM : getTabArray 
       RETURN TYPE : ARRAY.
     */--%>
    function sort( termArray1)
    {
      var termArray = termArray1;

      var tempFrom;
      var tempTo;
      var tempInstt;
     
      for(var i=0; i < termArray.length -1; i++)
      {
        for(var j=i+1; j<termArray.length; j++)
        {  
          if((parseInt(termArray[i][0]) > parseInt(termArray[j][0])))
	        {		
            tempFrom = termArray[i][0];
            termArray[i][0] = termArray[j][0];
            termArray[j][0] = tempFrom;
            
            tempTo = termArray[i][1];
            termArray[i][1] = termArray[j][1];
            termArray[j][1] = tempTo;
            
            tempInstt = termArray[i][2];
            termArray[i][2] = termArray[j][2];
            termArray[j][2] = tempInstt;
          }
        }
      }
      return termArray
    }

   <%--/** 
     PURPOSE     : SINCE THE VALIDATIONS ARE DONE ON A ARRAY WHICH IS SORTED AND 
                   THIS FUNCTION RETURNS THE ACTUAL ROW NUMBER OF THE RECORD.   
     RETURN TYPE : index( Row number of the field in the table. ).
   */--%>
   function getIndex(termFromVal)
   {
     FieldValue = termFromVal;
     for(var i=0;i < totalRowsInSchd; i++)
     {
       if(parseInt(document.getElementById("nTermFrom" + i).value,10) == FieldValue)
       {
         return i;
       }
     }
     return 0;
   }   


    function actionChk()
    {
<%if(colSchdDtl.size() == 0)
  {
%>
      BUTTON_PALLETE.changeAction("ScheduleCreate","frm_schedule");
<%}
  else
  {
    if(!Constants.STATUS_INSERT.equals(((Schedule)colSchdDtl.iterator().next()).getStatus()))
    {
%>
      BUTTON_PALLETE.changeAction("ScheduleUpdate","frm_schedule");
<%  }
    else
    {
%>
      BUTTON_PALLETE.changeAction("ScheduleCreate","frm_schedule");
<%
    }
  }
%>
    }

    function verifySchDtl()
    {
      if(cntRows(totalRowsInSchd,"frm_schedule","Payment Schedule Table","strDelStatus"))
      {
        if(!verifyMaster("ScheduleVerify","frm_schedule","Payment Schedule Table"))
        {
          return false;
        }
      }
      else
      {
        alert(getPRDErrorMsg(115,"Payment Schedule Table","Verified"));
        return false;
      }
      return true;
    }

    function authorizeSchDtl()
    {
      if(!authorizeMaster("ScheduleAuthorize","frm_schedule","Payment Schedule Table"))
      {
        return false;
      }
      return true;
    }

    function deVerifySchDtl()
    {
      if(!deVerifyMaster("ScheduleDeVerify","frm_schedule","Payment Schedule Table"))
      {
        return false;
      }
      return true;
    }
   //Start:02/01/2008:anilg:added to change status for schedule frequency for schedule type 
   //Start :RajeshP : 10-Apr-2012 : CR_306 : Changes for Periodic payments
  function disableNoOfDays(obj ,iIndex)
  {
   	var oNoOfDays    = eval("frm_schedule" +".noOfDays"+iIndex);
   	
	if(obj && obj.value=="4")
	{
		oNoOfDays.disabled = false ;
	}
	else
	{
		oNoOfDays.value=0;
		oNoOfDays.disabled = true ;
		
	}
  }
  
  //End :RajeshP : 10-Apr-2012 : CR_306 : Changes for Periodic payments
  // <!--09-07-2018 | Pawan Annuity Start here
    function changeStatus(obj)
    {
    
        var strNperiodUnit    = eval("frm_schedule" +".strNperiodUnit");
       	var strNperiodNbr    = eval("frm_schedule" +".strNperiodNbr");
       					       	
			       if((obj.value=="2" || obj.value=="3"))
			       { 
			           strNperiodUnit.value = "";
			           strNperiodNbr.value = "";
				       strNperiodNbr.disabled = true ; // <!--09-07-2018 | Pawan Annuity Start here
				       strNperiodUnit.disabled = true ;
				       	
			       }
			       else
			       {
			       	    strNperiodNbr.disabled = false ; 
				       strNperiodUnit.disabled = false ;		
			       }
			       return true;
    }
    // <!--09-07-2018 | Pawan Annuity End here
    -->
    </SCRIPT>

  </HEAD>

  <BODY CLASS="content">
 

  <FORM NAME="frm_schedule" METHOD="post">
   <INPUT TYPE="hidden" ID="actionevent" NAME="actionevent">
<%
  String strUpdated = "";
  if(oSchHeader!=null && oSchHeader.getUpdated()!=null)
  {
    strUpdated = oSchHeader.getUpdated().toString();
  }
%>
   <INPUT TYPE="hidden" ID="dtUpdated" NAME="dtUpdated"
    VALUE="<%=strUpdated%>">
<%
  String strVerifyUpdated = "";
  if(oSchHeader!=null && oSchHeader.getVerified()!=null)
  {
    strVerifyUpdated = oSchHeader.getVerified().toString();
  }
%>
   <INPUT TYPE="hidden" ID="dtVerifyUpdated" NAME="dtVerifyUpdated"
    VALUE="<%=strVerifyUpdated%>">
<%
  String strAuthorizeUpdated = "";
  if(oSchHeader!=null && oSchHeader.getAuth()!=null)
  {
    strAuthorizeUpdated = oSchHeader.getAuth().toString();
  }
  String strSchTy = "";
  String strSchFr = "";  
  String strNperNbr = "";//<!--09-07-2018 | Pawan Annuity Start here -->
  String strNperUnit = "";//<!--09-07-2018 | Pawan Annuity Start here -->
  
  //Start:04-07-2023||Rohit#22||TRM_MBF_URF24268_FIN_LTB_PROV_349||Added strIsLTB
  String strIsLTB = "";
  //End:04-07-2023||Rohit#22||TRM_MBF_URF24268_FIN_LTB_PROV_349||Added strIsLTB
  
  if(oSchHeader!=null )
  {
    if(oSchHeader.getSchFreq() != null)
    {
    	strSchFr = oSchHeader.getSchFreq().toString();
    }
    if(oSchHeader.getSchType() != null)
    {
    	strSchTy = oSchHeader.getSchType().toString();
    }

    //Start:04-07-2023||Rohit#22||TRM_MBF_URF24268_FIN_LTB_PROV_349||Added strIsLTB
     if(oSchHeader.getIsLTB() != null)
     {
       strIsLTB = oSchHeader.getIsLTB().toString();
     }
    //End:04-07-2023||Rohit#22||TRM_MBF_URF24268_FIN_LTB_PROV_349||Added strIsLTB

//  <!--09-07-2018 | Pawan Annuity Start here -->    
    if(oSchHeader.get_nperd() != null)
    {
    	strNperNbr = oSchHeader.get_nperd().toString();
    }
    
    if(oSchHeader.get_nPerdUnit() != null)
    {
    	strNperUnit = oSchHeader.get_nPerdUnit().toString();
    }
  
   // if(strSchTy != null && strSchTy.equals("1"))
   // {
   // 	strSchFr ="";
   // }
//<!--09-07-2018 | Pawan Annuity End here -->    
  }
%>
   <INPUT TYPE="hidden" ID="dtAuthorizeUpdated" NAME="dtAuthorizeUpdated"
    VALUE="<%=strAuthorizeUpdated%>">
	   <TABLE BORDER="0" CELLPADDING="0" CELLSPACING="0" WIDTH="100%"  >
		   <TR>
		     <TD>
		       <jsp:include page="/includes/header.jsp">
		         <jsp:param name="eelixir_home" value="<%=EELIXIR_HOME%>"/>
		         <jsp:param name="header" value="Payment Schedule"/>
		         <jsp:param name="module" value="<%=Constants.PRODUCT%>"/>
		       </jsp:include>
		     </TD>
		   </TR>
	   </TABLE>
       <TABLE BORDER="0" CELLPADDING="0" CELLSPACING="0" ALIGN="center" WIDTH="100%" CLASS="datatable">
         <TR >
           <TD  COLSPAN="6"><BR></TD>
         </TR>
         
         <TR >
           
           <TD  WIDTH="15%">
             Schedule Number<FONT CLASS="mandatory">*</FONT>
           </TD>
           <TD  WIDTH="15%">
             <!-- bug fix for PRD_27 -->
             <INPUT TYPE="text" ID="strSchedNbr" NAME="strSchedNbr" 
              VALUE="<%=strScheduleNo%>"  <%=strSchedNoAttrib%>
              SIZE="5"  MAXLENGTH="5" CLASS = <%=strSchedNoAttrib%>>
           </TD>
			<!-- Start:02/01/2008:dev1 -->  
			<!--START:29-01-2008:Mayban Ph1:Schedule Type to be Mandatory -->        
			<TD  WIDTH="15%">Schedule Type<FONT CLASS="mandatory">*</FONT></TD>
			  <!--END:29-01-2008:Mayban Ph1:Schedule Type to be Mandatory -->      
			  <!-- start:14/07/2009:defect resolution:dev1b:removed readonly -->    
			<TD  WIDTH="15%">
				 <EElixir:Select type="s1125" name="schType" 
			           selected="<%= strSchTy%>" mastertype="<%=Constants.SYSTEM_DATA%>" 
			           onChange="changeStatus(this);return false;" /></TD>
			           <%ArrayList alStatusHidden = new ArrayList();
			           alStatusHidden.add(PremiumMasterConstants.SCHD_FREQ_HALFYEARLY);
			           alStatusHidden.add(PremiumMasterConstants.SCHD_FREQ_QUATERLY);
			           alStatusHidden.add(PremiumMasterConstants.SCHD_FREQ_SINGLE);
			          // alStatusHidden.add(PremiumMasterConstants.SCHD_FREQ_YEARLY);
			           %>
           <TD  WIDTH="15%">Schedule Frequency</TD>
           <TD  WIDTH="15%"><EElixir:Select type="s5503" name="schFreq" 
           selected="<%= strSchFr%>" mastertype="<%=String.valueOf(Constants.SYSTEM_DATA)%>" hiddenlist="<%=alStatusHidden%>"  /></TD>
             <!-- end:14/07/2009:defect resolution:dev1b:-->    
           <!--09-07-2018 | Pawan Annuity Start here --> 
           <%ArrayList alStatusHidden = new ArrayList();
			           alStatusHidden.add("52");
			           alStatusHidden.add("365");
			      	%>
           
            <TD  WIDTH="15%">Period Unit</TD>
           <TD  WIDTH="15%"><EElixir:Select type="s5505" name="strNperiodUnit" 
		           selected="<%=strNperUnit%>" mastertype="<%=Constants.SYSTEM_DATA%>" hiddenlist="<%=alStatusHidden%>"/></TD>
		           
		           
		           
           <TD  WIDTH="15%">
             Period
           </TD>
           <TD  WIDTH="15%">
             <INPUT TYPE="text" ID="strNperiodNbr" NAME="strNperiodNbr" 
              VALUE="<%=strNperNbr%>" 
              SIZE="5"  MAXLENGTH="3" CLASS = <%=strSchedNoAttrib%>>
           </TD>
           
           <!--Start:04-07-2023||Rohit#22||TRM_MBF_URF24268_FIN_LTB_PROV_349||Added strIsLTB-->
             <TD  WIDTH="15%">
               LTB Applicable
             </TD>
             <TD  WIDTH="15%">
              <EElixir:Select type="s5501" name="isLTB"
           selected="<%= strIsLTB%>" mastertype="<%=Constants.SYSTEM_DATA%>"/>
           </TD>
           <!--End:04-07-2023||Rohit#22||TRM_MBF_URF24268_FIN_LTB_PROV_349||Added strIsLTB-->
           
      <!--09-07-2018 | Pawan Annuity End here -->       
         </TR>
         <TR >
           <TD  COLSPAN="6"><BR></TD>
         </TR>
 <!-- End:02/01/2008:dev1 -->         
       </TABLE>    
    
        <TABLE BORDER="0" CELLPADDING="0" CELLSPACING="0" WIDTH="100%" >
         <TR>
              <TD  WIDTH="96%"  ALIGN="center">
          </TD>
              <TD WIDTH="4%"  ALIGN="right">
                <INPUT TYPE="button" class="button" TITLE="Click here to add row(s)" VALUE="+" 
             		 ONCLICK="addSABenefit(oSchdTableBody,'frm_schedule');" ID="bt_add" NAME="bt_add">
           </TD>
         </TR>
       </TABLE>    
     <%@ include file="/includes/paging_content.jsp"%>
    <DIV ID="div_results" STYLE="OVERFLOW:auto; HEIGHT:330px; WIDTH:100%" >  
	    <TABLE NAME="results" ID="results" BORDER="0" CELLPADDING="0" CELLSPACING="0" WIDTH="100%" CLASS="datatable <%=strClassName%>">
	       <TR ID="trow" style="position: relative; top: expression(this.offsetParent.scrollTop-2);" >
		        <TH CLASS = "first" WIDTH="10%" ts_nosort ="y">
		          Delete
		        </TD>
		        <TH  CLASS= "mid <%=strClassSort%>" WIDTH="12%" >
		          Installment No.<FONT CLASS="mandatory">*</FONT>
		        </TH>
		        <TH  CLASS= "mid <%=strClassSort%>" WIDTH="12%">
		          Term From<FONT CLASS="mandatory">*</FONT>
		        </TH>
		        <TH  CLASS= "mid <%=strClassSort%>" WIDTH="12%">
		          Term To<FONT CLASS="mandatory">*</FONT>
		        </TH>
		        <TH   CLASS= "mid <%=strClassSort%>" WIDTH="12%" >
		          Period<FONT CLASS="mandatory">*</FONT>
		        </TH>
		        <TH   CLASS= "mid <%=strClassSort%>" WIDTH="12%" >
		          Period Unit<FONT CLASS="mandatory">*</FONT>
		        </TH>
		        <!-- Start :RajeshP : 10-Apr-2012 : CR_306 : Changes for Periodic payments -->
		        <TH   CLASS= "mid <%=strClassSort%>" WIDTH="12%" >
		          Payment Mode
		        </TH>
		        <!-- End :RajeshP : 10-Apr-2012 : CR_306 : Changes for Periodic payments -->
		        <TH   CLASS= "mid <%=strClassSort%>" WIDTH="16%" >
		          Payment Done After Date<FONT CLASS="mandatory">*</FONT>
		        </TH>
		        <!-- Start :RajeshP : 10-Apr-2012 : CR_306 : Changes for Periodic payments -->
		        <TH   CLASS= "mid <%=strClassSort%>" WIDTH="12%" >
		          Days to Add
		        </TH>
		        <!-- End :RajeshP : 10-Apr-2012 : CR_306 : Changes for Periodic payments -->
		        	<%-- Start:28/07/2009:rev_code_cleanup:shweta:Sorting --%>
		        <TH   CLASS= "last <%=strClassSort%>" WIDTH="12%" ts_type="money" >
		          Rate<FONT CLASS="mandatory">*</FONT>
		        </TH>
		        	<%-- End:28/07/2009:rev_code_cleanup:shweta:Sorting --%>
	      </TR>
	  
			<%
			 itSchdDtl = colSchdDtl.iterator();
			  while(itSchdDtl.hasNext()) 
			  { 
			    Schedule oSchedDtl = (Schedule) itSchdDtl.next();
			%>
			      <INPUT TYPE="hidden" ID="strSchNbr<%=cnt%>" NAME="strSchNbr<%=cnt%>" 
			                                           VALUE="<%=oSchedDtl.getSchedNbr()%>">
	      <TR ID="tr0" >
		        <TD CLASS="firstselect" WIDTH="10%" >
		          <CENTER>
		          <INPUT TYPE="checkbox" ID="strDelStatus<%=cnt%>" 
		                                  NAME="strDelStatus<%=cnt%>" >
		          <INPUT TYPE="hidden" ID="dtUpdated<%=cnt%>" NAME="dtUpdated<%=cnt%>" 
		                                          VALUE="<%=(oSchedDtl.getUpdated()!=null)?oSchedDtl.getUpdated().toString():""%>">
		          <INPUT TYPE="hidden" ID="strDVOStatus<%=cnt%>" NAME="strDVOStatus<%=cnt%>"
		                                           VALUE="<%=Constants.STATUS_UPDATE%>">
		          <INPUT TYPE="hidden" ID="lSchedDtlSeq<%=cnt%>" NAME="lSchedDtlSeq<%=cnt%>" 
		                                        VALUE="<%=oSchedDtl.getSchedDtlseq()%>">
		          </CENTER>
		        </TD>
		        <TD WIDTH="12%" CLASS="midleft">
		          <CENTER>
		          <INPUT TYPE="text" ID="nInsttNbr<%=cnt%>" NAME="nInsttNbr<%=cnt%>" 
		            SIZE="6" MAXLENGTH="3"
		                                      VALUE="<%=oSchedDtl.getInsttNbr()%>">
		          </CENTER>
		        </TD>
		        <TD WIDTH="12%" CLASS="midleft">
		          <CENTER>
		          <INPUT TYPE="text" ID="nTermFrom<%=cnt%>" NAME="nTermFrom<%=cnt%>" 
		            SIZE="6" MAXLENGTH="3" 
		                                         VALUE="<%=oSchedDtl.getTermFrom()%>">
		          </CENTER>
		        </TD>
		        <TD WIDTH="12%" CLASS="midleft">
		          <CENTER>
		          <INPUT TYPE="text" ID="nTermTo<%=cnt%>" NAME="nTermTo<%=cnt%>" 
		              SIZE="6" MAXLENGTH="3"
		                                            VALUE="<%=oSchedDtl.getTermTo()%>">
		          </CENTER>
		        </TD>
		        <TD WIDTH="12%"CLASS="midleft" >
		          <CENTER>
		          <INPUT TYPE="text" ID="nPerd<%=cnt%>" NAME="nPerd<%=cnt%>" 
		            SIZE="6" MAXLENGTH="3"
		                                      VALUE="<%=oSchedDtl.getPeriod()%>">
		          </CENTER>
		        </TD>
		        <TD WIDTH="13%" CLASS="midleft">
		          <CENTER>
		          <%
		            String strPerdUnit = "nPerdUnit" + cnt;
		            String strPrdUnitSelected = oSchedDtl.getPerdUnit().toString();
		          %>
		          <EElixir:Select type="s5505" name="<%=strPerdUnit%>" 
		           selected="<%=strPrdUnitSelected%>" mastertype="<%=Constants.SYSTEM_DATA%>"/>
		          </CENTER>
		        </TD>
		        <!-- Start :RajeshP : 10-Apr-2012 : CR_306 : Changes for Periodic payments -->
		        <TD WIDTH="13%" CLASS="midleft">
		          <CENTER>
		          <%
		            String strPmtMode = "nPmtMode" + cnt;
		            String strPmtModeSelected = JspUtil.checkNull(oSchedDtl.getPmtmode()).toString();
		            //String strPmtModeSelected = "";
		          %>
		          <EElixir:Select type="s5503" name="<%=strPmtMode%>" 
		           selected="<%=strPmtModeSelected%>" mastertype="<%=Constants.SYSTEM_DATA%>"/>
		          </CENTER>
		        </TD> 
				 <!-- End :RajeshP : 10-Apr-2012 : CR_306 : Changes for Periodic payments -->
				 
		        <TD WIDTH="13%" CLASS="midleft">
		        <!-- Start :RajeshP : 10-Apr-2012 : CR_306 : Changes for Periodic payments -->
		          <%
		            String strPmtDoneAfterDt = "nPmtDoneAfterDt" + cnt;
		            String strPmtDoneValue = oSchedDtl.getPmtDoneAfterDt().toString();
		            String strEnableDisable="";
		            String onChange = "";
		            if(!strPmtDoneValue.equals("4"))
		            {
		                strEnableDisable= "DISABLED";
		            }
		            onChange = "disableNoOfDays(this ,"+cnt+ ");";
		          %>
		        <!-- End :RajeshP : 10-Apr-2012 : CR_306 : Changes for Periodic payments -->
		          <CENTER>
		          <!-- Start :RajeshP : 10-Apr-2012 : CR_306 : Changes for Periodic payments -->
		          <EElixir:Select type="s1029" name="<%=strPmtDoneAfterDt%>" 
		           selected="<%=strPmtDoneValue%>" mastertype="<%=Constants.SYSTEM_DATA%>" onChange="<%=onChange%>"/>
		         <!-- End :RajeshP : 10-Apr-2012 : CR_306 : Changes for Periodic payments -->
		          </CENTER>
		        </TD>
		        <!-- Start :RajeshP : 10-Apr-2012 : CR_306 : Changes for Periodic payments -->
		        <TD WIDTH="13%" CLASS="midleft">
		        	<%
		            String strNoOfDays = "0";
		        	if(oSchedDtl.getNoOfDays()!=null)
		        	{
		        	 strNoOfDays=oSchedDtl.getNoOfDays().toString();
		        	}
		          %>
		          <CENTER>
		          <INPUT CLASS ="numeric" TYPE="text" ID="<%=cnt%>" NAME="noOfDays<%=cnt%>" 
		            SIZE="5" MAXLENGTH="3"
		           VALUE="<%=strNoOfDays%>" <%=strEnableDisable%>>
		          </CENTER>
		        </TD>
		        <!-- End :RajeshP : 10-Apr-2012 : CR_306 : Changes for Periodic payments -->
		        <TD WIDTH="13%" CLASS="lastnumeric">
		          <CENTER>
		          <INPUT CLASS ="numeric" TYPE="text" ID="<%=cnt%>" NAME="dRate<%=cnt%>" 
		            SIZE="25" MAXLENGTH="20"
		           VALUE="<%=EElixirUtils.getFormattedDouble(oSchedDtl.getRate(),"#0.00#############")%>">
		          </CENTER>
		        </TD>
	      </TR>
	<% 
	    cnt++;
	    } 
	%>
	    </TABLE>
       <%@ include file="/includes/paging_footer.jsp"%>
   </DIV>
</FORM>
</BODY>
</HTML>
