# Description

This project counts the occurence of words in a file sorted in descending order excluding the common
words presented in a common words file. The project assume case sensitivity when counting but improvements
can be made in the future to allow for this. The application also assumes words in the US locale
and can also be improved to allow for other locales.

# Technologies
1. Gradle 4.6 (included)
2. Java 8.x - chosen for some of its appealing functionality like streams, functions and method references.
3. Developed with IDEA Intellij CE 2018.2- chosen as my IDE of choice.
4. Spring Boot/Spring Shell

# Tests

Test were implemented to validate the counting, shell and script operations.

# Build

```
./gradlew clean build
```

# Interactive Execution (REPL Mode)
Below are some examples of how to execute the application.

### Printing the services supported

/counter is a wrapper script around the `java -jar count-words-[VERSION].jar`. Which
create a shell prompt.


```
./counter
```

**output:**

```
counter:>
````


You can type the following command in the prompt. `counter:>` is not part of the command

### Printing Help

In the shell you can type help. The help provides documentation of what the commands do.

```
counter:>help
AVAILABLE COMMANDS

Built-In Commands
        clear: Clear the shell screen.
        exit, quit: Exit the shell.
        help: Display help about available commands.
        history: Display or save the history of previously run commands
        stacktrace: Display the full stacktrace of the last error.

Commands
        count-words: Count words that are not in the common words file.


counter:>

```

```
counter:>help count-words


NAME
	count-words - Count words that are not in the common words file.

SYNOPSYS
	count-words [-common-file] string  [-count-file] string

OPTIONS
	-common-file  string
		Path to common file.
		[Mandatory]

	-count-file  string
		Path to file to count.
		[Mandatory]



counter:>

```

## Shell Example requires absolute paths

`count-words -common-file ./src/test/resources/common.txt -count-file ./src/test/resources/alice.txt`

## Exit

`counter:>exit`

# Non-Interactive Execution (Script Mode) Slower due to application startup

You can run all the commands listed above but with the -c options

```
./counter -c "count-words -common-file ${PWD}/src/test/resources/common.txt -count-file ${PWD}/src/test/resources/alice.txt"
```

```
./counter -c "count-words -common-file ./src/test/resources/common.txt -count-file ./src/test/resources/alice.txt"
```