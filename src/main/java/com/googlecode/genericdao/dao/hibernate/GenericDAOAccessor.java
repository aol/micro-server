package com.googlecode.genericdao.dao.hibernate;

public class GenericDAOAccessor {

	
	public static void setTargetType(GenericDAOImpl dao,Class targetType){
		dao.persistentClass =targetType;
	}
}
