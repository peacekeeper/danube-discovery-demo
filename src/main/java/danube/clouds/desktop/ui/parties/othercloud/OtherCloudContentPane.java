package danube.clouds.desktop.ui.parties.othercloud;

import java.util.ResourceBundle;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Button;
import nextapp.echo.app.Column;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Row;
import nextapp.echo.app.SelectField;
import nextapp.echo.app.SplitPane;
import nextapp.echo.app.TextField;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.layout.RowLayoutData;
import nextapp.echo.app.layout.SplitPaneLayoutData;
import nextapp.echo.app.list.DefaultListModel;
import xdi2.core.xri3.XDI3Segment;
import xdi2.discovery.XDIDiscoveryResult;
import danube.clouds.desktop.DanubeCloudsDesktopApplication;
import danube.clouds.desktop.parties.RegistryParty;
import danube.clouds.desktop.parties.impl.AnonymousParty;
import danube.clouds.desktop.parties.impl.MyCloudParty;
import danube.clouds.desktop.parties.impl.OtherCloudParty;
import danube.clouds.desktop.ui.MainWindow;
import danube.clouds.desktop.ui.MessageDialog;
import danube.clouds.desktop.ui.cloud.CloudDataWindowPane;
import danube.clouds.desktop.ui.xdi.XdiEndpointPanel;
import echopoint.ImageIcon;

public class OtherCloudContentPane extends ContentPane {

	private static final long serialVersionUID = 5781883512857770059L;

	protected ResourceBundle resourceBundle;

	private TextField xriTextField;
	private TextField endpointUriTextField;
	private SelectField registrySelectField;
	private Button cloudDataButton;

	private XdiEndpointPanel xdiEndpointPanel;

	public OtherCloudContentPane() {
		super();

		// Add design-time configured components.
		initComponents();
	}

	@Override
	public void init() {

		super.init();

		this.registrySelectField.removeAll();

		this.registrySelectField.setModel(new DefaultListModel(DanubeCloudsDesktopApplication.getApp().getRegistryParties().toArray()));
	}

	@Override
	public void dispose() {

		super.dispose();
	}

	public void refresh() {

		OtherCloudParty otherCloudParty = DanubeCloudsDesktopApplication.getApp().getOtherCloudParty();

		if (otherCloudParty != null) {

			this.xdiEndpointPanel.setData(otherCloudParty.getXdiEndpoint());

			this.cloudDataButton.setEnabled(true);
		}
	}

	private void onDiscoverFromXriActionPerformed(ActionEvent e) {

		// discovery

		AnonymousParty anonymousParty = DanubeCloudsDesktopApplication.getApp().getAnonymousParty();
		RegistryParty registryParty = (RegistryParty) this.registrySelectField.getSelectedItem();

		XDI3Segment xri = XDI3Segment.create(this.xriTextField.getText());
		XDIDiscoveryResult discoveryResult;

		try {

			discoveryResult = registryParty.discoverFromXri(anonymousParty, xri);
		} catch (Exception ex) {

			MessageDialog.problem("Sorry, we could not discover the Cloud: " + ex.getMessage(), ex);
			return;
		}

		// create other cloud party

		String endpointUri = discoveryResult.getEndpointUri();
		XDI3Segment cloudNumber = discoveryResult.getCloudNumber();

		OtherCloudParty otherCloudParty = OtherCloudParty.create(endpointUri, xri, cloudNumber, null);

		DanubeCloudsDesktopApplication.getApp().setOtherCloudParty(otherCloudParty);

		// done

		this.refresh();
	}

	private void onDiscoverFromEndpointUriActionPerformed(ActionEvent e) {

		RegistryParty registryParty = DanubeCloudsDesktopApplication.getApp().getGlobalRegistryParty();
		AnonymousParty anonymousParty = DanubeCloudsDesktopApplication.getApp().getAnonymousParty();

		String endpointUri = this.endpointUriTextField.getText();
		XDIDiscoveryResult discoveryResult;

		try {


			discoveryResult = registryParty.discoverFromEndpointUri(anonymousParty, endpointUri);
		} catch (Exception ex) {

			MessageDialog.problem("Sorry, we could not discover the Cloud: " + ex.getMessage(), ex);
			return;
		}

		// create other cloud party

		XDI3Segment xri = discoveryResult.getCloudNumber();
		XDI3Segment cloudNumber = discoveryResult.getCloudNumber();

		OtherCloudParty otherCloudParty = OtherCloudParty.create(endpointUri, xri, cloudNumber, null);

		DanubeCloudsDesktopApplication.getApp().setOtherCloudParty(otherCloudParty);

		// done

		this.refresh();
	}

