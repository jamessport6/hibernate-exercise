package core.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import static core.util.HibernateUtil.getSessionFactory;
import static core.util.HibernateUtil.shutdown;

@WebListener
public class HibernateListener implements ServletContextListener{
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		getSessionFactory();
	}
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		shutdown();
	}
}
