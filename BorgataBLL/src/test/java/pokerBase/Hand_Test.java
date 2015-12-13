package pokerBase;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import enums.eCardNo;
import enums.eHandStrength;
import enums.eRank;
import enums.eSuit;

import pokerBase.Card;
import pokerBase.Deck;
import pokerBase.Hand;

public class Hand_Test {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void Straight() {
		Deck d = new Deck();
		Hand h = new Hand();
		h.AddCardToHand(new Card(eSuit.CLUBS,eRank.TEN,0));
		h.AddCardToHand(new Card(eSuit.DIAMONDS,eRank.JACK,0));
		h.AddCardToHand(new Card(eSuit.CLUBS,eRank.QUEEN,0));
		h.AddCardToHand(new Card(eSuit.CLUBS,eRank.KING,0));
		h.AddCardToHand(new Card(eSuit.CLUBS,eRank.NINE,0));
		h = Hand.EvalHand(h);
		
		System.out.print(h.getHandStrength() + " handstrength");
		System.out.println(eHandStrength.Straight.getHandStrength());
		
		
		assertTrue(h.getHandStrength() == eHandStrength.Straight.getHandStrength());
		assertTrue(h.getHiHand() == eRank.KING.getRank());
		assertTrue(h.getLoHand() == 0);
		assertTrue(h.getKicker() == null);
	}
	
	@Test
	public void FiveOfAKind() {
		Deck d = new Deck();
		Hand h = new Hand();
		h.AddCardToHand(new Card(eSuit.CLUBS,eRank.TEN,0));
		h.AddCardToHand(new Card(eSuit.CLUBS,eRank.TEN,0));
		h.AddCardToHand(new Card(eSuit.CLUBS,eRank.TEN,0));
		h.AddCardToHand(new Card(eSuit.CLUBS,eRank.TEN,0));
		h.AddCardToHand(new Card(eSuit.CLUBS,eRank.TEN,0));
		h = Hand.EvalHand(h);
		
		assertTrue(h.getHandStrength() == eHandStrength.FiveOfAKind.getHandStrength());
		
	}

	@Test
	public void RoyalFlush() {
		Deck d = new Deck();
		Hand h = new Hand();
		h.AddCardToHand(new Card(eSuit.CLUBS,eRank.TEN,0));
		h.AddCardToHand(new Card(eSuit.CLUBS,eRank.JACK,0));
		h.AddCardToHand(new Card(eSuit.CLUBS,eRank.QUEEN,0));
		h.AddCardToHand(new Card(eSuit.CLUBS,eRank.KING,0));
		h.AddCardToHand(new Card(eSuit.CLUBS,eRank.ACE,0));
		h = Hand.EvalHand(h);
		
		assertTrue(h.getHandStrength() == eHandStrength.RoyalFlush.getHandStrength());
		
	}
	@Test
	public void StraightFlush() {
		Deck d = new Deck();
		Hand h = new Hand();
		h.AddCardToHand(new Card(eSuit.CLUBS,eRank.TEN,0));
		h.AddCardToHand(new Card(eSuit.CLUBS,eRank.JACK,0));
		h.AddCardToHand(new Card(eSuit.CLUBS,eRank.QUEEN,0));
		h.AddCardToHand(new Card(eSuit.CLUBS,eRank.KING,0));
		h.AddCardToHand(new Card(eSuit.CLUBS,eRank.NINE,0));
		h = Hand.EvalHand(h);
		
		assertTrue(h.getHandStrength() == eHandStrength.StraightFlush.getHandStrength());
		assertTrue(h.getHiHand() == eRank.KING.getRank());
		assertTrue(h.getLoHand() == 0);
		assertTrue(h.getKicker() == null);
		
		
	}
	
	@Test
	public void Flush() {
		Deck d = new Deck();
		Hand h = new Hand();
		h.AddCardToHand(new Card(eSuit.CLUBS,eRank.TEN,0));
		h.AddCardToHand(new Card(eSuit.CLUBS,eRank.JACK,0));
		h.AddCardToHand(new Card(eSuit.CLUBS,eRank.QUEEN,0));
		h.AddCardToHand(new Card(eSuit.CLUBS,eRank.KING,0));
		h.AddCardToHand(new Card(eSuit.CLUBS,eRank.TWO,0));
		h = Hand.EvalHand(h);
		
		assertTrue(h.getHandStrength() == eHandStrength.Flush.getHandStrength());
		assertTrue(h.getHiHand() == eRank.KING.getRank());
		assertTrue(h.getLoHand() == 0);
		assertTrue(h.getKicker() == null);
	}

