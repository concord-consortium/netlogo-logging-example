#!/bin/sh

cd `dirname $0`
DIR=`pwd`

echo "<?xml version='1.0' encoding='utf-8'?>
<jnlp spec='1.0+' codebase='file://${DIR}'>
  <information>
    <title>Netlogo Logging Example</title>
    <vendor>Concord Consortium</vendor>
    <description>Netlogo Logging Example</description>
    <offline-allowed/>
  </information>
  <security>
    <all-permissions/>
  </security>
  <resources>
    <j2se version='1.5+'/>
    <jar href='lib/BehaviorSpace-1.0.jar'/>
    <jar href='lib/MRJAdapter-1.1-macosx.jar'/>
    <jar href='lib/activation-1.1.jar'/>
    <jar href='lib/asm-3.1.jar'/>
    <jar href='lib/asm-commons-3.1.jar'/>
    <jar href='lib/asm-tree-3.1.jar'/>
    <jar href='lib/asm-util-3.1.jar'/>
    <jar href='lib/gluegen-rt-1.0b05.jar'/>
    <jar href='lib/jhotdraw-6.0.jar'/>
    <jar href='lib/jmf-2.1.1e.jar'/>
    <jar href='lib/jms-1.1.jar'/>
    <jar href='lib/jmxri-1.2.1.jar'/>
    <jar href='lib/jmxtools-1.2.1.jar'/>
    <jar href='lib/jogl-1.1.1-rc6.jar'/>
    <jar href='lib/log4j-1.2.15.jar'/>
    <jar href='lib/mail-1.4.jar'/>
    <jar href='lib/NetLogo-20110119.jar'/>
    <jar href='lib/picocontainer-2.9.jar'/>
    <jar href='lib/quaqua-5.4.1-2009-07-14-macosx.jar'/>
    <jar href='lib/scala-library-2.7.7.jar'/>
    <jar href='lib/swing-layout-1.0.3.jar'/>
    <jar href='nl-example.jar'/>
  </resources>
  <application-desc main-class='org.concord.netlogo.LoggingExample'/>
</jnlp>" > nl-example.jnlp

javaws nl-example.jnlp

cd -
