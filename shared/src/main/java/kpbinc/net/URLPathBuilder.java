package kpbinc.net;

import org.apache.commons.lang3.StringUtils;

public class URLPathBuilder {

	public static String build(String... pathParts) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0, l = pathParts.length; i < l; i++) {
			String pathPart = pathParts[i];
			String urlEncodedPathPart = UTF8URLEncoder.encode(pathPart);
			if (StringUtils.isNotBlank(urlEncodedPathPart)) {
				builder.append(URLKeySymbols.PATH_SEPARATOR).append(urlEncodedPathPart);
			}
		}
		String path = builder.toString();
		return path;
	}

}
