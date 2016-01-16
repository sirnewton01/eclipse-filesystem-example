Eclipse Filesystem Challenge
============================

Are your eclipse tools up for the challenge?

Normally, your eclipse projects are located in your eclipse workspace directory and have
the usual file URI's, but not always. There are eclipse tools that will manage your projects
for you and use special URI's to address the files. Fortunately, eclipse has the 
[EFS](https://wiki.eclipse.org/EFS) to accomplish just that.

Unfortunately, some eclipse tools make certain invalid assumptions about the form of the URI's
used for the files in your project. This project gives both end users and developers of eclipse
tools the ability to verify that their tools work in this environment.


Example EFS Provider
====================

Embedded in this project is both the source code and an update site to an example eclipse
filestyem provider. It uses magic "foo" URI's for the files. You can't easily convert these
URI's into file URI's on your local filesystem without knowledge of how the URI's work.
You're not really meant to understand how these URI's work. Most eclipse tools shouldn't, either.

To install the provider, you can install it into your eclipse environment using the update site.
In eclipse, go to Help->Install New Software..., click the Add button and paste this URL
into the Location box.

	https://github.com/sirnewton01/eclipse-filesystem-example/raw/master/com.example.eclipse.filesystem.updatesite

Give the site a name such as "Are you up for the challenge?" or
something more boring like "Example EFS Provider." Click "OK," choose the provider feature
and click "OK" again to install. Restart the eclipse workbench and you are ready to begin
the test.

Pick a project in your eclipse workspace that is **backed up elsewhere** and uses the eclipse tools
that you want to test. The project should not be under any kind of source control. Once you have
picked the project, right click on it and choose "Reload Project." You may notice that editors
for files in the project are closed, but otherwise the project should behave normally. Try
building or rebuilding the project. Also, check that editors open normally. Check that everything
behaves normally. Have a look at the eclipse error log view for any exceptions. Also, check for
unusual uses of the word "foo" as this is a special token used in the example provider's URI's.

If you find any problems, consider raising a bug report against the publisher of your eclipse
tool. Give them a link to this project so that they can verify the bug and test their fixes.
If you find a bug in the example, please raise an issue here.



Tools tested so far
===================

 * [JDT](http://www.eclipse.org/jdt/) (works very well, no problems reported in a very long time)
 * [PDE](http://www.eclipse.org/pde/) (same)

Raise an issue or pull request to add more tools to the list.

