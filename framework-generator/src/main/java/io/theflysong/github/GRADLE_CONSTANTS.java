package io.theflysong.github;

public class GRADLE_CONSTANTS {
    public static final String build_gradle =
            "import org.gradle.internal.os.OperatingSystem\n" +
                    "\n" +
                    "// {$Name$}\n" +
                    "\n" +
                    "apply plugin: 'java'\n" +
                    "\n" +
                    "group = '{$Group$}'\n" +
                    "version = project_version\n" +
                    "\n" +
                    "archivesBaseName = '{$Name$}'\n" +
                    "targetCompatibility = '1.8'\n" +
                    "sourceCompatibility = '1.8'\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "jar{\n" +
                    "    manifest{\n" +
                    "        attributes \"Main-Class\":\"{$Main-Class$}\"\n" +
                    "    }\n" +
                    "}\n"+
                    "project.ext.lwjglVersion = \"3.2.3\"\n" +
                    "project.ext.jomlVersion = \"1.10.0\"\n" +
                    "\n" +
                    "\n" +
                    "repositories {\n" +
                    "\n" +
                    "    {$Maven$}\n"+
                    "\n" +
                    "    mavenLocal()\n" +
                    "}\n" +
                    "\n" +
                    "\n" +
                    "task cpResource(type: Copy) {\n" +
                    "    from './src/main/resource'\n" +
                    "    into('./build/resource')\n" +
                    "}\n" +
                    "\n" +
                    "jar {\n" +
                    "    baseName = '{$Name$}'\n" +
                    "    version = project_version\n" +
                    "}\n" +
                    "\n" +
                    "project.ext.lwjglVersion = \"3.2.3\"\n" +
                    "project.ext.jomlVersion = \"1.10.0\"\n" +
                    "\n" +
                    "switch (OperatingSystem.current()) {\n" +
                    "    case OperatingSystem.LINUX:\n" +
                    "        project.ext.lwjglNatives = \"natives-linux\"\n" +
                    "        break\n" +
                    "    case OperatingSystem.MAC_OS:\n" +
                    "        project.ext.lwjglNatives = \"natives-macos\"\n" +
                    "        break\n" +
                    "    case OperatingSystem.WINDOWS:\n" +
                    "        project.ext.lwjglNatives = \"natives-windows\"\n" +
                    "        break\n" +
                    "}\n" +
                    "\n" +
                    "repositories {\n" +
                    "    mavenCentral()\n" +
                    "}\n" +
                    "\n" +
                    "dependencies {\n" +
                    "    implementation platform(\"org.lwjgl:lwjgl-bom:$lwjglVersion\")\n" +
                    "\n" +
                    "    implementation \"org.lwjgl:lwjgl\"\n" +
                    "    implementation \"org.lwjgl:lwjgl-assimp\"\n" +
                    "    implementation \"org.lwjgl:lwjgl-bgfx\"\n" +
                    "    implementation \"org.lwjgl:lwjgl-glfw\"\n" +
                    "    implementation \"org.lwjgl:lwjgl-llvm\"\n" +
                    "    implementation \"org.lwjgl:lwjgl-lz4\"\n" +
                    "    implementation \"org.lwjgl:lwjgl-nanovg\"\n" +
                    "    implementation \"org.lwjgl:lwjgl-nuklear\"\n" +
                    "    implementation \"org.lwjgl:lwjgl-openal\"\n" +
                    "    implementation \"org.lwjgl:lwjgl-opengl\"\n" +
                    "    implementation \"org.lwjgl:lwjgl-opus\"\n" +
                    "    implementation \"org.lwjgl:lwjgl-par\"\n" +
                    "    implementation \"org.lwjgl:lwjgl-stb\"\n" +
                    "    implementation \"org.lwjgl:lwjgl-tootle\"\n" +
                    "    implementation \"org.lwjgl:lwjgl-vma\"\n" +
                    "    implementation \"org.lwjgl:lwjgl-vulkan\"\n" +
                    "    implementation \"org.lwjgl:lwjgl-zstd\"\n" +
                    "    implementation fileTree(dir: 'libs', include: [ '*.jar'])\n" +
                    "    runtimeOnly \"org.lwjgl:lwjgl::$lwjglNatives\"\n" +
                    "    runtimeOnly \"org.lwjgl:lwjgl-assimp::$lwjglNatives\"\n" +
                    "    runtimeOnly \"org.lwjgl:lwjgl-bgfx::$lwjglNatives\"\n" +
                    "    runtimeOnly \"org.lwjgl:lwjgl-glfw::$lwjglNatives\"\n" +
                    "    runtimeOnly \"org.lwjgl:lwjgl-llvm::$lwjglNatives\"\n" +
                    "    runtimeOnly \"org.lwjgl:lwjgl-lz4::$lwjglNatives\"\n" +
                    "    runtimeOnly \"org.lwjgl:lwjgl-nanovg::$lwjglNatives\"\n" +
                    "    runtimeOnly \"org.lwjgl:lwjgl-nuklear::$lwjglNatives\"\n" +
                    "    runtimeOnly \"org.lwjgl:lwjgl-openal::$lwjglNatives\"\n" +
                    "    runtimeOnly \"org.lwjgl:lwjgl-opengl::$lwjglNatives\"\n" +
                    "    runtimeOnly \"org.lwjgl:lwjgl-opus::$lwjglNatives\"\n" +
                    "    runtimeOnly \"org.lwjgl:lwjgl-par::$lwjglNatives\"\n" +
                    "    runtimeOnly \"org.lwjgl:lwjgl-stb::$lwjglNatives\"\n" +
                    "    runtimeOnly \"org.lwjgl:lwjgl-tootle::$lwjglNatives\"\n" +
                    "    runtimeOnly \"org.lwjgl:lwjgl-vma::$lwjglNatives\"\n" +
                    "    if (lwjglNatives == \"natives-macos\") runtimeOnly \"org.lwjgl:lwjgl-vulkan::$lwjglNatives\"\n" +
                    "    runtimeOnly \"org.lwjgl:lwjgl-zstd::$lwjglNatives\"\n" +
                    "    implementation \"org.joml:joml:${jomlVersion}\"\n" +
                    "}";

    public static final String settings_gradle = "rootProject.name = '{$Name$}'";

    public static final String gradle_wrapper_properties =
            "distributionBase=GRADLE_USER_HOME\n" +
                    "distributionPath=wrapper/dists\n" +
                    "distributionUrl={$Maven$}\n" +
                    "zipStoreBase=GRADLE_USER_HOME\n" +
                    "zipStorePath=wrapper/dists\n";

    public static final String gradle_properties =
            "org.gradle.jvmargs=-Xmx1G\n" +
            "org.gradle.daemon=false\n" +
            "project_version=1.0.0";

    public static final String GRADLE_DIST_REPO = "https\\://services.gradle.org/distributions/gradle-6.8-bin.zip";
    public static final String CHINA_MIRROR_DIST_REPO = "https\\://longan.beanflame.cn/gradle/gradle-6.8-bin.zip";

    public static final String LWJGL_REPO = "mavenCentral()";
    public static final String CHINA_MIRROR_REPO =
            "maven {\n" +
                    "        url 'https://maven.aliyun.com/repository/public/'\n" +
                    "    }";
}
