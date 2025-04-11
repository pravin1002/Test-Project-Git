package com.mastek.eElixir.product.schedule.dax;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.mastek.eElixir.common.dvo.PageDetail;
import com.mastek.eElixir.common.exception.EElixirDAXException;
import com.mastek.eElixir.common.exception.EElixirException;
import com.mastek.eElixir.common.exception.EElixirLogicalDAXException;
import com.mastek.eElixir.common.helper.SearchListResult;
import com.mastek.eElixir.common.util.Constants;
import com.mastek.eElixir.common.util.DAX;
import com.mastek.eElixir.common.util.Logger;
import com.mastek.eElixir.common.util.PageUtil;
import com.mastek.eElixir.common.util.SqlRepositoryIF;
import com.mastek.eElixir.common.util.XMLConverter;
import com.mastek.eElixir.product.schedule.dvo.Schedule;
import com.mastek.eElixir.product.schedule.dvo.ScheduleHeader;
import com.mastek.eElixir.product.schedule.ejb.entitybean.SchedulePK;
import com.mastek.eElixir.product.util.ProductConstants;
import com.mastek.eElixir.product.util.ProductDVOListMapper;
import com.mastek.eElixir.product.util.ProductSqlRepository;

/**
 * <p>Title: eElixir</p>
 * <p>Description: ScheduleDAX is a DAX class for the entity bean ScheduleEJB.
 * </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Mastek Ltd.</p>
 * @author Ajay Molasi
 * @version 1.0
 * Program specification: PRD_PS_Claim_Payment Schedule.doc
 */
public class ScheduleDAX extends DAX
{

    /**
     * No Argument COnstructor
     */
    private ScheduleDAX()
    {
    }

    /**
     * Creates payment Schedule
     * @param a_oSchedule Schedule
     * @return Schedule
     * @throws EElixirException
     */
    public Schedule createSchedule(Schedule a_oSchedule) throws EElixirException
    {

        /*
        INSERT INTO prd_sched_dtl(lscheddtlseq,strschednbr,npmtdoneafterdt,
        ninsttnbr,ntermfrom,ntermto,nperd,nperdunit,drate,dtcreated,
        strcreatedby)
        VALUES(?,?,?,?,?,?,?,?,?,?,?)
        */
        if(_oLogger.getIsLogEnabled())_oLogger.entry("ScheduleDAX",
                       "createSchedule()",
                       "Creating Header");
        Object[] oValues =
        {
            null,
            a_oSchedule.getSchedNbr(),
            a_oSchedule.getPmtDoneAfterDt(),
            a_oSchedule.getInsttNbr(),
            a_oSchedule.getTermFrom(),
            a_oSchedule.getTermTo(),
            a_oSchedule.getPeriod(),
            a_oSchedule.getPerdUnit(),
            a_oSchedule.getRate(),
            a_oSchedule.getCreated(),
            a_oSchedule.getCreatedBy(),
  //Start :RajeshP : 10-Apr-2012 : CR_306 : Changes for Periodic payments          
            a_oSchedule.getPmtmode(),
            a_oSchedule.getNoOfDays()
 //End :RajeshP : 10-Apr-2012 : CR_306 : Changes for Periodic payments           
        };
        int[] iTypes =
        {
            Types.NUMERIC,
            Types.VARCHAR,
            Types.INTEGER,
            Types.INTEGER,
            Types.INTEGER,
            Types.INTEGER,
            Types.INTEGER,
            Types.INTEGER,
            Types.DOUBLE,
            Types.DATE,
            Types.VARCHAR,
//Start :RajeshP : 10-Apr-2012 : CR_306 : Changes for Periodic payments  
            Types.NUMERIC,
            Types.NUMERIC
//End :RajeshP : 10-Apr-2012 : CR_306 : Changes for Periodic payments              
        };
        PreparedStatement pstmtInsertSchedule = null;
        String strInsertSchedule = null;
        try
        {
            a_oSchedule.setSchedDtlseq(
                    getNextSequenceNumber(ProductConstants.SCHEDULE_SEQ));
            oValues[0] = a_oSchedule.getSchedDtlseq();

            strInsertSchedule =
                    getSQLString("Insert",ProductConstants.SCHEDULE_INSERT);
            pstmtInsertSchedule =
                    getPreparedStatement(strInsertSchedule,oValues,iTypes);

            executeUpdate(pstmtInsertSchedule);
            if(_oLogger.getIsLogEnabled())_oLogger.exit("ScheduleDAX",
                           "createSchedule()",
                           "returning Header DVO ");
            return a_oSchedule;
        }
        catch(SQLException sqex)
        {
            _oLogger.fatal("ScheduleDAX",
                           "createSchedule()",
                           sqex);
            throw new EElixirDAXException(sqex,sqex.getMessage());
        }
        catch(EElixirDAXException eex)
        {
            _oLogger.fatal("ScheduleDAX",
                           "createSchedule()",
                           eex);
            throw eex;
        }
        finally
        {
            try
            {
              /*Close prepared statement if not closed*/
                if(pstmtInsertSchedule != null)
                {
                    pstmtInsertSchedule.close();
                }
            }
            catch(SQLException sqlex)
            {
                _oLogger.fatal("ScheduleDAX","createAfflnSchedHdr",sqlex);
                throw new EElixirDAXException(sqlex);
            }
        }

    }

