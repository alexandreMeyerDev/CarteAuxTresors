package service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Singleton;

import models.Adventurer;
import models.Adventurer.Movement;
import models.Adventurer.Orientation;
import models.Game;
import models.TreasureMap;
import models.treasuremap.Coordinates;
import models.treasuremap.Mountain;
import models.treasuremap.Treasure;

// use play logger instead of java errors // import play.Logger;

@Singleton
public class GameService {
    
    // launch a test with fixed data
    public void launchTestGame() {

        // build TreasureMap
        Set<Mountain> mountains = new HashSet<Mountain>(){{
            add(new Mountain(new Coordinates(2, 1)));
            add(new Mountain(new Coordinates(1, 2)));
        }};

        Set<Treasure> treasures = new HashSet<Treasure>(){{
            add(new Treasure(new Coordinates(0, 0), 1));
            add(new Treasure(new Coordinates(1, 0), 3));
        }};
        
		TreasureMap treasureMap = new TreasureMap(4, 5, mountains, treasures);

        // build Adventurer1
		Coordinates a1FirstCoordinates = new Coordinates(1, 1);
		List<Movement> a1Movements = new ArrayList();
		a1Movements.add(Movement.FORWARD);
		a1Movements.add(Movement.LEFT);

		Adventurer a1 = new Adventurer("Lara", a1FirstCoordinates, Orientation.NORTH, a1Movements, 0);

        // build Adventurer2
		Coordinates a2FirstCoordinates = new Coordinates(2, 2);
		List<Movement> a2Movements = new ArrayList();
        a2Movements.add(Movement.FORWARD);
        a2Movements.add(Movement.LEFT);

   		Adventurer a2 = new Adventurer("Indy", a2FirstCoordinates, Orientation.EAST, a2Movements, 0);
        
        // build Adventurer3
		Coordinates a3FirstCoordinates = new Coordinates(3, 3);
		List<Movement> a3Movements = new ArrayList();
        a3Movements.add(Movement.FORWARD);
        a3Movements.add(Movement.LEFT);

   		Adventurer a3 = new Adventurer("Nathan", a3FirstCoordinates, Orientation.SOUTH, a3Movements, 0);

        // build List of adventurers
        List<Adventurer> adventurers = new ArrayList();
        adventurers.add(a1);
        adventurers.add(a2);
        adventurers.add(a3);

        // build game
		Game game = new Game(treasureMap, adventurers, 0);
		
        System.out.println("--------- BEGINNING ------------------");
        System.out.println("");
        System.out.println(game.getTreasureMap());
        for(Adventurer a : game.getAdventurers()) {
            System.out.println("--- adventurer " + a.getName());
            System.out.println(a);
        }
        
		
        playRounds(game); // play all rounds of the game
        System.out.println("--------- GAME OVER ------------------");
        System.out.println("");
        System.out.println("END treasureMap infos = " + game.getTreasureMap());
        System.out.println("");
        for(Adventurer a : game.getAdventurers()) {
		    System.out.println("END adventurer " + a.getName() + " infos = " + a);
        }
        System.out.println("");
        System.out.println("--------------------------------------");
	}

    // play all rounds until end of the adventurer's movements 
    public void playRounds(Game game) {
        // calculate expected number of rounds
        
        int nbOfRounds = game.findMaxMovementsSize(game.getAdventurers());
        System.out.println("nbOfRounds = " + nbOfRounds);
        System.out.println("");
        for(int round = 0 ; round < nbOfRounds; round++) {
            playRound(game, round);
        }    
    }


    // play the current round of the game
	public void playRound(Game game, int round) {
        System.out.println("///// Game Round n°" + round + " /////");
        
        game.setCurrentRound(round); // update game round for potential use elsewhere in code
        for(Adventurer a : game.getAdventurers()) {
            System.out.println("// adventurer " + a.getName());
            moveAdventurer(game.getTreasureMap(), a, game.getCurrentRound());
            System.out.println("");
        }
        
        // System.out.println("");
        // System.out.println("treasureMap updated infos = " + game.getTreasureMap());
        // System.out.println("");
		// System.out.println("adventurer updated infos = " + game.getAdventurer());
        // System.out.println("");
	}
    
    // move an Adventurer forward, left, or right based on their movement for this round
    public Adventurer moveAdventurer(
        TreasureMap treasureMap,
        Adventurer adventurer, 
        int currentRound
    ) {
        Movement currentMovement = adventurer.getMovements().get(currentRound);
        switch(currentMovement) {
            case FORWARD:
                adventurer.moveForward(treasureMap);
                break;
            case LEFT:
                adventurer.turnLeft();
                break;
            case RIGHT:
                adventurer.turnRight();
                break;
            default:
            System.err.println("invalid movement found");
        }
        return adventurer;
    }
}
