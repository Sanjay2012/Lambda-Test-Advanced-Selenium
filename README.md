Step to be follow to run the project from command prompt
1.Open project directory
> drive\LambdaTest-Selenium101>	

2. Clean and compile projct directory using maven command
> drive\LambdaTest-Selenium101>	mvn clean compile

3. run specific suite from project

Note: I have created two suites 
1. single.xml --> Cross browser testing for given specification for three scenarios given in assignment
2. parallel.xml ---> for parallel testing (for specific browser specification)
 (user can change parameter values for different brower, version & platform in )


 mvn clean integration-test -Duite="name of xml file"

**** for cross browser testing sequential 
 mvn clean test -Dsurefire.suite=single.xml


**** for parallel testing 
 mvn clean test -Dsurefire.suiteXmlFiles=parallel.xml

----------------------------------------------------------------
Or you can execute both suite in single shot

mvn clean test -Dsurefire.suiteXmlFiles=single.xml,parallel.xml


4. Once build execution done you can see test report in test-output folder under project
 refer--> emailable-report.html
Open this file in system editor, you can see clear report of all test cases as per given in assignment
------------------------------------------------------------------