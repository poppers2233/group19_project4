package edu.oregonstate.cs361.battleship;

import com.google.gson.Gson;
import spark.Request;

import java.util.Random;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

public class Main {

    public static void main(String[] args) {


        //This will allow us to server the static pages such as index.html, app.js, etc.
        staticFiles.location("/public");
        //This will listen to GET requests to /model and return a clean new model
        get("/model", (req, res) -> newModel());
        //This will listen to POST requests and expects to receive a game model, as well as location to fire to
        post("/fire/:row/:col", (req, res) -> prepFire(req));
        //This will listen to POST requests and expects to receive a game model, as well as location to scan
        post("/scan/:row/:col", (req, res) -> scan(req));
        //This will listen to POST requests and expects to receive a game model, as well as location to place the ship
        post("/placeShip/:id/:row/:col/:orientation/:difficulty", (req, res) -> placeShip(req));


    }
}