	@Test
	public void FourOfAKind_1() {
		Deck d = new Deck();
		Hand h = new Hand();
		h.AddCardToHand(new Card(eSuit.CLUBS,eRank.TEN,0));
		h.AddCardToHand(new Card(eSuit.DIAMONDS,eRank.TEN,0));
		h.AddCardToHand(new Card(eSuit.SPADES,eRank.TEN,0));
		h.AddCardToHand(new Card(eSuit.HEARTS,eRank.TEN,0));
		h.AddCardToHand(new Card(eSuit.CLUBS,eRank.NINE,0));
		h = Hand.EvalHand(h);
		
		System.out.print(h.getHandStrength() + " handstrength");
		System.out.println(eHandStrength.FourOfAKind.getHandStrength());
		
		assertTrue(h.getHandStrength() == eHandStrength.FourOfAKind.getHandStrength());
		assertTrue(h.getHiHand() == eRank.TEN.getRank());
		assertTrue(h.getLoHand() == 0);
		assertTrue(h.getKicker().size() == 1);
		
		Card c1 = (Card) h.getKicker().get(eCardNo.FirstCard.getCardNo());
		
		//	Check to see if the kicker is a NINE
		assertTrue(c1.getRank().getRank() == eRank.NINE.getRank());
		
	}		

	@Test
	public void FourOfAKind_2() {
		Deck d = new Deck();
		Hand h = new Hand();
		h.AddCardToHand(new Card(eSuit.CLUBS,eRank.TEN,0));
		h.AddCardToHand(new Card(eSuit.DIAMONDS,eRank.TEN,0));
		h.AddCardToHand(new Card(eSuit.SPADES,eRank.TEN,0));
		h.AddCardToHand(new Card(eSuit.HEARTS,eRank.TEN,0));
		h.AddCardToHand(new Card(eSuit.CLUBS,eRank.KING,0));
		h = Hand.EvalHand(h);
		
		assertTrue(h.getHandStrength() == eHandStrength.FourOfAKind.getHandStrength());
		assertTrue(h.getHiHand() == eRank.TEN.getRank());
		assertTrue(h.getLoHand() == 0);
		assertTrue(h.getKicker().size() == 1);
	}		

