package poker.app.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;



import org.apache.commons.math3.util.Combinations;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import domain.GameRuleCardsDomainModel;
import domain.GameRuleDomainModel;
import enums.eGame;
import enums.eRank;
import enums.eSuit;
import enums.eGame;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.SequentialTransitionBuilder;
import javafx.animation.TranslateTransition;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import logic.GameRuleBLL;
import logic.GameRuleCardsBLL;
import poker.app.MainApp;
import pokerBase.Card;
import pokerBase.Deck;
import pokerBase.GamePlay;
import pokerBase.GamePlayPlayerHand;
import pokerBase.Hand;
import pokerBase.Player;
import pokerBase.Rule;
import pokerBase.Action;
import pokerEnums.eDrawAction;
import pokerEnums.eGameState;


public class PokerTableController {

	boolean bPlay = false;
	
	boolean bP1Sit = false;
	boolean bP2Sit = false;
	boolean bP3Sit = false;
	boolean bP4Sit = false;

	// Reference to the main application.
	private MainApp mainApp;
	private GamePlay gme = null;
	private Timer timer;
	private int iCardDrawn = 0;
	private int iCardDrawnPlayer = 0;
	private int iCardDrawnCommon = 0;
	private int iTransFinished = 0;
	private int iDrawCount = 0;
	
	private long legnthOfTransitions = 450;

	private Player PlayerCommon = new Player("Common", 0);

	@FXML
	public AnchorPane APMainScreen;

	private ImageView imgTransCardP1 = new ImageView();
	private ImageView imgTransCardP2 = new ImageView();
	private ImageView imgTransCardP3 = new ImageView();
	private ImageView imgTransCardP4 = new ImageView();
	private ImageView imgTransCardCommon = new ImageView();
	
	private static GameRuleDomainModel rle;

	public static void setRle(GameRuleDomainModel rle) {
		PokerTableController.rle = rle;
	}

	@FXML
	public HBox HboxCommonArea;

	@FXML
	public HBox HboxCommunityCards;

	@FXML
	public HBox hBoxP1Cards;
	@FXML
	public HBox hBoxP2Cards;
	@FXML
	public HBox hBoxP3Cards;
	@FXML
	public HBox hBoxP4Cards;

	@FXML
	public TextField txtP1Name;
	@FXML
	public TextField txtP2Name;
	@FXML
	public TextField txtP3Name;
	@FXML
	public TextField txtP4Name;

	@FXML
	public Label lblP1Name;
	@FXML
	public Label lblP2Name;
	@FXML
	public Label lblP3Name;
	@FXML
	public Label lblP4Name;

	@FXML
	public Label winner1;
	@FXML
	public Label winner2;
	@FXML
	public Label winner3;
	@FXML
	public Label winner4;

	@FXML
	public ToggleButton btnP1SitLeave;
	@FXML
	public ToggleButton btnP2SitLeave;
	@FXML
	public ToggleButton btnP3SitLeave;
	@FXML
	public ToggleButton btnP4SitLeave;

	@FXML
	public Button btnDraw;

	@FXML
	public Button btnPlay;
	
	private eGameState eGameState;

	
	private ToggleGroup tglGame;
	
	
	public PokerTableController() {
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		this.btnPlay.setDisable(true);
		this.btnDraw.setVisible(false);
		this.winner1.setVisible(false);
		this.winner2.setVisible(false);
		this.winner3.setVisible(false);
		this.winner4.setVisible(false);

	}

	@FXML
	private void handleP1SitLeave() {
		int iPlayerPosition = 1;
		btnP1SitLeave.setDisable(true);
		bP1Sit = handleSitLeave(bP1Sit, iPlayerPosition, lblP1Name, txtP1Name, btnP1SitLeave);
		btnP1SitLeave.setDisable(false);
	}

	@FXML
	private void handleP2SitLeave() {
		int iPlayerPosition = 2;
		btnP2SitLeave.setDisable(true);
		bP2Sit = handleSitLeave(bP2Sit, iPlayerPosition, lblP2Name, txtP2Name, btnP2SitLeave);
		btnP2SitLeave.setDisable(false);
	}

	@FXML
	private void handleP3SitLeave() {
		int iPlayerPosition = 3;
		btnP3SitLeave.setDisable(true);
		bP3Sit = handleSitLeave(bP3Sit, iPlayerPosition, lblP3Name, txtP3Name, btnP3SitLeave);
		btnP3SitLeave.setDisable(false);
	}

