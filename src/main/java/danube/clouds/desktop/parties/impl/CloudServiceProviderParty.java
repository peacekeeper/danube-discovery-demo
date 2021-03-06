package danube.clouds.desktop.parties.impl;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;

import xdi2.client.XDIClient;
import xdi2.client.exceptions.Xdi2ClientException;
import xdi2.client.http.XDIHttpClient;
import xdi2.core.constants.XDIAuthenticationConstants;
import xdi2.core.constants.XDIDictionaryConstants;
import xdi2.core.features.nodetypes.XdiPeerRoot;
import xdi2.core.xri3.XDI3Segment;
import xdi2.core.xri3.XDI3Statement;
import xdi2.core.xri3.XDI3SubSegment;
import xdi2.messaging.Message;
import xdi2.messaging.target.interceptor.impl.authentication.secrettoken.DigestSecretTokenAuthenticator;
import danube.clouds.desktop.DanubeCloudsDesktopApplication;
import danube.clouds.desktop.parties.Party;
import danube.clouds.desktop.parties.RemoteParty;
import danube.clouds.desktop.xdi.XdiEndpoint;

public class CloudServiceProviderParty extends AbstractRemoteParty implements RemoteParty {

	private static String CSP_SECRET_TOKEN = "s3cr3t";
	private static String CSP_GLOBAL_SALT = "c2293773-3240-4524-8c19-c1f5cbe31b86";

	private CloudServiceProviderParty(XdiEndpoint xdiEndpoint) {

		super("Cloud Service Provider", xdiEndpoint);
	}

	public static CloudServiceProviderParty create() {

		XDIClient xdiClient = new XDIHttpClient("http://mycloud.neustar.biz:14440/registry");
		xdiClient.addClientListener(DanubeCloudsDesktopApplication.getApp().getEvents());

		XdiEndpoint xdiEndpoint = new XdiEndpoint(
				xdiClient, 
				XDI3Segment.create("@neustar"), 
				XDI3Segment.create("[@]!:uuid:0baea650-823b-2475-0bae-a650823b2475"), 
				CSP_SECRET_TOKEN
				);

		return new CloudServiceProviderParty(xdiEndpoint);
	}

	public String createCloudEndpointUri(XDI3Segment cloudNumber) {

		try {

			return "http://mycloud.neustar.biz:14440/users/" + URLEncoder.encode(cloudNumber.toString(), "UTF-8");
		} catch (UnsupportedEncodingException ex) {

			throw new RuntimeException(ex.getMessage(), ex);
		}
	}

	public RegisterCloudResult registerCloud(Party fromParty, XDI3Segment cloudName, XDI3Segment cloudNumber, String endpointUri, String secretToken) throws Xdi2ClientException {

		XDI3SubSegment cloudNamePeerRoot = XdiPeerRoot.createPeerRootArcXri(cloudName);
		XDI3SubSegment cloudNumberPeerRoot = XdiPeerRoot.createPeerRootArcXri(cloudNumber);

		// calculate digest secret token

		String localSaltAndDigestSecretToken = DigestSecretTokenAuthenticator.localSaltAndDigestSecretToken(secretToken, CSP_GLOBAL_SALT);

		// assemble message

		Message message = this.getXdiEndpoint().prepareMessage(fromParty.getCloudNumber());

		XDI3Statement[] statements = new XDI3Statement[] {

				XDI3Statement.fromRelationComponents(XDI3Segment.fromComponent(cloudNamePeerRoot), XDIDictionaryConstants.XRI_S_REF, XDI3Segment.fromComponent(cloudNumberPeerRoot)),
				XDI3Statement.fromLiteralComponents(XDI3Segment.create("" + cloudNumberPeerRoot + XDIAuthenticationConstants.XRI_S_DIGEST_SECRET_TOKEN), localSaltAndDigestSecretToken),
				XDI3Statement.fromLiteralComponents(XDI3Segment.create("" + cloudNumberPeerRoot + "$xdi<$uri>&"), endpointUri)
		};

		message.createSetOperation(Arrays.asList(statements).iterator());

		// send it

		this.getXdiEndpoint().send(message);

		return new RegisterCloudResult(cloudName, cloudNumber, endpointUri);
	}

	public static class RegisterCloudResult implements Serializable {

		private static final long serialVersionUID = -8778440729346205990L;

		private XDI3Segment cloudName;
		private XDI3Segment cloudNumber;
		private String endpointUri;

		public RegisterCloudResult(XDI3Segment cloudName, XDI3Segment cloudNumber, String endpointUri) {

			this.cloudName = cloudName;
			this.cloudNumber = cloudNumber;
			this.endpointUri = endpointUri;
		}

		public XDI3Segment getCloudName() {

			return this.cloudName;
		}

		public XDI3Segment getCloudNumber() {

			return this.cloudNumber;
		}

		public String getEndpointUri() {

			return this.endpointUri;
		}
	}
}
