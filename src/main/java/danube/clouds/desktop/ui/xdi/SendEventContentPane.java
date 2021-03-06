package danube.clouds.desktop.ui.xdi;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.EventObject;
import java.util.ResourceBundle;

import nextapp.echo.app.Column;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Font;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.Panel;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Row;
import nextapp.echo.app.SplitPane;
import nextapp.echo.app.layout.ColumnLayoutData;
import nextapp.echo.app.layout.SplitPaneLayoutData;
import nextapp.echo.extras.app.TabPane;
import nextapp.echo.extras.app.ToolTipContainer;
import nextapp.echo.extras.app.layout.TabPaneLayoutData;
import xdi2.client.events.XDISendErrorEvent;
import xdi2.client.events.XDISendEvent;
import xdi2.client.http.XDIHttpClient;
import xdi2.core.features.nodetypes.XdiPeerRoot;
import xdi2.core.xri3.XDI3Segment;
import danube.clouds.desktop.DanubeCloudsDesktopApplication;
import danube.clouds.desktop.parties.Party;

public class SendEventContentPane extends ContentPane  {

	private static final long serialVersionUID = 5781883512857770059L;

	private static final DateFormat DATEFORMAT = new SimpleDateFormat("HH:mm:ss.SSS");

	protected ResourceBundle resourceBundle;

	private XDISendEvent sendEvent;

	private ContentPane messageEnvelopeTab;
	private GraphContentPane messageEnvelopeGraphContentPane;
	private ContentPane messageResultTab;
	private GraphContentPane messageResultGraphContentPane;
	private ContentPane exceptionTab;
	private Label exceptionLabel;
	private Label beginTimestampLabel;
	private Label endTimestampLabel;
	private Label durationLabel;
	private Label endpointUriLabel;
	private Label fromLabel;
	private Label toLabel;
	private Label toToolTipLabel;
	private Label fromToolTipLabel;

	public SendEventContentPane() {
		super();

		// Add design-time configured components.
		initComponents();
	}

	@Override
	public void init() {

		super.init();
	}

	private void refresh(EventObject event) {

		this.beginTimestampLabel.setText(DATEFORMAT.format(this.sendEvent.getBeginTimestamp()));
		this.endTimestampLabel.setText(DATEFORMAT.format(this.sendEvent.getEndTimestamp()));
		this.durationLabel.setText(Long.toString(this.sendEvent.getEndTimestamp().getTime() - this.sendEvent.getBeginTimestamp().getTime()) + " ms");

		XDI3Segment fromAddress = this.sendEvent.getMessageEnvelope().getMessages().next().getFromAddress();
		XDI3Segment toAddress = this.sendEvent.getMessageEnvelope().getMessages().next().getToAddress();
		Party fromParty = DanubeCloudsDesktopApplication.getApp().getPartyByCloudNumber(XdiPeerRoot.getXriOfPeerRootArcXri(fromAddress.getFirstSubSegment()));
		Party toParty = DanubeCloudsDesktopApplication.getApp().getPartyByCloudNumber(XdiPeerRoot.getXriOfPeerRootArcXri(toAddress.getFirstSubSegment()));
		this.fromLabel.setText("" + fromAddress);
		this.fromToolTipLabel.setText(fromParty == null ? "(unknown)" : fromParty.getName());
		this.toLabel.setText("" + toAddress);
		this.toToolTipLabel.setText(toParty == null ? "(unknown)" : toParty.getName());

		if (this.sendEvent.getSource() instanceof XDIHttpClient) {

			this.endpointUriLabel.setText(((XDIHttpClient) this.sendEvent.getSource()).getEndpointUri().toString());
		} else {

			this.endpointUriLabel.setText("-");
		}

		this.messageEnvelopeGraphContentPane.setData(this.sendEvent.getMessageEnvelope().getGraph());

		if (this.sendEvent.getMessageResult() != null) {

			this.messageResultTab.setVisible(true);
			this.messageResultGraphContentPane.setData(this.sendEvent.getMessageResult().getGraph()); 
		} else {

			this.messageResultTab.setVisible(false);
		}

		if (this.sendEvent instanceof XDISendErrorEvent) {

			this.exceptionTab.setVisible(true);
			this.exceptionLabel.setText(((XDISendErrorEvent) this.sendEvent).getMessageResult().getErrorString());
		} else {

			this.exceptionTab.setVisible(false);
		}
	}

