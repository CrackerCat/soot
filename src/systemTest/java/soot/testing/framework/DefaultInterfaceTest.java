package soot.testing.framework;

import org.junit.Test;

import java.util.Iterator;
import java.util.Set;

import org.junit.Assert;
import soot.Body;
import soot.MethodOrMethodContext;
import soot.SootClass;
import soot.Scene;
import soot.SootMethod;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Edge;
import soot.jimple.toolkits.callgraph.ReachableMethods;

public class DefaultInterfaceTest extends AbstractTestingFramework {

  @Test
  public void interfaceTest() {
	  String testClass = "soot.interfaceTesting.TestSimpleDefault";

	  final SootMethod target =
			  prepareTarget(
					  methodSigFromComponents(testClass, "void", "main"),
					  testClass,
					  "soot.interfaceTesting.Default");

	  SootMethod defaultMethod = Scene.v().getMethod("<soot.interfaceTesting.Default: void target()>");

	  Body body = defaultMethod.retrieveActiveBody();

	  final CallGraph cg = Scene.v().getCallGraph(); 
	  
	  boolean edgePresent = checkInEdges(cg, defaultMethod, target);
	  	  
	  final ReachableMethods reachableMethods = Scene.v().getReachableMethods(); 
	  
	  Assert.assertEquals(defaultMethod.getName(), "target");
	  Assert.assertNotNull(defaultMethod);
	  Assert.assertTrue(reachableMethods.contains(defaultMethod));
	  Assert.assertTrue(edgePresent);
    
  }
  
  @Test
  public void interfaceWithSameSignatureTest() {
	  String testClass = "soot.interfaceTesting.TestInterfaceSameSignature";

	  final SootMethod target =
			  prepareTarget(
					  methodSigFromComponents(testClass, "void", "main"),
					  testClass,
					  "soot.interfaceTesting.Read", "soot.interfaceTesting.Write");

	  SootMethod mainPrintMethod = Scene.v().getMethod("<soot.interfaceTesting.TestInterfaceSameSignature: void print()>");
	  SootMethod readInterfacePrint = Scene.v().getMethod("<soot.interfaceTesting.Read: void print()>");
	  SootMethod writeInterfacePrint = Scene.v().getMethod("<soot.interfaceTesting.Write: void print()>");
	  SootMethod defaultRead = Scene.v().getMethod("<soot.interfaceTesting.Read: void read()>");
	  SootMethod defaultWrite = Scene.v().getMethod("<soot.interfaceTesting.Write: void write()>");

	  final CallGraph cg = Scene.v().getCallGraph();
	  
	  /* Edges should be present */
	  boolean edgeMainPrintToReadPrint = checkInEdges(cg, readInterfacePrint, mainPrintMethod);
	  boolean edgeMainPrintToWritePrint = checkInEdges(cg, writeInterfacePrint, mainPrintMethod);
	  boolean edgeMainMethodToPrint = checkInEdges(cg, mainPrintMethod, target);
	  
	  /* Edges should not be present */
	  boolean edgeMainMethodToReadPrint = checkInEdges(cg, readInterfacePrint, target);
	  boolean edgeMainMethodToWritePrint = checkInEdges(cg, writeInterfacePrint, target);
	  
	  /* Edges should be present */
	  boolean edgeMainMethodToReadMethod = checkInEdges(cg, defaultRead, target);
	  boolean edgeMainMethodToWriteMethod = checkInEdges(cg, defaultWrite, target);

	  final ReachableMethods reachableMethods = Scene.v().getReachableMethods();
	  
	  Assert.assertNotNull(mainPrintMethod);
	  Assert.assertNotNull(readInterfacePrint);
	  Assert.assertNotNull(writeInterfacePrint);
	  Assert.assertNotNull(defaultRead);
	  Assert.assertNotNull(defaultWrite);
	  
	  Assert.assertEquals(mainPrintMethod.getName(), "print");
	  Assert.assertEquals(readInterfacePrint.getName(), "print");
	  Assert.assertEquals(writeInterfacePrint.getName(), "print");
	  Assert.assertEquals(defaultRead.getName(), "read");
	  Assert.assertEquals(defaultWrite.getName(), "write");
	  
	  Assert.assertTrue(reachableMethods.contains(mainPrintMethod));
	  Assert.assertTrue(reachableMethods.contains(readInterfacePrint));
	  Assert.assertTrue(reachableMethods.contains(writeInterfacePrint));
	  Assert.assertTrue(reachableMethods.contains(defaultRead));
	  Assert.assertTrue(reachableMethods.contains(defaultWrite));
	  
	  Assert.assertTrue(edgeMainPrintToReadPrint);
	  Assert.assertTrue(edgeMainPrintToWritePrint);
	  Assert.assertTrue(edgeMainMethodToPrint);
	  Assert.assertFalse(edgeMainMethodToReadPrint);
	  Assert.assertFalse(edgeMainMethodToWritePrint);
	  Assert.assertTrue(edgeMainMethodToReadMethod);
	  Assert.assertTrue(edgeMainMethodToWriteMethod);
	  
  }
  
