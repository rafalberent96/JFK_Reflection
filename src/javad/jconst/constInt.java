package javad.jconst;

import java.io.*;


/*
 * constInt
 *
 */
public class constInt extends constBase {
  int val;

  public void read( DataInputStream dStream ) {
    val = readU4(dStream);
  }

  public String getString() {
    return Integer.toString( val );
  }
  
  public void pr() {
    System.out.print( val );
  } // pr

} // constInt;

