package client.network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import messagesbase.*;
import messagesbase.messagesfromclient.*;
import messagesbase.messagesfromserver.EPlayerGameState;
import messagesbase.messagesfromserver.GameState;
import messagesbase.messagesfromserver.PlayerState;
import client.enums.EDirection;
import client.model.GameModelService;
import client.model.HalfMapModel;

public class ServerEndPoint {
    private static Logger logger = LoggerFactory.getLogger(ServerEndPoint.class);

    private WebClient baseWebClient;

    private String serverURL;
	private String gameID;
	private String uniquePlayerID;
	private GameState gs;
	private GameModelService gameService;
	private Converter converter;
	
	public ServerEndPoint(String serverURL, String gameID, GameModelService gs) {
		this.converter = new Converter();
		this.serverURL = serverURL;
		this.gameID = gameID;

		this.baseWebClient = WebClient.builder().baseUrl(this.serverURL + "/games")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE) 
																							
				.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_XML_VALUE).build();
		
		this.gameService = gs;
	}
	
	public void registerPlayer(String fName, String lName, String id) {
		PlayerRegistration playerReg = new PlayerRegistration(fName, lName,id);
		Mono<ResponseEnvelope> webAccess = this.baseWebClient.method(HttpMethod.POST)
				.uri("/" + this.gameID + "/players").body(BodyInserters.fromValue(playerReg)).retrieve().bodyToMono(ResponseEnvelope.class);

		
		ResponseEnvelope<UniquePlayerIdentifier> reqResult = webAccess.block();
		this.uniquePlayerID = reqResult.getData().get().getUniquePlayerID();

		logger.info("Client ID: " + this.uniquePlayerID);
		
		

	}

	public void sendHalfMap(HalfMapModel chm) {
		waitForTurn();
		Mono<ResponseEnvelope> webAccess = this.baseWebClient.method(HttpMethod.POST)
				.uri("/" + this.gameID + "/halfmaps")
				.body(BodyInserters.fromValue(converter.convertToHalfMap(this.uniquePlayerID, chm))).retrieve()
				.bodyToMono(ResponseEnvelope.class);

		ResponseEnvelope<UniquePlayerIdentifier> resultReg = webAccess.block();

		if (resultReg.getState() == ERequestState.Error)
			logger.error("send HalfMap error, errormessage: " +resultReg.getExceptionMessage());
		else{
			logger.info("HalfMap sent Exception Msg: " + resultReg.getExceptionMessage());
		}
	}
	
	public void sendMove(EDirection dir) {
		
		waitForTurn();
		Mono<ResponseEnvelope> webAccess = this.baseWebClient.method(HttpMethod.POST)
				.uri("/" + this.gameID + "/moves")
				.body(BodyInserters.fromObject(converter.convertToPlayerMove(this.uniquePlayerID, dir))).retrieve()
				.bodyToMono(ResponseEnvelope.class);

		ResponseEnvelope<UniquePlayerIdentifier> resultReg = webAccess.block();

		if (resultReg.getState() == ERequestState.Error)
//			throw new ServerResponseException(
//					resultReg.getExceptionName() + ", message: " + resultReg.getExceptionMessage());
			logger.error("Client error, errormessage: " +resultReg.getExceptionMessage());
		
		logger.info("send move: " + dir +resultReg.getExceptionMessage());

	}
	
	
	public GameState getGameState() {
		Mono<ResponseEnvelope> webAccess = this.baseWebClient.method(HttpMethod.GET)
				.uri("/" + this.gameID + "/states/" + this.uniquePlayerID).retrieve()
				.bodyToMono(ResponseEnvelope.class);
		ResponseEnvelope<GameState> requestResult = webAccess.block();

		if (requestResult.getState() == ERequestState.Error)
			logger.error("getGameState error, errormessage: " + requestResult.getExceptionMessage());
		
		this.gs = requestResult.getData().get();
		
		this.gameService.setGameState(
				converter.convertToGameStateModel(gs, uniquePlayerID));
	
		return this.gs;
	}
		
	
	public EPlayerGameState getMyPlayer(GameState gs) {
		for (PlayerState p : gs.getPlayers()) {
			if (p.getUniquePlayerID().equals(uniquePlayerID))
				return p.getState();
			
		}
		return null;
	}

	
	public void waitForTurn() {
		while (getMyPlayer(getGameState()).equals(EPlayerGameState.MustWait))
			;
	}

	
}

