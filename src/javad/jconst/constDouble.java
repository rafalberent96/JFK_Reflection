package javad.jconst;

import java.io.*;


public class constDouble extends constLongConvert  {
  double d;
  
  private void toDouble( long l ) {
    d = Double.longBitsToDouble( l );
  }

  public void read( DataInputStream dStream ) {
    long longVal;

    longVal = readLong( dStream );
    toDouble( longVal );
  }

  public String getString() {
    return Double.toString( d );
  }

  public void pr() {
    System.out.print( d );
  } // pr
} // constDouble
