package kpbinc.cs462.shared.model;

import kpbinc.cs462.shared.event.Event;
import kpbinc.cs462.shared.model.aspect.HasId;
import kpbinc.util.logging.GlobalLogUtils;

public class LoggedEvent implements HasId<Long> {

	//= Class Data =====================================================================================================
	
	public enum TransmissionType {
		SENT, // successful
		TRIED_TO_SEND, // tried but not successful
		RECEIVED;
	}
	
	
	//= Member Data ====================================================================================================
	
	private Long id;
	private Event event;
	private TransmissionType transmissionType;
	private String esl;
	
	
	//= Initialization =================================================================================================
	
	public LoggedEvent() {
		GlobalLogUtils.logConstruction(this);
	}

	
	//= Interface ======================================================================================================
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public TransmissionType getTransmissionType() {
		return transmissionType;
	}

	public void setTransmissionType(TransmissionType transmissionType) {
		this.transmissionType = transmissionType;
	}

	public String getEsl() {
		return esl;
	}

	public void setEsl(String esl) {
		this.esl = esl;
	}

}
