package com.aol.micro.server.spring.boot;

import com.aol.micro.server.Plugin;
import com.aol.micro.server.spring.SpringBuilder;

/**
 * 
 * @author johnmcclean
 *
 */
public class BootPlugin implements Plugin{
	
	
	/**
	 * @return Engine for building Spring Context
	 */
	public SpringBuilder springBuilder(){
		return new BootApplicationConfigurator();
	}
	

}
