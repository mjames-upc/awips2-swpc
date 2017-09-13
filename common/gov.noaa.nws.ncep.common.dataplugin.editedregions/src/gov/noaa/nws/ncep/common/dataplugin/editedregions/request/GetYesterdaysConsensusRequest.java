package gov.noaa.nws.ncep.common.dataplugin.editedregions.request;

import java.util.Calendar;

import com.raytheon.uf.common.serialization.annotations.DynamicSerialize;
import com.raytheon.uf.common.serialization.annotations.DynamicSerializeElement;

import gov.noaa.nws.ncep.common.dataplugin.editedregions.request.intf.IRequest;

/**
 * Class for allowing the client to request the back-end to obtain the values
 * that make up yesterdays consensus
 * 
 * @author jtravis
 * @version 1.0
 */
@DynamicSerialize
public class GetYesterdaysConsensusRequest
        extends Request<GetYesterdaysConsensusRequest> implements IRequest {

    /**
     * The Requests ID
     */
    @DynamicSerializeElement
    private final long id;

    /**
     * The region number for which consensus is to be computed
     */
    @DynamicSerializeElement
    private Integer region = null;

    /**
     * The date for which consensus is to be computed
     */
    @DynamicSerializeElement
    private Long dttm = null;

    /**
     * 
     */
    public GetYesterdaysConsensusRequest() {
        this.id = System.currentTimeMillis();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.noaa.nws.ncep.common.dataplugin.editedevents.request.IRequest#getID()
     */
    @Override
    public long getId() {
        return this.id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.noaa.nws.ncep.common.dataplugin.editedevents.request.IRequest#isValid
     * ()
     */
    @Override
    public boolean isValid() {
        boolean valid = false;

        if (this.region != null && this.dttm != null) {
            valid = true;
        }

        return valid;
    }

    @Override
    public void setId(long ID) {
        // The id is set when the request is initialized
        // but the system still requires the method for
        // serialization / deserialization through the gateway.
        //
        // thus, this method does not actually change the id
        //
        // TODO - verify this
        ;
    }

    /**
     * @return the region
     */
    public int getRegion() {
        return region;
    }

    /**
     * @param region
     *            the region to set
     */
    public void setRegion(Integer region) {
        this.region = region;
    }

    /**
     * @return the dttm
     */
    public long getDttm() {
        return this.dttm;
    }

    /**
     * @param the
     *            dttm to set
     */
    public void setDttm(Calendar date) {

        if (date == null) {
            this.dttm = 0L;
        } else {
            this.dttm = date.getTimeInMillis();
        }

    }

}
