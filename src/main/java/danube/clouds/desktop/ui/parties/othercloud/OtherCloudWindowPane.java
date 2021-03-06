package danube.clouds.desktop.ui.parties.othercloud;

import java.util.ResourceBundle;

import nextapp.echo.app.Extent;
import nextapp.echo.app.WindowPane;

public class OtherCloudWindowPane extends WindowPane {

	private static final long serialVersionUID = 4111493581013444404L;

	protected ResourceBundle resourceBundle;

	public OtherCloudWindowPane() {
		super();

		// Add design-time configured components.
		initComponents();
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		this.setStyleName("Trans");
		this.setTitle("Other Cloud");
		this.setHeight(new Extent(700, Extent.PX));
		this.setMinimizeEnabled(false);
		this.setMaximizeEnabled(true);
		this.setClosable(true);
		this.setWidth(new Extent(1000, Extent.PX));
		OtherCloudContentPane otherCloudContentPane = new OtherCloudContentPane();
		add(otherCloudContentPane);
	}
}
