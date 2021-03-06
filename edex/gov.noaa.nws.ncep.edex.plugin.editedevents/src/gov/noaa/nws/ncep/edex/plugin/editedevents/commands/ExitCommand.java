package gov.noaa.nws.ncep.edex.plugin.editedevents.commands;

import com.raytheon.uf.common.status.IUFStatusHandler;
import com.raytheon.uf.common.status.UFStatus;

import gov.noaa.nws.ncep.common.dataplugin.editedevents.exception.EditedEventsException;
import gov.noaa.nws.ncep.common.dataplugin.editedevents.request.ExitRequest;
import gov.noaa.nws.ncep.common.dataplugin.editedevents.request.intf.IRequest;
import gov.noaa.nws.ncep.common.dataplugin.editedevents.response.ExitResponse;
import gov.noaa.nws.ncep.common.dataplugin.editedevents.response.intf.IResponse;
import gov.noaa.nws.ncep.common.dataplugin.editedevents.results.ExitResults;
import gov.noaa.nws.ncep.common.dataplugin.editedevents.results.intf.IResults;
import gov.noaa.nws.ncep.edex.plugin.editedevents.dao.EventsDao;

/**
 * The command class that is executed to perform certain operations
 * when the Edited Events application is closed.
 * 
 *  
 * <pre>
 * 
 * SOFTWARE HISTORY
 * 
 * Date         Ticket#    Engineer    Description
 * ------------ ---------- ----------- --------------------------
 * Mar 19, 2016  R9583     sgurung     Initial creation
 * 
 * </pre>
 * 
 * @author sgurung
 * @version 1.0
 */
public class ExitCommand extends BaseCommand {

	 /**
     *  The request from the client that resulted in
     *  creating an instance of the command
     */
    private ExitRequest request = null;
    
    /**
     * 
     */
    private EventsDao eventsDao = null;
    
    /**
	 * Logger
	 */
	 private static final IUFStatusHandler statusHandler = UFStatus.getHandler(ExitCommand.class);
    
    public ExitCommand() {
    	
    }
    
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
		this.request = (ExitRequest) request;
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
		
		ExitResults results = new ExitResults();
		
		try {
			eventsDao = new EventsDao();
			
			// Dump deleted events
			int numRecordsDeleted = eventsDao.removeDeletedEvents(this.request.getBeginDTTM());
			
			// Change events age to 'OLD' for events whose age is 'NEW' or 'COR'
			int numRecordsUpdated = eventsDao.setEventsAgeToOld(this.request.getBeginDTTM());
			
			// Set changeflag to 0 
			numRecordsUpdated += eventsDao.setChangeFlagToZero(this.request.getBeginDTTM());
			
			results.setNumRecordsDeleted(numRecordsDeleted);
			results.setNumRecordsUpdated(numRecordsUpdated);
		
		} catch (Exception e) {
			error = new EditedEventsException(
					"ERROR - Exception occured while executing the ExitCommand.",e);
			e.printStackTrace();
		}
		
		this.setEndTime();
		
		return createResponse(results);
	}

	/**
	 * Create a response based on the results provided
	 *  
	 * @param results
	 * @return IResponse
	 */
	private IResponse createResponse(IResults results) { 
		ExitResponse response = new ExitResponse();

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