  @Test
  public void classInterfaceWithSameSignatureTest() {
	  String testClass = "soot.interfaceTesting.TestClassPreferenceOverInterface";

	  final SootMethod target =
			  prepareTarget(
					  methodSigFromComponents(testClass, "void", "main"),
					  testClass,
					  "soot.interfaceTesting.HelloWorld");

	  SootMethod mainPrintMethod = Scene.v().getMethod("<soot.interfaceTesting.TestClassPreferenceOverInterface: void print()>");
	  SootMethod defaultPrintMethod = Scene.v().getMethod("<soot.interfaceTesting.HelloWorld: void print()>");

	  final CallGraph cg = Scene.v().getCallGraph();
	  
	  boolean edgeMainMethodToMainPrint = checkInEdges(cg, mainPrintMethod, target);
	  boolean edgeMainPrintToDefaultPrint = checkInEdges(cg, defaultPrintMethod, target);

	  final ReachableMethods reachableMethods = Scene.v().getReachableMethods();
	  
	  Assert.assertNotNull(mainPrintMethod);
	  Assert.assertNull(defaultPrintMethod);
	  
	  Assert.assertEquals(mainPrintMethod.getName(), "print");
	  
	  Assert.assertTrue(edgeMainMethodToMainPrint);
	  Assert.assertFalse(edgeMainPrintToDefaultPrint);
	  
	  Assert.assertTrue(reachableMethods.contains(mainPrintMethod));
	  Assert.assertFalse(reachableMethods.contains(defaultPrintMethod));	  
	  
  }
  
  @Test
  public void superClassInterfaceWithSameSignatureTest() {
	  String testClass = "soot.interfaceTesting.TestSuperClassInterfaceSameSignature";

	  final SootMethod target =
			  prepareTarget(
					  methodSigFromComponents(testClass, "void", "main"),
					  testClass,
					  "soot.interfaceTesting.PrintInterface", "soot.interfaceTesting.TestSuperClassImplementsInterface");

	  SootMethod defaultSuperMainMethod = Scene.v().getMethod("<soot.interfaceTesting.TestSuperClassImplementsInterface: void main()>");
	  SootMethod mainMethod = Scene.v().getMethod("<soot.interfaceTesting.TestSuperClassImplementsInterface: void print()>");
	  SootMethod defaultMethod = Scene.v().getMethod("<soot.interfaceTesting.PrintInterface: void print()>");
	  SootMethod defaultSuperClassMethod = Scene.v().getMethod("<soot.interfaceTesting.DefaultPrint: void print()>");

	  final CallGraph cg = Scene.v().getCallGraph();
	  
	  boolean edgeMainToSuperClassPrint = checkInEdges(cg, mainMethod, target);
	  boolean edgeMainToDefaultPrint = checkInEdges(cg, defaultMethod, target);
	  boolean edgeMainToSuperDefaultPrint = checkInEdges(cg, defaultSuperClassMethod, target);
	  boolean edgeSuperMainToSuperPrint = checkInEdges(cg, defaultSuperClassMethod, defaultSuperMainMethod);

	  final ReachableMethods reachableMethods = Scene.v().getReachableMethods();
	  
	  Assert.assertNotNull(mainMethod);
	  Assert.assertNull(defaultMethod);
	  Assert.assertNull(defaultSuperClassMethod);
	  
	  Assert.assertEquals(mainMethod.getName(), "print");  
	  
	  Assert.assertTrue(edgeMainToSuperClassPrint);
	  Assert.assertFalse(edgeMainToDefaultPrint);
	  Assert.assertFalse(edgeMainToSuperDefaultPrint);
	  Assert.assertFalse(edgeSuperMainToSuperPrint);
	  
	  Assert.assertTrue(reachableMethods.contains(mainMethod));
	  Assert.assertFalse(reachableMethods.contains(defaultSuperClassMethod));
	  Assert.assertFalse(reachableMethods.contains(defaultMethod));	  
  }  

