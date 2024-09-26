def file = new File(request.getOutputDirectory(), request.getArtifactId() + "/mvnw");
file.setExecutable(true, false);
file = new File(request.getOutputDirectory(), request.getArtifactId() + "/mvnw.cmd");
file.setExecutable(true, false);
