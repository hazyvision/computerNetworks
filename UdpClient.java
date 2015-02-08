
//Adriene Cuenco
// CS380(P5)

import java.io.*;
import java.util.*;
import java.net.*;
import java.nio.ByteBuffer;

import javax.xml.bind.DatatypeConverter;
public class UdpClient {
		public static void main(String[] args) {
	   		try( Socket socket = new Socket("76.91.123.97",38005)){ 
	   			InputStream fromServer =  socket.getInputStream();
	   			OutputStream toServer = socket.getOutputStream();
	   			short udpPortNumber = (short)handShake(fromServer,toServer); //Unsigned 16 bit int
	   			System.out.println(udpPortNumber);
	   			
	   			/*
				int ctr=0;
				int dataSize=2;
				while( ctr != 11){
				*/
	   				int dataSize=2;
		   			//Initialize data 
		   			byte data[] = new byte[dataSize];
		   			Random rand = new Random ();
		   			for(int i = 0; i < dataSize; i++){
		   				data[i] = (byte)rand.nextInt();
		   			} //end For
		   			
		   			//Initialize Header Variables
		   			byte version = 4;
		   			byte hlen = (byte) 5;
		   			byte tos=0;
		   			short length= ((short) ((hlen*4+data.length)));
		   			short ident = 0;
		   			short flags = 0;
		   			short offset = 1024;
		   			byte ttl = 50;
		   			byte protocol = 17; //UDP = 17 
		   			byte checksum = 0;
		   			int srcAddress = 0, destAddress=0;
		   			String ipSrcStr = "192.168.1.26", ipDestStr = "76.91.123.97";
		   			String readTheIP_src[] = ipSrcStr.split("\\.");
		   			String readTheIP_dest[] = ipDestStr.split("\\.");
		   			for(int i = 0; i < 4; ++i){
		   				srcAddress |= Integer.valueOf(readTheIP_src[i]) << ((3-i)*8);
		   				destAddress |= Integer.valueOf(readTheIP_dest[i])<< ((3-i)*8);	
		   			}
		   			
		   			// Header variables ByteBuffer Wrap
		   			byte b[] = new byte[length];
		   			ByteBuffer bb = ByteBuffer.wrap(b);
		   			bb.put((byte) (((version & 0xF)<< 4)|(hlen & 0xF)));
		   			bb.put(tos);
		   			bb.putShort(length);
		   			bb.putShort(ident);
		   			bb.putShort((short)(((flags & 0x7) <<13)| (offset & 0x1FFFF) << 4));
		   			bb.put(ttl);
		   			bb.put(protocol);
		   			bb.putShort(checksum);
		   			bb.putInt(srcAddress);
		   			bb.putInt(destAddress);
		   			checksum_Funct(checksum,bb,hlen);
		   			bb.put(data); 						
		   			toServer.write(b);
		   			byte[] server = new byte[4];
		   			fromServer.read(server);
		   			System.out.println("Server> " + DatatypeConverter.printHexBinary(server));

		   			//increment counter
		   			dataSize = dataSize * 2;
		   			//ctr++;
				//} // end while loop
	   		}catch (Exception e){return;}//end try		
		}//end main
		
		public static short handShake(InputStream fromServer,OutputStream toServer) throws IOException{
			byte[] toHandShake = new byte[4];
			ByteBuffer buf = ByteBuffer.wrap(toHandShake);
			buf.putInt( 0xDEADBEEF);
			toServer.write(toHandShake);
			return (short)fromServer.read();	
		} //end handShake
		
		public static void checksum_Funct(short checksum, ByteBuffer bb, byte hlen){
			int num = 0;
			bb.rewind();
			for(int i = 0; i < hlen*2; ++i){
				num += 0xFFFF & bb.getShort();
			}
			num = ((num >> 16) & 0xFFFF) + (num & 0xFFFF);
			checksum = (short) (~num & 0xFFFF);
			bb.putShort(10,checksum);
		}//end checksum_Funct
}//end UdpClient