  @Test
  public void derivedInterfacesTest() {
	  String testClass = "soot.interfaceTesting.TestDerivedInterfaces";
	  final SootMethod target =
			  prepareTarget(
					  methodSigFromComponents(testClass, "void", "main"),
					  testClass,
					  "soot.interfaceTesting.InterfaceTestOne", "soot.interfaceTesting.InterfaceTestTwo");	  
	  
	  SootMethod interfaceOnePrint = Scene.v().getMethod("<soot.interfaceTesting.InterfaceTestOne: void print()>");
	  SootMethod interfaceTwoPrint = Scene.v().getMethod("<soot.interfaceTesting.InterfaceTestTwo: void print()>");
	  
	  final CallGraph cg = Scene.v().getCallGraph();
	  
	  boolean edgeMainToInterfaceTwoPrint = checkInEdges(cg, interfaceTwoPrint, target);
	  boolean edgeMainToInterfaceOnePrint = checkInEdges(cg, interfaceOnePrint, target);
	  
	  final ReachableMethods reachableMethods = Scene.v().getReachableMethods();
	  
	  Assert.assertEquals(interfaceTwoPrint.getName(), "print");  
	  Assert.assertNotNull(interfaceTwoPrint);
	  Assert.assertNull(interfaceOnePrint);
	  
	  Assert.assertFalse(edgeMainToInterfaceOnePrint);
	  Assert.assertTrue(edgeMainToInterfaceTwoPrint);
	  
	  Assert.assertTrue(reachableMethods.contains(interfaceTwoPrint));
	  Assert.assertFalse(reachableMethods.contains(interfaceOnePrint));
	  
  }
  
  @Test
  public void interfaceInheritanceTest() {
	  String testClass = "soot.interfaceTesting.TestInterfaceInheritance";
	  final SootMethod target =
			  prepareTarget(
					  methodSigFromComponents(testClass, "void", "main"),
					  testClass,
					  "soot.interfaceTesting.InterfaceTestA", "soot.interfaceTesting.InterfaceTestB");	  
	  
	  SootMethod interfaceTestAPrint = Scene.v().getMethod("<soot.interfaceTesting.InterfaceTestA: void print()>");
	  SootMethod mainPrintMessageMethod = Scene.v().getMethod("<soot.interfaceTesting.TestInterfaceInheritance: void printMessage()>");
	  
	  final CallGraph cg = Scene.v().getCallGraph();
	  
	  boolean edgeMainToInterfaceTestAPrint = checkInEdges(cg, interfaceTestAPrint, target);
	  boolean edgeMainToMainPrintMessage = checkInEdges(cg, mainPrintMessageMethod, target);
	  
	  final ReachableMethods reachableMethods = Scene.v().getReachableMethods();
	  
	  Assert.assertEquals(interfaceTestAPrint.getName(), "print");
	  Assert.assertNotNull(interfaceTestAPrint);
	  Assert.assertNull(mainPrintMessageMethod);
	  
	  Assert.assertTrue(edgeMainToInterfaceTestAPrint);
	  Assert.assertFalse(edgeMainToMainPrintMessage);
	  
	  Assert.assertTrue(reachableMethods.contains(interfaceTestAPrint));
	  Assert.assertFalse(reachableMethods.contains(mainPrintMessageMethod));
	  
  }
  
