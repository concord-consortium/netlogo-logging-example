#!/bin/sh -e -v

javac -classpath NetLogo-20110207.jar -d target/classes src/main/java/org/concord/netlogo/LoggingExample.java 

java -classpath target/classes:NetLogo-20110207.jar org.concord.netlogo.LoggingExample

#./lib/BehaviorSpace-1.0.jar:./lib/MRJAdapter-1.1-macosx.jar:./lib/activation-1.1.jar:./lib/asm-3.1.jar:./lib/asm-commons-3.1.jar:./lib/asm-tree-3.1.jar:./lib/asm-util-3.1.jar:./lib/gluegen-rt-1.0b05.jar:./lib/jhotdraw-6.0.jar:./lib/jmf-2.1.1e.jar:./lib/jms-1.1.jar:./lib/jmxri-1.2.1.jar:./lib/jmxtools-1.2.1.jar:./lib/jogl-1.1.1-rc6.jar:./lib/log4j-1.2.15.jar:./lib/mail-1.4.jar:./lib/NetLogo-20110119.jar:./lib/picocontainer-2.9.jar:./lib/quaqua-5.4.1-2009-07-14-macosx.jar:./lib/scala-library-2.7.7.jar:./lib/swing-layout-1.0.3.jar:nl-example.jar org.concord.netlogo.LoggingExample
