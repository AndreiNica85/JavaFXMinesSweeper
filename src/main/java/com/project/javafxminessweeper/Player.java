package com.project.javafxminessweeper;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Player implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;
    private String name;
    private int posNumber;
    private int totalGamesPlayed;
    private int gamesFinished;
    private int bestNumberOfSweptMines;
    private int bestNumberOfMouseClicks;
    private int bestTimeInSeconds;
    private double percentageFinishedGames;
    private double bestPercentageRevealedMap;

    public Player(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return name.equals(player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", posNumber=" + posNumber +
                ", totalGamesPlayed=" + totalGamesPlayed +
                ", gamesFinished=" + gamesFinished +
                ", bestNumberOfSweptMines=" + bestNumberOfSweptMines +
                ", bestNumberOfMouseClicks=" + bestNumberOfMouseClicks +
                ", bestTimeInSeconds=" + bestTimeInSeconds +
                ", percentageFinishedGames=" + percentageFinishedGames +
                ", bestPercentageRevealedMap=" + bestPercentageRevealedMap +
                '}';
    }

    public double getPercentageFinishedGames() {
        return percentageFinishedGames;
    }

    public void setPercentageFinishedGames(double percentageFinishedGames) {
        this.percentageFinishedGames = percentageFinishedGames;
    }

    public double calculatePercentageFinishedGames(int totalGamesPlayed, int gamesFinished) {
        return Double.parseDouble(String.format("%.2f",(gamesFinished * 100.0 / totalGamesPlayed)));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosNumber() {
        return this.posNumber;
    }

    public void setPosNumber(int posNumber) {
        this.posNumber = posNumber;
    }

    public int getTotalGamesPlayed() {
        return this.totalGamesPlayed;
    }

    public void setTotalGamesPlayed(int totalGamesPlayed) {
        this.totalGamesPlayed = totalGamesPlayed;
    }

    public int getGamesFinished() {
        return this.gamesFinished;
    }

    public void setGamesFinished(int gamesFinished) {
        this.gamesFinished = gamesFinished;
    }

    public int getBestNumberOfSweptMines() {
        return this.bestNumberOfSweptMines;
    }

    public void setBestNumberOfSweptMines(int bestNumberOfSweptMines) {
        this.bestNumberOfSweptMines = bestNumberOfSweptMines;
    }

    public int getBestNumberOfMouseClicks() {
        return this.bestNumberOfMouseClicks;
    }

    public void setBestNumberOfMouseClicks(int bestNumberOfMouseClicks) {
        this.bestNumberOfMouseClicks = bestNumberOfMouseClicks;
    }

    public int getBestTimeInSeconds() {
        return this.bestTimeInSeconds;
    }

    public void setBestTimeInSeconds(int bestTimeInSeconds) {
        this.bestTimeInSeconds = bestTimeInSeconds;
    }

    public double getBestPercentageRevealedMap() {
        return Double.parseDouble(String.format("%.2f",this.bestPercentageRevealedMap));
    }

    public void setBestPercentageRevealedMap(double bestPercentageRevealedMap) {
        this.bestPercentageRevealedMap = bestPercentageRevealedMap;
    }

}
