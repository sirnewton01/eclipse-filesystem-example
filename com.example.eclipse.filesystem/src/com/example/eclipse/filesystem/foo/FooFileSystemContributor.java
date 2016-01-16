package com.example.eclipse.filesystem.foo;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ide.fileSystem.FileSystemContributor;

public class FooFileSystemContributor extends FileSystemContributor {

	public FooFileSystemContributor() {
		super();
	}

	public URI getURI(String pathString) {
		try {
			if (pathString.startsWith(FooFileSystem.SCHEME_FOO))
				return new URI(pathString);
		} catch (URISyntaxException e1) {
			return null;
		}
		
		if (File.separatorChar != '/')
			pathString = pathString.replace(File.separatorChar, '/');
		
		if (!pathString.startsWith("/")) {
			pathString = "/" +pathString;
		}
		
		pathString = "/foo" + pathString;
		
		try {
			//scheme, host, path, query, fragment
			return new URI(FooFileSystem.SCHEME_FOO, null, pathString, null); //$NON-NLS-1$
		} catch (URISyntaxException e) {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.ide.fileSystem.FileSystemContributor#browseFileSystem(java.lang.String, org.eclipse.swt.widgets.Shell)
	 */
	public URI browseFileSystem(String initialPath, Shell shell) {

		FileDialog dialog = new FileDialog(shell);

		if (initialPath.length() > 0)
			dialog.setFilterPath(initialPath);	

		String selectedFile = dialog.open();
		if (selectedFile == null)
			return null;
		return getURI(selectedFile);
	}

}