	@FXML
	private void handleP4SitLeave() {
		int iPlayerPosition = 4;
		btnP4SitLeave.setDisable(true);
		bP4Sit = handleSitLeave(bP4Sit, iPlayerPosition, lblP4Name, txtP4Name, btnP4SitLeave);
		btnP4SitLeave.setDisable(false);
	}

	private boolean handleSitLeave(boolean bSit, int iPlayerPosition, Label lblPlayer, TextField txtPlayer,
			ToggleButton btnSitLeave) {
		if (bSit == false) {
			Player p = new Player(txtPlayer.getText(), iPlayerPosition);
			mainApp.AddPlayerToTable(p);
			lblPlayer.setText(txtPlayer.getText());
			lblPlayer.setVisible(true);
			btnSitLeave.setText("Leave");
			txtPlayer.setVisible(false);
			bSit = true;
			btnPlay.setDisable(false);
		} else {
			mainApp.RemovePlayerFromTable(iPlayerPosition);
			btnSitLeave.setText("Sit");
			txtPlayer.setVisible(true);
			lblPlayer.setVisible(false);
			bSit = false;
		}

		return bSit;
	}
	
	private void SetGameControls(eGameState eGameState) {
		switch (eGameState) {
		case StartOfGame:
			btnDraw.setVisible(true);
			btnDraw.setDisable(false);
			btnPlay.setVisible(false);
			iCardDrawn = 0;
			iCardDrawnPlayer = 0;
			iCardDrawnCommon = 0;
			iDrawCount = 0;
			break;
		case PlayOfGame:
			btnDraw.setDisable(false);
			break;
		case EndOfGame:
			btnDraw.setVisible(false);
			btnPlay.setVisible(true);
			break;
		case DrawingCard:
			btnDraw.setDisable(true);
			break;
		}
	}

	@FXML
	private void handlePlay() {

		String strRuleName = mainApp.getRuleName();
		HashMap<String, GameRuleDomainModel> hs = new HashMap();
		hs = GameRuleBLL.getRuleHashSet();		
		GameRuleDomainModel gr = hs.get(strRuleName);
		
		//Creates an array list of the rows that correspond to that rule
		ArrayList<GameRuleCardsDomainModel> rlecards = GameRuleCardsBLL.getCardsRules(gr.getRULEID());
		
		//tglGame = mainApp.getToggleGroup();
		
		eGameState = eGameState.StartOfGame;
		
		// Clear all players hands
		hBoxP1Cards.getChildren().clear();
		hBoxP2Cards.getChildren().clear();
		hBoxP3Cards.getChildren().clear();
		hBoxP4Cards.getChildren().clear();

		ImageView imgBottomCard = new ImageView(
				new Image(getClass().getResourceAsStream("/res/img/b1fh.png"), 75, 75, true, true));

		HboxCommonArea.getChildren().clear();
		HboxCommonArea.getChildren().add(imgBottomCard);
		HboxCommunityCards.getChildren().clear();

		// reset winner
		winner1.setVisible(false);
		winner2.setVisible(false);
		winner3.setVisible(false);
		winner4.setVisible(false);

		// Get the Rule, start the Game
		gme = new GamePlay(rle, rlecards);

		// Add the seated players to the game
		for (Player p : mainApp.GetSeatedPlayers()) {
			gme.addPlayerToGame(p);
			GamePlayPlayerHand GPPH = new GamePlayPlayerHand();
			GPPH.setGame(gme);
			GPPH.setPlayer(p);
			GPPH.setHand(new Hand());
			gme.addGamePlayPlayerHand(GPPH);
			DealFaceDownCards(gme.getNbrOfCards(), p.getiPlayerPosition());
		}

		GamePlayPlayerHand GPCH = new GamePlayPlayerHand();
		GPCH.setGame(gme);
		GPCH.setPlayer(PlayerCommon);
		GPCH.setHand(new Hand());
		gme.addGamePlayCommonHand(GPCH);
		DealFaceDownCards(gme.getRule().getCOMMUNITYCARDSMAX(), 0);

		// Add a deck to the game
		gme.setGameDeck(new Deck(rle.getNUMBEROFJOKERS()));

		// Call common code to set the game controls
		SetGameControls(eGameState);
		
		btnDraw.setVisible(true);
		btnDraw.setDisable(false);
		btnPlay.setVisible(false);
		iCardDrawn = 0;
		iCardDrawnPlayer = 0;
		iCardDrawnCommon = 0;

	}

