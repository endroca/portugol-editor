name             := "SyntaxPane"

version          := "1.1.4-SNAPSHOT"

organization     := "de.sciss"

description      := "An extension of Java Swing's JEditorKit that supports syntax highlighting for several languages."

homepage         := Some(url("https://github.com/Sciss/" + name.value))

licenses         := Seq("Apache 2.0 License" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt"))

scalaVersion     := "2.11.2"

crossPaths       := false  // this is just a Java project right now!

// retrieveManaged  := true

autoScalaLibrary := false

mainClass in Compile := Some("de.sciss.syntaxpane.SyntaxTester")

javacOptions in (Compile, compile) ++= Seq("-g", "-source", "1.6", "-target", "1.6")

// ---- JFlex ----

seq(jflexSettings: _*)

// ---- publishing ----

publishMavenStyle := true

publishTo :=
  Some(if (isSnapshot.value)
    "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
  else
    "Sonatype Releases"  at "https://oss.sonatype.org/service/local/staging/deploy/maven2"
  )

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

pomExtra := { val n = name.value
<scm>
  <url>git@github.com:Sciss/{n}.git</url>
  <connection>scm:git:git@github.com:Sciss/{n}.git</connection>
</scm>
<developers>
  <developer>
    <id>sciss</id>
    <name>Hanns Holger Rutz</name>
    <url>http://www.sciss.de</url>
  </developer>
</developers>
}