	public void setData(XDISendEvent sendEvent) {

		this.sendEvent = sendEvent;

		this.refresh(null);
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
		splitPane1.setSeparatorHeight(new Extent(10, Extent.PX));
		splitPane1.setSeparatorVisible(false);
		add(splitPane1);
		Column column1 = new Column();
		column1.setCellSpacing(new Extent(10, Extent.PX));
		SplitPaneLayoutData column1LayoutData = new SplitPaneLayoutData();
		column1LayoutData.setMinimumSize(new Extent(75, Extent.PX));
		column1LayoutData.setMaximumSize(new Extent(75, Extent.PX));
		column1LayoutData.setOverflow(SplitPaneLayoutData.OVERFLOW_HIDDEN);
		column1.setLayoutData(column1LayoutData);
		splitPane1.add(column1);
		Row row2 = new Row();
		row2.setCellSpacing(new Extent(10, Extent.PX));
		column1.add(row2);
		Label label2 = new Label();
		label2.setStyleName("Default");
		label2.setText("Start:");
		row2.add(label2);
		beginTimestampLabel = new Label();
		beginTimestampLabel.setStyleName("Bold");
		beginTimestampLabel.setText("...");
		row2.add(beginTimestampLabel);
		Label label3 = new Label();
		label3.setStyleName("Default");
		label3.setText("End:");
		row2.add(label3);
		endTimestampLabel = new Label();
		endTimestampLabel.setStyleName("Bold");
		endTimestampLabel.setText("...");
		row2.add(endTimestampLabel);
		Label label4 = new Label();
		label4.setStyleName("Default");
		label4.setText("Duration:");
		row2.add(label4);
		durationLabel = new Label();
		durationLabel.setStyleName("Bold");
		durationLabel.setText("...");
		row2.add(durationLabel);
		Row row1 = new Row();
		row1.setCellSpacing(new Extent(10, Extent.PX));
		column1.add(row1);
		Label label1 = new Label();
		label1.setStyleName("Default");
		label1.setText("From:");
		row1.add(label1);
		ToolTipContainer toolTipContainer1 = new ToolTipContainer();
		row1.add(toolTipContainer1);
		fromLabel = new Label();
		fromLabel.setStyleName("Bold");
		fromLabel.setText("...");
		toolTipContainer1.add(fromLabel);
		Panel panel9 = new Panel();
		panel9.setStyleName("Tooltip");
		toolTipContainer1.add(panel9);
		fromToolTipLabel = new Label();
		fromToolTipLabel.setStyleName("Default");
		fromToolTipLabel.setText("TOOLTIP HERE");
		panel9.add(fromToolTipLabel);
		Label label6 = new Label();
		label6.setStyleName("Default");
		label6.setText("To:");
		row1.add(label6);
		ToolTipContainer toolTipContainer2 = new ToolTipContainer();
		row1.add(toolTipContainer2);
		toLabel = new Label();
		toLabel.setStyleName("Bold");
		toLabel.setText("...");
		toolTipContainer2.add(toLabel);
		Panel panel10 = new Panel();
		panel10.setStyleName("Tooltip");
		toolTipContainer2.add(panel10);
		toToolTipLabel = new Label();
		toToolTipLabel.setStyleName("Default");
		toToolTipLabel.setText("TOOLTIP HERE");
		panel10.add(toToolTipLabel);
		Row row3 = new Row();
		row3.setCellSpacing(new Extent(10, Extent.PX));
		column1.add(row3);
		Label label5 = new Label();
		label5.setStyleName("Default");
		label5.setText("Endpoint:");
		row3.add(label5);
		endpointUriLabel = new Label();
		endpointUriLabel.setStyleName("Bold");
		endpointUriLabel.setText("...");
		row3.add(endpointUriLabel);
		TabPane tabPane1 = new TabPane();
		tabPane1.setStyleName("Default");
		splitPane1.add(tabPane1);
		messageEnvelopeTab = new ContentPane();
		TabPaneLayoutData messageEnvelopeTabLayoutData = new TabPaneLayoutData();
		ResourceImageReference imageReference1 = new ResourceImageReference(
				"/danube/clouds/desktop/resource/image/xdi-request.png");
		messageEnvelopeTabLayoutData.setIcon(imageReference1);
		messageEnvelopeTabLayoutData.setTitle("XDI Request");
		messageEnvelopeTab.setLayoutData(messageEnvelopeTabLayoutData);
		tabPane1.add(messageEnvelopeTab);
		messageEnvelopeGraphContentPane = new GraphContentPane();
		messageEnvelopeTab.add(messageEnvelopeGraphContentPane);
		messageResultTab = new ContentPane();
		TabPaneLayoutData messageResultTabLayoutData = new TabPaneLayoutData();
		ResourceImageReference imageReference2 = new ResourceImageReference(
				"/danube/clouds/desktop/resource/image/xdi-response.png");
		messageResultTabLayoutData.setIcon(imageReference2);
		messageResultTabLayoutData.setTitle("XDI Response");
		messageResultTab.setLayoutData(messageResultTabLayoutData);
		tabPane1.add(messageResultTab);
		messageResultGraphContentPane = new GraphContentPane();
		messageResultTab.add(messageResultGraphContentPane);
		exceptionTab = new ContentPane();
		exceptionTab.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(
				5, Extent.PX), new Extent(0, Extent.PX), new Extent(0,
				Extent.PX)));
		TabPaneLayoutData exceptionTabLayoutData = new TabPaneLayoutData();
		ResourceImageReference imageReference3 = new ResourceImageReference(
				"/danube/clouds/desktop/resource/image/xdi-exception.png");
		exceptionTabLayoutData.setIcon(imageReference3);
		exceptionTabLayoutData.setTitle("XDI Error");
		exceptionTab.setLayoutData(exceptionTabLayoutData);
		tabPane1.add(exceptionTab);
		Column column2 = new Column();
		exceptionTab.add(column2);
		exceptionLabel = new Label();
		exceptionLabel.setStyleName("Bold");
		exceptionLabel.setText("...");
		exceptionLabel.setFont(new Font(null, Font.PLAIN, new Extent(14,
				Extent.PT)));
		ColumnLayoutData exceptionLabelLayoutData = new ColumnLayoutData();
		exceptionLabelLayoutData
				.setInsets(new Insets(new Extent(10, Extent.PX)));
		exceptionLabel.setLayoutData(exceptionLabelLayoutData);
		column2.add(exceptionLabel);
	}
}
