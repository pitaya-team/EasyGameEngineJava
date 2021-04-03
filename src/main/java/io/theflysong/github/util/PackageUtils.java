package io.theflysong.github.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PackageUtils {
    public static Set<String> getAllClass() {
        Set<String> classNames = new HashSet<>();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        URL[] urls = ((URLClassLoader)loader).getURLs();
        for (URL url : urls) {
            JarFile jarFile = null;
            try{
                jarFile = new JarFile(url.getPath().substring(url.getPath().indexOf("/")));
            } catch(IOException e){
                classNames.addAll(getClassNameInPackageFromDir(url.getPath().substring(url.getPath().indexOf("/")), ""));
            }

            if(jarFile != null){
                classNames.addAll(getClassNameInPackageFromJar(jarFile.entries(), ""));
            }
        }

        return classNames;
    }

    public static Set<String> getClassNameInPackage(String packageName) {
        Set<String> classNames = null;
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        String packagePath = packageName.replace(".", "/");

        URL url = loader.getResource(packagePath);
        if (url != null) {
            String protocol = url.getProtocol();
            if (protocol.equals("file")) {
                classNames = getClassNameInPackageFromDir(url.getPath(), packageName);
            } else if (protocol.equals("jar")) {
                JarFile jarFile = null;
                try{
                    jarFile = ((JarURLConnection) url.openConnection()).getJarFile();
                } catch(Exception e){
                    e.printStackTrace();
                }

                if(jarFile != null){
                    getClassNameInPackageFromJar(jarFile.entries(), packageName);
                }
            }
        } else {
            classNames = getClassNameInPackageFromJars(((URLClassLoader)loader).getURLs(), packageName);
        }

        return classNames;
    }

    private static Set<String> getClassNameInPackageFromDir(String filePath, String packageName) {
        Set<String> className = new HashSet<>();
        try {
            filePath = java.net.URLDecoder.decode(new String(filePath.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8), "UTF-8");
            if (filePath.startsWith("META-INF"))
                return className;
            File file = new File(filePath);
            File[] files = file.listFiles();
            if (files == null) {
                return className;
            }
            for (File childFile : files) {
                if (childFile.isDirectory()) {
                    className.addAll(getClassNameInPackageFromDir(childFile.getPath(), packageName+"."+childFile.getName()));
                } else {
                    String fileName = childFile.getName();
                    if (fileName.endsWith(".class")) {
                        if (!packageName.equals(""))
                            fileName = packageName + "." +fileName;
                        if (fileName.endsWith(".class"))
                            fileName = fileName.substring(0, fileName.indexOf(".class"));
                        if (fileName.charAt(0) == '.')
                            fileName = fileName.substring(1);
                        className.add(fileName);
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return className;
    }


    private static Set<String> getClassNameInPackageFromJar(Enumeration<JarEntry> jarEntries, String packageName){
        Set<String> classNames = new HashSet<>();

        while (jarEntries.hasMoreElements()) {
            JarEntry jarEntry = jarEntries.nextElement();
            if(! jarEntry.isDirectory() && ! jarEntry.getName().startsWith("META-INF")){
                String entryName = jarEntry.getName().replace("/", ".");
                if (jarEntry.getName().endsWith(".class") && entryName.startsWith(packageName)) {
                    entryName = entryName.replace(".class", "");
                    if (entryName.endsWith("module-info") || entryName.endsWith("package-info"))
                        continue;
                    classNames.add(entryName);
                }
            }
        }

        return classNames;
    }

    private static Set<String> getClassNameInPackageFromJars(URL[] urls, String packageName) {
        Set<String> classNames = new HashSet<>();

        for (URL url : urls) {
            String classPath = url.getPath();

            if (classPath.endsWith("classes/")) {
                continue;
            }

            JarFile jarFile = null;
            try {
                jarFile = new JarFile(classPath.substring(classPath.indexOf("/")));
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (jarFile != null) {
                classNames.addAll(getClassNameInPackageFromJar(jarFile.entries(), packageName));
            }
        }

        return classNames;
    }
}