	public void DealFaceDownCards(int nbrOfCards, int iPlayerPosition) {
		HBox PlayerCardBox = null;

		switch (iPlayerPosition) {
		case 0:
			PlayerCardBox = HboxCommunityCards;
			break;
		case 1:
			PlayerCardBox = hBoxP1Cards;
			break;
		case 2:
			PlayerCardBox = hBoxP2Cards;
			break;

		case 3:
			PlayerCardBox = hBoxP3Cards;
			break;

		case 4:
			PlayerCardBox = hBoxP4Cards;
			break;

		}
		String strCard = "/res/img/b1fv.png";

		for (int i = 0; i < nbrOfCards; i++) {
			ImageView img = new ImageView(new Image(getClass().getResourceAsStream(strCard), 75, 75, true, true));
			PlayerCardBox.getChildren().add(img);
		}
	}

	@FXML
	private void handleDraw() {
		iCardDrawn++;
		iDrawCount++;
		ImageView imView = null;
		eGameState = eGameState.PlayOfGame;

		// Disable the button in case of double-click
		SetGameControls(eGameState.DrawingCard);

		// Create the parent transition
		SequentialTransition tranDealCards = new SequentialTransition();

		// Figure the action based on the game, state of game
		Action act = new Action(gme, iCardDrawnPlayer, iCardDrawnCommon, iDrawCount);

		if (act.geteDrawAction() == eDrawAction.DrawPlayer) {
			// Draw a card for each player seated
			for (int iDraw = 0; iDraw < act.getiCardDrawn(); iDraw++) {
				iCardDrawnPlayer++;
				for (Player p : mainApp.GetSeatedPlayers()) {
					Card c = gme.getGameDeck().drawFromDeck();

					HBox PlayerCardBox = null;

					switch (p.getiPlayerPosition()) {
					case 1:
						PlayerCardBox = hBoxP1Cards;
						imView = imgTransCardP1;
						break;
					case 2:
						PlayerCardBox = hBoxP2Cards;
						imView = imgTransCardP2;
						break;

					case 3:
						PlayerCardBox = hBoxP3Cards;
						imView = imgTransCardP3;
						break;

					case 4:
						PlayerCardBox = hBoxP4Cards;
						imView = imgTransCardP4;
						break;

					}
					gme.FindPlayerGame(gme, p).addCardToHand(c);
					tranDealCards.getChildren().add(CalculateTransition(c, PlayerCardBox, imView, iCardDrawnPlayer));
				}
			}

		} else if (act.geteDrawAction() == eDrawAction.DrawCommon) {

			for (int iDraw = 0; iDraw < act.getiCardDrawn(); iDraw++) {
				iCardDrawnCommon++;
				imView = imgTransCardCommon;
				Card c = gme.getGameDeck().drawFromDeck();
				gme.FindCommonHand(gme).addCardToHand(c);
				tranDealCards.getChildren().add(CalculateTransition(c, HboxCommunityCards, imView, iCardDrawnCommon));
			}
		}

		tranDealCards.play();

		// If bEvalHand is true, it's time to evaluate the Hand...
		if (act.isbEvaluateHand()) {

			ArrayList<GamePlayPlayerHand> AllPlayersHands = new ArrayList<GamePlayPlayerHand>();
			ArrayList<Hand> BestPlayerHands = new ArrayList<Hand>();
			HashMap hsPlayerHand = new HashMap();

			for (Player p : mainApp.GetSeatedPlayers()) {
				GamePlayPlayerHand GPPH = gme.FindPlayerGame(gme, p);
				Hand PlayerHand = GPPH.getHand();
				GamePlayPlayerHand GPCH = gme.FindCommonHand(gme);

				ArrayList<Hand> AllHands = Hand.ListHands(GPPH.getHand(), GPCH.getHand(), GPPH.getGame());
				Hand hBestHand = Hand.PickBestHand(AllHands);
				GPPH.setBestHand(hBestHand);
				hsPlayerHand.put(hBestHand, GPPH.getPlayer());
				BestPlayerHands.add(hBestHand);
			}

			final Hand WinningHand = Hand.PickBestHand(BestPlayerHands);
			final Player WinningPlayer = (Player) hsPlayerHand.get(WinningHand);
			//System.out.println("Winning Player Position: " + WinningPlayer.getiPlayerPosition());
			
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				  @Override
				  public void run() {
					  winner(WinningPlayer);
					  winnersCards(WinningHand, WinningPlayer);
				  }
				}, ((long)mainApp.GetSeatedPlayers().size() * legnthOfTransitions));
			
			
			
		} else {
			
			
			if (iCardDrawnPlayer + iCardDrawnCommon + 2 >= gme.getRule().getCOMMUNITYCARDSMAX()) {
				for (Player p : mainApp.GetSeatedPlayers()) {
					Hand hPlayer = gme.FindPlayerGame(gme, p).getHand();
					for (int a = hPlayer.getCards().size(); a < gme.getRule().getPLAYERNUMBEROFCARDS(); a++) {
						hPlayer.AddCardToHand(new Card(eSuit.JOKER, eRank.JOKER, 0));
					}

					Hand hCommon = gme.FindCommonHand(gme).getHand();

					if (hCommon.getCards() == null) {
						for (int a = 0; a < gme.getRule().getCOMMUNITYCARDSMAX(); a++) {
							hCommon.AddCardToHand(new Card(eSuit.JOKER, eRank.JOKER, 0));
						}
					} else {

						for (int a = hCommon.getCards().size(); a < gme.getRule().getCOMMUNITYCARDSMAX(); a++) {
							hCommon.AddCardToHand(new Card(eSuit.JOKER, eRank.JOKER, 0));
						}
					}
					ArrayList<Hand> AllHands = Hand.ListHands(hPlayer, hCommon, gme);

				}
			}

			// Re-enable the draw button
			SetGameControls(eGameState);
		}

	}

	private void winner(Player WinningPlayer) {
		if (WinningPlayer.getiPlayerPosition() == 1) {
			winner1.setVisible(true);
		} else if (WinningPlayer.getiPlayerPosition() == 2) {
			winner2.setVisible(true);
		} else if (WinningPlayer.getiPlayerPosition() == 3) {
			winner3.setVisible(true);
		} else if (WinningPlayer.getiPlayerPosition() == 4) {
			winner4.setVisible(true);
		}
		btnPlay.setVisible(true);
	}
	
	private void winnersCards(Hand winningHand, Player p) {
		GamePlayPlayerHand gpph = gme.FindPlayerGame(gme, p);
		Hand playerHand = gpph.getHand();
		Hand communityC = gme.FindCommonHand(gme).getHand();
		HBox playerBox = null;
		if(p.getiPlayerPosition() == 1) {
			playerBox = hBoxP1Cards;
		} else if(p.getiPlayerPosition() == 2) {
			playerBox = hBoxP2Cards;
		} else if(p.getiPlayerPosition() == 3) {
			playerBox = hBoxP3Cards;
		} else if(p.getiPlayerPosition() == 4) {
			playerBox = hBoxP4Cards;
		}
		
		
		for(int i = 0; i < winningHand.getCardsInHand().size(); i++) {
			for(int q = 0; q < playerHand.getCardsInHand().size(); q++) {
				if(winningHand.GetCardFromHand(i) == playerHand.GetCardFromHand(q)) {
					ImageView winningCard = (ImageView)playerBox.getChildren().get(q);
					TranslateTransition translateTransition = new TranslateTransition(Duration.millis(300), winningCard);
					translateTransition.setFromX(0);
					translateTransition.setToX(0);
					translateTransition.setFromY(0);
					translateTransition.setToY(-10);
					translateTransition.setCycleCount(1);
					translateTransition.setAutoReverse(false);
					translateTransition.play();
				} else {
					
				}
			}
		}
		
		if(rle.getCOMMUNITYCARDSMAX() > 0) {
			for(int i = 0; i < winningHand.getCardsInHand().size(); i++) {
				for(int q = 0; q < communityC.getCardsInHand().size(); q++) {
					if(winningHand.GetCardFromHand(i) == communityC.GetCardFromHand(q)) {
						ImageView winningCard = (ImageView)HboxCommunityCards.getChildren().get(q);
						TranslateTransition translateTransition = new TranslateTransition(Duration.millis(300), winningCard);
						translateTransition.setFromX(0);
						translateTransition.setToX(0);
						translateTransition.setFromY(0);
						translateTransition.setToY(-10);
						translateTransition.setCycleCount(1);
						translateTransition.setAutoReverse(false);
						translateTransition.play();
					} else {
						
					}
				}
			}
		}
	}

	private SequentialTransition CalculateTransition(Card c, HBox PlayerCardBox, ImageView imView, int iCardDrawn) {
		// This is the card that is going to be dealt to the player.
		String strCard = "/res/img/" + c.getCardImg();
		ImageView imgvCardDealt = new ImageView(new Image(getClass().getResourceAsStream(strCard), 96, 71, true, true));

		// imgvCardFaceDown - There's already a place holder card
		// sitting in
		// the player's hbox. It's face down. Find it
		// and then determine it's bounds and top left hand handle.
		ImageView imgvCardFaceDown = (ImageView) PlayerCardBox.getChildren().get(iCardDrawn - 1);
		Bounds bndCardDealt = imgvCardFaceDown.localToScene(imgvCardFaceDown.getBoundsInLocal());
		Point2D pntCardDealt = new Point2D(bndCardDealt.getMinX(), bndCardDealt.getMinY());

		// imgvDealerDeck = the card in the common area, where dealer's
		// card
		// is located. Find the boundary top left point.
		ImageView imgvDealerDeck = (ImageView) HboxCommonArea.getChildren().get(0);
		Bounds bndCardDeck = imgvDealerDeck.localToScene(imgvDealerDeck.getBoundsInLocal());
		Point2D pntCardDeck = new Point2D(bndCardDeck.getMinX(), bndCardDeck.getMinY());

		// Add a sequential transition to the card (move, rotate)
		SequentialTransition transMoveRotCard = createTransition(pntCardDeck, pntCardDealt, imView);

		// Add a parallel transition to the card (fade in/fade out).
		final ParallelTransition transFadeCardInOut = createFadeTransition(imgvCardFaceDown,
				new Image(getClass().getResourceAsStream(strCard), 75, 75, true, true));

		SequentialTransition transAllActions = new SequentialTransition();
		transAllActions.getChildren().addAll(transMoveRotCard, transFadeCardInOut);

		return transAllActions;
	}

	private SequentialTransition createTransition(final Point2D pntStartPoint, final Point2D pntEndPoint,
			ImageView imView) {

		imView = new ImageView(new Image(getClass().getResourceAsStream("/res/img/b1fh.png"), 75, 75, true, true));

		imView.setX(pntStartPoint.getX());
		imView.setY(pntStartPoint.getY() - 30);

		APMainScreen.getChildren().add(imView);

		TranslateTransition translateTransition = new TranslateTransition(Duration.millis(300), imView);
		translateTransition.setFromX(0);
		translateTransition.setToX(pntEndPoint.getX() - pntStartPoint.getX());
		translateTransition.setFromY(0);
		translateTransition.setToY(pntEndPoint.getY() - pntStartPoint.getY());

		translateTransition.setCycleCount(1);
		translateTransition.setAutoReverse(false);

		int rnd = randInt(1, 3);

		RotateTransition rotateTransition = new RotateTransition(Duration.millis(150), imView);
		rotateTransition.setByAngle(90F);
		rotateTransition.setCycleCount(rnd);
		rotateTransition.setAutoReverse(false);

		ParallelTransition parallelTransition = new ParallelTransition();
		parallelTransition.getChildren().addAll(translateTransition, rotateTransition);

		SequentialTransition seqTrans = new SequentialTransition();
		seqTrans.getChildren().addAll(parallelTransition);

		final ImageView ivRemove = imView;
		seqTrans.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				APMainScreen.getChildren().remove(ivRemove);

			}
		});

		return seqTrans;
	}

	private ParallelTransition createFadeTransition(final ImageView iv, final Image img) {

		FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(.25), iv);
		fadeOutTransition.setFromValue(1.0);
		fadeOutTransition.setToValue(0.0);
		fadeOutTransition.setOnFinished(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				iv.setImage(img);

			}

		});

		FadeTransition fadeInTransition = new FadeTransition(Duration.seconds(.25), iv);
		fadeInTransition.setFromValue(0.0);
		fadeInTransition.setToValue(1.0);

		/*
		 * FadeTransition fadeFlyCare = FadeOutTransition(ivFlyCard);
		 */

		ParallelTransition parallelTransition = new ParallelTransition();
		parallelTransition.getChildren().addAll(fadeOutTransition, fadeInTransition);

		return parallelTransition;
	}

	/**
	 * randInt - Create a random number
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	private static int randInt(int min, int max) {

		return (int) (Math.random() * (min - max)) * -1;

	}
	
	

}
