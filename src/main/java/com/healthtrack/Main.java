package com.healthtrack;

import static spark.Spark.*;
import com.google.gson.Gson;

public class Main {

    private static Usuario currentUser;
    private static final Gson gson = new Gson();

    public static void main(String[] args) {
        port(8080);

        // --- CORRECCIÓN CRÍTICA AQUÍ ---
        // La configuración de archivos estáticos debe estar ANTES y FUERA de los grupos de rutas.
        staticFiles.location("/public"); 

        path("/healthtrack", () -> {
            
            get("/register", (req, res) -> {
                res.redirect("/healthtrack/register.html");
                return null;
            });

            post("/registerUser", (req, res) -> {
                String username = req.queryParams("username");
                double initialWeight = Double.parseDouble(req.queryParams("initialWeight"));

                currentUser = new Usuario(username, initialWeight);
                System.out.println("BACKEND: Usuario '" + username + "' registrado.");

                res.redirect("/healthtrack/dashboard.html");
                return null;
            });

            // --- API Endpoints ---
            get("/api/user/current", (req, res) -> {
                res.type("application/json");
                if (currentUser != null) {
                    return gson.toJson(currentUser);
                }
                return "{}";
            });

            post("/api/user/updateWeight", (req, res) -> {
                res.type("application/json");
                double newWeight = Double.parseDouble(req.queryParams("newWeight"));
                
                if (currentUser != null) {
                    currentUser.actualizarPeso(newWeight);
                    System.out.println("BACKEND: Peso actualizado a " + currentUser.getPeso());
                    return "{\"newWeight\": " + currentUser.getPeso() + "}";
                }
                
                res.status(400);
                return "{\"error\": \"No hay usuario registrado\"}";
            });
        });

        System.out.println("Servidor HealthTrack iniciado en http://localhost:8080/healthtrack/register");
    }
}