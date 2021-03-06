package gov.noaa.nws.ncep.edex.plugin.editedevents.commands;

import java.util.Calendar;

import gov.noaa.nws.ncep.common.dataplugin.editedevents.Event;
import gov.noaa.nws.ncep.common.dataplugin.editedevents.exception.EditedEventsException;
import gov.noaa.nws.ncep.common.dataplugin.editedevents.request.AddEventRequest;
import gov.noaa.nws.ncep.common.dataplugin.editedevents.request.intf.IRequest;
import gov.noaa.nws.ncep.common.dataplugin.editedevents.response.AddEventResponse;
import gov.noaa.nws.ncep.common.dataplugin.editedevents.response.intf.IResponse;
import gov.noaa.nws.ncep.common.dataplugin.editedevents.results.AddEventResults;
import gov.noaa.nws.ncep.common.dataplugin.editedevents.results.intf.IResults;
import gov.noaa.nws.ncep.edex.plugin.editedevents.dao.EventsDao;

import com.raytheon.uf.common.dataplugin.PluginException;
import com.raytheon.uf.edex.database.DataAccessLayerException;

/**
 * The command class that is executed to add an event
 * 
 *  
 * <pre>
 * 
 * SOFTWARE HISTORY
 * 
 * Date         Ticket#    Engineer    Description
 * ------------ ---------- ----------- --------------------------
 * Jan 9, 2016  R9583      jtravis     Initial creation
 * 
 * </pre>
 * 
 * @author jtravis
 * @version 1.0
 */
public class AddEventCommand extends BaseCommand {

    /**
     *  The request from the client that resulted in
     *  creating an instance of the command
     */
    private AddEventRequest request = null;
	
	/**
	 * Default Constructor
	 */
	public AddEventCommand() {}

	/* (non-Javadoc)
	 * @see gov.noaa.nws.ncep.edex.plugin.editedevents.commands.intf.ICommand#getError()
	 */
	@Override
	public EditedEventsException getError() {
		return this.error;
	}

	/* (non-Javadoc)
	 * @see gov.noaa.nws.ncep.edex.plugin.editedevents.commands.intf.ICommand#hasError()
	 */
	@Override
	public boolean hasError() {
        if (this.error == null) {
            return false;
        } else {
            return true;
        }
	}

	/* (non-Javadoc)
	 * @see gov.noaa.nws.ncep.edex.plugin.editedevents.commands.intf.ICommand#getStartTime()
	 */
	@Override
	public long getStartTime() {
		return this.startTime;
	}

	/* (non-Javadoc)
	 * @see gov.noaa.nws.ncep.edex.plugin.editedevents.commands.intf.ICommand#getEndTime()
	 */
	@Override
	public long getEndTime() {
		return this.endTime;
	}

	/* (non-Javadoc)
	 * @see gov.noaa.nws.ncep.edex.plugin.editedevents.commands.intf.ICommand#getProcessingTime()
	 */
	@Override
	public long getProcessingTime() {
        long time = 0L;

        time = this.getEndTime() - this.getStartTime();

        return time;
	}

	/* (non-Javadoc)
	 * @see gov.noaa.nws.ncep.edex.plugin.editedevents.commands.intf.ICommand#isValid()
	 * TODO - do we really need to validate the request here...
	 * TODO - the request should be validated before adding it to the command
	 */
	@Override
	public boolean isValid() {
		if (this.request != null && 
				this.request.isValid()) {

			// a request is associated with the command
			// and the request is valid
			return true;

		} else {
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see gov.noaa.nws.ncep.edex.plugin.editedevents.commands.intf.ICommand#setRequest(gov.noaa.nws.ncep.common.dataplugin.editedevents.request.intf.IRequest)
	 */
	@Override
	public void setRequest(IRequest request) {
		this.request = (AddEventRequest) request;
		
	}

	/* (non-Javadoc)
	 * @see gov.noaa.nws.ncep.edex.plugin.editedevents.commands.intf.ICommand#getRequest()
	 */
	@Override
	public IRequest getRequest() {
		return this.request;
	}

	/* (non-Javadoc)
	 * @see gov.noaa.nws.ncep.edex.plugin.editedevents.commands.intf.ICommand#execute()
	 */
	@Override
	public IResponse execute() {
		this.setStartTime();
		
		AddEventResults results = null;
		Integer eventID = this.request.getEventID();
		Event event = this.request.getEvent();
		
		try {
			EventsDao dao = new EventsDao();
			
			if (eventID == null || eventID == 0) {
				event.setInsertTime(Calendar.getInstance());
				eventID = dao.persist(event);
				this.request.setEventID(eventID);
			} else {
				event.setId(eventID);
				dao.saveOrUpdate(event);
			}			
			
			if (eventID != null) {			
			
				event = (Event) dao.queryById(eventID);
				
				results = new AddEventResults();
				results.setEvent(event);
			}
			
		} catch (PluginException e) {
			String errorMsg = "ERROR - PluginException Occured When Executing AddEventCommand";
			EditedEventsException exception = new EditedEventsException(errorMsg);
			exception.setStackTrace(e.getStackTrace());
			this.error = exception;
		} catch (DataAccessLayerException e) {
			String errorMsg = "ERROR - DataAccessLayerException Occured When Executing AddEventCommand";
			EditedEventsException exception = new EditedEventsException(errorMsg);
			exception.setStackTrace(e.getStackTrace());
			this.error = exception;
		}
		
		this.setEndTime();
        
        return this.createResponse(results);
	}
	
	/**
	 * @param results
	 * @return IResponse
	 */
	private IResponse createResponse(IResults results) { 
		AddEventResponse response = new AddEventResponse();

        if (this.hasError()) {
        	response.setError(this.getError());
        } else {
        	response.setResults(results); 
        }

        // populate the response and the results
        response.setRequest(this.getRequest()); 
        response.setProcessingTime(this.getProcessingTime());
        
        return response;
		
	}

}
