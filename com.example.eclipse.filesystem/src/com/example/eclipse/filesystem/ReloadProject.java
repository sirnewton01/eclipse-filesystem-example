package com.example.eclipse.filesystem;

import com.example.eclipse.filesystem.foo.FooFileSystem;
import java.net.URI;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.*;

public class ReloadProject implements IObjectActionDelegate {

	private IWorkbenchPart targetPart;
	private IProject project;

	/**
	 * Constructor for Action1.
	 */
	public ReloadProject() {
		super();
	}

	private void load(IPath location) {
		try {
			String projectName = location.lastSegment();
			
			location = new Path("/foo").append(location);
			
			IPath encodedPath = new Path("/");
			
			for (int i=0; i<location.segmentCount(); i++) {
				encodedPath = encodedPath.append(location.segment(i).replaceAll(" ", "%20"));
			}
			
			URI uri = new URI(FooFileSystem.SCHEME_FOO, null, encodedPath.toString(), null);
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
			IProjectDescription description = ResourcesPlugin.getWorkspace().newProjectDescription(projectName);
			description.setLocationURI(uri);
			project.create(description, null);
			project.open(null);
		} catch (Exception e) {
			MessageDialog.openError(getShell(), "Error", "Error setting up project");
			Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.getDefault().getBundle().getSymbolicName(), e.getMessage(), e));
		}
	}

	private Shell getShell() {
		return targetPart.getSite().getShell();
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		IPath location = project.getLocation();
		
		try {
			project.delete(IProject.FORCE | IProject.NEVER_DELETE_PROJECT_CONTENT, null);
			load(location);
		} catch (CoreException e) {
			MessageDialog.openError(getShell(), "Error", e.getMessage());
			Activator.getDefault().getLog().log(e.getStatus());
		}
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		project = null;
		
		if (!(selection instanceof IStructuredSelection)) {
			action.setEnabled(false);
			return;
		}
		
		IStructuredSelection structSel = (IStructuredSelection)selection;
		
		Object o = structSel.getFirstElement();
		if (!(o instanceof IAdaptable)) {
			action.setEnabled(false);
			return;
		}
		
		IAdaptable adaptable = (IAdaptable)o;
		
		project = (IProject)adaptable.getAdapter(IProject.class);
		
		action.setEnabled(project != null);
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		this.targetPart = targetPart;
	}

}
