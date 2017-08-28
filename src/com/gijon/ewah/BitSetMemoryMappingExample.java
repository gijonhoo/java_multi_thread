package com.gijon.ewah;

import com.googlecode.javaewah.datastructure.BitSet;
import com.googlecode.javaewah.datastructure.ImmutableBitSet;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


public class BitSetMemoryMappingExample {
    
    public static void main(String[] args) throws IOException {
  		File tmpfile = File.createTempFile("javaewah", "bin");
  		tmpfile.deleteOnExit();
  		final FileOutputStream fos = new FileOutputStream(tmpfile);
  		BitSet bitmap = BitSet.bitmapOf(0, 2, 55, 64, 512);
  		System.out.println("Created the bitmap " + bitmap);
  		bitmap.serialize(new DataOutputStream(fos));
  		long totalCount = fos.getChannel().position();
  		System.out.println("Serialized total count = " + totalCount + " bytes");
  		fos.close();
  		RandomAccessFile memoryMappedFile = new RandomAccessFile(tmpfile, "r");
  		ByteBuffer bb = memoryMappedFile.getChannel().map(
  				FileChannel.MapMode.READ_ONLY, 0, totalCount);
  		ImmutableBitSet mapped = new ImmutableBitSet(bb.asLongBuffer());
  		System.out.println("Mapped the bitmap " + mapped);
  		memoryMappedFile.close();
  		if (!mapped.equals(bitmap))
  			throw new RuntimeException("Will not happen");
    }
}
