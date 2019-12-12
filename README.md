# advent-of-code-2019
Advent of code, but this time it's 2019!

## Running the days

Each day requires a file containing the puzzle input, which is set by an environment variable.

The environment variable for each file is AOC2019_DAY[two-digit day]_PART[1 digit part],
e.g. AOC2019_DAY02_PART1

Set the value of that environment variable to the _full path_ of the puzzle input file!

mvn clean verify will run the tests; changing to a day's directory and running `mvn exec:java` will execute the day's puzzles (or complain, if you don't have the required variables/files!).

If you'd prefer not to run with `mvn exec:java` you can visit the `target` directory under each day, and run:
```
java -cp day01-0.1.0-SNAPSHOT.jar org.nerdsofprey.Day01
```
(replacing day01 and Day01 with the appropriate day, of course)