  @Test
  public void interfaceReAbstractionTest() {
	  String testClass = "soot.interfaceTesting.TestInterfaceReAbstracting";
	  final SootMethod target =
			  prepareTarget(
					  methodSigFromComponents(testClass, "void", "main"),
					  testClass,
					  "soot.interfaceTesting.InterfaceA", "soot.interfaceTesting.InterfaceB");
	  
	  SootMethod interfaceAPrint = Scene.v().getMethod("<soot.interfaceTesting.InterfaceA: void print()>");
	  SootMethod mainMethodPrint = Scene.v().getMethod("<soot.interfaceTesting.TestInterfaceReAbstracting: void print()>");
	  
	  final CallGraph cg = Scene.v().getCallGraph();
	  
	  boolean edgeMainMethodToMainPrint = checkInEdges(cg, mainMethodPrint, target);
	  boolean edgeMainMethodToInterfaceAPrint = checkInEdges(cg, interfaceAPrint, target);
	  
	  final ReachableMethods reachableMethods = Scene.v().getReachableMethods();
	  
	  Assert.assertEquals(mainMethodPrint.getName(), "print");
	  Assert.assertNotNull(mainMethodPrint);
	  Assert.assertNull(interfaceAPrint);
	  
	  Assert.assertTrue(edgeMainMethodToMainPrint);
	  Assert.assertFalse(edgeMainMethodToInterfaceAPrint);
	  
	  Assert.assertTrue(reachableMethods.contains(mainMethodPrint));
	  Assert.assertFalse(reachableMethods.contains(interfaceAPrint));
  }
  
  public void SuperClassPreferenceOverDefaultMethodTest() {
	  String testClass = "soot.interfaceTesting.TestSuperClassPreferenceOverInterface";
	  final SootMethod target =
			  prepareTarget(
					  methodSigFromComponents(testClass, "void", "main"),
					  testClass,
					  "soot.interfaceTesting.InterfaceOne", "soot.interfaceTesting.InterfaceTwo", "soot.interfaceTesting.TestSuperClass");
	  
	  SootMethod interfaceOnePrint = Scene.v().getMethod("<soot.interfaceTesting.InterfaceOne: void print()>");
	  SootMethod interfaceTwoPrint = Scene.v().getMethod("<soot.interfaceTesting.InterfaceTwo: void print()>");
	  SootMethod superClassPrint = Scene.v().getMethod("<soot.interfaceTesting.TestSuperClass: void print()>");
	  
	  final CallGraph cg = Scene.v().getCallGraph();
	  
	  boolean edgeMainToInterfaceOnePrint = checkInEdges(cg, interfaceOnePrint, target);
	  boolean edgeMainToInterfaceTwoPrint = checkInEdges(cg, interfaceTwoPrint, target);
	  boolean edgeMainToSuperClassPrint = checkInEdges(cg, superClassPrint, target);
	  
	  final ReachableMethods reachableMethods = Scene.v().getReachableMethods();
	  
	  Assert.assertNotNull(superClassPrint);
	  Assert.assertNull(interfaceOnePrint);
	  Assert.assertNull(interfaceTwoPrint);
	  
	  Assert.assertEquals(superClassPrint.getName(), "print");
	  
	  Assert.assertTrue(edgeMainToSuperClassPrint);
	  Assert.assertFalse(edgeMainToInterfaceOnePrint);
	  Assert.assertFalse(edgeMainToInterfaceTwoPrint);
	  
	  Assert.assertTrue(reachableMethods.contains(superClassPrint));
	  Assert.assertFalse(reachableMethods.contains(interfaceOnePrint));
	  Assert.assertFalse(reachableMethods.contains(interfaceTwoPrint));
	  
	  
  }
  
  
  private boolean checkInEdges(CallGraph callGraph, SootMethod defaultMethod, SootMethod targetMethod) {

	  boolean isPresent = false;
	  Iterator<Edge> inEdges = callGraph.edgesInto(defaultMethod);
	  while(inEdges.hasNext()) {
		  MethodOrMethodContext sourceMethod = inEdges.next().getSrc();
		  if(sourceMethod.equals(targetMethod)) {
			  isPresent = true;
		  }
	  }
	  return isPresent;
  }	  
}
  