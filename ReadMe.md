# Simple Ticker Collector

## Process csv file and show small analysis below

Application takes csv file with unsorted data and collect it to the required statistics

### Application build

```shell
./gradlew build
```

### Application run

```shell
java -jar build/libs/ticker.collector-1.0-SNAPSHOT-standalone.jar src/test/resources/example.csv 10 3
```

### Application algorythm

Application takes file and process it by the scanner line by line.  It uses java.util.Scanner for prevent exhausting of available memory.  
In the real world we should compare this way BufferedReader, FileChannels and so on.  I also had thought about to do it and add some memory and time metrics but obviuosly it takes more than three hour.
Read line is immediately sent to the collector which stores only required data.
According to initial requirements I didn't do anything special to prevent concurrency problem, but only used ConcurrentHashMap in the main storage, but in the case of Big Data it requires some modification in the Ticker collector itself.
Also, application is ready to the infinite processing modification.  It stores all data required for the stats and doesn't modify it when returns data.  So it might process stream in a thread and almost ready to return info to another.  Probably.

### Ticker collector algoryth

Ticker collector gets a ticker, increment days counter (it might be a trap here, because I supposed 'one record per day', there is no sense in two different max price for the one ticker)
Collector checks if max price more than price it contains, min price less and store the best one.
Finally, Collector processes the moving average.

### Moving Average storage

MA Storage contains N + M records which allows showing moving average for N elements for M days.  For the last day a moving average calculates from the N last records, for the previous one from the N + 1 last records except the last one and so on.
MA Storage is filling until it reach N + M items, then it replaces the oldest item in the store.

### Common things

It was interesting.  I have a lot of ideas how to make it better, but I think it wouldn't be fair if I spend a lot of time.  Frankly speaking I made a cheat because I read the task on Sunday and started to work on it only on Monday.
