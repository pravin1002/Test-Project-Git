package com.mastek.eElixir.product.schedule.action;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.ArrayList;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import java.sql.Timestamp;

import com.mastek.eElixir.common.util.Action;
import com.mastek.eElixir.common.util.Logger;
import com.mastek.eElixir.common.util.Constants;
import com.mastek.eElixir.common.util.EElixirUtils;
import com.mastek.eElixir.common.util.EJBHomeFactory;
import com.mastek.eElixir.common.exception.EElixirException;
import com.mastek.eElixir.common.exception.EElixirBussinessException;
import com.mastek.eElixir.product.ejb.sessionbean.ProductSLHome;
import com.mastek.eElixir.product.ejb.sessionbean.ProductSL;
import com.mastek.eElixir.product.util.ProductResult;
import com.mastek.eElixir.product.schedule.dvo.Schedule;
import com.mastek.eElixir.product.schedule.dvo.ScheduleHeader;

/**
 * <p>Title: eElixir</p>
 * <p>Description: ScheduleUpdate is an action class that updates the payment
 * schedule detail.</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Mastek Ltd.</p>
 * @author Ajay Molasi
 * @version 1.0
 * Program specification: PRD_PS_Claim_Payment Schedule.doc
 */

public class ScheduleUpdate extends Action
{

    /**
     * No Argument Constructor
     */
    public ScheduleUpdate()
    {
    }

    /**
     * This method gets the payment schedule details from the HttpServletRequest
     * object and updates them.
     * @param a_oRequest HttpServletRequest
     * @throws EElixirException
     */
    public void process(HttpServletRequest a_oRequest) throws EElixirException
    {
        if(_oLogger.getIsLogEnabled())_oLogger.entry("ScheduleUpdate","process","Entering");
        ProductSLHome homeProductSL = null;
        ProductSL remoteProductSL = null;
        EJBHomeFactory oEJBHomeFactory = null;
        ProductResult oProdResult = new ProductResult();
     //   Schedule oSchedule = null;
        Collection colScheduleDtl = new ArrayList();
        String strDVOStatus = null;
        String strDelStatus = null;
        ScheduleHeader oScheduleHeader = new ScheduleHeader();

        try
        {


            _oLogger.fatal("ScheduleUpdate",
                           "process",
                           "Creating Header Info::"
                           );

            /*Get Refence to Remote Product Session Bean*/
            oEJBHomeFactory = EJBHomeFactory.getFactory();
            homeProductSL =
                    (ProductSLHome) oEJBHomeFactory.lookUpHome(EElixirUtils.
                        getJNDIName(Constants.PRODUCT,"ProductSLHome"),
                        ProductSLHome.class,
                        getUserId(a_oRequest)
                        );
            remoteProductSL = homeProductSL.create();

            /*Creating the ScheduleHeader DVO*/
            oScheduleHeader.setSchedNbr(a_oRequest.getParameter("strSchedNbr"));
            // Start:02/01/2008:MaybanPH1:anilg:<>:	
            if(a_oRequest.getParameter("schFreq") != null &&
            		!a_oRequest.getParameter("schFreq").equals("")	)
            {
            	oScheduleHeader.setSchFreq(getShortValue(a_oRequest.getParameter("schFreq")));
            }
            if(a_oRequest.getParameter("schType") != null &&
            		!a_oRequest.getParameter("schType").equals("")	)
            {
            	oScheduleHeader.setSchType(getShortValue(a_oRequest.getParameter("schType")));
            }
            //Start:02/01/2008:MaybanPH1:anilg:<>:	            
            if(a_oRequest.getParameter("dtUpdated")!=null &&
                   !a_oRequest.getParameter("dtUpdated").equals(""))
                {
                    oScheduleHeader.setUpdated
                    (Timestamp.valueOf(a_oRequest.getParameter("dtUpdated")));
                }
//          <!--09-07-2018 | Pawan Annuity Start here -->  
            if(a_oRequest.getParameter("strNperiodNbr") != null &&
            		!a_oRequest.getParameter("strNperiodNbr").equals("")	)
            {
            	oScheduleHeader.set_nperd(getShortValue(a_oRequest.getParameter("strNperiodNbr")));
            }

            if(a_oRequest.getParameter("strNperiodUnit") != null &&
            		!a_oRequest.getParameter("strNperiodUnit").equals("")	)
            {
            	oScheduleHeader.set_nPerdUnit(getShortValue(a_oRequest.getParameter("strNperiodUnit")));
            }

            //Start:04-07-2023||Rohit#22||TRM_MBF_URF24268_FIN_LTB_PROV_349||Added strIsLTB
            if(a_oRequest.getParameter("isLTB") != null &&
                    !a_oRequest.getParameter("isLTB").equals("")	)
            {
                Short isLTB = getShortValue(a_oRequest.getParameter("isLTB"));
                oScheduleHeader.setIsLTB(isLTB);
                _oLogger.debug("ScheduleUpdate","process()","isLTB received for update is:"+isLTB);
            }
            //End:04-07-2023||Rohit#22||TRM_MBF_URF24268_FIN_LTB_PROV_349||Added strIsLTB

//          <!--09-07-2018 | Pawan Annuity End here -->  
            oScheduleHeader.setStatus(Constants.STATUS_UPDATE);
            oScheduleHeader.setUpdatedBy(getUserObject(a_oRequest).getUserId());

            for(int indx=0;;indx++)
            {
                strDVOStatus =
                         a_oRequest.getParameter("strDVOStatus" + indx);
                strDelStatus =
                        a_oRequest.getParameter("strDelStatus" + indx);
                if(a_oRequest.getParameter("nTermFrom" + indx)==null)
                {
                    break;
                }
                if(strDelStatus!=null && strDVOStatus.equals(Constants.STATUS_INSERT))
                {
                    continue;
                }
                if(strDelStatus==null && strDVOStatus.equals(""))
                {
                    continue;
                }
                colScheduleDtl.add(createSchedule(a_oRequest,indx));
            }

            oProdResult.setSchedules(colScheduleDtl);
            oProdResult.setScheduleHeader(oScheduleHeader);

            /*Create NonForfeiture details*/
            remoteProductSL.updateSchedule(oProdResult);


            /*Set the Product result in HttpServletRequest*/
            oProdResult = remoteProductSL.searchSchedule(oProdResult);
            setResult(oProdResult);

            if(_oLogger.getIsLogEnabled())_oLogger.exit("ScheduleUpdate","process","Returninig");
        }
        catch(EElixirBussinessException ebex)
        {
            _oLogger.fatal("ScheduleUpdate","process",ebex);
            try
            {
                oProdResult = remoteProductSL.searchSchedule(oProdResult);
            }
            catch(RemoteException rex)
            {
                _oLogger.fatal("ScheduleUpdate","process",rex);
                a_oRequest.setAttribute("ResultObject",oProdResult);
                throw new EElixirException("PRD1003");
            }
            a_oRequest.setAttribute("ResultObject",oProdResult);
            throw ebex;
        }
        catch(CreateException cex)
        {
            _oLogger.fatal("ScheduleUpdate","process",cex);
            a_oRequest.setAttribute("ResultObject",oProdResult);
            throw new EElixirException("PRD1013");
        }
        catch(RemoteException rex)
        {
            _oLogger.fatal("ScheduleUpdate","process",rex);
            a_oRequest.setAttribute("ResultObject",oProdResult);
            throw new EElixirException("PRD1013");
        }
     }

