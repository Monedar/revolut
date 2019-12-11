package com.revolut.challenge.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revolut.challenge.model.Account;
import com.revolut.challenge.model.Transaction;
import com.revolut.challenge.service.ServiceImpl;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by Fazel on 12/11/2019.
 */
public class RestHandler {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final ServiceImpl service = new ServiceImpl();

    public static HttpHandler handleCreateAccount() throws IOException {
        return exchange -> {
            if (exchange.getRequestMethod().equalsIgnoreCase("POST")) {
                Account requestAccount = mapper.readValue(exchange.getRequestBody(), Account.class);
                service.saveOrUpdate(requestAccount);
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(201, -1); //Created
            } else {
                exchange.sendResponseHeaders(405, -1); //Method not allowed
            }
            exchange.close();
        };
    }

    public static HttpHandler handleFetchAllAccount() throws IOException {
        return exchange -> {
            if (exchange.getRequestMethod().equalsIgnoreCase("GET")) {
                List<Account> accounts = service.fetchAllAccounts();
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, mapper.writeValueAsBytes(accounts).length);
                OutputStream output = exchange.getResponseBody();
                output.write(mapper.writeValueAsBytes(accounts));
                output.flush();
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
            exchange.close();
        };

    }

    public static HttpHandler handleTransferMoney() throws IOException {
        return exchange -> {
            if (exchange.getRequestMethod().equalsIgnoreCase("POST")) {
                Transaction requestTransaction = mapper.readValue(exchange.getRequestBody(), Transaction.class);
                service.transferMoney(requestTransaction);
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, -1);
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
            exchange.close();
        };
    }

}