    /**
     * Creates payment Schedule header
     * @param a_oScheduleHeader ScheduleHeader
     * @return Schedule
     * @throws EElixirException
     */
    public ScheduleHeader createScheduleHdr(ScheduleHeader a_oScheduleHeader)
                                                      throws EElixirException
    {
        if(_oLogger.getIsLogEnabled())_oLogger.entry("ScheduleDAX",
                         "createScheduleHdr()",
                         "Creating Header");

        /*
        INSERT INTO prd_sched_hdr(strschednbr,dtcreated,strcreatedby)
        VALUES(?,?,?)
        */
         //<!--Start:02/01/2008:anilg :added for schedule type ,schedule freq -->
          Object[] oValues =
          {
              a_oScheduleHeader.getSchedNbr(),
              a_oScheduleHeader.getCreated(),
              a_oScheduleHeader.getCreatedBy(),
              a_oScheduleHeader.getSchType(),
              a_oScheduleHeader.getSchFreq(),
              a_oScheduleHeader.get_nperd(),//  <!--09-07-2018 | Pawan Annuity Start here -->
              //Start:04-07-2023||Rohit#22||TRM_MBF_URF24268_FIN_LTB_PROV_349||Added strIsLTB
              a_oScheduleHeader.get_nPerdUnit(),//  <!--09-07-2018 | Pawan Annuity Start here -->
              a_oScheduleHeader.getIsLTB()
              //End:04-07-2023||Rohit#22||TRM_MBF_URF24268_FIN_LTB_PROV_349||Added strIsLTB
              
              
          };
          int[] iTypes =
          {
              Types.VARCHAR,
              Types.DATE,
              Types.VARCHAR,
              Types.INTEGER,
              Types.INTEGER,
              Types.INTEGER,//  <!--09-07-2018 | Pawan Annuity Start here -->
              //Start:04-07-2023||Rohit#22||TRM_MBF_URF24268_FIN_LTB_PROV_349||Added strIsLTB
              Types.INTEGER,//  <!--09-07-2018 | Pawan Annuity Start here -->
              Types.INTEGER
              //End:04-07-2023||Rohit#22||TRM_MBF_URF24268_FIN_LTB_PROV_349||Added strIsLTB
          };
          PreparedStatement pstmtInsertScheduleHdr = null;
          String strInsertScheduleHdr = null;
          try
          {
              strInsertScheduleHdr =
                      getSQLString("Insert",ProductConstants.SCHEDULE_HDR_INSERT);
              pstmtInsertScheduleHdr =
                      getPreparedStatement(strInsertScheduleHdr,oValues,iTypes);
              executeUpdate(pstmtInsertScheduleHdr);
              if(_oLogger.getIsLogEnabled())_oLogger.exit("ScheduleDAX",
                             "createScheduleHdr()",
                             "returning Header DVO ");
              return a_oScheduleHeader;
          }
          catch(SQLException sqex)
          {
              _oLogger.fatal("ScheduleDAX",
                             "createScheduleHdr()",
                             sqex);
              throw new EElixirDAXException(sqex,sqex.getMessage());
          }
          catch(EElixirDAXException eex)
          {
              _oLogger.fatal("ScheduleDAX",
                             "createScheduleHdr()",
                             eex);
              throw eex;
          }
          finally
          {
              try
              {
                /*Close prepared statement if not closed*/
                  if(pstmtInsertScheduleHdr != null)
                  {
                      pstmtInsertScheduleHdr.close();
                  }
              }
              catch(SQLException sqlex)
              {
                  _oLogger.fatal("ScheduleDAX","createScheduleHdr",sqlex);
                  throw new EElixirDAXException(sqlex);
              }
        }
    }

