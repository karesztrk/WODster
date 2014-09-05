name := "wodster"

version := "1.0-SNAPSHOT"

resolvers += "Maven" at "http://repo1.maven.org/maven2"

libraryDependencies ++= Seq(
  javaJdbc, 
  javaJpa, 
  "org.hibernate" % "hibernate-entitymanager" % "4.2.2.Final",
  "postgresql" % "postgresql" % "9.1-901-1.jdbc4",
  cache,
  "com.lambdaworks" % "scrypt" % "1.3.1"
)     

play.Project.playJavaSettings
