package kpbinc.cs462.shared.model.mixin;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class SimpleGrantedAuthorityJacksonAnnotatedMixin {

	public SimpleGrantedAuthorityJacksonAnnotatedMixin(
			@JsonProperty("authority") String authority) {
		// place-holder for introspection of mixed-in annotations
	}

}
