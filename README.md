# EBank

Author: Ferenc Dániel Takács

##Summary

This is the repository of my homework project. This program implements a simple electronic banking interface. It can be used to perform the most basic operations of a real-life e-bank (eg. registration, login, transaction, loan, etc.).

This project is made for the `Programming Technologies` and `Programming Environments` subjects at the University of Debrecen.

##Instructions

###Prerequisities
To be able to run this program, you need to have the following:

- `Apache Maven` version 3.x
- `Java Runtime Environment` 1.8

###Running the program
You have to execute the following commands in the root folder of the project:

```
mvn package
java -jar target/EBank-1.0-jar-with-dependencies.jar
```

If you want to view the project summary and reports, execute the following command in the same folder as above:

```
mvn site
```

After that you can find the reports in `HTML` format under `target/site/`.

