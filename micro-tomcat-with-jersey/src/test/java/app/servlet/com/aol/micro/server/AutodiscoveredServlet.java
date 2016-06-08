package app.servlet.com.aol.micro.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.aol.micro.server.auto.discovery.AutoServletConfiguration;

@Component
public class AutodiscoveredServlet extends HttpServlet implements AutoServletConfiguration {

	@Override
	public String[] getMapping() {
		return new String[] { "/servlet" };
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setContentType("text/html");
		resp.getWriter().write("hello world");
	}

}
