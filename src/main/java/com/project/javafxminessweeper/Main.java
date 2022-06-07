package com.project.javafxminessweeper;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** This class is for Desktop Application executable file purpose to be able to package and launch */

public class Main {

    static Path topPath;

    /* Populate Top 10 Tables on Start Menu */
    static{
        /* Path directory where .exe file is located */
        topPath = Path.of("").toAbsolutePath();

        /* Path and names for the 3 files to be created to contain a List of Player objects per file for Top 10 Tables */
        Path fileTopEasy = Path.of(topPath.toString(),"bestPlayersEasy.dat");
        Path fileTopNormal = Path.of(topPath.toString(),"bestPlayersNormal.dat");
        Path fileTopHard = Path.of(topPath.toString(),"bestPlayersHard.dat");

        /* Create the files only if they DO NOT exist - and write empty List objects on them for the game to use on Load Menu Stage */
        if(!Files.exists(fileTopEasy)){
            try {
                Files.createFile(fileTopEasy);
                Files.createFile(fileTopNormal);
                Files.createFile(fileTopHard);
            } catch (IOException e) {
                e.printStackTrace();
            }

            List<Player> listEasy = new ArrayList<>();
            List<Player> listNormal = new ArrayList<>();
            List<Player> listHard = new ArrayList<>();

            try(ObjectOutputStream objectOutputStream1 = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileTopEasy.toString())))){
                try {
                    objectOutputStream1.writeObject(listEasy);
                    objectOutputStream1.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }catch (IOException f){
                f.printStackTrace();
            }
            try(ObjectOutputStream objectOutputStream1 = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileTopNormal.toString())))){
                try {
                    objectOutputStream1.writeObject(listNormal);
                    objectOutputStream1.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }catch (IOException f){
                f.printStackTrace();
            }
            try(ObjectOutputStream objectOutputStream1 = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileTopHard.toString())))){
                try {
                    objectOutputStream1.writeObject(listHard);
                    objectOutputStream1.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }catch (IOException f){
                f.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        /* This property enables you to use any Scale percentage on Windows Display. Without it, you won't be able to see the square texts
        * on higher than 100% Scale length */
        System.setProperty("prism.allowhidpi", "false");

        /* Launch the game */
        MinesSweeper.minesSweeper(args);
    }
}