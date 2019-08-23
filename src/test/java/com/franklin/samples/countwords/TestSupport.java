package com.franklin.samples.countwords;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

class TestSupport {

  static Path resourceToPath(String resourcePath) throws FileNotFoundException, URISyntaxException {
    URL resourceUrl = WordsCounter.class.getClassLoader().getResource(resourcePath);
    if (resourceUrl == null) {
      throw new FileNotFoundException(resourcePath);
    }
    return Paths.get(resourceUrl.toURI());
  }
}