    /**
     * Updates payment Schedule header
     * @param a_oScheduleHeader ScheduleHeader
     * @throws EElixirException
     */
    public void updateScheduleHdr(ScheduleHeader a_oScheduleHeader)
                                                      throws EElixirException
    {
        if(_oLogger.getIsLogEnabled())_oLogger.entry("ScheduleDAX",
                         "updateScheduleHdr()",
                         "Updating Header");

        /*
        UPDATE prd_sched_hdr
        SET dtupdated = ?,strupdatedby = ?
        FROM prd_sched_hdr
        WHERE strschednbr = ?
        */

          Object[] oValues =
          {

              null,
              a_oScheduleHeader.getUpdatedBy(),
              a_oScheduleHeader.getSchType(),
              a_oScheduleHeader.getSchFreq(),
              a_oScheduleHeader.get_nperd(),//          <!--09-07-2018 | Pawan Annuity Start here --> 
              a_oScheduleHeader.get_nPerdUnit(),//          <!--09-07-2018 | Pawan Annuity Start here --> 
              //Start:04-07-2023||Rohit#22||TRM_MBF_URF24268_FIN_LTB_PROV_349||Added strIsLTB
              a_oScheduleHeader.getIsLTB(),
              //End:04-07-2023||Rohit#22||TRM_MBF_URF24268_FIN_LTB_PROV_349||Added strIsLTB
              a_oScheduleHeader.getSchedNbr()
              
          };
          int[] iTypes =
          {
              Types.DATE,
              Types.VARCHAR,
              Types.INTEGER,
              Types.INTEGER,
              Types.INTEGER,//          <!--09-07-2018 | Pawan Annuity Start here --> 
              Types.INTEGER,//          <!--09-07-2018 | Pawan Annuity Start here --> 
              //Start:04-07-2023||Rohit#22||TRM_MBF_URF24268_FIN_LTB_PROV_349||Added strIsLTB
              Types.INTEGER,
              //End:04-07-2023||Rohit#22||TRM_MBF_URF24268_FIN_LTB_PROV_349||Added strIsLTB
              Types.VARCHAR
              
          };
          PreparedStatement pstmtUpdateScheduleHdr = null;
          String strUpdateScheduleHdr = null;
          try
          {
              a_oScheduleHeader.setUpdated(getTimeStamp());
              oValues[0] = a_oScheduleHeader.getUpdated();

              if(a_oScheduleHeader.getStatus()==null ||
                 !a_oScheduleHeader.getStatus().equals(Constants.STATUS_UPDATE))
              {
                  if(_oLogger.getIsLogEnabled())_oLogger.debug("ScheduleDAX",
                             "updateScheduleHdr()",
                             "Cannot update header");
                  return;
              }

              strUpdateScheduleHdr =
                      getSQLString("Update",ProductConstants.SCHEDULE_HDR_UPDATE);
              pstmtUpdateScheduleHdr =
                      getPreparedStatement(strUpdateScheduleHdr,oValues,iTypes);
 
              executeUpdate(pstmtUpdateScheduleHdr);
              if(_oLogger.getIsLogEnabled())_oLogger.exit("ScheduleDAX",
                             "updateScheduleHdr()",
                             "returning Header DVO ");

          }
          catch(SQLException sqex)
          {
              _oLogger.fatal("ScheduleDAX",
                             "updateScheduleHdr()",
                             sqex);
              throw new EElixirDAXException(sqex,sqex.getMessage());
          }
          catch(EElixirDAXException eex)
          {
              _oLogger.fatal("ScheduleDAX",
                             "updateScheduleHdr()",
                             eex);
              throw eex;
          }
          finally
          {
              try
              {
                /*Close prepared statement if not closed*/
                  if(pstmtUpdateScheduleHdr != null)
                  {
                      pstmtUpdateScheduleHdr.close();
                  }
              }
              catch(SQLException sqlex)
              {
                  _oLogger.fatal("ScheduleDAX","updateScheduleHdr",sqlex);
                  throw new EElixirDAXException(sqlex);
              }
        }
    }

    /**
     * Updates Payment Schedule Detail.
     * @param a_oSchedule Schedule DVO
     * @throws EElixirException
     */
    public void updateSchedule(Schedule a_oSchedule,String a_strUserName)
                                                       throws EElixirException
    {
        /*
        UPDATE prd_sched_dtl
        SET strschednbr = ?,npmtdoneafterdt = ?,ninsttnbr = ?,ntermfrom = ?,
        ntermto = ?,nperd = ?,nperdunit = ?,drate = ?,
        dtupdated = ?,strupdatedby = ?
        WHERE lscheddtlseq = ?
        */

        if(_oLogger.getIsLogEnabled())_oLogger.entry("ScheduleDAX",
                       "updateSchedule()",
                       "Creating Header");
        Object[] oValues =
        {
            a_oSchedule.getSchedNbr(),
            a_oSchedule.getPmtDoneAfterDt(),
            a_oSchedule.getInsttNbr(),
            a_oSchedule.getTermFrom(),
            a_oSchedule.getTermTo(),
            a_oSchedule.getPeriod(),
            a_oSchedule.getPerdUnit(),
            a_oSchedule.getRate(),
            getTimeStamp(),
            a_strUserName,
  //Start :RajeshP : 10-Apr-2012 : CR_306 : Changes for Periodic payments
           a_oSchedule.getPmtmode(), 
           a_oSchedule.getNoOfDays(),
 //End :RajeshP : 10-Apr-2012 : CR_306 : Changes for Periodic payments            
            a_oSchedule.getSchedDtlseq()
        };
        int[] iTypes =
        {
            Types.VARCHAR,
            Types.INTEGER,
            Types.INTEGER,
            Types.INTEGER,
            Types.INTEGER,
            Types.INTEGER,
            Types.INTEGER,
            Types.DOUBLE,
            Types.DATE,
            Types.VARCHAR,
//Start :RajeshP : 10-Apr-2012 : CR_306 : Changes for Periodic payments
            Types.NUMERIC,
            Types.NUMERIC,
//End :RajeshP : 10-Apr-2012 : CR_306 : Changes for Periodic payments
            Types.NUMERIC
        };
        PreparedStatement pstmtUpdateSchedule = null;
        String strUpdateSchedule = null;
        int iRowsUpdated = 0;
        try
        {
            if(a_oSchedule.getStatus()==null ||
               !a_oSchedule.getStatus().equals(Constants.STATUS_UPDATE))
            {
                return;
            }
            
            a_oSchedule.setUpdatedBy(a_strUserName);
            strUpdateSchedule =
                    getSQLString("Update",ProductConstants.SCHEDULE_UPDATE);
            pstmtUpdateSchedule =
                    getPreparedStatement(strUpdateSchedule,oValues,iTypes);
            
            iRowsUpdated = executeUpdate(pstmtUpdateSchedule);
            if(iRowsUpdated == 0)
            {
                throw new EElixirLogicalDAXException("PRD9200");
            }
            if(_oLogger.getIsLogEnabled())_oLogger.exit("ScheduleDAX",
                           "updateSchedule()",
                           "returning Header DVO ");
        }
        catch(SQLException sqex)
        {
            _oLogger.fatal("ScheduleDAX",
                           "updateSchedule()",
                           sqex);
            throw new EElixirDAXException(sqex,sqex.getMessage());
        }
        catch(EElixirDAXException eex)
        {
            _oLogger.fatal("ScheduleDAX",
                           "updateSchedule()",
                           eex);
            throw eex;
        }
        finally
        {
            try
            {
              /*Close prepared statement if not closed*/
                if(pstmtUpdateSchedule != null)
                {
                    pstmtUpdateSchedule.close();
                }
            }
            catch(SQLException sqlex)
            {
                _oLogger.fatal("ScheduleDAX","updateSchedule",sqlex);
                throw new EElixirDAXException(sqlex);
            }
        }


    }

