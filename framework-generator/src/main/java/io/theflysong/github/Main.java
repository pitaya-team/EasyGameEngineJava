package io.theflysong.github;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Scanner;

import static io.theflysong.github.FILE_PATH.*;
import static io.theflysong.github.GRADLE_CONSTANTS.*;
import static io.theflysong.github.PROJECT_CONSTANTS.*;

public class Main {
    public static void main(String[] args) throws IOException {
        boolean useMirror = false;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input eg-engine version");
        String version = scanner.next();
        System.out.println("Input your project name:");
        String name = scanner.next();
        System.out.println("Input your project group:");
        String group = scanner.next();
        System.out.println("Input your project main-class name:");
        String main_class = scanner.next();
        System.out.println("Use China Mirror?[Y/n]");
        useMirror = scanner.next() != "n";

        File project_root = new File("./" + name + "/");
        if (! project_root.exists()) {
            project_root.mkdirs();
        }

        File project_wrapper = new File(project_root.getPath() + GRADLE_WRAPPER);
        if (! project_wrapper.exists()) {
            project_wrapper.mkdirs();
        }

        File project_libs = new File(project_root.getPath() + LIBS);
        if (! project_libs.exists()) {
            project_libs.mkdirs();
        }

        File project_src_main = new File(project_root.getPath() + SRC_MAIN);
        if (! project_src_main.exists()) {
            project_src_main.mkdirs();
        }

        File project_group = new File(project_src_main.getPath() + SRC_JAVA + group.replaceAll("\\.", "/") + "/");
        if (! project_group.exists()) {
            project_group.mkdirs();
        }

        File project_res_main = new File(project_src_main.getPath() + SRC_RESOURCE);
        if (! project_res_main.exists()) {
            project_res_main.mkdirs();
        }

        File project_main_class = new File(project_group.getPath() + "/" + main_class + ".java");
        if (! project_main_class.exists()) {
            project_main_class.createNewFile();
        }

        File project_gradle_wrapper_properties = new File(project_wrapper.getPath() + "/" + "gradle-wrapper.properties");
        if (! project_gradle_wrapper_properties.exists()) {
            project_gradle_wrapper_properties.createNewFile();
        }

        File project_build_gradle = new File(project_root.getPath() + "/" + "build.gradle");
        if (! project_build_gradle.exists()) {
            project_build_gradle.createNewFile();
        }

        File project_settings_gradle = new File(project_root.getPath() + "/" + "settings.gradle");
        if (! project_settings_gradle.exists()) {
            project_settings_gradle.createNewFile();
        }

        File project_gradle_properties = new File(project_root.getPath() + "/" + "gradle.properties");
        if (! project_gradle_properties.exists()) {
            project_gradle_properties.createNewFile();
        }

        File project_license = new File(project_root.getPath() + "/" + "LICENSE");
        if (! project_license.exists()) {
            project_license.createNewFile();
        }

        File project_readme = new File(project_root.getPath() + "/" + "README.md");
        if (! project_readme.exists()) {
            project_readme.createNewFile();
        }

        PrintWriter printer = new PrintWriter(project_main_class);
        printer.println(MAIN_JAVA.
                replaceAll("\\{\\$Group\\$}", group).
                replaceAll("\\{\\$Name\\$}", main_class)
        );
        printer.close();

        printer = new PrintWriter(project_build_gradle);
        printer.println(build_gradle.
                replaceAll("\\{\\$Maven\\$}", useMirror ? CHINA_MIRROR_REPO : LWJGL_REPO).
                replaceAll("\\{\\$Name\\$}", name).
                replaceAll("\\{\\$Group\\$}", group).
                replaceAll("\\{\\$Main-Class\\$}", main_class)
        );
        printer.close();

        printer = new PrintWriter(project_gradle_wrapper_properties);
        printer.println(gradle_wrapper_properties.
                replaceAll("\\{\\$Maven\\$}", useMirror ? CHINA_MIRROR_DIST_REPO : GRADLE_DIST_REPO)
        );
        printer.close();

        printer = new PrintWriter(project_settings_gradle);
        printer.println(settings_gradle.
                replaceAll("\\{\\$Name\\$}", name)
        );
        printer.close();

        printer = new PrintWriter(project_license);
        printer.println(LICENSE);
        printer.close();

        printer = new PrintWriter(project_readme);
        printer.println(README_MD.
                replaceAll("\\{\\$Name\\$}", name)
        );
        printer.close();

        printer = new PrintWriter(project_gradle_properties);
        printer.println(gradle_properties);
        printer.close();

        File project_eg_lib = new File(project_root.getPath() + LIBS + "EG-Engine-" + version + ".jar");
        File generator_eg_lib = new File("." + LIBS + "EG-Engine-" + version + ".jar");
        if (! generator_eg_lib.exists()) {
            throw new IllegalArgumentException("Invalid EG-Engine Version: " + version);
        }
        Files.copy(generator_eg_lib.toPath(), project_eg_lib.toPath());

        File project_wrapper_gradle = new File(project_root.getPath() + GRADLE_WRAPPER + "gradle-wrapper.jar");
        File generator_wrapper_gradle = new File("." + LIBS + "gradle-wrapper.jar");
        if (! generator_wrapper_gradle.exists()) {
            throw new IllegalArgumentException("No gradle-wrapper.jar in ./libs/");
        }
        Files.copy(generator_wrapper_gradle.toPath(), project_wrapper_gradle.toPath());
    }
}
