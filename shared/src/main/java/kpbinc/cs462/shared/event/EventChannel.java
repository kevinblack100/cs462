package kpbinc.cs462.shared.event;

import kpbinc.cs462.shared.model.aspect.HasId;
import kpbinc.util.logging.GlobalLogUtils;

/**
 * @author Kevin Black
 *
 * @param <X> local entity ID type
 * @param <Y> remote entity ID type
 */
public abstract class EventChannel<X, Y> implements HasId<Long> {

	//= Member Data ====================================================================================================
	
	private Long id;
	private X localEntityId;
	private Y remoteEntityId;
	private String sendESL;
	private String receiveESL;
	
	
	//= Initialization =================================================================================================
	
	public EventChannel() {
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
	
	public X getLocalEntityId() {
		return localEntityId;
	}
	
	public void setLocalEntityId(X localEntityId) {
		this.localEntityId = localEntityId;
	}

	public Y getRemoteEntityId() {
		return remoteEntityId;
	}

	public void setRemoteEntityId(Y remoteEntityId) {
		this.remoteEntityId = remoteEntityId;
	}
	
	public String getSendESL() {
		return sendESL;
	}
	
	public void setSendESL(String sendESL) {
		this.sendESL = sendESL;
	}

	public String getReceiveESL() {
		return receiveESL;
	}
	
	public void setReceiveESL(String receiveESL) {
		this.receiveESL = receiveESL;
	}
	
}
