package danube.clouds.desktop;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class DanubeCloudsDesktopSpringServlet extends HttpServlet {

	private static final long serialVersionUID = 6309712463675944196L;

	private DanubeCloudsDesktopServlet discoveryDemoServlet;

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {

		super.init(servletConfig);

		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		this.discoveryDemoServlet = (DanubeCloudsDesktopServlet) wac.getBean("DanubeCloudsDesktopServlet", DanubeCloudsDesktopServlet.class);
		this.discoveryDemoServlet.init(servletConfig);
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.discoveryDemoServlet.service(request, response);
	}

	@Override
	public void destroy() {

		super.destroy();

		this.discoveryDemoServlet.destroy();
	}
}
