package kpbinc.cs462.shared.event;

import kpbinc.cs462.shared.model.aspect.HasId;
import kpbinc.util.logging.GlobalLogUtils;

/**
 * @author Kevin Black
 *
 * @param <L> local entity ID type
 * @param <R> remote entity ID type
 */
public class EventChannel<L, R> implements HasId<Long> {

	//= Member Data ====================================================================================================
	
	private Long id;
	private L localEntityId;
	private R remoteEntityId;
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

	public L getLocalEntityId() {
		return localEntityId;
	}

	public R getRemoteEntityId() {
		return remoteEntityId;
	}

	public String getSendESL() {
		return sendESL;
	}

	public String getReceiveESL() {
		return receiveESL;
	}

	public void setLocalEntityId(L localEntityId) {
		this.localEntityId = localEntityId;
	}

	public void setRemoteEntityId(R remoteEntityId) {
		this.remoteEntityId = remoteEntityId;
	}

	public void setSendESL(String sendESL) {
		this.sendESL = sendESL;
	}

	public void setReceiveESL(String receiveESL) {
		this.receiveESL = receiveESL;
	}
	
}
