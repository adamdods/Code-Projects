shopt -s globstar 2> /dev/null
java -jar testsuite/checkstyle-8.16-all.jar -c testsuite/stylespec.xml src/**/*.java