	@Test
	public void ThreeOfAKind_1() {
		Deck d = new Deck();
		Hand h = new Hand();
		h.AddCardToHand(new Card(eSuit.CLUBS,eRank.TEN,0));
		h.AddCardToHand(new Card(eSuit.DIAMONDS,eRank.TEN,0));
		h.AddCardToHand(new Card(eSuit.SPADES,eRank.TEN,0));
		h.AddCardToHand(new Card(eSuit.HEARTS,eRank.FOUR,0));
		h.AddCardToHand(new Card(eSuit.CLUBS,eRank.NINE,0));
		h = Hand.EvalHand(h);
		
		assertTrue(h.getHandStrength() == eHandStrength.ThreeOfAKind.getHandStrength());
		assertTrue(h.getHiHand() == eRank.TEN.getRank());
		assertTrue(h.getLoHand() == 0);
		assertTrue(h.getKicker().size() == 2);
	}		
	@Test
	public void ThreeOfAKind_2() {
		Deck d = new Deck();
		Hand h = new Hand();
		h.AddCardToHand(new Card(eSuit.CLUBS,eRank.TEN,0));
		h.AddCardToHand(new Card(eSuit.DIAMONDS,eRank.TEN,0));
		h.AddCardToHand(new Card(eSuit.SPADES,eRank.TEN,0));
		h.AddCardToHand(new Card(eSuit.HEARTS,eRank.KING,0));
		h.AddCardToHand(new Card(eSuit.CLUBS,eRank.NINE,0));
		h = Hand.EvalHand(h);
		
		assertTrue(h.getHandStrength() == eHandStrength.ThreeOfAKind.getHandStrength());
		assertTrue(h.getHiHand() == eRank.TEN.getRank());
		assertTrue(h.getLoHand() == 0);
		assertTrue(h.getKicker().size() == 2);
		
		Card c1 = (Card) h.getKicker().get(eCardNo.FirstCard.getCardNo());
		Card c2 = (Card) h.getKicker().get(eCardNo.SecondCard.getCardNo());
		
		//	Check to see if the first kicker is a KING
		assertTrue(c1.getRank().getRank() == eRank.KING.getRank());
		
		//	Check to see if the second kicker is a NINE
		assertTrue(c2.getRank().getRank() == eRank.NINE.getRank());
	}		
	@Test
	public void ThreeOfAKind_3() {
		Deck d = new Deck();
		Hand h = new Hand();
		h.AddCardToHand(new Card(eSuit.CLUBS,eRank.TEN,0));
		h.AddCardToHand(new Card(eSuit.DIAMONDS,eRank.TEN,0));
		h.AddCardToHand(new Card(eSuit.SPADES,eRank.TEN,0));
		h.AddCardToHand(new Card(eSuit.HEARTS,eRank.TWO,0));
		h.AddCardToHand(new Card(eSuit.CLUBS,eRank.THREE,0));
		h = Hand.EvalHand(h);
		
		assertTrue(h.getHandStrength() == eHandStrength.ThreeOfAKind.getHandStrength());
		assertTrue(h.getHiHand() == eRank.TEN.getRank());
		assertTrue(h.getLoHand() == 0);
		assertTrue(h.getKicker().size() == 2);
		
		Card c1 = (Card) h.getKicker().get(eCardNo.FirstCard.getCardNo());
		Card c2 = (Card) h.getKicker().get(eCardNo.SecondCard.getCardNo());
		
		//	Check to see if the first kicker is a THREE
		assertTrue(c1.getRank().getRank() == eRank.THREE.getRank());
		
		//	Check to see if the second kicker is a TWO
		assertTrue(c2.getRank().getRank() == eRank.TWO.getRank());		
	}		
	@Test
	public void FullHouse_1() {
		Deck d = new Deck();
		Hand h = new Hand();
		h.AddCardToHand(new Card(eSuit.CLUBS,eRank.TEN,0));
		h.AddCardToHand(new Card(eSuit.DIAMONDS,eRank.TEN,0));
		h.AddCardToHand(new Card(eSuit.SPADES,eRank.TEN,0));
		h.AddCardToHand(new Card(eSuit.HEARTS,eRank.TWO,0));
		h.AddCardToHand(new Card(eSuit.CLUBS,eRank.TWO,0));
		h = Hand.EvalHand(h);
		
		assertTrue(h.getHandStrength() == eHandStrength.FullHouse.getHandStrength());
		assertTrue(h.getHiHand() == eRank.TEN.getRank());
		assertTrue(h.getLoHand() == eRank.TWO.getRank());
		assertTrue(h.getKicker() == null);	
	}		
	@Test
	public void FullHouse_2() {
		Deck d = new Deck();
		Hand h = new Hand();
		h.AddCardToHand(new Card(eSuit.CLUBS,eRank.TEN,0));
		h.AddCardToHand(new Card(eSuit.DIAMONDS,eRank.TEN,0));
		h.AddCardToHand(new Card(eSuit.SPADES,eRank.TWO,0));
		h.AddCardToHand(new Card(eSuit.HEARTS,eRank.TWO,0));
		h.AddCardToHand(new Card(eSuit.CLUBS,eRank.TWO,0));
		h = Hand.EvalHand(h);
		
		assertTrue(h.getHandStrength() == eHandStrength.FullHouse.getHandStrength());
		assertTrue(h.getHiHand() == eRank.TWO.getRank());
		assertTrue(h.getLoHand() == eRank.TEN.getRank());
		assertTrue(h.getKicker() == null);	
	}		
	
	@Test
	public void TwoPair() {
		Deck d = new Deck();
		Hand h = new Hand();
		h.AddCardToHand(new Card(eSuit.CLUBS,eRank.TEN,0));
		h.AddCardToHand(new Card(eSuit.DIAMONDS,eRank.TEN,0));
		h.AddCardToHand(new Card(eSuit.SPADES,eRank.TWO,0));
		h.AddCardToHand(new Card(eSuit.HEARTS,eRank.TWO,0));
		h.AddCardToHand(new Card(eSuit.CLUBS,eRank.ACE,0));
		h = Hand.EvalHand(h);
		
		assertTrue(h.getHandStrength() == eHandStrength.TwoPair.getHandStrength());
		assertTrue(h.getHiHand() == eRank.TEN.getRank());
		assertTrue(h.getLoHand() == eRank.TWO.getRank());
		assertTrue(h.getKicker().size() == 1);
		
		Card c1 = (Card) h.getKicker().get(eCardNo.FirstCard.getCardNo());
		
		//	Check to see if the first kicker is a THREE
		assertTrue(c1.getRank().getRank() == eRank.ACE.getRank());
	}		
	@Test
	public void Pair() {
		Deck d = new Deck();
		Hand h = new Hand();
		h.AddCardToHand(new Card(eSuit.CLUBS,eRank.TEN,0));
		h.AddCardToHand(new Card(eSuit.DIAMONDS,eRank.TEN,0));
		h.AddCardToHand(new Card(eSuit.SPADES,eRank.THREE,0));
		h.AddCardToHand(new Card(eSuit.HEARTS,eRank.TWO,0));
		h.AddCardToHand(new Card(eSuit.CLUBS,eRank.ACE,0));
		h = Hand.EvalHand(h);
		
		assertTrue(h.getHandStrength() == eHandStrength.Pair.getHandStrength());
		assertTrue(h.getHiHand() == eRank.TEN.getRank());
		assertTrue(h.getLoHand() == 0);
		assertTrue(h.getKicker().size() == 3);
		
		Card c1 = (Card) h.getKicker().get(eCardNo.FirstCard.getCardNo());
		Card c2 = (Card) h.getKicker().get(eCardNo.SecondCard.getCardNo());
		Card c3 = (Card) h.getKicker().get(eCardNo.ThirdCard.getCardNo());
		
		//	Check value of kicker
		assertTrue(c1.getRank().getRank() == eRank.ACE.getRank());

		//	Check value of kicker
		assertTrue(c2.getRank().getRank() == eRank.THREE.getRank());

		//	Check value of kicker
		assertTrue(c3.getRank().getRank() == eRank.TWO.getRank());

	}	
	
