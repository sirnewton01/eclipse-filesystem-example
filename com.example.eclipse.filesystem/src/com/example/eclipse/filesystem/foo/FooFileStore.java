package com.example.eclipse.filesystem.foo;

import com.example.eclipse.filesystem.Activator;
import java.io.*;
import java.net.*;
import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.provider.FileStore;
import org.eclipse.core.runtime.*;


public class FooFileStore extends FileStore {

	private IPath path;
	private IFileStore baseStore;

	public FooFileStore(IFileStore baseStore, IPath path) {
		this.baseStore = baseStore;
		this.path = path;
	}

	public IFileInfo[] childInfos(int options, IProgressMonitor monitor) throws CoreException {
		return baseStore.childInfos(options, monitor);
	}

	public String[] childNames(int options, IProgressMonitor monitor) throws CoreException {
		return baseStore.childNames(options, monitor);
	}

	public IFileInfo fetchInfo(int options, IProgressMonitor monitor) throws CoreException {
		return baseStore.fetchInfo(options, monitor);
	}

	public IFileStore getChild(String name) {
		return new FooFileStore(baseStore.getChild(name), path.append(name));
	}

	public String getName() {
		String name = path.lastSegment();
		return name == null ? "" : name; //$NON-NLS-1$
	}

	public IFileStore getParent() {
		if (path.segmentCount() > 0) {
			return new FooFileStore(baseStore.getParent(), path.removeLastSegments(1));
			
		}
		//the root entry has no parent
		return null;
	}

	public InputStream openInputStream(int options, IProgressMonitor monitor) throws CoreException {
		return baseStore.openInputStream(options, monitor);
	}

	public URI toURI() {
		try {
			IPath encodedPath = new Path("/foo/");
			
			for (int i=0; i<path.segmentCount(); i++) {
				encodedPath = encodedPath.append(path.segment(i).replaceAll(" ", "%20"));
			}
			
			return new URI(FooFileSystem.SCHEME_FOO, null, encodedPath.toString(), null, null);
		} catch (URISyntaxException e) {
			Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.getDefault().getBundle().getSymbolicName(), e.getMessage(), e));
		}
		
		return null;
	}
	
	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
		return baseStore.getAdapter(adapter);
	}
	
	@Override
	public void copy(IFileStore destination, int options, IProgressMonitor monitor) throws CoreException {
		if (destination instanceof FooFileStore) {
			destination = ((FooFileStore)destination).baseStore;
		}
		
		baseStore.copy(destination, options, monitor);
	}
	
	@Override
	public IFileStore mkdir(int options, IProgressMonitor monitor) throws CoreException {
		return baseStore.mkdir(options, monitor);
	}
	
	@Override
	public void move(IFileStore destination, int options, IProgressMonitor monitor) throws CoreException {
		if (destination instanceof FooFileStore) {
			destination = ((FooFileStore)destination).baseStore;
		}
		
		baseStore.move(destination, options, monitor);
	}
	
	@Override
	public OutputStream openOutputStream(int options, IProgressMonitor monitor) throws CoreException {
		return baseStore.openOutputStream(options, monitor);
	}
	
	@Override
	public void putInfo(IFileInfo info, int options, IProgressMonitor monitor) throws CoreException {
		baseStore.putInfo(info, options, monitor);
	}
	
	@Override
	public File toLocalFile(int options, IProgressMonitor monitor) throws CoreException {
		return baseStore.toLocalFile(options, monitor);
	}
	
	@Override
	public void delete(int options, IProgressMonitor monitor) throws CoreException {
		baseStore.delete(options, monitor);
	}
}
