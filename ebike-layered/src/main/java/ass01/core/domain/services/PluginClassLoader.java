package ass01.core.domain.services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PluginClassLoader extends ClassLoader {

    private final JarFile jarFile;

    public PluginClassLoader(String jarFilePath) throws Exception {
        this.jarFile = new JarFile(getFileFromResource(jarFilePath));
    }

    private File getFileFromResource(String fileName) throws URISyntaxException {

        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return new File(resource.toURI());
        }

    }

    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException {
        // Convert the class name to the expected path in the JAR
        String classFile = className.replace('.', '/') + ".class";
        JarEntry entry = jarFile.getJarEntry(classFile);

        if (entry == null) {
            throw new ClassNotFoundException(className);
        }

        try (InputStream input = jarFile.getInputStream(entry)) {
            // Read the class bytes
            byte[] classData = input.readAllBytes();
            // Define the class using the byte array
            return defineClass(className, classData, 0, classData.length);
        } catch (IOException e) {
            throw new ClassNotFoundException(className, e);
        }
    }
}