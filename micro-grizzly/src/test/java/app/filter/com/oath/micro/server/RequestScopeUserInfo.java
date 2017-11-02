package app.filter.com.oath.micro.server;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value=WebApplicationContext.SCOPE_REQUEST,proxyMode=ScopedProxyMode.TARGET_CLASS)
public class RequestScopeUserInfo {
		@Autowired
	 private HttpServletRequest request;
		
		public void print(){
			System.out.println(request.getAttribute("oc.info"));
		}
}
