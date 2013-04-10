package kpbinc.cs462.shared.event;

public class CommonEventSerializationConstants {

	//= Class Data =====================================================================================================
	
	private static final String RESERVED_INDICATOR = "_";
	
	
	//= Constants ======================================================================================================
	
	public static final String NON_STANDARD_DOMAIN_KEY = "domain";
	public static final String STANDARD_DOMAIN_KEY = makeReservedAttributeName(NON_STANDARD_DOMAIN_KEY);
	
	public static final String NON_STANDARD_NAME_KEY = "name";
	public static final String STANDARD_NAME_KEY = makeReservedAttributeName(NON_STANDARD_NAME_KEY);

	
	//= Utilities ======================================================================================================
	
	public static boolean isReservedAttributeName(String name) {
		boolean result = (   name != null
						  && name.startsWith(RESERVED_INDICATOR));
		return result;
	}
	
	public static String makeReservedAttributeName(String mainName) {
		String reservedName = mainName;
		if (!isReservedAttributeName(reservedName)) {
			reservedName = String.format("%s%s", RESERVED_INDICATOR, mainName);
		}
		return reservedName;
	}
	
}
