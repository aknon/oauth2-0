package com.goraksh.rest;
import java.util.Random;

/**
 * 
 * @author niteshk
 *
 */
public class RandomString
{
	
	private static final int LENGHT = 20;

  private static final char[] symbols = new char[36];

  static {
    for (int idx = 0; idx < 10; ++idx)
      symbols[idx] = (char) ('0' + idx);
    for (int idx = 10; idx < 36; ++idx)
      symbols[idx] = (char) ('a' + idx - 10);
  }

  private final Random random = new Random();
  private static RandomString instance;

  private final char[] buf;

  public static synchronized RandomString getInstance() {
	  if ( instance == null )
		  instance = new RandomString(LENGHT);
	  return instance;
  }
  
  private RandomString(int length)
  {
    if (length < 1)
      throw new IllegalArgumentException("length < 1: " + length);
    buf = new char[length];
  }

  public String nextString()
  {
    for (int idx = 0; idx < buf.length; ++idx) 
      buf[idx] = symbols[random.nextInt(symbols.length)];
    return new String(buf);
  }

}