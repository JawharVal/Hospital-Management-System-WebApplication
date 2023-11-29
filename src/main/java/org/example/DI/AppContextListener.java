package org.example.DI;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        BeanFactory.init("org.example");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // clean up resources here if needed
    }
}
