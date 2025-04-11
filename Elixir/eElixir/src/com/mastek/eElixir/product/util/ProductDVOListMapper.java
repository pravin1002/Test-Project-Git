package com.mastek.eElixir.product.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mastek.eElixir.common.exception.EElixirException;
import com.mastek.eElixir.common.helper.SearchListResult;
//Start|CR264
import com.mastek.eElixir.commonbussobj.masters.clinic.dvo.TakafulRateDVO;
//End|CR264
import com.mastek.eElixir.product.RSAFactor.dvo.PRDListRSAFactorDVO;
import com.mastek.eElixir.product.afflictionschedule.dvo.PRDAfflnBenSchListDVO;
import com.mastek.eElixir.product.afflictionschedule.dvo.PRDAfflnTypListDVO;
import com.mastek.eElixir.product.bonusdeclaration.dvo.PRDBonusListSearchDVO;
import com.mastek.eElixir.product.costofinsurance.dvo.PRDListCOIDVO;
import com.mastek.eElixir.product.extramortalitydefn.dvo.PRDListExtraMortalityDefnDVO;
import com.mastek.eElixir.product.invlinkchgtype.dvo.PRDListInvLnkChgDVO;
import com.mastek.eElixir.product.masters.premium.dvo.PRDListLienSIDVO;
import com.mastek.eElixir.product.masters.premium.dvo.PRDPrmRatesListSearchDVO;
import com.mastek.eElixir.product.nonforfeiture.dvo.PRDNFSearchListDVO;
import com.mastek.eElixir.product.occupationextra.dvo.PRDOccupClsListDVO;
import com.mastek.eElixir.product.occupationextra.dvo.PRDOccupExtraListDVO;
import com.mastek.eElixir.product.occupationextra.dvo.PRDOccupSearchDVO;
import com.mastek.eElixir.product.productcombination.dvo.PRDRiderListSearchDVO;
import com.mastek.eElixir.product.productdefinition.dvo.PRDSearchListDVO;
import com.mastek.eElixir.product.schedule.dvo.PRDListScheduleDVO;
import com.mastek.eElixir.product.bonusdeclaration.dvo.FundListSearchDVO;//Start:30/05/2018:Ashish R: TRM_MBF_CR372_239: Code Added for Golden Retirement

/**
 * -------------------- Change History:
 * -----------------------------------------------------------------------------------------------
 * SR No.| Modified By | Modification Date | Description
 * ------------------------------------------------------------------------------------------------
 * 1 |DipikaM  | 15/05/2009 | Added Methods to fetch Conversion List Search Method
 * 2 |Shweta   |29/06/2009  | Defect-2746|IT|:shweta:Pagination
 *  3 |Ashish R   |30/05/2018  | TRM_MBF_CR372_239| Code Added for Golden Retirement
 * 4 |Rohit#22   |04/07/2023  |TRM_MBF_URF24268_FIN_LTB_PROV_349||Added strIsLTB
 * 
 */
/**
 * <p>Title: ProductDVOListMapper</p>
 * <p>Description: ProductDVOListMapper Class to populate DVO</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Mastek Ltd.</p>
 * @author Dipika M
 * @version 1.0
 */
public class ProductDVOListMapper {
	  /**
     * Default Constructor
     */
    private ProductDVOListMapper()
    {
    }
    