    /**
     * Searches for the Payment Schedule details for a gioven primary key
     * and returns the Schedule DVO.
     * @param a_pkSchedule Primary Key
     * @return Schedule DVO
     * @throws EElixirException
     */
    public Collection getScheduleDetails(SchedulePK a_pkSchedule) throws EElixirException
    {
        PreparedStatement pstmtSearchSchedule = null;
        ResultSet rsSchedule = null;
        Collection _colScheduleDetails  = null;
        if(_oLogger.getIsLogEnabled())
        	_oLogger.entry("ScheduleDAX","getScheduleDetails","Entering");

        try
        {

            String strSearchSchedule =
                    getSQLString("Select",ProductConstants.SCHEDULE_SEARCH);
           
            pstmtSearchSchedule =
                    getPreparedStatement(strSearchSchedule);
            /*
            SELECT lscheddtlseq,npmtdoneafterdt,ninsttnbr,ntermfrom,
            ntermto,nperd,nperdunit,drate,dtcreated,strcreatedby,
            dtupdated,strupdatedby
            FROM prd_sched_dtl
            WHERE strschednbr = ?
            */

            _colScheduleDetails = new ArrayList();

            pstmtSearchSchedule.
                    setString(1,a_pkSchedule.getSchedNbr());

            rsSchedule = executeQuery(pstmtSearchSchedule);
 
            while(rsSchedule.next())
            {
               
                Schedule oSchedule = new Schedule();

                oSchedule.setInsttNbr(
                        new Short(rsSchedule.getShort("ninsttnbr")));
                oSchedule.setPerd(
                        new Short(rsSchedule.getShort("nperd")));
                oSchedule.setPerdUnit(
                        new Short(rsSchedule.getShort("nperdunit")));
                oSchedule.setPmtDoneAfterDt(
                        new Short(rsSchedule.getShort("npmtdoneafterdt"))
                        );
                oSchedule.setRate(
                        new Double(rsSchedule.getDouble("drate")));
                oSchedule.setSchedNbr(a_pkSchedule.getSchedNbr());
                oSchedule.setSchedDtlseq(new Long(rsSchedule.getLong("lscheddtlseq")));
                oSchedule.setTermFrom(
                        new Short(rsSchedule.getShort("ntermfrom")));
                oSchedule.setTermTo(
                        new Short(rsSchedule.getShort("ntermto")));
                oSchedule.setCreated(
                        rsSchedule.getDate("dtcreated"));
                oSchedule.setCreatedBy(
                        rsSchedule.getString("strcreatedby"));
                oSchedule.
                        setUpdated(rsSchedule.getTimestamp("dtupdated"));
                oSchedule.
                        setUpdatedBy(rsSchedule.getString("strupdatedby"));

                //Start :RajeshP : 10-Apr-2012 : CR_306 : Changes for Periodic payments
                if(rsSchedule.getString("npmtmode")!=null)
                {
                    oSchedule.setPmtmode(new Short(rsSchedule.getShort("npmtmode")));                   
                }
                
                if(rsSchedule.getString("ndays")!=null)
                {
                    oSchedule.setNoOfDays(new Long(rsSchedule.getLong("ndays")));               
                }

                //End :RajeshP : 10-Apr-2012 : CR_306 : Changes for Periodic payments

                _colScheduleDetails.add(oSchedule);

            }
            if(_oLogger.getIsLogEnabled())_oLogger.exit("ScheduleDAX",
                          "getScheduleDetails",
                          "returning colScheduleDetails");

            return _colScheduleDetails;
        }
        catch(SQLException sqlex)
        {
            _oLogger.fatal("ScheduleDAX",
                           "getScheduleDetails",
                           sqlex.getMessage());
            throw new EElixirDAXException(sqlex);
        }
        finally
        {
            try
            {
                if(rsSchedule != null)
                {
                    rsSchedule.close();
                }

              /*Close prepared statement if not closed*/
                if(pstmtSearchSchedule != null)
                {
                    pstmtSearchSchedule.close();
                }
            }
            catch(SQLException sqlex)
            {
                _oLogger.fatal("ScheduleDAX","getScheduleDetails",sqlex);
                throw new EElixirDAXException(sqlex);
            }
        }
    }

