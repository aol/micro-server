package com.oath.micro.server.spring.boot;

import com.oath.micro.server.Plugin;
import com.oath.micro.server.spring.SpringBuilder;

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
