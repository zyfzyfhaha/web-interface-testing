package com.cgw360.cls.web.base;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Class to hold the spring context.
 * @author zyf
 */
public final class SpringContextHolder {

    /**
     * The private constructor for utility class.
     */
    private SpringContextHolder() {

    }

    /**
     * The application context.
     */
    private static ApplicationContext applicationContext;

    static {
        applicationContext = new ClassPathXmlApplicationContext(
                "applicationContext.xml");
    }

    /**
     * Return the bean defined in spring configuration.
     * @param name
     *            the name of the bean to load.
     * @param <T>
     *            the type of the instance.
     * @return the bean instance.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(final String name) {
        assertContextInjected();
        return (T) applicationContext.getBean(name);
    }

    /**
     * Return the bean defined in spring configuration.
     * @param requiredType
     *            the type of the bean.
     * @param <T>
     *            the type of the instance.
     * @return the bean instance.
     */
    public static <T> T getBean(final Class<T> requiredType) {
        assertContextInjected();
        return applicationContext.getBean(requiredType);
    }
    
    /**
     * Return the bean defined in spring configuration.
     * @param beanName
     *            the name of the bean.
     * @return the bean instance.
     */
    public static Object getBeanForName(final String beanName) {
        assertContextInjected();
        return applicationContext.getBean(beanName);
    }

    /**
     * Check if the application context is injected.
     */
    private static void assertContextInjected() {
        if (applicationContext == null) {
            throw new IllegalStateException(
                    "applicaitonContext is not injected, Please setup"
                            + "SpringContextHolder in the applicationContext.xml");
        }
    }
}
