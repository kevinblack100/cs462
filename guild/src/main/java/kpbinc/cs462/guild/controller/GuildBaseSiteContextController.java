package kpbinc.cs462.guild.controller;

import kpbinc.cs462.shared.controller.SharedBaseSiteContextController;
import kpbinc.util.logging.GlobalLogUtils;

/**
 * @see SharedBaseSiteContextController
 * 
 * @author Kevin Black
 */
public class GuildBaseSiteContextController extends
		SharedBaseSiteContextController {

	public GuildBaseSiteContextController() {
		super();
		GlobalLogUtils.logConstruction(this);
	}

}
