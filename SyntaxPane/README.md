# SyntaxPane

SyntaxPane is an extension to Java Swing's JEditorKit component which adds syntax highlighting support for various languages, including Scala and Java. The original project JSyntaxPane can be found [on google-code](http://code.google.com/p/jsyntaxpane/). This is a fork from the 0.9.6 branch.

The original project is (C)opyright by Ayman Al-Sairafi and released under the [Apache License, Version 2.0](http://github.com/Sciss/JSyntaxPane/blob/master/licenses/JSyntaxPane-License.txt).

All changes, reworkings and extensions in SyntaxPane (C)opyright 2011&ndash;2014 by Hanns Holger Rutz. Released under that same license.

## linking

The group-id and version have been adjusted to use my name space at Maven Central:

    "de.sciss" % "syntaxpane" % v

The current version `v` is `"1.1.3"`

## building

JSyntaxPane builds with sbt 0.13. The source code is purely Java at the moment, so no Scala compilation is run. The project uses the [sbt-jflex](https://github.com/sbt/sbt-jflex) plugin v0.1-SNAPSHOT, which in turn uses [JFlex](http://jflex.de/) 1.4.3 to generate the lexer Java sources for the supported languages.

To build run `sbt compile`. To run a demo application, run `sbt run`.

