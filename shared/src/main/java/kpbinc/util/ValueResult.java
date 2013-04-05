package kpbinc.util;

import kpbinc.util.logging.GlobalLogUtils;

public class ValueResult<T> {

	//= Member Data ====================================================================================================
	
	private boolean operationSuccessful;
	private String message;
	private T value;
	
	
	//= Initialization =================================================================================================
	
	public ValueResult() {
		GlobalLogUtils.logConstruction(this);
		setOperationSuccessful(false);
		setMessage("");
		setValue(null);
	}

	public ValueResult(String message) {
		GlobalLogUtils.logConstruction(this);
		setOperationSuccessful(false);
		setMessage(message);
		setValue(null);
	}
	
	public ValueResult(String message, T value) {
		GlobalLogUtils.logConstruction(this);
		setOperationSuccessful(true);
		setMessage(message);
		setValue(value);
	}
	
	
	//= Interface ======================================================================================================

	public boolean isOperationSuccessful() {
		return operationSuccessful;
	}

	public void setOperationSuccessful(boolean operationSuccessful) {
		this.operationSuccessful = operationSuccessful;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

}
