/*
 * Copyright (c) 2002-2012, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.whatsnew.business;

import fr.paris.lutece.portal.business.XmlContent;

import java.sql.Timestamp;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;


/**
 *
 * IWhatsNew
 *
 */
public interface IWhatsNew extends XmlContent
{
    /**
    * Returns the type of the whatsnew object
    * @return the type of the whatsnew object
    */
    WhatsNewType getWhatsNewType(  );

    /**
     * Returns the title of the whatsnew object
     * @return the title of the whatsnew object
     */
    String getTitle(  );

    /**
     * Returns the description of the whatsnew object
     * @return the description of the whatsnew object
     */
    String getDescription(  );

    /**
     * Returns the date of the last update of the whatsnew object
     * @return the date of the last update of the whatsnew object
     */
    Timestamp getDateUpdate(  );

    /**
     * Sets the type of the whatsnew object to the specified String
     * @param whatsNewType the new type of the whatsnew object
     */
    void setWhatsNewType( WhatsNewType whatsNewType );

    /**
     * Set the whatsnew type
     * @param locale {@link Locale}
     */
    void setWhatsNewType( Locale locale );

    /**
     * Sets the title of the whatsnew object to the specified String
     * @param strTitle the new title of the whatsnew object
     */
    void setTitle( String strTitle );

    /**
     * Sets the description of the whatsnew object to the specified String
     * @param strDescription the new description of the whatsnew object
     */
    void setDescription( String strDescription );

    /**
     * Sets the date of the last update of the whatsnew object
     * @param date the date update of the whatsnew object
     */
    void setDateUpdate( Timestamp date );

    /**
     * Get the document ID
     * @return Returns the _nDocumentId.
     */
    int getDocumentId(  );

    /**
     * Set the document ID
     * @param nDocumentId The _nDocumentId to set.
     */
    void setDocumentId( int nDocumentId );

    /**
     * Get the page ID
     * @return Returns the _nPageId.
     */
    int getPageId(  );

    /**
     * Set the page ID
     * @param nPageId The _nPageId to set.
     */
    void setPageId( int nPageId );

    /**
     * Get the portlet ID
     * @return Returns the _nPortletId.
     */
    int getPortletId(  );

    /**
     * Set the portlet ID
     * @param nPortletId The _nPortletId to set.
     */
    void setPortletId( int nPortletId );

    /**
     * Get the type of the whatsnew
     * @return the type of the whatsnew
     */
    String getType(  );

    /**
     * Set the type of the whatsnew
     * @param strType the type
     */
    void setType( String strType );

    /**
     * Get the HTML code of the moderated elements
     * @return HTML code
     */
    String getTemplateModeratedElement(  );

    /**
     * Build the url of the whatsnew
     * @return the url
     */
    String buildUrl(  );

    /**
     * Adds the XML header to the XML content of this whatsnew object and returns it
     * @param request The HTTP Servlet request
     * @return the Xml document with header
     */
    String getXmlDocument( HttpServletRequest request );

    /**
     * Builds the XML content of this whatsnew object and returns it
     * @param request The HTTP Servlet request
     * @return the Xml content of this whatsnew object
     */
    String getXml( HttpServletRequest request );
}
