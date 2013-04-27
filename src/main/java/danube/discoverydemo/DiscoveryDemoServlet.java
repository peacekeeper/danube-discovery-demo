package danube.discoverydemo;


import ibrokerkit.epptools4java.EppTools;

import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.webcontainer.WebContainerServlet;
import nextapp.echo.webcontainer.service.JavaScriptService;
import nextapp.echo.webcontainer.service.StaticTextService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiscoveryDemoServlet extends WebContainerServlet {

	private static final long serialVersionUID = -7856586634363745175L;

	private static final Logger log = LoggerFactory.getLogger(DiscoveryDemoServlet.class);

	private Properties properties;
	private EppTools eppTools;
	private ibrokerkit.iname4java.store.XriStore xriStore;
	private ibrokerkit.ibrokerstore.store.Store ibrokerStore;

	@Override
	public ApplicationInstance newApplicationInstance() {

		return new DiscoveryDemoApplication(this);
	}

	public void init(ServletConfig servletConfig) throws ServletException {

		super.init(servletConfig);

		log.info("Initializing...");

		this.addInitScript(JavaScriptService.forResource("CustomWaitIndicator", "danube/discoverydemo/resource/js/CustomWaitIndicator.js"));
		this.addInitStyleSheet(StaticTextService.forResource("discoverydemo.css", "text/css", "danube/discoverydemo/resource/style/discoverydemo.css"));

		log.info("Initializing complete.");
	}

	public Properties getProperties() {

		return this.properties;
	}

	public void setProperties(Properties properties) {

		this.properties = properties;
	}

	public EppTools getEppTools() {

		return this.eppTools;
	}

	public void setEppTools(EppTools eppTools) {

		this.eppTools = eppTools;
	}

	public ibrokerkit.ibrokerstore.store.Store getIbrokerStore() {

		return this.ibrokerStore;
	}

	public void setIbrokerStore(ibrokerkit.ibrokerstore.store.Store ibrokerStore) {

		this.ibrokerStore = ibrokerStore;
	}

	public ibrokerkit.iname4java.store.XriStore getXriStore() {

		return this.xriStore;
	}

	public void setXriStore(ibrokerkit.iname4java.store.XriStore xriStore) {

		this.xriStore = xriStore;
	}
}