    public Schedule createSchedule(HttpServletRequest a_oRequest,int a_iCount)
                                                      throws EElixirException
    {
        Schedule oSchedule = new Schedule();
        String strDVOStatus = a_oRequest.getParameter("strDVOStatus"+a_iCount);
        String strDelStatus = a_oRequest.getParameter("strDelStatus" + a_iCount);
        oSchedule.setSchedNbr(a_oRequest.getParameter("strSchedNbr"));
        if(a_oRequest.getParameter("lSchedDtlSeq"+a_iCount)!=null &&
           !a_oRequest.getParameter("lSchedDtlSeq"+a_iCount).equals(""))
        {
            oSchedule.setSchedDtlseq(
                    getLongValue(a_oRequest.getParameter("lSchedDtlSeq"+a_iCount))
                    );
        }
        if(strDelStatus!=null)
        {
            oSchedule.setStatus(Constants.STATUS_DELETE);
            return oSchedule;
        }
        else
        {
            if(strDVOStatus.equalsIgnoreCase(Constants.STATUS_UPDATE))
            {
                if(a_oRequest.getParameter("dtUpdated" + a_iCount)!=null &&
                   !a_oRequest.getParameter("dtUpdated" + a_iCount).equals(""))
                {
                    oSchedule.setUpdated(Timestamp.valueOf(
                            a_oRequest.getParameter("dtUpdated" + a_iCount))
                            );
                    oSchedule.setUpdatedBy(getUserObject(a_oRequest).getUserId());
                }

                oSchedule.setStatus(Constants.STATUS_UPDATE);
            }
            else if(strDVOStatus.equalsIgnoreCase(Constants.STATUS_INSERT))
            {
                oSchedule.setCreated(EElixirUtils.getSystemDate());
                oSchedule.setCreatedBy(getUserObject(a_oRequest).getUserId());
                oSchedule.setStatus(Constants.STATUS_INSERT);
            }
        }
        oSchedule.setInsttNbr(
                getShortValue(a_oRequest.getParameter("nInsttNbr" + a_iCount)));
        oSchedule.setPerd(
                getShortValue(a_oRequest.getParameter("nPerd" + a_iCount)));
        oSchedule.setPerdUnit(
                getShortValue(a_oRequest.getParameter("nPerdUnit" + a_iCount)));
        oSchedule.setPmtDoneAfterDt(
                getShortValue(a_oRequest.getParameter("nPmtDoneAfterDt" + a_iCount)));
        oSchedule.setRate(
                getDoubleValue(a_oRequest.getParameter("dRate" + a_iCount)));
        oSchedule.setTermFrom(
                getShortValue(a_oRequest.getParameter("nTermFrom" + a_iCount)));
        oSchedule.setTermTo(
                 getShortValue(a_oRequest.getParameter("nTermTo" + a_iCount)));
        //Start :RajeshP : 10-Apr-2012 : CR_306 : Changes for Periodic payments
        if(a_oRequest.getParameter("noOfDays" + a_iCount)!=null && !a_oRequest.getParameter("noOfDays" + a_iCount).equals(""))
        {
            oSchedule.setNoOfDays(
                    getLongValue(a_oRequest.getParameter("noOfDays" + a_iCount)));
        }
        else
        {
            oSchedule.setNoOfDays(new Long("0"));
        }
        oSchedule.setPmtmode(
                getShortValue(a_oRequest.getParameter("nPmtMode" + a_iCount)));
        //End :RajeshP : 10-Apr-2012 : CR_306 : Changes for Periodic payments
        return oSchedule;
    }

    /*Member variables*/
    private Logger _oLogger = Logger.getInstance(Constants.PRODUCT_LOG);
}