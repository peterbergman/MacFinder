package org.macfinder;

public class App 
{
    public static void main( String[] args )
    {
		Installer installer = new Installer();
		installer.install();
		NetworkInfo networkInfo = new NetworkInfo();
		System.out.println(networkInfo.getNetworks());
    }
}
