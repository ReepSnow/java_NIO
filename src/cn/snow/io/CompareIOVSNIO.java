package cn.snow.io;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class CompareIOVSNIO {
	//NIO的实现方式
	public static void method1(){
        RandomAccessFile aFile = null;
        try{
            aFile = new RandomAccessFile("src/nio.txt","rw");
            FileChannel fileChannel = aFile.getChannel();
            ByteBuffer buf = ByteBuffer.allocate(1024);
 
            int bytesRead = fileChannel.read(buf);
            System.out.println(bytesRead);
 
            while(bytesRead != -1)
            {
                buf.flip();
                while(buf.hasRemaining())
                {
                    System.out.print((char)buf.get());
                }
 
                buf.compact();
                bytesRead = fileChannel.read(buf);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally{
            try{
                if(aFile != null){
                    aFile.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
	//传统IO的实现方式
	public static void method2(){
	       InputStream in = null;
	       try{
	           in = new BufferedInputStream(new FileInputStream("src/nomal_io.txt"));
	 
	           byte [] buf = new byte[1024];
	           int bytesRead = in.read(buf);
	           while(bytesRead != -1)
	           {
	               for(int i=0;i<bytesRead;i++)
	                   System.out.print((char)buf[i]);
	               bytesRead = in.read(buf);
	           }
	       }catch (IOException e)
	       {
	           e.printStackTrace();
	       }finally{
	           try{
	               if(in != null){
	                   in.close();
	               }
	           }catch (IOException e){
	               e.printStackTrace();
	           }
	       }
	   }
	 //NIO的实现方式，利用method3()是采用MappedByteBuffer读取的
	 public static void method3(){
	      RandomAccessFile aFile = null;
	      FileChannel fc = null;
	      try{
	          aFile = new RandomAccessFile("src/1.ppt","rw");
	          fc = aFile.getChannel();
	          long timeBegin = System.currentTimeMillis();
	          MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0, aFile.length());
	          // System.out.println((char)mbb.get((int)(aFile.length()/2-1)));
	          // System.out.println((char)mbb.get((int)(aFile.length()/2)));
	          //System.out.println((char)mbb.get((int)(aFile.length()/2)+1));
	          long timeEnd = System.currentTimeMillis();
	          System.out.println("Read time: "+(timeEnd-timeBegin)+"ms");
	      }catch(IOException e){
	          e.printStackTrace();
	      }finally{
	          try{
	              if(aFile!=null){
	                  aFile.close();
	              }
	              if(fc!=null){
	                  fc.close();
	              }
	          }catch(IOException e){
	              e.printStackTrace();
	          }
	      }
	  }

	//NIO的实现方式，method4()对应的是ByteBuffer。
	public static void method4(){
	      RandomAccessFile aFile = null;
	      FileChannel fc = null;
	      try{
	          aFile = new RandomAccessFile("src/1.ppt","rw");
	          fc = aFile.getChannel();
	 
	          long timeBegin = System.currentTimeMillis();
	          ByteBuffer buff = ByteBuffer.allocate((int) aFile.length());
	          buff.clear();
	          fc.read(buff);
	          //System.out.println((char)buff.get((int)(aFile.length()/2-1)));
	          //System.out.println((char)buff.get((int)(aFile.length()/2)));
	          //System.out.println((char)buff.get((int)(aFile.length()/2)+1));
	          long timeEnd = System.currentTimeMillis();
	          System.out.println("Read time: "+(timeEnd-timeBegin)+"ms");
	 
	      }catch(IOException e){
	          e.printStackTrace();
	      }finally{
	          try{
	              if(aFile!=null){
	                  aFile.close();
	              }
	              if(fc!=null){
	                  fc.close();
	              }
	          }catch(IOException e){
	              e.printStackTrace();
	          }
	      }
	  }
}
