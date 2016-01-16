package com.example.eclipse.filesystem.foo;

import java.net.URI;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.provider.FileSystem;
import org.eclipse.core.runtime.*;

public class FooFileSystem extends FileSystem {
	public static final String SCHEME_FOO = "foo"; //$NON-NLS-1$

	public IFileStore getStore(URI uri) {
		if (SCHEME_FOO.equals(uri.getScheme())) {
			String p = uri.getPath().replaceAll("%20", " ");

			IPath path = new Path(p);
			if (!path.isAbsolute() || !"foo".equals(path.segment(0))) {
				throw new RuntimeException("Illegal URI for the Foo FileSystem!");
			}

			path = path.removeFirstSegments(1);
			path = path.makeAbsolute();

			IPath encodedPath = new Path("/");
			for (int i = 0; i < path.segmentCount(); i++) {
				encodedPath = encodedPath.append(path.segment(i).replaceAll(" ", "%20"));
			}

			try {
				return new FooFileStore(EFS.getStore(URI.create("file://" + encodedPath.toString())), path);
			} catch (CoreException e) {
				// ignore and fall through below
			}
		}

		return EFS.getNullFileSystem().getStore(uri);
	}
}