    /**
	 * This method populates the PRDSearchListDVO for Products using
	 * SQL ID=ProductListSearch.
	 * 
	 * @param a_oSearchListResult
	 *            The SearchListResult.
	 * @return a_rs The ResultSet used to retrieve records.
	 * @return the ResultList.
	 */
	public static SearchListResult populatePRDSearchListDVOList(ResultSet a_rsPRDSearchList, SearchListResult a_oSearchListResult) throws EElixirException{
		ArrayList alPRDSearchListList = null;
		PRDSearchListDVO oPRDSearchList = null;
		if(a_oSearchListResult == null){
			a_oSearchListResult = new SearchListResult();
		}
		try{
			if(a_rsPRDSearchList != null){
				alPRDSearchListList = new ArrayList(); 
				
				while(a_rsPRDSearchList.next()){
					oPRDSearchList = new PRDSearchListDVO();
					oPRDSearchList.setProdCd(a_rsPRDSearchList.getString("STRPRODCD"));
					oPRDSearchList.setProdName(a_rsPRDSearchList.getString("STRPRODNAME"));
					if(a_rsPRDSearchList.getObject("NPRODVER") != null){
						oPRDSearchList.setPRODVER(new Short(a_rsPRDSearchList.getShort("NPRODVER")));
					}
					oPRDSearchList.setCountryCd(a_rsPRDSearchList.getString("STRCOUNTRYCD"));
					oPRDSearchList.setStateCd(a_rsPRDSearchList.getString("STRSTATECD"));
					oPRDSearchList.setState(a_rsPRDSearchList.getString("STRSTATE"));
					oPRDSearchList.setEFFFROM(a_rsPRDSearchList.getDate("DTEFFFROM"));
					oPRDSearchList.setEffTo(a_rsPRDSearchList.getDate("DTEFFTO"));
					oPRDSearchList.setStatus(a_rsPRDSearchList.getString("strStatus"));
					
					alPRDSearchListList.add(oPRDSearchList);
				}
				
			}
		}
		catch(SQLException sqlex){
			throw new EElixirException(sqlex, "Error while populating PRDSearchListDVO");
		}
		a_oSearchListResult.setResultList(alPRDSearchListList);
		return a_oSearchListResult;
	}
	/**
	 * This method populates the PRDPrmRatesListSearchDVO for Products using
	 * SQL ID=PremiumTableTypeHdrListSearch.
	 * 
	 * @param a_oSearchListResult
	 *            The SearchListResult.
	 * @return a_rs The ResultSet used to retrieve records.
	 * @return the ResultList.
	 */
	public static SearchListResult populatePRDPrmRatesListSearchDVOList(ResultSet a_rsPRDPrmRatesListSearch, SearchListResult a_oSearchListResult) throws EElixirException{
		ArrayList alPRDPrmRatesListSearchList = null;
		PRDPrmRatesListSearchDVO oPRDPrmRatesListSearch = null;
		if(a_oSearchListResult == null){
			a_oSearchListResult = new SearchListResult();
		}
		try{
			if(a_rsPRDPrmRatesListSearch != null){
				alPRDPrmRatesListSearchList = new ArrayList();
				while(a_rsPRDPrmRatesListSearch.next()){
					oPRDPrmRatesListSearch = new PRDPrmRatesListSearchDVO();
					if(a_rsPRDPrmRatesListSearch.getObject("LPRMTBLTYPHDRSEQ") != null){
						oPRDPrmRatesListSearch.setPrmTblTypHdrSeq(new Long(a_rsPRDPrmRatesListSearch.getLong("LPRMTBLTYPHDRSEQ")));
					}
					oPRDPrmRatesListSearch.setTblCd(a_rsPRDPrmRatesListSearch.getString("STRTBLCD"));
					if(a_rsPRDPrmRatesListSearch.getObject("NBASIS") != null){
						oPRDPrmRatesListSearch.setBasis(a_rsPRDPrmRatesListSearch.getString("NBASIS"));
						//TODO 
						
					}
					if(a_rsPRDPrmRatesListSearch.getObject("IUNIT") != null){
						oPRDPrmRatesListSearch.setUnit(new Integer(a_rsPRDPrmRatesListSearch.getInt("IUNIT")));
					}
					if(a_rsPRDPrmRatesListSearch.getObject("NPMTMODE") != null){
						oPRDPrmRatesListSearch.setPmtMode(a_rsPRDPrmRatesListSearch.getString("NPMTMODE"));
					}
					alPRDPrmRatesListSearchList.add(oPRDPrmRatesListSearch);
				}
			}
		}
		catch(SQLException sqlex){
			throw new EElixirException(sqlex, "Error while populating PRDPrmRatesListSearchDVO");
		}
		a_oSearchListResult.setResultList(alPRDPrmRatesListSearchList);
		return a_oSearchListResult;
	}
	/**
	 * This method populates the PRDPrmRatesListSearchDVO for Products using
	 * SQL ID=SearchValTblHdrAll.
	 * 
	 * @param a_oSearchListResult
	 *            The SearchListResult.
	 * @return a_rs The ResultSet used to retrieve records.
	 * @return the ResultList.
	 */
	public static SearchListResult populatePRDNFSearchListDVOList(ResultSet a_rsPRDNFSearchList, SearchListResult a_oSearchListResult) throws EElixirException{
		ArrayList alPRDNFSearchListList = null;
		PRDNFSearchListDVO oPRDNFSearchList = null;
		if(a_oSearchListResult == null){
			a_oSearchListResult = new SearchListResult();
		}
		try{
			if(a_rsPRDNFSearchList != null){
				alPRDNFSearchListList = new ArrayList();
				while(a_rsPRDNFSearchList.next()){
					oPRDNFSearchList = new PRDNFSearchListDVO();
					oPRDNFSearchList.setTblCd(a_rsPRDNFSearchList.getString("STRTBLCD"));
					if(a_rsPRDNFSearchList.getObject("NVALUETYPE") != null){
						oPRDNFSearchList.setValueType(new Short(a_rsPRDNFSearchList.getShort("NVALUETYPE")));
					}
					if(a_rsPRDNFSearchList.getObject("DTUPDATED") != null){
						oPRDNFSearchList.setDtUpdated(a_rsPRDNFSearchList.getDate("DTUPDATED"));
					}
					oPRDNFSearchList.setSValueType(a_rsPRDNFSearchList.getString("STRVALUETYPE"));
					alPRDNFSearchListList.add(oPRDNFSearchList);
				}
			}
		}
		catch(SQLException sqlex){
			throw new EElixirException(sqlex, "Error while populating PRDNFSearchListDVO");
		}
		a_oSearchListResult.setResultList(alPRDNFSearchListList);
		return a_oSearchListResult;
	}
	/**
	 * This method populates the PRDListScheduleDVO for Products using
	 * SQL ID=SelectAllSchedDtl.
	 * 
	 * @param a_oSearchListResult
	 *            The SearchListResult.
	 * @return a_rs The ResultSet used to retrieve records.
	 * @return the ResultList.
	 */
	public static SearchListResult populatePRDListScheduleDVOList(ResultSet a_rsPRDListSchedule, SearchListResult a_oSearchListResult) throws EElixirException{
		ArrayList alPRDListScheduleList = null;
		PRDListScheduleDVO oPRDListSchedule = null;
		if(a_oSearchListResult == null){
			a_oSearchListResult = new SearchListResult();
		}
		try{
			if(a_rsPRDListSchedule != null){
				alPRDListScheduleList = new ArrayList();
				while(a_rsPRDListSchedule.next()){
					oPRDListSchedule = new PRDListScheduleDVO();
					oPRDListSchedule.setSchedNbr(a_rsPRDListSchedule.getString("STRSCHEDNBR"));
					oPRDListSchedule.setSchType(a_rsPRDListSchedule.getString("NSCHTYPE"));
					oPRDListSchedule.setSchFreq(a_rsPRDListSchedule.getString("NSCHFREQ"));
					//Start:04-07-2023||Rohit#22||TRM_MBF_URF24268_FIN_LTB_PROV_349||Added strIsLTB
					oPRDListSchedule.setIsLTB(a_rsPRDListSchedule.getString("NISLTB"));
					//End:04-07-2023||Rohit#22||TRM_MBF_URF24268_FIN_LTB_PROV_349||Added strIsLTB
					alPRDListScheduleList.add(oPRDListSchedule);
				}
			}
		}
		catch(SQLException sqlex){
			throw new EElixirException(sqlex, "Error while populating PRDListScheduleDVO");
		}
		a_oSearchListResult.setResultList(alPRDListScheduleList);
		return a_oSearchListResult;
	}
//  start:10/03/2009:MaybanPH1:dipikam:Pagination:added for Pagination
	/**
	 * This method populates the PRDAfflnBenSchListDVO for Products using
	 * SQL ID=SelectAllAfflnBenSchedHdr
	 * 
	 * @param a_oSearchListResult
	 *            The SearchListResult.
	 * @return a_rs The ResultSet used to retrieve records.
	 * @return the ResultList.
	 */
	public static SearchListResult populatePRDAfflnBenSchListDVOList(ResultSet a_rsPRDAfflnBenSchList, SearchListResult a_oSearchListResult) throws EElixirException{
		ArrayList alPRDAfflnBenSchListList = null;
		PRDAfflnBenSchListDVO oPRDAfflnBenSchList = null;
		if(a_oSearchListResult == null){
			a_oSearchListResult = new SearchListResult();
		}
		try{
			if(a_rsPRDAfflnBenSchList != null){
				alPRDAfflnBenSchListList = new ArrayList();
				while(a_rsPRDAfflnBenSchList.next()){
					oPRDAfflnBenSchList = new PRDAfflnBenSchListDVO();
					oPRDAfflnBenSchList.setAfflnSchedNbr(a_rsPRDAfflnBenSchList.getString("STRAFFLNSCHEDNBR"));
					oPRDAfflnBenSchList.setDtCreated(a_rsPRDAfflnBenSchList.getDate("DTCREATED"));
					alPRDAfflnBenSchListList.add(oPRDAfflnBenSchList);
				}
			}
		}
		catch(SQLException sqlex){
			throw new EElixirException(sqlex, "Error while populating PRDAfflnBenSchListDVO");
		}
		a_oSearchListResult.setResultList(alPRDAfflnBenSchListList);
		return a_oSearchListResult;
	}
	/**
	 * This method populates the PRDOccupExtraListDVO for Products using
	 * SQL ID=OccupationExtraSearchOETypes.
	 * 
	 * @param a_oSearchListResult
	 *            The SearchListResult.
	 * @return a_rs The ResultSet used to retrieve records.
	 * @return the ResultList.
	 */
	public static SearchListResult populatePRDOccupExtraListDVOList(ResultSet a_rsPRDOccupExtraList, SearchListResult a_oSearchListResult) throws EElixirException{
		ArrayList alPRDOccupExtraListList = null;
		PRDOccupExtraListDVO oPRDOccupExtraList = null;
		if(a_oSearchListResult == null){
			a_oSearchListResult = new SearchListResult();
		}
		try{
			if(a_rsPRDOccupExtraList != null){
				alPRDOccupExtraListList = new ArrayList();
				while(a_rsPRDOccupExtraList.next()){
					oPRDOccupExtraList = new PRDOccupExtraListDVO();
					oPRDOccupExtraList.setOEType(a_rsPRDOccupExtraList.getString("STROETYPE"));
					oPRDOccupExtraList.setDesc(a_rsPRDOccupExtraList.getString("STRDESC"));
					alPRDOccupExtraListList.add(oPRDOccupExtraList);
				}
			}
		}
		catch(SQLException sqlex){
			throw new EElixirException(sqlex, "Error while populating PRDOccupExtraListDVO");
		}
		a_oSearchListResult.setResultList(alPRDOccupExtraListList);
		return a_oSearchListResult;
	}
	/**
	 * This method populates the PRDBonusListSearchDVO for Products using
	 * SQL ID=ProductBonusListSearch
	 * 
	 * @param a_oSearchListResult
	 *            The SearchListResult.
	 * @return a_rs The ResultSet used to retrieve records.
	 * @return the ResultList.
	 */
	public static SearchListResult populatePRDBonusListSearchDVOList(ResultSet a_rsPRDBonusListSearch, SearchListResult a_oSearchListResult) throws EElixirException{
		ArrayList alPRDBonusListSearchList = null;
		PRDBonusListSearchDVO oPRDBonusListSearch = null;
		if(a_oSearchListResult == null){
			a_oSearchListResult = new SearchListResult();
		}
		try{
			if(a_rsPRDBonusListSearch != null){
				alPRDBonusListSearchList = new ArrayList();
				while(a_rsPRDBonusListSearch.next()){
					oPRDBonusListSearch = new PRDBonusListSearchDVO();
					if(a_rsPRDBonusListSearch.getObject("LBONHDRSEQ") != null){
						oPRDBonusListSearch.setBonHdrSeq(new Long(a_rsPRDBonusListSearch.getLong("LBONHDRSEQ")));
					}
					oPRDBonusListSearch.setProdCd(a_rsPRDBonusListSearch.getString("STRPRODCD"));
					if(a_rsPRDBonusListSearch.getObject("NPRODVER") != null){
						oPRDBonusListSearch.setProdVer(new Short(a_rsPRDBonusListSearch.getShort("NPRODVER")));
					}
					oPRDBonusListSearch.setRiskType(a_rsPRDBonusListSearch.getString("NRISKTYPE"));
					oPRDBonusListSearch.setBonBas(a_rsPRDBonusListSearch.getString("NBONBAS"));
					if(a_rsPRDBonusListSearch.getObject("NBONUNIT") != null){
						oPRDBonusListSearch.setBonUnit(new Integer(a_rsPRDBonusListSearch.getInt("NBONUNIT")));
					}
					alPRDBonusListSearchList.add(oPRDBonusListSearch);
				}
			}
		}
		catch(SQLException sqlex){
			throw new EElixirException(sqlex, "Error while populating PRDBonusListSearchDVO");
		}
		a_oSearchListResult.setResultList(alPRDBonusListSearchList);
		return a_oSearchListResult;
	}
	/**
	 * This method populates the PRDListCOIDVO for Products using
	 * SQL ID=SelectAllCoiDtl
	 * 
	 * @param a_oSearchListResult
	 *            The SearchListResult.
	 * @return a_rs The ResultSet used to retrieve records.
	 * @return the ResultList.
	 */
	public static SearchListResult populatePRDListCOIDVOList(ResultSet a_rsPRDListCOI, SearchListResult a_oSearchListResult) throws EElixirException{
		ArrayList alPRDListCOIList = null;
		PRDListCOIDVO oPRDListCOI = null;
		if(a_oSearchListResult == null){
			a_oSearchListResult = new SearchListResult();
		}
		try{
			if(a_rsPRDListCOI != null){
				alPRDListCOIList = new ArrayList();
				while(a_rsPRDListCOI.next()){
					oPRDListCOI = new PRDListCOIDVO();
					oPRDListCOI.setCOITblCd(a_rsPRDListCOI.getString("STRCOITBLCD"));
					oPRDListCOI.setCOITblDesc(a_rsPRDListCOI.getString("STRCOITBLDESC"));
					alPRDListCOIList.add(oPRDListCOI);
				}
			}
		}
		catch(SQLException sqlex){
			throw new EElixirException(sqlex, "Error while populating PRDListCOIDVO");
		}
		a_oSearchListResult.setResultList(alPRDListCOIList);
		return a_oSearchListResult;
	}
//  end:10/03/2009:MaybanPH1:dipikam:Pagination:added for Pagination
	
//  start:13/03/2009:MaybanPH1:dipikam:Pagination:added for Pagination
	/**
	 * This method populates the PRDListInvLnkChgDVOList for Products using
	 * SQL ID=SelectAllInvLinkChgDtl.
	 * 
	 * @param a_oSearchListResult
	 *            The SearchListResult.
	 * @return a_rs The ResultSet used to retrieve records.
	 * @return the ResultList.
	 */
	public static SearchListResult populatePRDListInvLnkChgDVOList(ResultSet a_rsPRDListInvLnkChg, SearchListResult a_oSearchListResult) throws EElixirException{
		ArrayList alPRDListInvLnkChgList = null;
		PRDListInvLnkChgDVO oPRDListInvLnkChg = null;
		if(a_oSearchListResult == null){
			a_oSearchListResult = new SearchListResult();
		}
		try{
			if(a_rsPRDListInvLnkChg != null){
				alPRDListInvLnkChgList = new ArrayList();
				while(a_rsPRDListInvLnkChg.next()){
					oPRDListInvLnkChg = new PRDListInvLnkChgDVO();
					oPRDListInvLnkChg.setInvestHdrTblCd(a_rsPRDListInvLnkChg.getString("STRINVESTHDRTBLCD"));
					oPRDListInvLnkChg.setInvestTblDesc(a_rsPRDListInvLnkChg.getString("STRINVESTTBLDESC"));
					alPRDListInvLnkChgList.add(oPRDListInvLnkChg);
				}
			}
		}
		catch(SQLException sqlex){
			throw new EElixirException(sqlex, "Error while populating PRDListInvLnkChgDVO");
		}
		a_oSearchListResult.setResultList(alPRDListInvLnkChgList);
		return a_oSearchListResult;
	}
	/**
	 * This method populates the PRDListExtraMortalityDefnDVO for Products using
	 * SQL ID=ListExtraMortalityDefnHdr.
	 * 
	 * @param a_oSearchListResult
	 *            The SearchListResult.
	 * @return a_rs The ResultSet used to retrieve records.
	 * @return the ResultList.
	 */
	public static SearchListResult populatePRDListExtraMortalityDefnDVOList(ResultSet a_rsPRDListExtraMortalityDefn, SearchListResult a_oSearchListResult) throws EElixirException{
		ArrayList alPRDListExtraMortalityDefnList = null;
		PRDListExtraMortalityDefnDVO oPRDListExtraMortalityDefn = null;
		if(a_oSearchListResult == null){
			a_oSearchListResult = new SearchListResult();
		}
		try{
			if(a_rsPRDListExtraMortalityDefn != null){
				alPRDListExtraMortalityDefnList = new ArrayList();
				while(a_rsPRDListExtraMortalityDefn.next()){
					oPRDListExtraMortalityDefn = new PRDListExtraMortalityDefnDVO();
					oPRDListExtraMortalityDefn.setExtraPrmTblCd(a_rsPRDListExtraMortalityDefn.getString("STREXTRAPRMTBLCD"));
					oPRDListExtraMortalityDefn.setBasis(a_rsPRDListExtraMortalityDefn.getString("BASIS"));
					if(a_rsPRDListExtraMortalityDefn.getObject("DBASIS") != null){
						oPRDListExtraMortalityDefn.setDBasis(new Short(a_rsPRDListExtraMortalityDefn.getShort("DBASIS")));
					}
					alPRDListExtraMortalityDefnList.add(oPRDListExtraMortalityDefn);
				}
			}
		}
		catch(SQLException sqlex){
			throw new EElixirException(sqlex, "Error while populating PRDListExtraMortalityDefnDVO");
		}
		a_oSearchListResult.setResultList(alPRDListExtraMortalityDefnList);
		return a_oSearchListResult;
	}
//  end:13/03/2009:MaybanPH1:dipikam:Pagination:added for Pagination
//  start:20/03/2009:MaybanPH1:dipikam:Pagination:added for Pagination
	/**
	 * This method populates the PRDAfflnTypListDVO for Products using
	 * SQL ID=SelectAfflnTypeList.
	 * 
	 * @param a_oSearchListResult
	 *            The SearchListResult.
	 * @return a_rs The ResultSet used to retrieve records.
	 * @return the ResultList.
	 */
	public static SearchListResult populatePRDAfflnTypListDVOList(ResultSet a_rsPRDAfflnTypList, SearchListResult a_oSearchListResult) throws EElixirException{
		ArrayList alPRDAfflnTypListList = null;
		PRDAfflnTypListDVO oPRDAfflnTypList = null;
		if(a_oSearchListResult == null){
			a_oSearchListResult = new SearchListResult();
		}
		try{
			if(a_rsPRDAfflnTypList != null){
				alPRDAfflnTypListList = new ArrayList();
				while(a_rsPRDAfflnTypList.next()){
					oPRDAfflnTypList = new PRDAfflnTypListDVO();
					if(a_rsPRDAfflnTypList.getObject("NPARAMCD") != null){
						oPRDAfflnTypList.setParamCd(new Short(a_rsPRDAfflnTypList.getShort("NPARAMCD")));
					}
					oPRDAfflnTypList.setCdDesc(a_rsPRDAfflnTypList.getString("STRCDDESC"));
					alPRDAfflnTypListList.add(oPRDAfflnTypList);
				}
			}
		}
		catch(SQLException sqlex){
			throw new EElixirException(sqlex, "Error while populating PRDAfflnTypListDVO");
		}
		a_oSearchListResult.setResultList(alPRDAfflnTypListList);
		return a_oSearchListResult;
	}
//  end:20/03/2009:MaybanPH1:dipikam:Pagination:added for Pagination
//  start:21/03/2009:MaybanPH1:dipikam:Pagination:added for Pagination
	/**
	 * This method populates the PRDOccupSearchDVO for Products using
	 * SQL ID=OccupationExtraCodesSearch.
	 * 
	 * @param a_oSearchListResult
	 *            The SearchListResult.
	 * @return a_rs The ResultSet used to retrieve records.
	 * @return the ResultList.
	 */
	public static SearchListResult populatePRDOccupSearchDVOList(ResultSet a_rsPRDOccupSearch, SearchListResult a_oSearchListResult) throws EElixirException{
		ArrayList alPRDOccupSearchList = null;
		PRDOccupSearchDVO oPRDOccupSearch = null;
		if(a_oSearchListResult == null){
			a_oSearchListResult = new SearchListResult();
		}
		try{
			if(a_rsPRDOccupSearch != null){
				alPRDOccupSearchList = new ArrayList();
				while(a_rsPRDOccupSearch.next()){
					oPRDOccupSearch = new PRDOccupSearchDVO();
					oPRDOccupSearch.setOccupCd(a_rsPRDOccupSearch.getString("STROCCUPCD"));
					oPRDOccupSearch.setOccupName(a_rsPRDOccupSearch.getString("STROCCUPNAME"));
					oPRDOccupSearch.setOccupClass(a_rsPRDOccupSearch.getString("STROCCUPCLASS"));
					alPRDOccupSearchList.add(oPRDOccupSearch);
				}
			}
		}
		catch(SQLException sqlex){
			throw new EElixirException(sqlex, "Error while populating PRDOccupSearchDVO");
		}
		a_oSearchListResult.setResultList(alPRDOccupSearchList);
		return a_oSearchListResult;
	}

