package kpbinc.cs462.shared.controller.context;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import kpbinc.net.URLKeySymbols;
import kpbinc.util.logging.GlobalLogUtils;

public class ContextPaths {
	
	//= Member Data ====================================================================================================
	
	@Autowired
	private ServletContext servletContext;
	
	private String dynamicRelativePath;
	private String staticRelativePath;
	
	
	//= Initialization =================================================================================================
	
	public ContextPaths() {
		GlobalLogUtils.logConstruction(this);
		setDynamicRelativePath("");
		setStaticRelativePath("");
	}
	

	//= Interface ======================================================================================================

	public String getBasePath() {
		String path = servletContext.getContextPath();
		return path;
	}

	public String getDynamicPath() {
		String path = buildBaseRelativePath(getDynamicRelativePath());
		return path;
	}
	
	public String getDynamicRelativePath() {
		return dynamicRelativePath;
	}

	public void setDynamicRelativePath(String dynamicRelativePath) {
		this.dynamicRelativePath = dynamicRelativePath;
	}

	public String getStaticPath() {
		String path = buildBaseRelativePath(getStaticRelativePath());
		return path;
	}
	
	public String getStaticRelativePath() {
		return staticRelativePath;
	}

	public void setStaticRelativePath(String staticRelativePath) {
		this.staticRelativePath = staticRelativePath;
	}
	
	
	//= Support ========================================================================================================
	
	private String buildBaseRelativePath(String relativePath) {
		StringBuilder builder = new StringBuilder(getBasePath());
		if (StringUtils.isNotBlank(relativePath)) {
			builder.append(URLKeySymbols.PATH_SEPARATOR).append(relativePath);
		}
		String path = builder.toString();
		return path;
	}
	
}
