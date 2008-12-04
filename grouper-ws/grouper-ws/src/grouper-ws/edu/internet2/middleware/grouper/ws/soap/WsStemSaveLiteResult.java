package edu.internet2.middleware.grouper.ws.soap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.internet2.middleware.grouper.util.GrouperUtil;
import edu.internet2.middleware.grouper.ws.GrouperWsVersion;
import edu.internet2.middleware.grouper.ws.WsResultCode;
import edu.internet2.middleware.grouper.ws.exceptions.WsInvalidQueryException;
import edu.internet2.middleware.grouper.ws.rest.WsResponseBean;
import edu.internet2.middleware.grouper.ws.util.GrouperServiceUtils;

/**
 * <pre>
 * results for the stems save call.
 * 
 * result code:
 * code of the result for this stem overall
 * SUCCESS: means everything ok
 * STEM_NOT_FOUND: cant find the stem
 * STEM_DUPLICATE: found multiple stems
 * </pre>
 * @author mchyzer
 */
public class WsStemSaveLiteResult implements WsResponseBean {

  /**
   * result code of a request
   */
  public static enum WsStemSaveLiteResultCode implements WsResultCode {

    /** found the stems, saved them (lite http status code 201) (success: T) */
    SUCCESS(201),

    /** either overall exception, or one or more stems had exceptions (lite http status code 500) (success: F) */
    EXCEPTION(500),

    /** problem deleting existing stems (lite http status code 500) (success: F) */
    PROBLEM_SAVING_STEMS(500),

    /** invalid query (e.g. if everything blank) (lite http status code 400) (success: F) */
    INVALID_QUERY(400), 
    
    /** user not allowed (lite http status code 403) (success: F) */
    INSUFFICIENT_PRIVILEGES(403), 
    
    /** the save was not found (lite http status code 404) (success: F) */
    STEM_NOT_FOUND(404);

    /** http status code for rest/lite e.g. 200 */
    private int httpStatusCode;

    /** get the name label for a certain version of client 
     * @param clientVersion 
     * @return */
    public String nameForVersion(GrouperWsVersion clientVersion) {
      return this.name();
    }

    /**
     * status code for rest/lite e.g. 200
     * @param statusCode
     */
    private WsStemSaveLiteResultCode(int statusCode) {
      this.httpStatusCode = statusCode;
    }

    /**
     * @see edu.internet2.middleware.grouper.ws.WsResultCode#getHttpStatusCode()
     */
    public int getHttpStatusCode() {
      return this.httpStatusCode;
    }

    /**
     * if this is a successful result
     * @return true if success
     */
    public boolean isSuccess() {
      return this == SUCCESS;
    }

  }

  /**
   * logger 
   */
  private static final Log LOG = LogFactory.getLog(WsStemSaveLiteResult.class);

  /**
   * assign the code from the enum
   * @param stemSaveResultsCode
   */
  public void assignResultCode(WsStemSaveLiteResultCode stemSaveResultsCode) {
    this.getResultMetadata().assignResultCode(stemSaveResultsCode);
  }

  /**
   * convert the result code back to enum
   * @return the enum code
   */
  public WsStemSaveLiteResultCode retrieveResultCode() {
    if (StringUtils.isBlank(this.getResultMetadata().getResultCode())) {
      return null;
    }
    return WsStemSaveLiteResultCode.valueOf(this.getResultMetadata().getResultCode());
  }

  /**
   * empty
   */
  public WsStemSaveLiteResult() {
    //empty
  }
  
  /**
   * construct from results of other
   * @param wsStemSaveResults
   */
  public WsStemSaveLiteResult(WsStemSaveResults wsStemSaveResults) {

    this.getResultMetadata().copyFields(wsStemSaveResults.getResultMetadata());

    WsStemSaveResult wsStemSaveResult = GrouperServiceUtils
        .firstInArrayOfOne(wsStemSaveResults.getResults());
    if (wsStemSaveResult != null) {
      this.getResultMetadata().copyFields(wsStemSaveResult.getResultMetadata());
      this.getResultMetadata().assignResultCode(
          wsStemSaveResult.resultCode().convertToLiteCode());
      this.setWsStem(wsStemSaveResult.getWsStem());
    }
  }

  /**
   * metadata about the result
   */
  private WsResultMeta resultMetadata = new WsResultMeta();

  /**
   * metadata about the result
   */
  private WsResponseMeta responseMetadata = new WsResponseMeta();

  /**
   * stem that is saved 
   */
  private WsStem wsStem = null;

  /**
   * @return the resultMetadata
   */
  public WsResultMeta getResultMetadata() {
    return this.resultMetadata;
  }

  /**
   * prcess an exception, log, etc
   * @param wsStemSaveResultsCodeOverride
   * @param theError
   * @param e
   */
  public void assignResultCodeException(
      WsStemSaveLiteResultCode wsStemSaveResultsCodeOverride, String theError, Exception e) {

    if (e instanceof WsInvalidQueryException) {
      wsStemSaveResultsCodeOverride = GrouperUtil.defaultIfNull(
          wsStemSaveResultsCodeOverride, WsStemSaveLiteResultCode.INVALID_QUERY);
      //      if (e.getCause() instanceof StemNotFoundException) {
      //        wsStemSaveResultsCodeOverride = WsStemSaveResultsCode.STEM_NOT_FOUND;
      //      }
      //a helpful exception will probably be in the getMessage()
      this.assignResultCode(wsStemSaveResultsCodeOverride);
      this.getResultMetadata().appendResultMessage(e.getMessage());
      this.getResultMetadata().appendResultMessage(theError);
      LOG.warn(e);

    } else {
      wsStemSaveResultsCodeOverride = GrouperUtil.defaultIfNull(
          wsStemSaveResultsCodeOverride, WsStemSaveLiteResultCode.EXCEPTION);
      LOG.error(theError, e);

      theError = StringUtils.isBlank(theError) ? "" : (theError + ", ");
      this.getResultMetadata().appendResultMessage(
          theError + ExceptionUtils.getFullStackTrace(e));
      this.assignResultCode(wsStemSaveResultsCodeOverride);

    }
  }

  /**
   * @see edu.internet2.middleware.grouper.ws.rest.WsResponseBean#getResponseMetadata()
   * @return the response metadata
   */
  public WsResponseMeta getResponseMetadata() {
    return this.responseMetadata;
  }

  /**
   * @param responseMetadata1 the responseMetadata to set
   */
  public void setResponseMetadata(WsResponseMeta responseMetadata1) {
    this.responseMetadata = responseMetadata1;
  }

  
  /**
   * @param resultMetadata1 the resultMetadata to set
   */
  public void setResultMetadata(WsResultMeta resultMetadata1) {
    this.resultMetadata = resultMetadata1;
  }

  /**
   * @return the wsStem
   */
  public WsStem getWsStem() {
    return this.wsStem;
  }

  /**
   * @param wsStem1 the wsStem to set
   */
  public void setWsStem(WsStem wsStem1) {
    this.wsStem = wsStem1;
  }

}
