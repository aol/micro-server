package com.aol.micro.server.rest;

import java.util.Map;

import javax.servlet.http.HttpServlet;

import lombok.Value;

@Value
public class RestConfiguration {

	HttpServlet  servlet;
	String name;
	String providersName;
	Map<String,String> initParams;
}
