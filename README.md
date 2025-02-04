### example run test from command line
We can run test with ./gradlew test -Pui -Dapp-url="stage"

## Run suit via xml
for run ui tests - Pweb

### Set system property
set url - -Dapp-url=http://localhost

## Simple Framework Description
The automation framework is written in Kotlin programming language using the Gradle build tool.
The Java version is 11. Currently, you can run two browsers: Chrome and Firefox. 
Remote browser implementation has also been added (just an example).
I used the latest Java Selenium version, 4.28.1, to ensure we can utilize Selenium 4 features in the future (e.g., browser dev tools).