	/**
	 * This method populates the PRDOccupClsListDVO for Products using
	 * SQL ID=OccupationExtraClassSearch.
	 * 
	 * @param a_oSearchListResult
	 *            The SearchListResult.
	 * @return a_rs The ResultSet used to retrieve records.
	 * @return the ResultList.
	 */
	public static SearchListResult populatePRDOccupClsListDVOList(ResultSet a_rsPRDOccupClsList, SearchListResult a_oSearchListResult) throws EElixirException{
		ArrayList alPRDOccupClsListList = null;
		PRDOccupClsListDVO oPRDOccupClsList = null;
		if(a_oSearchListResult == null){
			a_oSearchListResult = new SearchListResult();
		}
		try{
			if(a_rsPRDOccupClsList != null){
				alPRDOccupClsListList = new ArrayList();
				while(a_rsPRDOccupClsList.next()){
					oPRDOccupClsList = new PRDOccupClsListDVO();
					oPRDOccupClsList.setParamCd(a_rsPRDOccupClsList.getString("STRPARAMCD"));
					oPRDOccupClsList.setCdDesc(a_rsPRDOccupClsList.getString("STRCDDESC"));
					alPRDOccupClsListList.add(oPRDOccupClsList);
				}
			}
		}
		catch(SQLException sqlex){
			throw new EElixirException(sqlex, "Error while populating PRDOccupClsListDVO");
		}
		a_oSearchListResult.setResultList(alPRDOccupClsListList);
		return a_oSearchListResult;
	}
//  end:21/03/2009:MaybanPH1:dipikam:Pagination:added for Pagination
//  Start:15/05/2009:MaybanPH1:dipikam:Pagination:added for Pagination	
	/**
	 * This method populates the PRDSearchListDVO for Products using
	 * SQL ID=Search_PRD_Conversion.
	 * 
	 * @param a_oSearchListResult
	 *            The SearchListResult.
	 * @return a_rs The ResultSet used to retrieve records.
	 * @return the ResultList.
	 */
	public static SearchListResult populatePRDConversionListDVOList(ResultSet a_rsPRDSearchList, SearchListResult a_oSearchListResult) throws EElixirException{
		ArrayList alPRDSearchListList = null;
		PRDSearchListDVO oPRDSearchList = null;
		if(a_oSearchListResult == null){
			a_oSearchListResult = new SearchListResult();
		}
		try{
			if(a_rsPRDSearchList != null){
				alPRDSearchListList = new ArrayList(); 
				
				while(a_rsPRDSearchList.next()){
					oPRDSearchList = new PRDSearchListDVO();
					oPRDSearchList.setProdCd(a_rsPRDSearchList.getString("STRPRODCD"));
					oPRDSearchList.setProdName(a_rsPRDSearchList.getString("STRPRODNAME"));
					if(a_rsPRDSearchList.getObject("NPRODVER") != null){
						oPRDSearchList.setPRODVER(new Short(a_rsPRDSearchList.getShort("NPRODVER")));
					}
					
					alPRDSearchListList.add(oPRDSearchList);
				}
				
			}
		}
		catch(SQLException sqlex){
			throw new EElixirException(sqlex, "Error while populating PRDSearchListDVO");
		}
		a_oSearchListResult.setResultList(alPRDSearchListList);
		return a_oSearchListResult;
	}
//  End:15/05/2009:MaybanPH1:dipikam:Pagination:added for Pagination
	
