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
package org.picketlink.identity.federation.bindings.tomcat.idp;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import javax.servlet.ServletException;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;
import org.apache.log4j.Logger;
import org.picketlink.identity.federation.core.util.Base64;
import org.picketlink.identity.federation.web.constants.GeneralConstants;

/**
 * Debug Valve on the IDP end that will 
 * inform whether the SP is sending the SAMLRequest or not
 * properly
 * @author Anil.Saldhana@redhat.com
 * @since May 22, 2009
 */
public class IDPSAMLDebugValve extends ValveBase
{ 
   private static Logger log = Logger.getLogger(IDPSAMLDebugValve.class);
   private boolean debugEnabled = log.isDebugEnabled();
   @Override
   public void invoke(Request request, Response response) 
   throws IOException, ServletException
   {
      StringBuilder builder = new StringBuilder(); 
      String param = request.getParameter(GeneralConstants.SAML_REQUEST_KEY);
      builder.append("Method = " + request.getMethod()).append("\n");
      builder.append("SAMLRequest=" + decode(param, false, false)).append("\n");
      builder.append("SAMLResponse=" + decode(request.getParameter(GeneralConstants.SAML_RESPONSE_KEY), false, false)).append("\n");
      builder.append("Parameter exists? " + (param != null) ).append("\n");
      String debugInfo = builder.toString();
      if(debugEnabled)
         log.debug("SP Sent::"+ debugInfo);
       
      getNext().invoke(request, response);
   }
   
	private static String decode(String encoded, boolean urlDecode, boolean deflat) {
		try {
			if ( urlDecode ) {
				encoded = URLDecoder.decode(encoded, "UTF-8");
			}
			byte[] decoded = Base64.decode(encoded);
			
			InputStream is = null;
			if ( deflat ) {
				is = new InflaterInputStream(new ByteArrayInputStream(decoded), new Inflater(true));
			} else {
				is = new ByteArrayInputStream(decoded);
			}
			
			byte[] deflated = new byte[4096];
			
			int len = is.read(deflated);
			
			byte[] result = new byte[len];
			
			System.arraycopy(deflated, 0, result, 0, len);
			
			return new String(result);
		} catch (Exception e) {
			return encoded;
		}
	}

}
