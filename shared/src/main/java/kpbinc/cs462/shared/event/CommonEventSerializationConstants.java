package kpbinc.cs462.shared.event;

public class CommonEventSerializationConstants {

	//= Class Data =====================================================================================================
	
	private static final String RESERVED_INDICATOR = "_";
	
	
	//= Constants ======================================================================================================
	
	public static final String DOMAIN_KEY = "_domain";
	
	public static final String NAME_KEY = "_name";

	
	//= Utilities ======================================================================================================
	
	public static boolean isReservedAttributeName(String name) {
		boolean result = (   name != null
						  && name.startsWith(RESERVED_INDICATOR));
		return result;
	}
	
}
