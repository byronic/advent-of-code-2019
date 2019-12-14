# advent-of-code-2019
Advent of code, but this time it's 2019!

## Running the days

Each day requires a file containing the puzzle input, which is set by an environment variable.

The environment variable for each file is AOC2019_DAY[two-digit day],
e.g. AOC2019_DAY02

Set the value of that environment variable to the _full path_ of the puzzle input file!

`mvn clean package`
`mvn exec:java`

Java 11 and Maven 3.6.3 are recommended; [SDK Man](https://sdkman.io/) is the best way to manage this, regardless of platform.