	private void onCloudDataActionPerformed(ActionEvent e) {

		MyCloudParty myCloudParty = DanubeCloudsDesktopApplication.getApp().getMyCloudParty();
		OtherCloudParty otherCloudParty = DanubeCloudsDesktopApplication.getApp().getOtherCloudParty();

		if (myCloudParty == null) {

			MessageDialog.warning("My Cloud not open.");
			return;
		}

		if (otherCloudParty == null) {

			MessageDialog.warning("Other Cloud not open.");
			return;
		}

		CloudDataWindowPane cloudDataWindowPane = new CloudDataWindowPane();
		cloudDataWindowPane.setData(myCloudParty, otherCloudParty, null, true);

		MainWindow.findMainContentPane(this).add(cloudDataWindowPane);
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
				"/danube/clouds/desktop/resource/image/cloud.png");
		imageIcon2.setIcon(imageReference1);
		imageIcon2.setHeight(new Extent(48, Extent.PX));
		imageIcon2.setWidth(new Extent(48, Extent.PX));
		row2.add(imageIcon2);
		Label label2 = new Label();
		label2.setStyleName("Header");
		label2.setText("Other Cloud");
		row2.add(label2);
		Column column1 = new Column();
		column1.setCellSpacing(new Extent(10, Extent.PX));
		splitPane1.add(column1);
		Row row1 = new Row();
		row1.setCellSpacing(new Extent(20, Extent.PX));
		column1.add(row1);
		ImageIcon imageIcon4 = new ImageIcon();
		ResourceImageReference imageReference2 = new ResourceImageReference(
				"/danube/clouds/desktop/resource/image/cloud_big_other.png");
		imageIcon4.setIcon(imageReference2);
		imageIcon4.setHeight(new Extent(200, Extent.PX));
		imageIcon4.setWidth(new Extent(200, Extent.PX));
		row1.add(imageIcon4);
		Column column3 = new Column();
		column3.setCellSpacing(new Extent(20, Extent.PX));
		RowLayoutData column3LayoutData = new RowLayoutData();
		column3LayoutData.setAlignment(new Alignment(Alignment.DEFAULT,
				Alignment.TOP));
		column3LayoutData.setWidth(new Extent(600, Extent.PX));
		column3.setLayoutData(column3LayoutData);
		row1.add(column3);
		Row row3 = new Row();
		row3.setCellSpacing(new Extent(10, Extent.PX));
		column3.add(row3);
		Label label5 = new Label();
		label5.setStyleName("Default");
		label5.setText("Registry:");
		row3.add(label5);
		registrySelectField = new SelectField();
		registrySelectField.setInsets(new Insets(new Extent(5, Extent.PX)));
		row3.add(registrySelectField);
		Column column2 = new Column();
		column2.setCellSpacing(new Extent(10, Extent.PX));
		column3.add(column2);
		Row row6 = new Row();
		row6.setCellSpacing(new Extent(10, Extent.PX));
		column2.add(row6);
		Label label1 = new Label();
		label1.setStyleName("Default");
		label1.setText("Cloud Name / Cloud Number:");
		row6.add(label1);
		xriTextField = new TextField();
		xriTextField.setStyleName("Default");
		xriTextField.setWidth(new Extent(300, Extent.PX));
		xriTextField.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;
	
			public void actionPerformed(ActionEvent e) {
				onDiscoverFromXriActionPerformed(e);
			}
		});
		row6.add(xriTextField);
		Row row5 = new Row();
		column2.add(row5);
		Button button1 = new Button();
		button1.setStyleName("Default");
		button1.setText("Discover");
		RowLayoutData button1LayoutData = new RowLayoutData();
		button1LayoutData.setWidth(new Extent(200, Extent.PX));
		button1.setLayoutData(button1LayoutData);
		button1.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;
	
			public void actionPerformed(ActionEvent e) {
				onDiscoverFromXriActionPerformed(e);
			}
		});
		row5.add(button1);
		Column column4 = new Column();
		column4.setCellSpacing(new Extent(10, Extent.PX));
		column3.add(column4);
		Row row7 = new Row();
		row7.setCellSpacing(new Extent(10, Extent.PX));
		column4.add(row7);
		Label label4 = new Label();
		label4.setStyleName("Default");
		label4.setText("XDI Endpoint:");
		row7.add(label4);
		endpointUriTextField = new TextField();
		endpointUriTextField.setStyleName("Default");
		endpointUriTextField.setWidth(new Extent(400, Extent.PX));
		endpointUriTextField.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;
	
			public void actionPerformed(ActionEvent e) {
				onDiscoverFromEndpointUriActionPerformed(e);
			}
		});
		row7.add(endpointUriTextField);
		Row row4 = new Row();
		column4.add(row4);
		Button button2 = new Button();
		button2.setStyleName("Default");
		button2.setText("Discover");
		RowLayoutData button2LayoutData = new RowLayoutData();
		button2LayoutData.setWidth(new Extent(200, Extent.PX));
		button2.setLayoutData(button2LayoutData);
		button2.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;
	
			public void actionPerformed(ActionEvent e) {
				onDiscoverFromEndpointUriActionPerformed(e);
			}
		});
		row4.add(button2);
		xdiEndpointPanel = new XdiEndpointPanel();
		column1.add(xdiEndpointPanel);
		Row row8 = new Row();
		column1.add(row8);
		cloudDataButton = new Button();
		cloudDataButton.setStyleName("Default");
		cloudDataButton.setEnabled(false);
		ResourceImageReference imageReference3 = new ResourceImageReference(
				"/danube/clouds/desktop/resource/image/connect-cloud.png");
		cloudDataButton.setIcon(imageReference3);
		cloudDataButton.setText("Request Cloud Data");
		cloudDataButton.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;
	
			public void actionPerformed(ActionEvent e) {
				onCloudDataActionPerformed(e);
			}
		});
		row8.add(cloudDataButton);
	}
}
