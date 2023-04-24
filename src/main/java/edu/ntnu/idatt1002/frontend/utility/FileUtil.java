package edu.ntnu.idatt1002.frontend.utility;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class FileUtil {
  public static String getResourceFilePath(String fileName) {
    String filePath = null;
    URL url = FileUtil.class.getResource("/" + fileName); // Get resource URL
    if (url != null) {
      if (url.getProtocol().equals("file")) {
        // Running from IDE or as loose files
        filePath = url.getPath();
      } else if (url.getProtocol().equals("jar")) {
        // Running from JAR file
        String jarPath = url.getPath().substring(5, url.getPath().indexOf("!")); // Extract JAR file path
        try {
          JarFile jarFile = new JarFile(URLDecoder.decode(jarPath, "UTF-8")); // Create JarFile object
          JarEntry jarEntry = jarFile.getJarEntry(fileName); // Get JarEntry for the file
          if (jarEntry != null) {
            File tempFile = File.createTempFile(fileName, ""); // Create a temporary file
            try (InputStream is = jarFile.getInputStream(jarEntry);
                 OutputStream os = new FileOutputStream(tempFile)) {
              byte[] buffer = new byte[4096];
              int bytesRead;
              while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
              }
            } catch (IOException e) {
              throw new RuntimeException(e);
            }
            filePath = tempFile.getAbsolutePath();
            tempFile.deleteOnExit(); // Delete temporary file on exit
          }
          jarFile.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return filePath;
  }

  public static String getPictureResourceFilePath(String filePath) {
    URL resourceUrl = FileUtil.class.getClassLoader().getResource(filePath);
    if (resourceUrl != null) {
      // Running from JAR file
      if (resourceUrl.getProtocol().equals("jar")) {
        String jarFilePath = resourceUrl.getFile();
        try {
          String decodedJarFilePath = URLDecoder.decode(jarFilePath, "UTF-8");
          String jarPath = decodedJarFilePath.substring(0, decodedJarFilePath.lastIndexOf("!"));
          return jarPath + "!/" + filePath;
        } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
        }
      }
      // Running from local directory
      else if (resourceUrl.getProtocol().equals("file")) {
        return new File("src/main/resources", filePath).getPath();
      }
    }
    // Resource not found
    return null;
  }
}