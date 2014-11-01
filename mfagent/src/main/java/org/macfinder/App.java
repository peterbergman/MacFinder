package org.macfinder;

public class App {
    public static void main( String[] args ) {
		Installer installer = new Installer();
		if (args.length == 0) {
			installer.install();
		} else if (args.length == 1) {
			if (args[0].equals("execute")) {
				NetworkInfo networkInfo = new NetworkInfo();
				System.out.println(networkInfo.getNetworks());
			}
		}
    }
}
