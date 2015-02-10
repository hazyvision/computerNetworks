
//Adriene Cuenco
// CS380(P5)


// UDP HEADERS BRANCH


import java.io.*;
import java.util.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.xml.bind.DatatypeConverter;
public class UdpClient {
		public static void main(String[] args) {
	   		try( Socket socket = new Socket("76.91.123.97",38005)){ 
	   			InputStream fromServer = socket.getInputStream();
	   			OutputStream toServer = socket.getOutputStream();
	   			/*
				int ctr=0;
				int dataSize=2;
				while( ctr != 11){
				*/
	   				int dataSize=4;
		   			
		   			//Initialize Ipv4 Header Variables Handshake packet
		   			byte version = 4;
		   			byte hlen = (byte) 5;
		   			byte tos=0;
		   			short length= ((short) (((hlen*4)+dataSize)));
		   			short ident = 0;
		   			short flags = 0;
		   			short offset = 1024;
		   			byte ttl = 50;
		   			byte protocol = 17; //UDP = 17 
		   			short checksum = 0;
		   			short udpChecksum= 0;
		   			int srcAddress = 0, destAddress=0;
		   			String ipSrcStr = "192.168.1.26", ipDestStr = "76.91.123.97";
		   			String readTheIP_src[] = ipSrcStr.split("\\.");
		   			String readTheIP_dest[] = ipDestStr.split("\\.");
		   			for(int i = 0; i < 4; ++i){
		   				srcAddress |= Integer.valueOf(readTheIP_src[i]) << ((3-i)*8);
		   				destAddress |= Integer.valueOf(readTheIP_dest[i])<< ((3-i)*8);	
		   			}

		   			// wrap and send Handshake packet 
		   			byte handshake[] = new byte[length];
		   			ByteBuffer bb = ByteBuffer.wrap(handshake);
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
		   			checksum = (byte) checksum_Funct(bb,hlen);
		   			bb.put(ByteBuffer.allocate(4).putInt((int) 0xDEADBEEF).array());
		   			
		   			toServer.write(handshake);
		   			byte array[] = new byte[2];
		   			fromServer.read(array);
					System.out.println("handshake> " + DatatypeConverter.printHexBinary(array));

<<<<<<< HEAD
//20 + datasize--------> 1st packet with UDP and pseudo
=======
					//20 + datasize--------> 1st packet with UDP and pseudo
>>>>>>> c62b483c9bdff8e4e1c735b6502a6e5acec7f24b
		   			//START UDP-----------------------------------------------------------------------------------------------------------
		   			// wrap and send udp packet 
					int dataSize2 = 2; 
					byte udpHlen = 8;

		   			
		   			//dataSize2+= udpHlen;
					
					short newLength =(short) (hlen*4 + (dataSize2 + udpHlen)); 	
	   				byte udpHeader[] = new byte[newLength];
	   				ByteBuffer buf = ByteBuffer.wrap(udpHeader);
		   			buf.put((byte) (((version & 0xF)<< 4)|(hlen & 0xF)));
		   			buf.put(tos);
		   			buf.putShort(newLength);
		   			buf.putShort(ident);
		   			buf.putShort((short)(((flags & 0x7) <<13)| (offset & 0x1FFFF) << 4));
		   			buf.put(ttl);
		   			buf.put(protocol);
		   			buf.putShort(udpChecksum);
		   			buf.putInt(srcAddress);
		   			buf.putInt(destAddress);
		   			checksum = (byte) checksum_Funct(buf,hlen);
		   			//--------------------------------------------------------------
	   				//pseudo Header for checksum calculations. Do not send to server
	   				int pseudoSrcAddress = srcAddress;
	   				int pseudoDestAddress = destAddress;
	   				byte zeros = 0;
	   				byte pseudoProtocol = 17;
	   				short pseudoUdpLength= newLength;
	   				
	   				byte psuedoHeader[] = new byte[12];
	   				ByteBuffer pseudoBuf = ByteBuffer.wrap(psuedoHeader);
	   				
	   				pseudoBuf.putInt(pseudoSrcAddress);
	   				pseudoBuf.putInt(pseudoDestAddress);
	   				pseudoBuf.put(zeros);
	   				pseudoBuf.put(pseudoProtocol);
	   				pseudoBuf.putShort(pseudoUdpLength);
	   				
		   			//--------------------UDP HEADER---------------------------

		   			
	   				short sourcePort =(short) 4201;
	   				short destinationPort = ByteBuffer.wrap(array).order(ByteOrder.LITTLE_ENDIAN).getShort();
	   				short udpLength = (short) (udpHlen + dataSize2);
	   				byte[] udpArr = new byte[udpLength];
	   				ByteBuffer udpBuf = ByteBuffer.wrap(udpArr);
	   				
	   				udpBuf.putShort(sourcePort);
	   				udpBuf.putShort(destinationPort);
	   				udpBuf.putShort(udpLength);
	   				udpBuf.putShort(udpChecksum);
	   				
		   			//Initialize dummy data 
		   			byte data[] = new byte[2];
		   			Random rand = new Random ();
		   			for(int i = 0; i < 2; i++){
		   				data[i] = (byte)rand.nextInt();
		   			} //end For
	   				udpBuf.put(data);

	   				//udpChecksum = checksum_Funct(pseudoBuf,(byte) 8 );
		   			buf.put(udpArr);
		   			toServer.write(udpHeader);
		   			byte[] server = new byte[4];
		   			fromServer.read(server);
		   			System.out.println("Server> " + DatatypeConverter.printHexBinary(server));

		   			//increment counter
		   			//dataSize = dataSize * 2;
		   			//ctr++;
				//} // end while loop
	   		}catch (Exception e){return;}//end try		
		}//end main
		
		/*
		public static short handShake(InputStream fromServer,OutputStream toServer) throws IOException{
			byte[] toHandShake = new byte[4];
			ByteBuffer buf = ByteBuffer.wrap(toHandShake);
			buf.putInt( 0xDEADBEEF);
			toServer.write(toHandShake);
			return (short)fromServer.read();	
		} //end handShake
		*/
		public static short checksum_Funct(ByteBuffer bb, byte hlen){
			short checksum;
			int num = 0;
			bb.rewind();
			for(int i = 0; i < hlen*2; ++i){
				num += 0xFFFF & bb.getShort();
			}
			num = ((num >> 16) & 0xFFFF) + (num & 0xFFFF);
			checksum = (short) (~num & 0xFFFF);
			bb.putShort(10,checksum);
			return checksum;
		}//end checksum_Funct
}//end UdpClient
