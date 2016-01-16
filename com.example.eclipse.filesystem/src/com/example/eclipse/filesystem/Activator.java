package com.example.eclipse.filesystem;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class Activator extends AbstractUIPlugin {
	private static Activator plugin;
	
	public Activator() {
		super();
	}
	
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}
	
	public static Activator getDefault() {
		return plugin;
	}
}
