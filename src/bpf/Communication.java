/*
 * Copyright 2012 KTH
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package bpf;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import se.kth.ssvl.tslab.wsn.general.bpf.BPF;
import se.kth.ssvl.tslab.wsn.general.bpf.BPFCommunication;

public class Communication implements BPFCommunication {

	private static final String TAG = "Communication";

	public InetAddress getBroadcastAddress(String interfaceName) {
		System.setProperty("java.net.preferIPv4Stack", "true");
		try {
			Enumeration<NetworkInterface> niEnum = NetworkInterface
					.getNetworkInterfaces();
			while (niEnum.hasMoreElements()) {
				NetworkInterface ni = niEnum.nextElement();
				if (!ni.isLoopback()) {
					for (InterfaceAddress interfaceAddress : ni
							.getInterfaceAddresses()) {
						if (interfaceAddress.getBroadcast() != null
								&& ni.getName().equals(interfaceName)) {
							BPF.getInstance()
									.getBPFLogger()
									.debug(TAG,
											"getBroadcastAddress from interface "
													+ interfaceName
													+ " is: "
													+ interfaceAddress
															.getBroadcast());
							return interfaceAddress.getBroadcast();
						}
					}
				}
			}
		} catch (SocketException e) {
			BPF.getInstance().getBPFLogger()
					.error(TAG, "Exception while getting broadcast address.");
		}

		BPF.getInstance()
				.getBPFLogger()
				.error(TAG,
						"Called getBroadcastAddress but couldn't find the interface: "
								+ interfaceName);
		return null;
	}

	public InetAddress getDeviceIP(String interfaceName) {
		try {
			Enumeration<NetworkInterface> ifaces = NetworkInterface
					.getNetworkInterfaces();
			while (ifaces.hasMoreElements()) {
				NetworkInterface iface = ifaces.nextElement();
				Enumeration<InetAddress> addresses = iface.getInetAddresses();

				while (addresses.hasMoreElements()) {
					InetAddress addr = addresses.nextElement();
					if (addr instanceof Inet4Address
							&& !addr.isLoopbackAddress()
							&& iface.getName().equals(interfaceName)) {
						BPF.getInstance().getBPFLogger()
								.debug(TAG, "getDeviceIP returning " + addr);
						return addr;
					}
				}
			}
		} catch (SocketException e) {
			BPF.getInstance().getBPFLogger()
					.error(TAG, "Exception while getting device address.");
		}

		BPF.getInstance()
				.getBPFLogger()
				.error(TAG,
						"Called getDeviceIp but couldn't find the interface: "
								+ interfaceName);

		return null;
	}

}
