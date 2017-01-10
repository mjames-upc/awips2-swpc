package gov.noaa.nws.ncep.common.dataplugin.editedregions.request;

import com.raytheon.uf.common.serialization.annotations.DynamicSerialize;
import com.raytheon.uf.common.serialization.annotations.DynamicSerializeElement;

import gov.noaa.nws.ncep.common.dataplugin.editedregions.request.intf.IRequest;

/**
 * Request class for Revisiting old bins when an event has been
 * moved to a new bin
 * 
 * <pre>
 * 
 * SOFTWARE HISTORY
 * 
 * Date         Ticket#    Engineer    Description
 * ------------ ---------- ----------- --------------------------
 * Jan 14, 2016  R9583     jtravis     Initial creation
 * 
 * </pre>
 * 
 * @author jtravis
 * @version 1.0
 */
@DynamicSerialize
public class ReVisitOldBinRequest extends Request<ReVisitOldBinRequest> implements IRequest {

    /**
     * The request id
     */
    @DynamicSerializeElement
    private final long id;
	
	/**
	 * 
	 */
	public ReVisitOldBinRequest() {
		this.id = System.currentTimeMillis();
	}

	/* (non-Javadoc)
	 * @see gov.noaa.nws.ncep.common.dataplugin.editedevents.request.intf.IRequest#getId()
	 */
	@Override
	public long getId() {
		return this.id;
	}

	/* (non-Javadoc)
	 * @see gov.noaa.nws.ncep.common.dataplugin.editedevents.request.intf.IRequest#setId(long)
	 */
	@Override
	public void setId(long Id) {
        // The id is set when the request is initialized
        // but the system still requires the method for
        // serialization / deserialization through the gateway.
        //
        // thus, this method does not actually change the id
        //
        // TODO - verify this
        ;
        ;
	}

	/* (non-Javadoc)
	 * @see gov.noaa.nws.ncep.common.dataplugin.editedevents.request.intf.IRequest#isValid()
	 */
	@Override
	public boolean isValid() {
		return true;
	}

}