    /**
     * Searches for the Payment Schedule details for a gioven primary key
     * and returns the Schedule DVO.
     * @param a_pkSchedule Primary Key
     * @return Schedule DVO
     * @throws EElixirException
     */
    public ScheduleHeader getScheduleHdr(SchedulePK a_pkSchedule) throws EElixirException
    {
        PreparedStatement pstmtSearchSchedule = null;
        ResultSet rsSchedule = null;
        ScheduleHeader oScheduleHeader = null;
        if(_oLogger.getIsLogEnabled())_oLogger.entry("ScheduleDAX","getScheduleHdr","Entering");

        try
        {
            String strSearchSchedule =
                    getSQLString("Select",ProductConstants.SCHEDULE_HDR_SEARCH);
            pstmtSearchSchedule =
                    getPreparedStatement(strSearchSchedule);
            /*
            SELECT dtcreated,strcreatedby,dtupdated,strupdatedby,strverifiedby,
            dtverified,strauthby,dtauth
            FROM prd_sched_hdr
            WHERE strschednbr = ?
            */


            pstmtSearchSchedule.
                    setString(1,a_pkSchedule.getSchedNbr());

            rsSchedule = executeQuery(pstmtSearchSchedule);

            if(rsSchedule.next())
            {
                oScheduleHeader = new ScheduleHeader();

                oScheduleHeader.setSchedNbr(a_pkSchedule.getSchedNbr());

                oScheduleHeader.setCreated(
                        rsSchedule.getDate("dtcreated"));
                oScheduleHeader.setCreatedBy(
                        rsSchedule.getString("strcreatedby"));
                oScheduleHeader.
                        setUpdated(rsSchedule.getTimestamp("dtupdated"));
                oScheduleHeader.
                        setUpdatedBy(rsSchedule.getString("strupdatedby"));
                oScheduleHeader.setAuth(
                        rsSchedule.getDate("dtauth"));
                oScheduleHeader.setAuthBy(
                        rsSchedule.getString("strauthby"));
                oScheduleHeader.setVerified(
                        rsSchedule.getDate("dtverified"));
                oScheduleHeader.setVerifiedBy(
                        rsSchedule.getString("strverifiedby"));
                if(rsSchedule.getObject("NSCHFREQ") != null)
                {
                	oScheduleHeader.setSchFreq(new Short(rsSchedule.getShort("NSCHFREQ")));
                }
                if(rsSchedule.getObject("NSCHTYPE") != null)
                {
                 oScheduleHeader.setSchType(new Short(rsSchedule.getShort("NSCHTYPE")));
                }
                //<!--09-07-2018 | Pawan Annuity Start here -->   
                if(rsSchedule.getObject("NPERD") != null)
                {
                	oScheduleHeader.set_nperd(new Short(rsSchedule.getShort("NPERD")));
                }
                if(rsSchedule.getObject("NPERDUNIT") != null)
                {
                 oScheduleHeader.set_nPerdUnit(new Short(rsSchedule.getShort("NPERDUNIT")));
                } 
                //<!--09-07-2018 | Pawan Annuity End here --> 

                //Start:04-07-2023||Rohit#22||TRM_MBF_URF24268_FIN_LTB_PROV_349||Added NISLTB
                if(rsSchedule.getObject("NISLTB") != null)
                {
                    oScheduleHeader.setIsLTB(new Short(rsSchedule.getShort("NISLTB")));
                    //_oLogger.debug("ScheduleDAX","getScheduleHdr()","NISLTB fetched is"+rsSchedule.getObject("NISLTB"));
                }
                //End:04-07-2023||Rohit#22||TRM_MBF_URF24268_FIN_LTB_PROV_349||Added NISLTB
            }

            if(_oLogger.getIsLogEnabled())_oLogger.exit("ScheduleDAX",
                          "getScheduleHdr",
                          "Returning DVO oScheduleHeader");

            return oScheduleHeader;
        }
        catch(SQLException sqlex)
        {
            _oLogger.fatal("ScheduleDAX",
                           "getScheduleHdr",
                           sqlex.getMessage());
            throw new EElixirDAXException(sqlex);
        }
        finally
        {
            try
            {
                if(rsSchedule != null)
                {
                    rsSchedule.close();
                }

              /*Close prepared statement if not closed*/
                if(pstmtSearchSchedule != null)
                {
                    pstmtSearchSchedule.close();
                }
            }
            catch(SQLException sqlex)
            {
                _oLogger.fatal("ScheduleDAX","getScheduleHdr",sqlex);
                throw new EElixirDAXException(sqlex);
            }
        }
    }

    /**
     * Removes Payment Schedule Header for a Sched Nbr.
     * @param a_pkSchedule Primary key class
     * @throws EElixirException
     */
    public void removeScheduleHdr(SchedulePK a_pkSchedule) throws EElixirException
    {
        PreparedStatement pstmtDeleteSchedule = null;

        int iRowsDeleted = 0;
        if(_oLogger.getIsLogEnabled())
        	_oLogger.entry("ScheduleDAX","removeSchedule","Entering");

        try
        {

            String strDeleteSchedule =
                    getSQLString("Delete",ProductConstants.SCHEDULE_HDR_DELETE);
            pstmtDeleteSchedule =
                    getPreparedStatement(strDeleteSchedule);

            /*
            DELETE prd_sched_hdr
            WHERE strschednbr = ?
            */

            pstmtDeleteSchedule.
                    setString(1,a_pkSchedule.getSchedNbr());
            iRowsDeleted = executeUpdate(pstmtDeleteSchedule);
            if(iRowsDeleted==0)
            {
                throw new EElixirLogicalDAXException("PRD9201");
            }

        }
        catch(SQLException sqlex)
        {
            _oLogger.fatal("ScheduleDAX",
                           "removeScheduleHdr",
                           sqlex.getMessage());
            throw new EElixirDAXException(sqlex);
        }
        finally
        {
            try
            {
              /*Close prepared statement if not closed*/
                if(pstmtDeleteSchedule != null)
                {
                    pstmtDeleteSchedule.close();
                }
            }
            catch(SQLException sqlex)
            {
                _oLogger.fatal("ScheduleDAX","removeScheduleHdr",sqlex);
                throw new EElixirDAXException(sqlex);
            }
        }
    }

