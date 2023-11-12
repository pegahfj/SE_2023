package client.main;

import client.controller.MainController;
import client.model.HalfMapModel;
import client.model.HalfMapService;
import client.network.ServerEndPoint;

public class MyMain {
	public static void main(String[] args) {
		System.out.println("hi");
//		String gameMode = args[0];
		String serverURL = args[1];
		String gameID =  args[2];
		MainController cntrl = new MainController(serverURL, gameID) ;
		cntrl.gameHandler();
	}
}
