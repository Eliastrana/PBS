package edu.ntnu.idatt1002.frontend.utility;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * The type File util.
 */
public class FileUtil {
  /**
   * Gets resource file path.
   *
   * @param fileName the file name
   * @return the resource file path
   */
  public static String getResourceFilePath(String fileName) {
    String filePath = null;
    URL url = FileUtil.class.getResource("/" + fileName);
    if (url != null) {
      if (url.getProtocol().equals("file")) {
        filePath = url.getPath();
      } else if (url.getProtocol().equals("jar")) {
        String jarPath = url.getPath().substring(5, url.getPath().indexOf("!"));
        try {
          JarFile jarFile = new JarFile(URLDecoder.decode(jarPath, StandardCharsets.UTF_8));
          JarEntry jarEntry = jarFile.getJarEntry(fileName);
          if (jarEntry != null) {
            File tempFile = File.createTempFile(fileName, "");
            try (InputStream is = jarFile.getInputStream(jarEntry);
                 OutputStream os = new FileOutputStream(tempFile)) {
              byte[] buffer = new byte[4096];
              int bytesRead;
              while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
              }
            } catch (IOException e) {
              throw new RuntimeException(e);
            } finally {
              jarFile.close();
            }
            filePath = tempFile.getAbsolutePath();
            tempFile.deleteOnExit();
          }
          jarFile.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return filePath;
  }

  /**
   * Gets picture resource file path.
   *
   * @param filePath the file path
   * @return the picture resource file path
   */
  public static String getPictureResourceFilePath(String filePath) {
    URL resourceUrl = FileUtil.class.getClassLoader().getResource(filePath);
    if (resourceUrl != null) {
      if (resourceUrl.getProtocol().equals("jar")) {
        String jarFilePath = resourceUrl.getFile();
        String decodedJarFilePath = URLDecoder.decode(jarFilePath, StandardCharsets.UTF_8);
        String jarPath = decodedJarFilePath.substring(0, decodedJarFilePath.lastIndexOf("!"));
        return jarPath + "!/" + filePath;
      } else if (resourceUrl.getProtocol().equals("file")) {
        return new File("src/main/resources", filePath).getPath();
      }
    }
    return null;
  }
}