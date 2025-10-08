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

var integrationPackageFile = new File(request.getOutputDirectory(),
        request.getArtifactId() + "/src/test/java/" + request.package.replace('.', '/') + "/its")
integrationPackageFile.mkdirs()

javaDirectories.each { it.eachFileRecurse {
    File javaFile ->
        if (javaFile.isFile() && javaFile.getName().endsWith(".java")) {
            def javaFileText = javaFile.text
            switch (request.properties['packagingType']) {
                case "war":
                    javaFileText = javaFileText.replace('JavaArchive', 'WebArchive')
                    break
                case "ear":
                    javaFileText = javaFileText.replace('JavaArchive', 'EnterpriseArchive')
                    break
            }
            javaFile.write(headerText + javaFileText)

            if (javaFile.getName().endsWith("IT.java")) {
                javaFile.renameTo(integrationPackageFile.getPath() + "/" + javaFile.getName())
            }
        }
    }
}

if (!request.properties['useShiro'].toBoolean()) {
    new File(request.getOutputDirectory(), request.getArtifactId() + "/src/main/webapp/WEB-INF/shiro.ini").delete()
}

if (request.properties['packagingType'] != "war") {
    new File(request.getOutputDirectory(), request.getArtifactId() + "/src/main/webapp").deleteDir()
}