    //Start:29/06/2009:Defect-2746|IT|:shweta:Pagination
	/** 
	 * This method populates the PRDRiderListSearchDVO for product using SQL ID=sqlid.
	 * @param a_oSearchListResult The SearchListResult.
	 * @return a_rs The ResultSet used to retrieve records.
	 * @return the  ResultList.
	 */ 
	public static SearchListResult populatePRDRiderListSearchDVOList(ResultSet a_rs, SearchListResult a_oSearchListResult) throws EElixirException
	{
		ArrayList alPRDRiderListSearchList = null;
		PRDRiderListSearchDVO oPRDRiderListSearch = null;
		if(a_oSearchListResult == null){
			a_oSearchListResult = new SearchListResult();
		}
		try{
			if(a_rs != null){
				alPRDRiderListSearchList = new ArrayList();
				while(a_rs.next()){
					oPRDRiderListSearch = new PRDRiderListSearchDVO();
					oPRDRiderListSearch.setProdCd(a_rs.getString("STRPRODCD"));
					if(a_rs.getObject("NPRODVER") != null){
						oPRDRiderListSearch.setProdVer(new Short(a_rs.getShort("NPRODVER")));
					}
					oPRDRiderListSearch.setProdName(a_rs.getString("STRPRODNAME"));
					if(a_rs.getObject("NISTAKAFULPROD") != null){
						oPRDRiderListSearch.setIsTakafulProd(new Short(a_rs.getShort("NISTAKAFULPROD")));
					}
					alPRDRiderListSearchList.add(oPRDRiderListSearch);
				}
			}
		}
		catch(SQLException sqlex){
			throw new EElixirException(sqlex, "Error while populating PRDRiderListSearchDVO");
		}
		a_oSearchListResult.setResultList(alPRDRiderListSearchList);
		return a_oSearchListResult;
	}
    //End:29/06/2009:Defect-2746|IT|:shweta:Pagination
	