    /**
     * Removes Payment Schedule details for a Sched Nbr.
     * @param a_pkSchedule Primary key class
     * @throws EElixirException
     */
    public void removeAllScheduleDetails(SchedulePK a_pkSchedule) throws EElixirException
    {
        PreparedStatement pstmtDeleteSchedule = null;

        int iRowsDeleted = 0;
        if(_oLogger.getIsLogEnabled())
        	_oLogger.entry("ScheduleDAX","removeAllScheduleDetails","Entering");

        try
        {

            String strDeleteSchedule =
                    getSQLString("Delete",ProductConstants.SCHEDULE_ALL_DTL_DELETE);
            pstmtDeleteSchedule =
                    getPreparedStatement(strDeleteSchedule);

            /*
            DELETE prd_sched_dtl
            WHERE strschednbr = ?
            */

            pstmtDeleteSchedule.
                    setString(1,a_pkSchedule.getSchedNbr());
            iRowsDeleted = executeUpdate(pstmtDeleteSchedule);
            if(iRowsDeleted==0)
            {
                throw new EElixirLogicalDAXException("PRD9201");
            }

        }
        catch(SQLException sqlex)
        {
            _oLogger.fatal("ScheduleDAX",
                           "removeAllScheduleDetails",
                           sqlex.getMessage());
            throw new EElixirDAXException(sqlex);
        }
        finally
        {
            try
            {
              /*Close prepared statement if not closed*/
                if(pstmtDeleteSchedule != null)
                {
                    pstmtDeleteSchedule.close();
                }
            }
            catch(SQLException sqlex)
            {
                _oLogger.fatal("ScheduleDAX","removeAllScheduleDetails",sqlex);
                throw new EElixirDAXException(sqlex);
            }
        }
    }

    /**
     * Removes Payment Schedule details for a Sched Nbr.
     * @param a_oSchedule Schedule
     * @throws EElixirException
     */
    public void removeScheduleDetail(Schedule a_oSchedule) throws EElixirException
    {
        PreparedStatement pstmtDeleteSchedule = null;

        int iRowsDeleted = 0;
        if(_oLogger.getIsLogEnabled())
        	_oLogger.entry("ScheduleDAX","removeScheduleDetail","Entering");

        try
        {
            String strDeleteSchedule =
                    getSQLString("Delete",ProductConstants.SCHEDULE_DELETE);
            pstmtDeleteSchedule =
                    getPreparedStatement(strDeleteSchedule);

            /*
            DELETE prd_sched_dtl
            WHERE lscheddtlseq = ?
            */

            pstmtDeleteSchedule.
                    setLong(1,a_oSchedule.getSchedDtlseq().longValue());
            iRowsDeleted = executeUpdate(pstmtDeleteSchedule);
            if(iRowsDeleted==0)
            {
                throw new EElixirLogicalDAXException("PRD9201");
            }

        }
        catch(SQLException sqlex)
        {
            _oLogger.fatal("ScheduleDAX",
                           "removeScheduleDetail",
                           sqlex.getMessage());
            throw new EElixirDAXException(sqlex);
        }
        finally
        {
            try
            {
              /*Close prepared statement if not closed*/
                if(pstmtDeleteSchedule != null)
                {
                    pstmtDeleteSchedule.close();
                }
            }
            catch(SQLException sqlex)
            {
                _oLogger.fatal("ScheduleDAX","removeScheduleDetail",sqlex);
                throw new EElixirDAXException(sqlex);
            }
        }
    }

    /**
     * Returns XML String representing all Payment Schedule yet defined.
     * @return String XMLString
     * @throws EElixirException
     */
    public SearchListResult listSearchAllScheduleNumbers(PageDetail detail) throws EElixirException
    {
        PreparedStatement pstmtSearchSchedule = null;
        ResultSet rsSchedule = null;
        //String strXMLString = null;
        SearchListResult list = null;
        if(_oLogger.getIsLogEnabled())
        	_oLogger.entry("ScheduleDAX","searchAllSchedule","Entering");

        try
        {
            String strSearchSchedule =
                    getSQLString("Select",ProductConstants.SCHEDULE_SEARCHALL);
            //         start:9/03/2009:MaybanPH1:dipikam:Pagination:Modified for Pagination 
            strSearchSchedule =PageUtil.getPaginationSQLString(strSearchSchedule, detail);
            pstmtSearchSchedule =
                    getPreparedStatement(strSearchSchedule);
            /*
            SELECT lscheddtlseq,strschednbr
            FROM prd_sched_dtl
            */
           
            rsSchedule = executeQuery(pstmtSearchSchedule);
            
            list = ProductDVOListMapper.populatePRDListScheduleDVOList(rsSchedule, null);
            //strXMLString = getXMLString(rsSearchProduct);
            
            //Start:04-07-2023||Rohit#22||TRM_MBF_URF24268_FIN_LTB_PROV_349||Added logger
            if(list != null){
                _oLogger.debug("ScheduleDAX","searchAllSchedule","Result List is:"+list.getResultList());
            }
            //End:04-07-2023||Rohit#22||TRM_MBF_URF24268_FIN_LTB_PROV_349||Added logger
            
//          end:4/03/2009:MaybanPH1:dipikam:Pagination:Added for Pagination 
            
           // strXMLString = XMLConverter.getXMLString(rsSchedule);

            if(_oLogger.getIsLogEnabled())_oLogger.exit("ScheduleDAX",
                          "searchAllSchedule",
                          "returning xml string" );
           // return strXMLString;
            return list;
        }
        catch(EElixirException eex)
        {
            _oLogger.fatal("ScheduleDAX",
                           "searchAllSchedule",
                           eex);
            throw eex;
        }
        finally
        {
            try
            {
                if(rsSchedule != null)
                {
                    rsSchedule.close();
                }

              /*Close prepared statement if not closed*/
                if(pstmtSearchSchedule != null)
                {
                    pstmtSearchSchedule.close();
                }
            }
            catch(SQLException sqlex)
            {
                _oLogger.fatal("ScheduleDAX","getSchedule",sqlex);
                throw new EElixirDAXException(sqlex);
            }
        }
    }

