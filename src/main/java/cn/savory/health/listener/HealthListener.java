package cn.savory.health.listener;

import cn.savory.health.filter.HealthFilter;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author hc_zhang
 * @date 2018/6/20.
 */
@WebListener
public class HealthListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        FilterRegistration.Dynamic filterRegistration = sce.getServletContext().addFilter("healthFilter", HealthFilter.class);

        filterRegistration.addMappingForUrlPatterns(null, false, "/health/*");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