	   /**
     * This method populates the PRDListExtraMortalityDefnDVO for Products using
     * SQL ID=ListExtraMortalityDefnHdr.
     * 
     * @param a_oSearchListResult
     *            The SearchListResult.
     * @return a_rs The ResultSet used to retrieve records.
     * @return the ResultList.
     */
   /* Start: UAT_2387:reviewed by janga:Screen require for RSA Factor Table Setup */
    public static SearchListResult populatePRDListRSAFactorDVOList(ResultSet a_rsPRDListRSAFactor, SearchListResult a_oSearchListResult) throws EElixirException{
        ArrayList alPRDListRSAFactorList = null;
        PRDListRSAFactorDVO oPRDListRSAFactor = null;
        if(a_oSearchListResult == null){
            a_oSearchListResult = new SearchListResult();
        }
        try{
            if(a_rsPRDListRSAFactor != null){
                alPRDListRSAFactorList = new ArrayList();
                while(a_rsPRDListRSAFactor.next()){
                    oPRDListRSAFactor = new PRDListRSAFactorDVO();
                    oPRDListRSAFactor.setRSATblCd(a_rsPRDListRSAFactor.getString("STRRSATBLCD"));
                    oPRDListRSAFactor.setBasis(a_rsPRDListRSAFactor.getString("BASIS"));
                    if(a_rsPRDListRSAFactor.getObject("NBASIS") != null){
                        oPRDListRSAFactor.setDBasis(new Short(a_rsPRDListRSAFactor.getShort("NBASIS")));
                    }
                    alPRDListRSAFactorList.add(oPRDListRSAFactor);
                }
            }
        }
        catch(SQLException sqlex){
            throw new EElixirException(sqlex, "Error while populating PRDListRSAFactorDVO");
        }
        a_oSearchListResult.setResultList(alPRDListRSAFactorList);
        return a_oSearchListResult;
    }
	/* End: UAT_2387:reviewed by janga:Screen require for RSA Factor Table Setup */
	
