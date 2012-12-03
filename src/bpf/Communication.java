package bpf;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import se.kth.ssvl.tslab.wsn.general.bpf.BPF;
import se.kth.ssvl.tslab.wsn.general.bpf.BPFCommunication;

public class Communication implements BPFCommunication {

	private static final String TAG = "Communication";
	
	public InetAddress getBroadcastAddress() {
		InetAddress foundBcastAddress = null;
		System.setProperty("java.net.preferIPv4Stack", "true");
		try {
			Enumeration<NetworkInterface> niEnum = NetworkInterface
					.getNetworkInterfaces();
			while (niEnum.hasMoreElements()) {
				NetworkInterface ni = niEnum.nextElement();
				if (!ni.isLoopback()) {
					for (InterfaceAddress interfaceAddress : ni
							.getInterfaceAddresses()) {
						foundBcastAddress = interfaceAddress.getBroadcast();
					}
				}
			}
		} catch (SocketException e) {
			BPF.getInstance().getBPFLogger()
					.error(TAG, "Exception while getting broadcast address.");
		}

		if (foundBcastAddress != null) {
			BPF.getInstance().getBPFLogger().debug(TAG, "Called getBroadcastAddress. Returning: " + foundBcastAddress.getHostAddress());
		} else {
			BPF.getInstance().getBPFLogger().warning(TAG, "Called getBroadcastAddress but foundBcastAddress is null!");
		}
		
		return foundBcastAddress;
	}

	public InetAddress getDeviceIP() {
		try {
			return InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
