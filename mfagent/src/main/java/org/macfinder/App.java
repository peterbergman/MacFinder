package org.macfinder;

public class App 
{
    public static void main( String[] args )
    {
		NetworkInfo ni = new NetworkInfo();
		System.out.println(ni.getNetworks());
    }
}
