/**
 *--------------------
 *  Change History:
 * -----------------------------------------------------------------------------------------------
 * SR No. | Modified By   | Modification Date  | Description
 * ------------------------------------------------------------------------------------------------
 * 1      |Anil L Gupta   | 02/01/2008(dd/mm/yy|<TaskID>:added field _nSchType ,_nSchFreq
 **/
package com.mastek.eElixir.product.schedule.dvo;

import java.sql.Date;

import com.mastek.eElixir.product.helper.ProductCommonInfo;

/**
 * <p>Title: eElixir</p>
 * <p>Description: This is  a DVO class that represents a single record value
 * of database table prd_sched_hdr.</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Mastek Ltd.</p>
 * @author AniketM
 * @version 1.0
 */

public class ScheduleHeader extends ProductCommonInfo
{
    /**
     * No Argument constructor
     */
    public ScheduleHeader()
    {
    }

    /**
     * Sets the schedule number
     * @return String
     */
    public String getSchedNbr()
    {
        return _strSchedNbr;
    }

    /**
     * Sets the schedule number
     * @param a_strSchedNbr String
     */
    public void setSchedNbr(String a_strSchedNbr)
    {
        this._strSchedNbr = a_strSchedNbr;
    }

    /**
     * Sets the VerifiedBy
     * @return String
     */
    public String getVerifiedBy()
    {
        return _strVerifiedBy;
    }

    /**
     * Sets the VerifiedBy
     * @param a_strVerifiedBy String
     */
    public void setVerifiedBy(String a_strVerifiedBy)
    {
        this._strVerifiedBy = a_strVerifiedBy;
    }

    /**
     * Sets the AuthBy
     * @return String
     */
    public String getAuthBy()
    {
        return _strAuthBy;
    }

    /**
     * Sets the AuthBy
     * @param a_strAuthBy String
     */
    public void setAuthBy(String a_strAuthBy)
    {
        this._strAuthBy = a_strAuthBy;
    }

    /**
   * returns the Verified
   * @return _dtVerified Date
   */
  public Date getVerified()
  {
    return this._dtVerified;
  }
  /**
   * sets the _dtVerified
   * @param a_dtVerified Date
   */
   public void setVerified(Date a_dtVerified)
   {
     this._dtVerified = a_dtVerified;
   }

   /**
   * returns the Auth
   * @return _dtAuth Date
   */
  public Date getAuth()
  {
    return this._dtAuth;
  }
  /**
   * sets the _dtAuth
   * @param a_dtAuth Date
   */
   public void setAuth(Date a_dtAuth)
   {
     this._dtAuth = a_dtAuth;
    }

    /**
     * Sets the Status of DVO I - Insert, U - update, R - remove
     * @param a_strStatus String status.
     */
    public void setStatus(String a_strStatus)
    {
        this._strStatus = a_strStatus;
    }

    /**
     * Returns the Status of the DVO I - Insert, U - update, R - remove
     * @return Status
     */
    public String getStatus()
    {
        return this._strStatus;
    }
    //  Start:02/01/2007:MaybanPH1:anilg:<>:
	public Short getSchFreq() {
		return _nSchFreq;
	}

	public void setSchFreq(Short schFreq) {
		_nSchFreq = schFreq;
	}

	public Short getSchType() {
		return _nSchType;
	}

	public void setSchType(Short schType) {
		_nSchType = schType;
	}
    //  End:02/01/2007:MaybanPH1:anilg:<>:	
    /*Class level variables*/
    private String _strSchedNbr;
    private String _strVerifiedBy;
    private String _strAuthBy;
    private Date _dtVerified;
    private Date _dtAuth;
    private String _strStatus;
    //  Start:02/01/2007:MaybanPH1:anilg:<>:    
    private Short _nSchType; 
    private Short _nSchFreq;
    //  End:02/01/2007:MaybanPH1:anilg:<>:      
//  <!--09-07-2018 | Pawan Annuity Start here -->     
    private Short _nperd;
    private Short _nPerdUnit;
    
	public Short get_nperd() {
		return _nperd;
	}

	public void set_nperd(Short _nperd) {
		this._nperd = _nperd;
	}

	public Short get_nPerdUnit() {
		return _nPerdUnit;
	}

	public void set_nPerdUnit(Short perdUnit) {
		_nPerdUnit = perdUnit;
	}
    
    //Start:04-07-2023||Rohit#22||TRM_MBF_URF24268_FIN_LTB_PROV_349||Added nIsLTB
    private Short nIsLTB;
    public Short getIsLTB() {
        return nIsLTB;
    }
    public void setIsLTB(Short nIsLTB) {
        this.nIsLTB = nIsLTB;
    }
    //End:04-07-2023||Rohit#22||TRM_MBF_URF24268_FIN_LTB_PROV_349||Added nIsLTB
    
//  <!--09-07-2018 | Pawan Annuity End here -->     
}