/*
 * Copyright (c) 2002-2010, Mairie de Paris
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
package fr.paris.lutece.plugins.whatsnew.business.portlet;

import fr.paris.lutece.plugins.whatsnew.business.IWhatsNew;
import fr.paris.lutece.plugins.whatsnew.service.WhatsNewService;
import fr.paris.lutece.plugins.whatsnew.service.portlet.WhatsNewPortletService;
import fr.paris.lutece.plugins.whatsnew.utils.constants.WhatsNewConstants;
import fr.paris.lutece.plugins.whatsnew.utils.sort.WhatsNewComparator;
import fr.paris.lutece.portal.business.portlet.Portlet;
import fr.paris.lutece.util.xml.XmlUtil;

import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;


/**
 *
 * WhatsNewPortlet
 *
 */
public class WhatsNewPortlet extends Portlet
{
    private boolean _bShowDocuments;
    private boolean _bShowPortlets;
    private boolean _bShowPages;
    private boolean _bIsAscSort;
    private int _nPeriod;
    private int _nNbElementsMax;
    private int _nElementsOrder;
    private boolean _bIsDynamic;

    /**
     * Sets the identifier of the portlet type to the value specified in the WhatsNewPortletHome class
     */
    public WhatsNewPortlet(  )
    {
        setPortletTypeId( WhatsNewPortletService.getInstance(  ).getPortletTypeId(  ) );
    }

    /**
     * Check if the portlet must show the documents or not
     * @return Returns the boolean that indicates if the user wants to see the documents
     */
    public boolean getShowDocuments(  )
    {
        return _bShowDocuments;
    }

    /**
     * Set true if the portlet must show the documents
     * @param bShowDocuments The new boolean that indicates if the user wants to see the articles
     */
    public void setShowDocuments( boolean bShowDocuments )
    {
        _bShowDocuments = bShowDocuments;
    }

    /**
     * Check if the portlet must show the portlets or not
     * @return Returns the boolean that indicates if the user wants to see the portlets
     */
    public boolean getShowPortlets(  )
    {
        return _bShowPortlets;
    }

    /**
     * Set true if the portlet must show the portlets
     * @param bPortlets The new boolean that indicates if the user wants to see the portlets
     */
    public void setShowPortlets( boolean bPortlets )
    {
        _bShowPortlets = bPortlets;
    }

    /**
     * Check if the portlet must show the pages or not
     * @return Returns the boolean that indicates if the user wants to see the pages
     */
    public boolean getShowPages(  )
    {
        return _bShowPages;
    }

    /**
     * Set true if the portlet must show the pages
     * @param bPages The new boolean that indicates if the user wants to see the pages
     */
    public void setShowPages( boolean bPages )
    {
        _bShowPages = bPages;
    }

    /**
     * Get the period
     * @return Returns the period (number of days)
     */
    public int getPeriod(  )
    {
        return _nPeriod;
    }

    /**
     * Set the period
     * @param nPeriod The period to set (number of days)
     */
    public void setPeriod( int nPeriod )
    {
        this._nPeriod = nPeriod;
    }

    /**
     * Get the maximum number of elements to see in the portlet
     * @return Returns the nbElementsMax.
     */
    public int getNbElementsMax(  )
    {
        return _nNbElementsMax;
    }

    /**
     * Set maximum number of elements to see in the portlet
     * @param nElementsMax The maximum number of elements to see in the portlet.
     */
    public void setNbElementsMax( int nElementsMax )
    {
        _nNbElementsMax = nElementsMax;
    }

    /**
     * Get the element order
     * @return Returns order of the elements to show.
     */
    public int getElementsOrder(  )
    {
        return _nElementsOrder;
    }

    /**
     * Set the element order
     * @param nOrder The maximum number of elements to see in the portlet.
     */
    public void setElementsOrder( int nOrder )
    {
        _nElementsOrder = nOrder;
    }

    /**
     * Check if the portlet is sorting ascendingly
     * @return true if it is sorting ascendingly
     */
    public boolean getAscSort(  )
    {
        return _bIsAscSort;
    }

    /**
     * Set the sorting attribute
     * @param bIsAscSort true if it is sorting ascendingly
     */
    public void setAscSort( boolean bIsAscSort )
    {
        _bIsAscSort = bIsAscSort;
    }

