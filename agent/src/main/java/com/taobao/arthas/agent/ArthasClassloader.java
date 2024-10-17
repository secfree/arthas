package com.taobao.arthas.agent;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author beiwei30 on 09/12/2016.
 */
public class ArthasClassloader extends URLClassLoader {
    public ArthasClassloader(URL[] urls) {
        super(urls, ClassLoader.getSystemClassLoader().getParent());
    }

    @Override
    protected synchronized Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        final Class<?> loadedClass = findLoadedClass(name);
        if (loadedClass != null) {
            return loadedClass;
        }

        if (name != null) {
            try {
                return super.loadClass(name, resolve);
            } catch (ClassNotFoundException e) {
                // ignore
            }
        }

        try {
            Class<?> aClass = findClass(name);
            if (resolve) {
                resolveClass(aClass);
            }
            return aClass;
        } catch (Exception e) {
            // ignore
        }
        return super.loadClass(name, resolve);
    }
}