  //Start:CR 335|Lien Master for SI 
    public static SearchListResult populatePRDLienSIDVOList(ResultSet a_rsLienSIDtl, SearchListResult a_oSearchListResult) throws EElixirException{
        ArrayList alLienSIList = null;
        PRDListLienSIDVO oPRDListLienSIDVO = null;
        if(a_oSearchListResult == null){
            a_oSearchListResult = new SearchListResult();
        }
        try{
            if(a_rsLienSIDtl != null){
                alLienSIList = new ArrayList();
                while(a_rsLienSIDtl.next()){
                    oPRDListLienSIDVO = new PRDListLienSIDVO();
                    oPRDListLienSIDVO.setLJuvHdrSeq(a_rsLienSIDtl.getLong("LJUVHDRSEQ"));
                    oPRDListLienSIDVO.setStrPrdCd(a_rsLienSIDtl.getString("STRPRODCD"));
                    oPRDListLienSIDVO.setNPrdVer(new Short(a_rsLienSIDtl.getShort("NPRODVER")));
                    oPRDListLienSIDVO.setStrJuvbenType(a_rsLienSIDtl.getString("STRJUVBENTYPEDESC"));
                    alLienSIList.add(oPRDListLienSIDVO);
                }
            }
        }
        catch(SQLException sqlex){
            throw new EElixirException(sqlex, "Error while populating PRDListLienSIDVO");
        }
        a_oSearchListResult.setResultList(alLienSIList);
        return a_oSearchListResult;
    }
  //End: :CR 335|Lien Master for SI 
	
