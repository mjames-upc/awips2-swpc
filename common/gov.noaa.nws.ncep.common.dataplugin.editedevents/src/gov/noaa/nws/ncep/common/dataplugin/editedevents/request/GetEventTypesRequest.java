package gov.noaa.nws.ncep.common.dataplugin.editedevents.request;

import gov.noaa.nws.ncep.common.dataplugin.editedevents.request.intf.IRequest;

import com.raytheon.uf.common.serialization.annotations.DynamicSerialize;
import com.raytheon.uf.common.serialization.annotations.DynamicSerializeElement;

/**
 * Class for allowing the client to request the back-end to
 * obtain a list of all event types
 * 
 * <pre>
 * 
 * SOFTWARE HISTORY
 * 
 * Date         Ticket#    Engineer    Description
 * ------------ ---------- ----------- --------------------------
 * Dec 12, 2015  R9583     jtravis     Initial creation
 * 
 * </pre>
 * 
 * @author jtravis
 * @version 1.0
 */
@DynamicSerialize
public class GetEventTypesRequest extends Request<GetEventTypesRequest> implements
        IRequest {

    /**
     * The Request ID
     */
    @DynamicSerializeElement
    private final long id;

	/**
	 * 
	 */
	public GetEventTypesRequest() {
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
        //TODO add check
    	boolean isValid = true;
    	
        return isValid;
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
    	;;
    }

}
