package javad.jconst;

import java.io.*;


/*
 * constFloat
 *
 */
class constFloat extends constBase {
  float f;

  // Convert the integer bits into a floating point number
  // with the same bit pattern.
  private void toFloat( int v ) { f = Float.intBitsToFloat( v ); }

  public void read( DataInputStream dStream ) {
    int val;

    val = readU4(dStream);
    toFloat( val );
  }

  public String getString() {
    return Float.toString( f );
  }

  public void pr() {
    System.out.print( f );
  } // pr

} // constFloat

