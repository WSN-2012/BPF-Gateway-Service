package service;

import java.io.File;

import se.kth.ssvl.tslab.wsn.general.bpf.BPF;
import se.kth.ssvl.tslab.wsn.general.bpf.BPFActionReceiver;
import se.kth.ssvl.tslab.wsn.general.bpf.BPFCommunication;
import se.kth.ssvl.tslab.wsn.general.bpf.BPFDB;
import se.kth.ssvl.tslab.wsn.general.bpf.BPFLogger;
import se.kth.ssvl.tslab.wsn.general.bpf.BPFService;
import se.kth.ssvl.tslab.wsn.general.bpf.exceptions.BPFException;
import se.kth.ssvl.tslab.wsn.general.dtnapi.exceptions.DTNOpenException;
import se.kth.ssvl.tslab.wsn.general.dtnapi.types.DTNEndpointID;
import DirWatcher.FileWatcherException;
import DirWatcher.PeriodicDirWatcher;
import bpf.ActionReceiver;
import bpf.Communication;
import bpf.DB;
import bpf.Logger;

public class Service implements BPFService {
	
	private static String TAG = "Service";
	
	private Logger logger; 
	private ActionReceiver action;
	private Communication comm;
	private DB db;
	private static String destination;
	private String path;
	
	public static void main(String args[]) {
		new Service(args);
	}
	
	public Service(String args[]) {
		if (args.length == 3) {
			init(args);
			logger.info(TAG, "No argmunets means listening mode");
		} else if (args.length == 4) {
			init(args);
			destination = args[3];
			path = args[2];
			try {
				// monitor the the directory containing file with sensor data
				PeriodicDirWatcher pdw = new PeriodicDirWatcher(path, 1000);

			} catch (FileWatcherException fwe) {
				fwe.printStackTrace();
			}
			 
			logger.info(TAG, "Arguments passed trying to send");
		} else {
			usage();
			System.exit(-1);
		}
	}
	
	private void init(String args[]) {
		// Init a logger first of all
		logger = new Logger(Integer.parseInt(args[1]));
		
		// Init the action receiver
		action = new ActionReceiver(logger);
		
		// Init the communications object
		comm = new Communication();
		
		// Init the DB object
		db = new DB(new File("build/database.db"), logger);

		// Try to init the BPF
		try {
			BPF.init(this, args[0]);
		} catch (BPFException e) {
			logger.error(TAG, "Couldn't initialize the BPF, exception: " + e.getMessage());
			System.exit(-1);
		}
	}
	
	private void usage() {
		//System.out.println("config-file-path <dest eid> <source eid> <payload type> <payload> \n payload type: <f|m> \n payload: <filename|double quoted message>");
		System.out.println("config-file-path <dest eid>");
	}

	/* ***************************** */
	
	public BPFCommunication getBPFCommunication() {
		return comm;
	}

	public BPFDB getBPFDB() {
		return db;
	}

	public BPFLogger getBPFLogger() {
		return logger;
	}

	public BPFActionReceiver getBPFActionReceiver() {
		return action;
	}
	
	public static void send(byte[] buf){
		try {
			BPF.getInstance().send(new DTNEndpointID(destination), 10000000, buf);
		} catch (DTNOpenException e) {
			e.printStackTrace();
		}
	}
	
}
