package com.franklin.samples.countwords;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

public class FileReader implements Closeable {

  private final Scanner scanner;

  private final Iterator<String> iterator;

  /**
   * Constructor
   *
   * @param file file to be read
   */
  public FileReader(final File file) {
    try {
      scanner = new Scanner(file);
      iterator = createIterator(scanner);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * @param file      file to be read
   * @param delimiter delimiter for producing tokens
   */
  public FileReader(File file, String delimiter) {
    this(file);
    scanner.useDelimiter(delimiter);
  }

  public Iterator<String> getIterator() {
    return iterator;
  }

  private Iterator<String> createIterator(Scanner scanner) {
    return new Iterator<String>() {

      @Override
      public boolean hasNext() {
        return scanner.hasNext();
      }

      @Override
      public String next() {
        return scanner.nextLine();
      }
    };
  }

  @Override
  public void close() {
    scanner.close();
  }
}