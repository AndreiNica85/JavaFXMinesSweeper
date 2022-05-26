package com.project.javafxminessweeper;

import java.util.Comparator;

public class PlayerComparator implements Comparator<Player> {
    @Override
    public int compare(Player p1, Player p2) {
        if(p1.getGamesFinished() - p2.getGamesFinished() != 0){
            return p2.getGamesFinished() - p1.getGamesFinished();
        }else if(p1.getPercentageFinishedGames() - p2.getPercentageFinishedGames() != 0.0){
            return (int) Math.ceil(p2.getPercentageFinishedGames() - p1.getPercentageFinishedGames());
        }else if(p1.getBestTimeInSeconds() - p2.getBestTimeInSeconds() != 0){
            return p1.getBestTimeInSeconds() - p2.getBestTimeInSeconds();
        }else if(p1.getBestPercentageRevealedMap() - p2.getBestPercentageRevealedMap() != 0.0){
            return (int) Math.ceil(p2.getBestPercentageRevealedMap() - p1.getBestPercentageRevealedMap());
        }else if(p1.getBestNumberOfSweptMines() - p2.getBestNumberOfSweptMines() != 0){
            return p2.getBestNumberOfSweptMines() - p1.getBestNumberOfSweptMines();
        }else {
            return p1.getBestNumberOfMouseClicks() - p2.getBestNumberOfMouseClicks();
        }
    }

}
