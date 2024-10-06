// set the executable flag on the mvnw and mvnw.cmd files
def mavenExecutable = new File(request.getOutputDirectory(), request.getArtifactId() + "/mvnw")
mavenExecutable.setExecutable(true, false)
mavenExecutable = new File(request.getOutputDirectory(), request.getArtifactId() + "/mvnw.cmd")
mavenExecutable.setExecutable(true, false)

// prepend apache-header.txt to all Java files
def headerText = new File(request.getOutputDirectory(), request.getArtifactId() + "/src/checkstyle/apache-header.txt").text
def javaDirectories = []
javaDirectories << new File(request.getOutputDirectory(), request.getArtifactId() + "/src/main/java")
javaDirectories << new File(request.getOutputDirectory(), request.getArtifactId() + "/src/test/java")
javaDirectories.each { it.eachFileRecurse {
    javaFile ->
        if (javaFile.isFile() && javaFile.getName().endsWith(".java")) {
            javaFile.write(headerText + javaFile.text)
        }
    }
}

if (!request.properties['useShiro'].toBoolean()) {
    new File(request.getOutputDirectory(), request.getArtifactId() + "/src/main/webapp/WEB-INF/shiro.ini").delete()
}
