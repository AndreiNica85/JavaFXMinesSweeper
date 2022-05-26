package com.project.javafxminessweeper;

import javafx.animation.Animation;

import java.io.IOException;

public enum Status {

    INITIAL_STATE(false){
        @Override
        public void startStopShowStatsAndEndGameDialog(InGameController inGameController) {

        }
    },

    STARTED_RATED_GAME(true) {
        @Override
        public void startStopShowStatsAndEndGameDialog(InGameController inGameController) {
            inGameController.timeLine.setCycleCount(Animation.INDEFINITE);
            inGameController.timeLine.play();
        }
    },

    STARTED_CUSTOM_GAME(true) {
        @Override
        public void startStopShowStatsAndEndGameDialog(InGameController inGameController) {
            inGameController.timeLine.setCycleCount(Animation.INDEFINITE);
            inGameController.timeLine.play();
        }
    },

    FINISHED_RATED_GAME(false){
        @Override
        public void startStopShowStatsAndEndGameDialog(InGameController inGameController) {
            try {
                inGameController.timeLine.stop();
                inGameController.showEndGameDialogPane(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    },

    FINISHED_CUSTOM_GAME(false){
        @Override
        public void startStopShowStatsAndEndGameDialog(InGameController inGameController) {
            try {
                inGameController.timeLine.stop();
                inGameController.showEndGameDialogPane(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    },

    GAME_OVER(false) {
        @Override
        public void startStopShowStatsAndEndGameDialog(InGameController inGameController) {
            try {
                inGameController.timeLine.stop();
                inGameController.showEndGameDialogPane(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    final private boolean isPlaying;

    public abstract void startStopShowStatsAndEndGameDialog(InGameController inGameController);

    Status(boolean isPlaying){
        this.isPlaying = isPlaying;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

}
