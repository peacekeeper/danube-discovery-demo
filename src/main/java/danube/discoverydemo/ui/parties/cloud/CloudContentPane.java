package danube.discoverydemo.ui.parties.cloud;

import java.util.ResourceBundle;

import nextapp.echo.app.Column;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Row;
import nextapp.echo.app.SplitPane;
import nextapp.echo.app.layout.SplitPaneLayoutData;
import xdi2.connector.facebook.mapping.FacebookMapping;
import xdi2.core.constants.XDIPolicyConstants;
import xdi2.core.xri3.XDI3Segment;
import danube.discoverydemo.DiscoveryDemoApplication;
import danube.discoverydemo.events.ApplicationEvent;
import danube.discoverydemo.events.ApplicationListener;
import danube.discoverydemo.events.ApplicationXdiEndpointOpenedEvent;
import danube.discoverydemo.parties.impl.CloudParty;
import danube.discoverydemo.ui.MessageDialog;
import danube.discoverydemo.ui.xdi.XdiEndpointPanel;
import danube.discoverydemo.xdi.XdiEndpoint;
import echopoint.ImageIcon;
import danube.discoverydemo.ui.parties.cloud.XriSignInPanel;
import danube.discoverydemo.ui.parties.cloud.XdiEntityColumn;
import danube.discoverydemo.ui.parties.cloud.FacebookConnectorPanel;

public class CloudContentPane extends ContentPane implements ApplicationListener {

	private static final long serialVersionUID = 5781883512857770059L;

	protected ResourceBundle resourceBundle;

	private XdiEntityColumn xdiEntityColumn;
	private FacebookConnectorPanel facebookConnectorPanel;
	private XdiEndpointPanel xdiEndpointPanel;

	public CloudContentPane() {
		super();

		// Add design-time configured components.
		initComponents();
	}

	@Override
	public void init() {

		super.init();
	}

	@Override
	public void dispose() {

		super.dispose();
	}

	public void refresh() {

		CloudParty cloudParty = DiscoveryDemoApplication.getApp().getCloudParty();

		if (cloudParty != null) {

			try {

				this.xdiEndpointPanel.setData(cloudParty.getXdiEndpoint());
				this.xdiEntityColumn.setData(cloudParty.getXdiEndpoint(), cloudParty.getXdiEndpoint().getCloudNumber(), null);
				this.facebookConnectorPanel.setData(cloudParty.getXdiEndpoint(), XDI3Segment.create("" + FacebookMapping.XRI_S_FACEBOOK_CONTEXT + cloudParty.getXdiEndpoint().getCloudNumber() + XDIPolicyConstants.XRI_S_OAUTH_TOKEN), null);
			} catch (Exception ex) {

				MessageDialog.problem("Sorry, a problem occurred while retrieving your Personal Data: " + ex.getMessage(), ex);
				return;
			}
		}
	}

	@Override
	public void onApplicationEvent(ApplicationEvent applicationEvent) {

		if (applicationEvent instanceof ApplicationXdiEndpointOpenedEvent) {

			XdiEndpoint xdiEndpoint = ((ApplicationXdiEndpointOpenedEvent) applicationEvent).getXdiEndpoint();

			this.xdiEntityColumn.setData(xdiEndpoint, xdiEndpoint.getCloudNumber(), null);
		}
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		this.setInsets(new Insets(new Extent(10, Extent.PX)));
		SplitPane splitPane1 = new SplitPane();
		splitPane1.setStyleName("Default");
		splitPane1.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		splitPane1.setResizable(false);
		splitPane1.setSeparatorVisible(false);
		add(splitPane1);
		Row row2 = new Row();
		row2.setCellSpacing(new Extent(10, Extent.PX));
		SplitPaneLayoutData row2LayoutData = new SplitPaneLayoutData();
		row2LayoutData.setMinimumSize(new Extent(70, Extent.PX));
		row2LayoutData.setMaximumSize(new Extent(70, Extent.PX));
		row2.setLayoutData(row2LayoutData);
		splitPane1.add(row2);
		ImageIcon imageIcon2 = new ImageIcon();
		ResourceImageReference imageReference1 = new ResourceImageReference(
				"/danube/discoverydemo/resource/image/cloud.png");
		imageIcon2.setIcon(imageReference1);
		imageIcon2.setHeight(new Extent(48, Extent.PX));
		imageIcon2.setWidth(new Extent(48, Extent.PX));
		row2.add(imageIcon2);
		Label label2 = new Label();
		label2.setStyleName("Header");
		label2.setText("Cloud");
		row2.add(label2);
		Column column1 = new Column();
		splitPane1.add(column1);
		xdiEndpointPanel = new XdiEndpointPanel();
		column1.add(xdiEndpointPanel);
		XriSignInPanel xriSignInPanel1 = new XriSignInPanel();
		column1.add(xriSignInPanel1);
		xdiEntityColumn = new XdiEntityColumn();
		column1.add(xdiEntityColumn);
		facebookConnectorPanel = new FacebookConnectorPanel();
		facebookConnectorPanel.setId("facebookConnectorPanel");
		column1.add(facebookConnectorPanel);
	}
}
