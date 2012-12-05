package bpf;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import jlibs.core.lang.Ansi;
import se.kth.ssvl.tslab.wsn.general.bpf.BPFLogger;

public class Logger implements BPFLogger {

	private int logLevel;
	private String logFile;
	
	public Logger(int logLevel, String logFile) {
		this.logLevel = logLevel;
		this.logFile = logFile;
	}

	private String log(String text, Ansi ansi) {
		ansi.out(text + "\n");
		
		if (logFile != null) {
			try
			{
				FileWriter fw = new FileWriter(logFile,true);
				fw.write(text + "\n");
				fw.close();
			}
			catch(IOException ioe)
			{
				ansi.out("IOException writing in log file" + "\n");
			}
		}
		
		return text;
	}

	public String debug(String TAG, String text) {
		if (logLevel >= 3) {
			return log("[DEBUG] [" + Thread.currentThread().getName() + "] [" +
					TAG + "]: " + text, new Ansi(Ansi.Attribute.BRIGHT, Ansi.Color.CYAN, null));
		} else {
			return "[DEBUG] [" + Thread.currentThread().getName() + "] [" + TAG + "]: " + text;
		}
	}

	public String info(String TAG, String text) {
		if (logLevel >= 2) {
			return log("[INFO] [" + Thread.currentThread().getName() + "] [" +
					TAG + "]: " + text, new Ansi(Ansi.Attribute.BRIGHT, Ansi.Color.GREEN, null));
		} else {
			return "[INFO] [" + Thread.currentThread().getName() + "] [" + TAG + "]: " + text;
		}
	}

	public String warning(String TAG, String text) {
		if (logLevel >= 1) {
			return log("[WARN] [" + Thread.currentThread().getName() + "] [" +
					TAG + "]: " + text, new Ansi(Ansi.Attribute.BRIGHT, Ansi.Color.YELLOW, null));
		} else {
			return "[WARN] [" + Thread.currentThread().getName() + "] [" + TAG + "]: " + text;
		}
	}

	public String error(String TAG, String text) {
		if (logLevel >= 0) {
			return log("[ERROR] [" + Thread.currentThread().getName() + "] [" +
					TAG + "]: " + text, new Ansi(Ansi.Attribute.BRIGHT, Ansi.Color.RED, null));
		} else {
			return "[ERROR] [" + Thread.currentThread().getName() + "] [" + TAG + "]: " + text;
		}
	}
}
