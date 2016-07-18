package com.example.aditya.sridhara.rao.myapplication.backend;



import com.firebase.client.Firebase;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by aditya.sridhara.rao on 7/15/2016.
 */
public class useronlineservlet extends HttpServlet {

    static Logger Log = Logger.getLogger("com.example.looppool.backend.useronlineservlet");

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        Log.info("Got a request for online");
        Firebase firebase = new Firebase("https://loop-48f94.firebaseio.com/todoItems");
    }
}
