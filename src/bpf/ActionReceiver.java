package bpf;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import se.kth.ssvl.tslab.wsn.general.bpf.BPF;
import se.kth.ssvl.tslab.wsn.general.bpf.BPFActionReceiver;
import se.kth.ssvl.tslab.wsn.general.servlib.bundling.bundles.Bundle;
import se.kth.ssvl.tslab.wsn.general.servlib.bundling.exception.BundleLockNotHeldByCurrentThread;
import se.kth.ssvl.tslab.wsn.general.servlib.storage.Stats;

public class ActionReceiver implements BPFActionReceiver {

	private static final String TAG = "Actions";
	
	private Logger logger;
	
	
	public ActionReceiver(Logger logger) {
		this.logger = logger;
	}
	
	public void bundleReceived(Bundle bundle) {
		logger.info(TAG, "Received bundle! Reading:");
		switch (bundle.payload().location()) {
		case DISK:
			RandomAccessFile f = null;
			try {
				f = new RandomAccessFile(bundle.payload().file(), "r");
				byte[] buffer = new byte[(int) f.length()];
				f.read(buffer);
				logger.info(TAG, new String(buffer));
			} catch (FileNotFoundException e) {
				BPF.getInstance().getBPFLogger().error(TAG, "Payload should be in file: " +
						bundle.payload().file().getAbsolutePath() + ". But did not exist!");
			} catch (Exception e) {
				BPF.getInstance().getBPFLogger().error(TAG, e.getMessage());
			} finally {
				try {
					f.close();
				} catch (IOException e) {
					BPF.getInstance().getBPFLogger().error(TAG, e.getMessage());
				}
			}
			break;
		case MEMORY:
			try {
				logger.info(TAG, new String(bundle.payload().memory_buf()));
			} catch (BundleLockNotHeldByCurrentThread e) {
				e.printStackTrace();
			}
			break;
		default:
			logger.warning(TAG,
					"The bundle was neither stored in disk nor memory");
		}
	}

	public void notify(String header, String description) {
		logger.info(TAG, header + ":" + description);
	}

	//TODO: this method needs to notify the app
	public void updateStats (Stats stats) {
		logger.debug(TAG, "New Stats object received:" +
				"\nTotal size: " + stats.totalSize() + 
				"\nStored bundles: " + stats.storedBundles() +
				"\nTransmitted bundles: " + stats.transmittedBundles() +
				"\nReceived bundles: " + stats.receivedBundles() );
	}
}
