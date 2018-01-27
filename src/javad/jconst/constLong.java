package javad.jconst;

import java.io.*;

/*
 * constLong
 *
 */
public class constLong extends constLongConvert {
  long longVal;

  public void read( DataInputStream dStream ) {
    longVal = readLong( dStream );
  }

  public String getString() {
    return Long.toString( longVal );
  }

  public void pr() {
    System.out.print( longVal );
  } // pr

} // constLong
