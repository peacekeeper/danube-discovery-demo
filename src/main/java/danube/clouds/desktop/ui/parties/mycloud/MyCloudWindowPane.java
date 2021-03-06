package danube.clouds.desktop.ui.parties.mycloud;

import java.util.ResourceBundle;

import nextapp.echo.app.Extent;
import nextapp.echo.app.WindowPane;
import danube.clouds.desktop.parties.impl.GlobalRegistryParty.RegisterCloudNameResult;

public class MyCloudWindowPane extends WindowPane {

	private static final long serialVersionUID = 4111493581013444404L;

	protected ResourceBundle resourceBundle;

	private MyCloudContentPane cloudContentPane;

	public MyCloudWindowPane() {
		super();

		// Add design-time configured components.
		initComponents();
	}

	public void setData(RegisterCloudNameResult registerCloudNameResult) {

		this.cloudContentPane.setData(registerCloudNameResult);
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		this.setStyleName("Red");
		this.setTitle("My Cloud");
		this.setHeight(new Extent(700, Extent.PX));
		this.setMinimizeEnabled(false);
		this.setMaximizeEnabled(true);
		this.setClosable(true);
		this.setWidth(new Extent(1000, Extent.PX));
		cloudContentPane = new MyCloudContentPane();
		add(cloudContentPane);
	}
}
