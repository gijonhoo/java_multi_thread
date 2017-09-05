package com.gijon.concurrent;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * PipedInputStream PipedOutputStream
 * PipedReader PipedWriter
 */
public class Thread24 extends Thread{
    private boolean isRead = true;
    private ReadData read;
    private WriteData write;
    private PipedInputStream input;
    private PipedOutputStream output;
    public void setRead(boolean read) {
        isRead = read;
    }

    public void setRead(ReadData read) {
        this.read = read;
    }

    public void setWrite(WriteData write) {
        this.write = write;
    }

    public void setInput(PipedInputStream input) {
        this.input = input;
    }

    public void setOutput(PipedOutputStream output) {
        this.output = output;
    }

    @Override
    public void run() {
        if(isRead){
            read.readMethod(input);
        }else{
            write.writeMethod(output);
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        ReadData read = new ReadData();
        WriteData write = new WriteData();

        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream();
        in.connect(out);

        Thread24 t1 = new Thread24();
        t1.setRead(true);
        t1.setRead(read);
        t1.setWrite(write);
        t1.setOutput(out);
        t1.setInput(in);
        t1.start();

        ThreadC.sleep(1000);

        Thread24 t2 = new Thread24();
        t2.setRead(false);
        t2.setRead(read);
        t2.setWrite(write);
        t2.setOutput(out);
        t2.setInput(in);
        t2.start();
    }
}

class WriteData{
    public void writeMethod(PipedOutputStream out){
        try{
            System.out.println("write:");
            for (int i=0;i < 300; i++){
                String outData = ""+(i+1);
                out.write(outData.getBytes());
                System.out.print(outData);
            }
            System.out.println();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ReadData {
    public void readMethod(PipedInputStream input){
        try{
            System.out.println("read: ");
            byte[] byteArray = new byte[20];
            int readLength = input.read(byteArray);
            while( readLength != -1){
                String newData = new String(byteArray, 0, readLength);
                System.out.print(newData);
                readLength = input.read(byteArray);
            }
            System.out.println();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
