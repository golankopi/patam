package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class PipeSimpleServer implements Server {
    final static int TCP_SERVER_PORT = 32;
    private ServerSocket serverSocket;
    private boolean stop = false;
	
    @Override
    public void start(ClientHandler clientHandler){
    	new Thread(() -> {
            try {
                startServer(clientHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    
	}
    
    private void startServer(ClientHandler clientHandler) throws IOException {
        serverSocket = new ServerSocket(PipeSimpleServer.TCP_SERVER_PORT);
        serverSocket.setSoTimeout(1000);
        System.out.println("Server connected - waiting");

        while (!stop) {
            try {
                Socket aClient = serverSocket.accept();
                System.out.println("client connected");
                clientHandler.handle(aClient.getInputStream(), aClient.getOutputStream());
                aClient.close();
            } catch (SocketTimeoutException e) {
//                System.out.println("Client did not connect...");
            }
        }
        serverSocket.close();
    }

	@Override
	public void stop() {
		stop=true;
	}

	public static void main(String[] args)
	{
		Server s = new PipeSimpleServer();
		s.start(new PipeSimpleClientHandler());
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
        s.stop();
        System.out.println("Closed server");
	}
}
