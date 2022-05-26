package com.project.javafxminessweeper;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {

    static Path topPath;

    /* Populate Top 10 Tables on Start Menu */
    static{

        topPath = Path.of("").toAbsolutePath();

        Path fileTopEasy = Path.of(topPath.toString(),"bestPlayersEasy.dat");
        Path fileTopNormal = Path.of(topPath.toString(),"bestPlayersNormal.dat");
        Path fileTopHard = Path.of(topPath.toString(),"bestPlayersHard.dat");

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
        System.setProperty("prism.allowhidpi", "false");
        MinesSweeper.main(args);
    }
}