/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors. 
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.picketlink.identity.federation.core.impl;

import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * Privileged Blocks
 * @author Anil.Saldhana@redhat.com
 * @since Dec 9, 2008
 */
class SecurityActions
{

   static Class<?> loadClass(final Class<?> theClass, final String fqn)
   {
      return AccessController.doPrivileged(new PrivilegedAction<Class<?>>()
      {
         public Class<?> run()
         {
            ClassLoader classLoader = theClass.getClassLoader();

            Class<?> clazz = loadClass(classLoader, fqn);
            if (clazz == null)
            {
               classLoader = Thread.currentThread().getContextClassLoader();
               clazz = loadClass(classLoader, fqn);
            }
            return clazz;
         }
      });
   }

   static Class<?> loadClass(final ClassLoader cl, final String fqn)
   {
      return AccessController.doPrivileged(new PrivilegedAction<Class<?>>()
      {
         public Class<?> run()
         {
            try
            {
               return cl.loadClass(fqn);
            }
            catch (ClassNotFoundException e)
            {
            }
            return null;
         }
      });
   }

   /**
    * Load a resource based on the passed {@link Class} classloader.
    * Failing which try with the Thread Context CL
    * @param clazz
    * @param resourceName
    * @return
    */
   static URL loadResource(final Class<?> clazz, final String resourceName)
   {
      return AccessController.doPrivileged(new PrivilegedAction<URL>()
      {
         public URL run()
         {
            URL url = null;
            ClassLoader clazzLoader = clazz.getClassLoader();
            url = clazzLoader.getResource(resourceName);

            if (url == null)
            {
               clazzLoader = Thread.currentThread().getContextClassLoader();
               url = clazzLoader.getResource(resourceName);
            }

            return url;
         }
      });
   }

   /**
    * Get a system property
    * @param key the key for the property
    * @param defaultValue A default value to return if the property is not set (Can be null)
    * @return
    */
   static String getProperty(final String key, final String defaultValue)
   {
      return AccessController.doPrivileged(new PrivilegedAction<String>()
      {
         public String run()
         {
            return System.getProperty(key, defaultValue);
         }
      });
   }

   /**
    * Get the system property
    * @param key
    * @param defaultValue
    * @return
    */
   static String getSystemProperty(final String key, final String defaultValue)
   {
      return AccessController.doPrivileged(new PrivilegedAction<String>()
      {
         public String run()
         {
            return System.getProperty(key, defaultValue);
         }
      });
   }
}