	@Test
	public void HighCard() {
		Deck d = new Deck();
		Hand h = new Hand();
		h.AddCardToHand(new Card(eSuit.CLUBS,eRank.TEN,0));
		h.AddCardToHand(new Card(eSuit.DIAMONDS,eRank.KING,0));
		h.AddCardToHand(new Card(eSuit.SPADES,eRank.THREE,0));
		h.AddCardToHand(new Card(eSuit.HEARTS,eRank.TWO,0));
		h.AddCardToHand(new Card(eSuit.CLUBS,eRank.ACE,0));
		h = Hand.EvalHand(h);
		
		assertTrue(h.getHandStrength() == eHandStrength.HighCard.getHandStrength());
		assertTrue(h.getHiHand() == eRank.ACE.getRank());
		assertTrue(h.getLoHand() == 0);
		assertTrue(h.getKicker().size() == 4);
		
		Card c1 = (Card) h.getKicker().get(eCardNo.FirstCard.getCardNo());
		Card c2 = (Card) h.getKicker().get(eCardNo.SecondCard.getCardNo());
		Card c3 = (Card) h.getKicker().get(eCardNo.ThirdCard.getCardNo());
		Card c4 = (Card) h.getKicker().get(eCardNo.FourthCard.getCardNo());
		
		//	Check value of kicker
		assertTrue(c1.getRank().getRank() == eRank.KING.getRank());

		//	Check value of kicker
		assertTrue(c2.getRank().getRank() == eRank.TEN.getRank());

		//	Check value of kicker
		assertTrue(c3.getRank().getRank() == eRank.THREE.getRank());

		//	Check value of kicker
		assertTrue(c4.getRank().getRank() == eRank.TWO.getRank());

	}		
	
	
	@Test
	public void CompareTwoHands() {
		Deck d = new Deck();
		Hand h1 = new Hand();
		h1.AddCardToHand(new Card(eSuit.CLUBS,eRank.TEN,0));
		h1.AddCardToHand(new Card(eSuit.DIAMONDS,eRank.TEN,0));
		h1.AddCardToHand(new Card(eSuit.SPADES,eRank.TEN,0));
		h1.AddCardToHand(new Card(eSuit.HEARTS,eRank.TWO,0));
		h1.AddCardToHand(new Card(eSuit.CLUBS,eRank.ACE,0));
		h1 = Hand.EvalHand(h1);
		
		Hand h2 = new Hand();
		h2.AddCardToHand(new Card(eSuit.CLUBS,eRank.NINE,0));
		h2.AddCardToHand(new Card(eSuit.DIAMONDS,eRank.NINE,0));
		h2.AddCardToHand(new Card(eSuit.SPADES,eRank.NINE,0));
		h2.AddCardToHand(new Card(eSuit.HEARTS,eRank.NINE,0));
		h2.AddCardToHand(new Card(eSuit.CLUBS,eRank.ACE,0));
		h2 = Hand.EvalHand(h2);
		
		ArrayList<Hand> TwoHands = new ArrayList<Hand>();
		TwoHands.add(h1);
		TwoHands.add(h2);
		
		Collections.sort(TwoHands,Hand.HandRank);
		
		Hand winningHand = new Hand();
		
		winningHand = TwoHands.get(0);
		
		assertTrue(winningHand.getHandStrength() == eHandStrength.FourOfAKind.getHandStrength());
		assertTrue(winningHand.getHiHand() == eRank.NINE.getRank());
		
	}	
	
}














