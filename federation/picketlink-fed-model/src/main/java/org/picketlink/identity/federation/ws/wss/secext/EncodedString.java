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
package org.picketlink.identity.federation.ws.wss.secext;

/**
 * This type is used for elements containing stringified binary data.
 * 
 * <p>Java class for EncodedString complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EncodedString">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd>AttributedString">
 *       &lt;attribute name="EncodingType" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
public class EncodedString extends AttributedString
{
   protected String encodingType;

   /**
    * Gets the value of the encodingType property.
    * 
    * @return
    *     possible object is
    *     {@link String }
    *     
    */
   public String getEncodingType()
   {
      return encodingType;
   }

   /**
    * Sets the value of the encodingType property.
    * 
    * @param value
    *     allowed object is
    *     {@link String }
    *     
    */
   public void setEncodingType(String value)
   {
      this.encodingType = value;
   }
}