    /**
     * Returns the XML content of the portlet with the XML header
     * @param request The HTTP Servlet request
     * @return The XML content of this portlet
     */
    public String getXmlDocument( HttpServletRequest request )
    {
        return XmlUtil.getXmlHeader(  ) + getXml( request );
    }

    /**
     * Check if the whatsnew is dynamic
     * @return true if it is dynamic, false otherwise
     */
    public boolean getDynamic(  )
    {
        return _bIsDynamic;
    }

    /**
     * Set the attribute dynamic of the whatsnew
     * @param bIsDynamic true if it is dynamic, false otherwise
     */
    public void setDynamic( boolean bIsDynamic )
    {
        _bIsDynamic = bIsDynamic;
    }

    /**
     * Returns the XML content of the portlet without the XML header
     * @param request The HTTP Servlet request
     * @return The Xml content of this portlet
     */
    public String getXml( HttpServletRequest request )
    {
        StringBuffer strXml = new StringBuffer(  );
        XmlUtil.beginElement( strXml, WhatsNewConstants.TAG_WHATS_NEW_PORTLET );

        List<IWhatsNew> listElements = new ArrayList<IWhatsNew>(  );
        Locale locale = request.getLocale(  );
        Timestamp limitTimestamp = WhatsNewService.getInstance(  ).getTimestampFromPeriodAndCurrentDate( _nPeriod,
                locale );

        if ( _bShowPages )
        {
            Collection<IWhatsNew> listPages;

            if ( !_bIsDynamic )
            {
                listPages = WhatsNewService.getInstance(  ).getModeratedPages( getId(  ), locale );
            }
            else
            {
                listPages = WhatsNewService.getInstance(  ).getPagesByCriterias( limitTimestamp, locale );
            }

            listElements.addAll( listPages );
        }

        if ( _bShowPortlets )
        {
            Collection<IWhatsNew> listPortlets;

            if ( !_bIsDynamic )
            {
                listPortlets = WhatsNewService.getInstance(  ).getModeratedPortlets( getId(  ), locale );
            }
            else
            {
                listPortlets = WhatsNewService.getInstance(  ).getPortletsByCriterias( limitTimestamp, locale );
            }

            listElements.addAll( listPortlets );
        }

        if ( _bShowDocuments && WhatsNewService.getInstance(  ).isPluginDocumentActivated(  ) )
        {
            Collection<IWhatsNew> listDocuments;

            if ( !_bIsDynamic )
            {
                listDocuments = WhatsNewService.getInstance(  ).getModeratedDocuments( getId(  ), locale );
            }
            else
            {
                listDocuments = WhatsNewService.getInstance(  ).getDocumentsByCriterias( limitTimestamp, locale );
            }

            listElements.addAll( listDocuments );
        }

        Collections.sort( listElements, new WhatsNewComparator( _nElementsOrder, _bIsAscSort ) );

        // retrieve from request the current display id parameter : to paginate the results
        // the request parameter is postfixed by the portlet id to be able to handle more than
        // one portlet in a page
        String strMinDisplay = null;

        strMinDisplay = request.getParameter( WhatsNewConstants.PARAMETER_MIN_DISPLAY + WhatsNewConstants.UNDERSCORE +
                getId(  ) );

        if ( StringUtils.isNotBlank( strMinDisplay ) )
        {
            XmlUtil.addElement( strXml, WhatsNewConstants.TAG_WHATS_NEW_MIN_DISPLAY, strMinDisplay );
        }
        else
        {
            XmlUtil.addElement( strXml, WhatsNewConstants.TAG_WHATS_NEW_MIN_DISPLAY, 1 );
        }

        // retrieve the number of elements to display in a portlet
        // this is filtered in the xsl in order to allow easy pagination
        XmlUtil.addElement( strXml, WhatsNewConstants.TAG_WHATS_NEW_NUMBER_DISPLAY, _nNbElementsMax );

        // get the xml list of elements
        for ( IWhatsNew whatsnew : listElements )
        {
            strXml.append( whatsnew.getXml( request ) );
        }

        XmlUtil.endElement( strXml, WhatsNewConstants.TAG_WHATS_NEW_PORTLET );

        return addPortletTags( strXml );
    }

    /**
     * Updates the current instance of the form portlet object
     */
    public void update(  )
    {
        WhatsNewPortletService.getInstance(  ).update( this );
    }

    /**
     * Removes the current instance of the  the form portlet  object
     */
    public void remove(  )
    {
        WhatsNewPortletService.getInstance(  ).remove( this );
    }
}
