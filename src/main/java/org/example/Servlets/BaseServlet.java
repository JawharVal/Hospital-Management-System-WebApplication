package org.example.Servlets;

import org.example.DI.BeanFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

// for injecting dependency for servlets
public abstract class BaseServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        try {
            BeanFactory.injectDependencies(this);
        } catch (IllegalAccessException e) {
            throw new ServletException(e);
        }
    }
}
