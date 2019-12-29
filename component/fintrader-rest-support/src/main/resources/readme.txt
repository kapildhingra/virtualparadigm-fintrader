See below for intentions/directory contents:

	src/main/java/: application source code

	src/main/resources/: additional files (like configs) that will be embedded in the jar

	src/main/resources/META-INF/: spring context files here

	src/main/config/: 
		templatized config files that will be depoloyed by installer/automation. Variable replacement will occur at install time.

	src/main/install/: 
		templatized installation related scripts. These will automatically be picked up by the install automation. 
		Variable replacement will occur at install time. This is NOT for upgrade/hotfix/CP scripting. This is for 
		NEW installation of this component only.
		
	src/test/java/: unit test source code

	src/test/resources/: files that would be embedded in the jar used in running unit tests

	src/test/resources/META-INF/: put spring context files here for unit test runtime

	src/test/config/: 
		config files that WOULD be depoloyed by installer/automation. No need to make these templatized. These could hold actual test values.

