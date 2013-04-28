package danube.discoverydemo.ui.parties.cloudserviceprovider;

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
import danube.discoverydemo.DiscoveryDemoApplication;
import danube.discoverydemo.parties.impl.CloudServiceProviderParty;
import danube.discoverydemo.ui.xdi.XdiEndpointPanel;
import echopoint.ImageIcon;

public class CloudServiceProviderContentPane extends ContentPane {

	private static final long serialVersionUID = 5781883512857770059L;

	protected ResourceBundle resourceBundle;

	private CloudServiceProviderParty cloudServiceProviderParty;

	private XdiEndpointPanel xdiEndpointPanel;

	public CloudServiceProviderContentPane() {
		super();

		// Add design-time configured components.
		initComponents();

		this.cloudServiceProviderParty = DiscoveryDemoApplication.getApp().getCloudServiceProviderParty();
	}

	@Override
	public void init() {

		super.init();

		this.xdiEndpointPanel.setData(this.cloudServiceProviderParty.getXdiEndpoint());
	}

	@Override
	public void dispose() {

		super.dispose();
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
				"/danube/discoverydemo/resource/image/cloudserviceprovider.png");
		imageIcon2.setIcon(imageReference1);
		imageIcon2.setHeight(new Extent(48, Extent.PX));
		imageIcon2.setWidth(new Extent(48, Extent.PX));
		row2.add(imageIcon2);
		Label label2 = new Label();
		label2.setStyleName("Header");
		label2.setText("Cloud Service Provider");
		row2.add(label2);
		Column column1 = new Column();
		column1.setCellSpacing(new Extent(10, Extent.PX));
		splitPane1.add(column1);
		xdiEndpointPanel = new XdiEndpointPanel();
		column1.add(xdiEndpointPanel);
	}
}
