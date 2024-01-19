# ActivityChecker
Utility tool to check Wynncraft's player activity status

This checks for the online players every 30 seconds (the TTL)
and outputs it to a json (latestAnalytics.json) and logs to a file.

## Building
Java 21 w/ dependencies in pom.xml

## Processing

Currently, I wrote a quick code snippet, just put the
json you want to parse as "data.json" and place it under
the src/main/java/resources folder, and run the code in Dump.java
