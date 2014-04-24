/*
 * Copyright (c) 2002-2014, Mairie de Paris
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

import fr.paris.lutece.plugins.whatsnew.utils.constants.WhatsNewConstants;
import fr.paris.lutece.util.date.DateUtil;
import fr.paris.lutece.util.xml.XmlUtil;

import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;


/**
 *
 * WhatsNew
 *
 */
public abstract class WhatsNew implements IWhatsNew
{
    public static final String RESOURCE_TYPE = "WHATSNEW";
    private WhatsNewType _whatsNewType;

    /** Title of the whatsnew object */
    private String _strTitle;

    /** Description of the whatsnew object */
    private String _strDescription;

    /** Date of the last update */
    private Timestamp _dateUpdate;

    /** ids that are used to build the url to the element */
    private int _nPageId;
    private int _nPortletId;
    private int _nDocumentId;
    private String _strType;

    /**
     * Creates a new WhatsNew object.
     */
    public WhatsNew(  )
    {
    }

    /**
     * Returns the type of the whatsnew object
     * @return the type of the whatsnew object
     */
    public WhatsNewType getWhatsNewType(  )
    {
        return _whatsNewType;
    }

    /**
     * Returns the title of the whatsnew object
     * @return the title of the whatsnew object
     */
    public String getTitle(  )
    {
        return _strTitle;
    }

    /**
     * Returns the description of the whatsnew object
     * @return the description of the whatsnew object
     */
    public String getDescription(  )
    {
        return _strDescription;
    }

    /**
     * Returns the date of the last update of the whatsnew object
     * @return the date of the last update of the whatsnew object
     */
    public Timestamp getDateUpdate(  )
    {
        return _dateUpdate;
    }

    /**
     * Sets the type of the whatsnew object to the specified String
     * @param whatsNewType the new type of the whatsnew object
     */
    public void setWhatsNewType( WhatsNewType whatsNewType )
    {
        _whatsNewType = whatsNewType;
    }

    /**
     * Sets the title of the whatsnew object to the specified String
     * @param strTitle the new title of the whatsnew object
     */
    public void setTitle( String strTitle )
    {
        _strTitle = strTitle;
    }

    /**
     * Sets the description of the whatsnew object to the specified String
     * @param strDescription the new description of the whatsnew object
     */
    public void setDescription( String strDescription )
    {
        _strDescription = strDescription;
    }

    /**
     * Sets the date of the last update of the whatsnew object
     * @param date the date update of the whatsnew object
     */
    public void setDateUpdate( Timestamp date )
    {
        _dateUpdate = date;
    }

    /**
     * Get the document ID
     * @return Returns the _nDocumentId.
     */
    public int getDocumentId(  )
    {
        return _nDocumentId;
    }

    /**
     * Set the document ID
     * @param nDocumentId The _nDocumentId to set.
     */
    public void setDocumentId( int nDocumentId )
    {
        _nDocumentId = nDocumentId;
    }

    /**
     * Get the page ID
     * @return Returns the _nPageId.
     */
    public int getPageId(  )
    {
        return _nPageId;
    }

    /**
     * Set the page ID
     * @param nPageId The _nPageId to set.
     */
    public void setPageId( int nPageId )
    {
        _nPageId = nPageId;
    }

    /**
     * Get the portlet ID
     * @return Returns the _nPortletId.
     */
    public int getPortletId(  )
    {
        return _nPortletId;
    }

    /**
     * Set the portlet ID
     * @param nPortletId The _nPortletId to set.
     */
    public void setPortletId( int nPortletId )
    {
        _nPortletId = nPortletId;
    }

    /**
     * Get the type
     * @return the type
     */
    public String getType(  )
    {
        return _strType;
    }

    /**
     * Set the type
     * @param strType the type
     */
    public void setType( String strType )
    {
        _strType = strType;
    }

    /////////////////////////////////////////////////////////////////////////////
    // XML generation

    /**
     * Adds the XML header to the XML content of this whatsnew object and returns it
     * @param request The HTTP Servlet request
     * @return the Xml document with header
     */
    public String getXmlDocument( HttpServletRequest request )
    {
        return XmlUtil.getXmlHeader(  ) + getXml( request );
    }

    /**
     * Builds the XML content of this whatsnew object and returns it
     * @param request The HTTP Servlet request
     * @return the Xml content of this whatsnew object
     */
    public String getXml( HttpServletRequest request )
    {
    	Locale locale;

        if ( request != null )
        {
            locale = request.getLocale(  );
        }
        else
        {
            locale = Locale.getDefault(  );
        }
        StringBuffer strXml = new StringBuffer(  );
        XmlUtil.beginElement( strXml, WhatsNewConstants.TAG_WHATS_NEW_ELEMENT );

        XmlUtil.addElementHtml( strXml, WhatsNewConstants.TAG_WHATS_NEW_TYPE, getWhatsNewType(  ).getName(  ) );
        XmlUtil.addElementHtml( strXml, WhatsNewConstants.TAG_WHATS_NEW_TITLE, getTitle(  ) );

        if ( StringUtils.isNotBlank( getDescription(  ) ) )
        {
            XmlUtil.addElementHtml( strXml, WhatsNewConstants.TAG_WHATS_NEW_DESCRIPTION, getDescription(  ) );
        }

        XmlUtil.addElementHtml( strXml, WhatsNewConstants.TAG_WHATS_NEW_DATE_UPDATE,
            DateUtil.getDateString( getDateUpdate(  ), locale ) );

        XmlUtil.addElementHtml( strXml, WhatsNewConstants.TAG_WHATS_NEW_URL, buildUrl(  ) );

        XmlUtil.endElement( strXml, WhatsNewConstants.TAG_WHATS_NEW_ELEMENT );

        return strXml.toString(  );
    }
}
