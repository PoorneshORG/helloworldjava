package com.coveros.demo.helloworld;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class HelloWorld {

    public static void main(String[] args) throws IOException {
        // Use PORT from environment or default to 8080
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8080"));

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new HelloHandler());
        server.setExecutor(null); // use default executor
        server.start();
        System.out.println("✅ Server started on port " + port);
    }

    static class HelloHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "Hello, World! The current time is " + getCurrentTime();
            exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
            exchange.sendResponseHeaders(200, response.getBytes().length);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }

        private String getCurrentTime() {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("h:mm:ss a 'on' MMMM d, yyyy'.'");
            LocalDateTime now = LocalDateTime.now();
            return dtf.format(now);
        }
    }
}