    /**
    * This method takes the collection of Schedule DVOs and extracts the
    * Schedule DVO and passes it to updateSchedule().
    * @param a_colSchedule Collection
    * @throws EElixirException
    */
   public void updateScheduleDetails(Collection a_colSchedule,
                                     String a_strUserName)
       throws EElixirException
 {
     if(_oLogger.getIsLogEnabled())_oLogger.entry("ScheduleDAX",
                    "updateScheduleDetails()",
                    "Entering" );
     Schedule oSchedule = null;
     Iterator itSchedule = null;

     try
     {
         itSchedule = a_colSchedule.iterator();

         /*Update each schedule detail in the collection*/
         while(itSchedule.hasNext())
         {
             oSchedule = (Schedule) itSchedule.next();
             updateSchedule(oSchedule,a_strUserName);
         }
         if(_oLogger.getIsLogEnabled())_oLogger.exit("ScheduleDAX",
                       "updateScheduleDetails()",
                       "All records Successfully updated");
     }
     catch(EElixirException eex)
     {
         _oLogger.fatal("ScheduleDAX","updateScheduleDetails()",eex);
         throw eex;
       }

  }

  /**
     * This method is used to insert the AuthBy and Auth Date details
     * into the prd_affln_ben_hdr table.
     * @param a_oScheduleHeader ScheduleHeader
     * @throws EElixirException
     */
    public void updateMastersAuthorize(ScheduleHeader a_oScheduleHeader)
                                                         throws EElixirException
    {
        if(_oLogger.getIsLogEnabled())_oLogger.entry("ScheduleDAX",
                      "updateMastersAuthorize()",
                      " Schedule number = " + a_oScheduleHeader.getSchedNbr());

        PreparedStatement pstmtInsertMastersAuthorize = null;
        String strInsertMastersAuthorize = null;
        int iInsertMastersAuthorize = 0;
        try
        {
            strInsertMastersAuthorize =
              getSQLString("Update",ProductConstants.SCHEDULE_HDR_AUTHORIZE);

           pstmtInsertMastersAuthorize =
                getPreparedStatement(strInsertMastersAuthorize);


           pstmtInsertMastersAuthorize.
              setString(1,a_oScheduleHeader.getAuthBy());

           pstmtInsertMastersAuthorize.
              setDate(2,getSystemDate());

           pstmtInsertMastersAuthorize.
              setString(3,a_oScheduleHeader.getSchedNbr());

           iInsertMastersAuthorize = executeUpdate(pstmtInsertMastersAuthorize);

           if(_oLogger.getIsLogEnabled())_oLogger.exit("ScheduleDAX",
                      "updateMastersAuthorize()",
                      " iInsertMastersAuthorize = " + iInsertMastersAuthorize);
        }
        catch(SQLException sqlex)
       {
         _oLogger.fatal("ScheduleDAX",
                           "updateMastersAuthorize()",sqlex.getMessage());
         throw new EElixirDAXException(sqlex,sqlex.getMessage());
       }
       finally
        {
          try
            {
                if(pstmtInsertMastersAuthorize != null)
                 pstmtInsertMastersAuthorize.close();
             }
           catch(SQLException sqlex)
             {
               _oLogger.fatal("ScheduleDAX",
                             "updateMastersAuthorize()",
                             "SQLException in ScheduleDAX " + sqlex);
               throw new EElixirDAXException(sqlex,sqlex.getMessage());
             }
       }
    }