  //Start:CR264:1-oct-2013:TRM_MBF_CR264_0067:Takaful rate master screen.
    public static SearchListResult populateTakafulRateListSearchDVOList(ResultSet a_rsTakafulRateListSearch, SearchListResult a_oSearchListResult) throws EElixirException{
        ArrayList alTakafulRateSearchList = null;
        TakafulRateDVO oTakafulRateDVOSearch = null;
        if(a_oSearchListResult == null){
            a_oSearchListResult = new SearchListResult();
        }
        try{
            if(a_rsTakafulRateListSearch != null){
                alTakafulRateSearchList = new ArrayList();
                while(a_rsTakafulRateListSearch.next())
                {
                    oTakafulRateDVOSearch = new TakafulRateDVO();
                    
                    if(a_rsTakafulRateListSearch.getObject("LWHTSEQ") != null)
                    {
                        oTakafulRateDVOSearch.setTakafulRateSeq(new Long(a_rsTakafulRateListSearch.getLong("LWHTSEQ")));
                    }
                    if(a_rsTakafulRateListSearch.getObject("DTDECLARATION")!=null)
                    {
                        oTakafulRateDVOSearch.setDtDeclaration(a_rsTakafulRateListSearch.getDate("DTDECLARATION"));
                    }
                    if(a_rsTakafulRateListSearch.getObject("NDECLARATIONTYPE")!=null)
                    {
                        oTakafulRateDVOSearch.setNDeclarationType(new Short(a_rsTakafulRateListSearch.getShort("NDECLARATIONTYPE")));
                    }
                    oTakafulRateDVOSearch.setProdCd(a_rsTakafulRateListSearch.getString("STRPRODCD"));
                    if(a_rsTakafulRateListSearch.getObject("NFISCALYEAR") != null)
                    {
                        oTakafulRateDVOSearch.setFiscalYears(new Short(a_rsTakafulRateListSearch.getShort("NFISCALYEAR")));
                    }
                    if(a_rsTakafulRateListSearch.getObject("NPRODVER") != null)
                    {
                        oTakafulRateDVOSearch.setProdVer(new Short(a_rsTakafulRateListSearch.getShort("NPRODVER")));
                    }
                    if(a_rsTakafulRateListSearch.getObject("DTEFFFROM")!=null)
                    {
                        oTakafulRateDVOSearch.setEffFrom(a_rsTakafulRateListSearch.getDate("DTEFFFROM"));
                    }
                    if(a_rsTakafulRateListSearch.getObject("DTEFFTO")!=null)
                    {
                        oTakafulRateDVOSearch.setEffTo(a_rsTakafulRateListSearch.getDate("DTEFFTO"));
                    }
                    if(a_rsTakafulRateListSearch.getObject("NCLIENTTYPE")!=null)
                    {
                        oTakafulRateDVOSearch.setNClientType(new Short(a_rsTakafulRateListSearch.getShort("NCLIENTTYPE")));
                    }
                    if(a_rsTakafulRateListSearch.getObject("NFUNDTYPE")!=null)
                    {
                        oTakafulRateDVOSearch.setNFundType(new Short(a_rsTakafulRateListSearch.getShort("NFUNDTYPE")));
                    }
                    if(a_rsTakafulRateListSearch.getObject("NBASIS")!=null)
                    {
                        oTakafulRateDVOSearch.setNTakafulBas(new Short(a_rsTakafulRateListSearch.getShort("NBASIS")));
                    }
                    if(a_rsTakafulRateListSearch.getObject("DGROSSRATE")!=null)
                    {
                        oTakafulRateDVOSearch.setDGrossRate(new Double(a_rsTakafulRateListSearch.getDouble("DGROSSRATE")));
                    }
                    if(a_rsTakafulRateListSearch.getObject("DTAXRATE")!=null)
                    {
                        oTakafulRateDVOSearch.setDTaxRate(new Double(a_rsTakafulRateListSearch.getDouble("DTAXRATE")));
                    }
                    if(a_rsTakafulRateListSearch.getObject("DNETRATE")!=null)
                    {
                        oTakafulRateDVOSearch.setDNetRate(new Double(a_rsTakafulRateListSearch.getDouble("DNETRATE")));
                    }
                    if(a_rsTakafulRateListSearch.getObject("DGROSSAMNT")!=null)
                    {
                        oTakafulRateDVOSearch.setDGrossAmt(new Double(a_rsTakafulRateListSearch.getDouble("DGROSSAMNT")));
                    }
                    if(a_rsTakafulRateListSearch.getObject("DTAXAMNT")!=null)
                    {
                        oTakafulRateDVOSearch.setDTaxAmt(new Double(a_rsTakafulRateListSearch.getDouble("DTAXAMNT")));
                    }
                    if(a_rsTakafulRateListSearch.getObject("DNETAMNT")!=null)
                    {
                        oTakafulRateDVOSearch.setDNetAmt(new Double(a_rsTakafulRateListSearch.getDouble("DNETAMNT")));
                    }
                    if(a_rsTakafulRateListSearch.getObject("NUNIT") != null)
                    {
                        oTakafulRateDVOSearch.setITakafulUnit(new Integer(a_rsTakafulRateListSearch.getInt("NUNIT")));
                    }
                    if(a_rsTakafulRateListSearch.getObject("NSTATUS")!=null)
                    {
                        oTakafulRateDVOSearch.setNStatus(new Short(a_rsTakafulRateListSearch.getShort("NSTATUS")));
                    }
                    oTakafulRateDVOSearch.setCreatedBy(a_rsTakafulRateListSearch.getString("STRCREATEDBY"));
                    oTakafulRateDVOSearch.setUpdatedBy(a_rsTakafulRateListSearch.getString("STRUPDATEDBY"));
                    alTakafulRateSearchList.add(oTakafulRateDVOSearch);
                }
            }
        }
        catch(SQLException sqlex){
            throw new EElixirException(sqlex, "Error while populating PRDBonusListSearchDVO");
        }
        a_oSearchListResult.setResultList(alTakafulRateSearchList);
        return a_oSearchListResult;
    }
  //End:CR264:1-oct-2013:TRM_MBF_CR264_0067:Takaful rate master screen.
    ///Start:30/05/2018:Ashish R: TRM_MBF_CR372_239: Code Added for Golden Retirement
    
