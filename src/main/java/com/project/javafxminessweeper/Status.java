package com.project.javafxminessweeper;

import javafx.animation.Animation;

import java.io.IOException;

/** In Game Status Enum */

public enum Status {

    /* Initial Status is used when object created and is active before first mouse click on squares */
    INITIAL_STATUS{
        @Override
        public void startStopTimer_EndGameDialog(InGameController inGameController) {

        }
    },

    STARTED_RATED_GAME{
        @Override
        public void startStopTimer_EndGameDialog(InGameController inGameController) {
            inGameController.getTimeLine().setCycleCount(Animation.INDEFINITE);
            inGameController.getTimeLine().play();
        }
    },

    STARTED_CUSTOM_GAME{
        @Override
        public void startStopTimer_EndGameDialog(InGameController inGameController) {
            inGameController.getTimeLine().setCycleCount(Animation.INDEFINITE);
            inGameController.getTimeLine().play();
        }
    },

    FINISHED_RATED_GAME{
        @Override
        public void startStopTimer_EndGameDialog(InGameController inGameController) {
            try {
                inGameController.getTimeLine().stop();
                inGameController.showEndGameDialogPane(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    },

    FINISHED_CUSTOM_GAME{
        @Override
        public void startStopTimer_EndGameDialog(InGameController inGameController) {
            try {
                inGameController.getTimeLine().stop();
                inGameController.showEndGameDialogPane(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    },

    GAME_OVER {
        @Override
        public void startStopTimer_EndGameDialog(InGameController inGameController) {
            try {
                inGameController.getTimeLine().stop();
                inGameController.showEndGameDialogPane(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    /* Starts or Stops the Timer and shows EndGameDialog */
    public abstract void startStopTimer_EndGameDialog(InGameController inGameController);

}