    /**
     * This method is used to insert the VerifiedBy and Verified Date details
     * into the prd_affln_ben_hdr table.
     * @param a_oScheduleHeader ScheduleHeader
     * @throws EElixirException
     */
    public void updateMastersDeVerify(ScheduleHeader a_oScheduleHeader)
                                                        throws EElixirException
    {
        if(_oLogger.getIsLogEnabled())_oLogger.entry("ScheduleDAX",
                     "updateMastersDeVerify()",
                     " Schedule Number = " + a_oScheduleHeader.getSchedNbr());

       PreparedStatement pstmtInsertMastersDeVerify = null;
       String strInsertMastersDeVerify = null;
       int iInsertMastersDeVerify = 0;
       try
       {
           strInsertMastersDeVerify =
             getSQLString("Update",ProductConstants.SCHEDULE_HDR_VERIFY);

          pstmtInsertMastersDeVerify =
               getPreparedStatement(strInsertMastersDeVerify);


          pstmtInsertMastersDeVerify.setString(1,null);

          pstmtInsertMastersDeVerify.setDate(2,null);

          pstmtInsertMastersDeVerify.
             setString(3,a_oScheduleHeader.getSchedNbr());

          iInsertMastersDeVerify = executeUpdate(pstmtInsertMastersDeVerify);

          if(_oLogger.getIsLogEnabled())_oLogger.exit("ScheduleDAX",
                     "updateMastersDeVerify()",
                     " iInsertMastersDeVerify = " + iInsertMastersDeVerify);
       }
       catch(SQLException sqlex)
      {
        _oLogger.fatal("ScheduleDAX",
                          "updateMastersDeVerify()",sqlex.getMessage());
        throw new EElixirDAXException(sqlex,sqlex.getMessage());
      }
      finally
       {
         try
           {
               if(pstmtInsertMastersDeVerify != null)
                pstmtInsertMastersDeVerify.close();
            }
          catch(SQLException sqlex)
            {
              _oLogger.fatal("ScheduleDAX",
                            "updateMastersDeVerify()",
                            "SQLException in ScheduleDAX " + sqlex);
              throw new EElixirDAXException(sqlex,sqlex.getMessage());
            }
       }

    }

    /**
     * This method is used to insert the VerifiedBy and Verified Date details
     * into the prd_affln_ben_hdr table.
     * @param a_oScheduleHeader ScheduleHeader
     * @throws EElixirException
     */
    public void updateMastersVerify(ScheduleHeader a_oScheduleHeader)
                                                        throws EElixirException
    {
        if(_oLogger.getIsLogEnabled())_oLogger.entry("ScheduleDAX",
                     "updateMastersVerify()",
                     " Schedule number = " + a_oScheduleHeader.getSchedNbr());

       PreparedStatement pstmtInsertMastersVerify = null;
       String strInsertMastersVerify = null;
       int iInsertMastersVerify = 0;
       try
       {
           strInsertMastersVerify =
             getSQLString("Update",ProductConstants.SCHEDULE_HDR_VERIFY);

          pstmtInsertMastersVerify =
               getPreparedStatement(strInsertMastersVerify);


          pstmtInsertMastersVerify.
             setString(1,a_oScheduleHeader.getVerifiedBy());

          pstmtInsertMastersVerify.
             setDate(2,getSystemDate());

          pstmtInsertMastersVerify.
             setString(3,a_oScheduleHeader.getSchedNbr());

          iInsertMastersVerify = executeUpdate(pstmtInsertMastersVerify);

          if(_oLogger.getIsLogEnabled())_oLogger.exit("ScheduleDAX",
                     "updateMastersVerify()",
                     " iInsertMastersVerify = " + iInsertMastersVerify);
       }
       catch(SQLException sqlex)
      {
        _oLogger.fatal("ScheduleDAX",
                          "updateMastersVerify()",sqlex.getMessage());
        throw new EElixirDAXException(sqlex,sqlex.getMessage());
      }
      finally
       {
         try
           {
               if(pstmtInsertMastersVerify != null)
                pstmtInsertMastersVerify.close();
            }
          catch(SQLException sqlex)
            {
              _oLogger.fatal("ScheduleDAX",
                            "updateMastersVerify()",
                            "SQLException in ScheduleDAX " + sqlex);
              throw new EElixirDAXException(sqlex,sqlex.getMessage());
            }
       }

    }





    /**
     * Description getSQLString takes querytype and key and returns query
     * @return query string
     * @param a_strSQLType SQL Type i.e Select , Insert , Delete , Update
     * @param a_strKey String
     * @return String
     * @throws EElixirException
     */
    private String getSQLString(String a_strSQLType,String a_strKey)
                                                        throws EElixirException
    {
      if(_oLogger.getIsLogEnabled())_oLogger.entry("ScheduleDAX","getSQLString","::");
      SqlRepositoryIF sqlRFIF = null;
      String strSql = "";
      sqlRFIF = ProductSqlRepository.getSqlRepository();
      strSql=sqlRFIF.getSQLString(a_strKey,a_strSQLType);
      if(_oLogger.getIsLogEnabled())_oLogger.exit("ScheduleDAX","getSQLString",strSql);
      return strSql;
    }

    /**
     * Static method for getting reference to AgeDax instance
     * @return DAX
     * @throws EElixirException
     */
    public static DAX getDAX() throws EElixirException
    {
      if(_oLogger.getIsLogEnabled())_oLogger.entry("ScheduleDAX","getDAX()","No Parameters");

      /*This is commented for avoiding concurrency problem, should not use DAX
        Singleton Instance.*/
      /*
      if(_oSchedDAXSingleton == null)
      {
        _oSchedDAXSingleton = new ScheduleDAX();
        if(_oLogger.getIsLogEnabled())_oLogger.debug("getDAX ...._oSchedDAXSingleton"+_oSchedDAXSingleton);
      }
      if(_oLogger.getIsLogEnabled())_oLogger.exit("ScheduleDAX",
                   "getDAX()","Returning DAX instance " + _oSchedDAXSingleton);
      return _oSchedDAXSingleton;*/

      return new ScheduleDAX();
    }
    /**
     * Memeber Variables
     */
    private static Logger _oLogger = Logger.getInstance(Constants.PRODUCT_LOG);
   // private static ScheduleDAX _oSchedDAXSingleton;

}