    public static SearchListResult populateInvLockInListSearchDVOList(ResultSet a_rsPRDBonusListSearch, SearchListResult a_oSearchListResult) throws EElixirException{
        ArrayList alFundListSearchList = null;
        FundListSearchDVO oFundListSearchDVO = null;
        if(a_oSearchListResult == null){
            a_oSearchListResult = new SearchListResult();
        }
        try{
            if(a_rsPRDBonusListSearch != null){
                alFundListSearchList = new ArrayList();
                while(a_rsPRDBonusListSearch.next()){
                    oFundListSearchDVO = new FundListSearchDVO();
                    if(a_rsPRDBonusListSearch.getObject("lbonhdrseq") != null){
                        oFundListSearchDVO.setBonHdrSeq(new Long(a_rsPRDBonusListSearch.getLong("lbonhdrseq")));
                    }
                    oFundListSearchDVO.setProdCd(a_rsPRDBonusListSearch.getString("strprodcd"));
                    if(a_rsPRDBonusListSearch.getObject("nprodver") != null){
                        oFundListSearchDVO.setProdVer(new Short(a_rsPRDBonusListSearch.getShort("nprodver")));
                    }
                    oFundListSearchDVO.setFundName(a_rsPRDBonusListSearch.getString("STRFUNDNAME"));
                    oFundListSearchDVO.setBonBas(a_rsPRDBonusListSearch.getString("nbonbas"));
                    if(a_rsPRDBonusListSearch.getObject("nbonunit") != null){
                        oFundListSearchDVO.setBonUnit(new Integer(a_rsPRDBonusListSearch.getInt("nbonunit")));
                    }
                    alFundListSearchList.add(oFundListSearchDVO);
                }
            }
        }
        catch(SQLException sqlex){
            throw new EElixirException(sqlex, "Error while populating populateInvLockInListSearchDVOList");
        }
        a_oSearchListResult.setResultList(alFundListSearchList);
        return a_oSearchListResult;
    }
    ///Start:30/05/2018:Ashish R: TRM_MBF_CR372_239: Code Added for Golden Retirement
    
 	
}
