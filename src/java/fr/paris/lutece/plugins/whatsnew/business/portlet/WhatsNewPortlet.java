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
package fr.paris.lutece.plugins.whatsnew.business.portlet;

import fr.paris.lutece.plugins.whatsnew.business.IWhatsNew;
import fr.paris.lutece.plugins.whatsnew.service.WhatsNewService;
import fr.paris.lutece.plugins.whatsnew.service.portlet.WhatsNewPortletService;
import fr.paris.lutece.plugins.whatsnew.utils.constants.WhatsNewConstants;
import fr.paris.lutece.plugins.whatsnew.utils.sort.WhatsNewComparator;
import fr.paris.lutece.portal.business.portlet.PortletHtmlContent;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.util.date.DateUtil;
import jakarta.enterprise.inject.spi.CDI;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;


/**
 *
 * WhatsNewPortlet
 *
 */
public class WhatsNewPortlet extends PortletHtmlContent
{
    private static final String TEMPLATE_PORTLET = "skin/plugins/whatsnew/portlet/whatsnew_portlet.html";
    private static final String MARK_PORTLET = "portlet";
    private static final String MARK_SITE_PATH = "site_path";
    private static final String MARK_ELEMENTS = "elements";
    private static final String MARK_TOTAL = "total";
    private static final String MARK_MIN_DISPLAY = "min_display";
    private static final String MARK_MAX_DISPLAY = "max_display";
    private static final String MARK_PREVIOUS_MIN = "previous_min";
    private static final String MARK_NEXT_MIN = "next_min";
    private static final String MARK_MIN_DISPLAY_PARAMETER = "min_display_parameter";
    private static final String MARK_TITLE = "title";
    private static final String MARK_TYPE = "type";
    private static final String MARK_DESCRIPTION = "description";
    private static final String MARK_DATE_UPDATE = "date_update";
    private static final String MARK_URL = "url";

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
        setPortletTypeId( CDI.current( ).select( WhatsNewPortletService.class ).get( ).getPortletTypeId(  ) );
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
     * Returns the HTML content of the portlet: the elements of the period, sorted and paginated
     * @param request The HTTP Servlet request
     * @return The HTML content of this portlet
     */
    @Override
    public String getHtmlContent( HttpServletRequest request )
    {
        Locale locale = ( request != null ) ? request.getLocale( ) : Locale.getDefault( );
        List<IWhatsNew> listElements = getElements( locale );
        int nTotal = listElements.size( );
        String strMinDisplayParameter = WhatsNewConstants.PARAMETER_MIN_DISPLAY + WhatsNewConstants.UNDERSCORE + getId( );
        int nMinDisplay = getMinDisplay( request, strMinDisplayParameter );
        int nMaxDisplay = Math.min( ( nMinDisplay + _nNbElementsMax ) - 1, nTotal );

        List<Map<String, Object>> listDisplayed = new ArrayList<>( );

        for ( IWhatsNew whatsNew : listElements.subList( Math.min( nMinDisplay - 1, nTotal ), nMaxDisplay ) )
        {
            Map<String, Object> element = new HashMap<>( );
            element.put( MARK_TITLE, whatsNew.getTitle( ) );
            element.put( MARK_TYPE, whatsNew.getWhatsNewType( ).getName( ) );
            element.put( MARK_DESCRIPTION, whatsNew.getDescription( ) );
            element.put( MARK_DATE_UPDATE, DateUtil.getDateString( whatsNew.getDateUpdate( ), locale ) );
            element.put( MARK_URL, whatsNew.buildUrl( ) );
            listDisplayed.add( element );
        }

        Map<String, Object> model = new HashMap<>( );
        model.put( MARK_PORTLET, this );
        model.put( MARK_SITE_PATH, AppPathService.getPortalUrl( ) );
        model.put( MARK_ELEMENTS, listDisplayed );
        model.put( MARK_TOTAL, nTotal );
        model.put( MARK_MIN_DISPLAY, nMinDisplay );
        model.put( MARK_MAX_DISPLAY, nMaxDisplay );
        model.put( MARK_MIN_DISPLAY_PARAMETER, strMinDisplayParameter );
        model.put( MARK_PREVIOUS_MIN, Math.max( nMinDisplay - _nNbElementsMax, 1 ) );
        model.put( MARK_NEXT_MIN, nMaxDisplay + 1 );

        return AppTemplateService.getTemplate( TEMPLATE_PORTLET, locale, model ).getHtml( );
    }

    /**
     * Returns the elements the portlet shows, sorted as configured
     * @param locale The locale
     * @return The sorted elements
     */
    private List<IWhatsNew> getElements( Locale locale )
    {
        WhatsNewService whatsNewService = CDI.current( ).select( WhatsNewService.class ).get( );
        Timestamp limitTimestamp = whatsNewService.getTimestampFromPeriodAndCurrentDate( _nPeriod, locale );
        List<IWhatsNew> listElements = new ArrayList<>( );

        if ( _bShowPages )
        {
            Collection<IWhatsNew> listPages = _bIsDynamic ? whatsNewService.getPagesByCriterias( limitTimestamp, locale )
                    : whatsNewService.getModeratedPages( getId( ), locale );
            listElements.addAll( listPages );
        }

        if ( _bShowPortlets )
        {
            Collection<IWhatsNew> listPortlets = _bIsDynamic ? whatsNewService.getPortletsByCriterias( limitTimestamp, locale )
                    : whatsNewService.getModeratedPortlets( getId( ), locale );
            listElements.addAll( listPortlets );
        }

        if ( _bShowDocuments && whatsNewService.isPluginDocumentActivated( ) )
        {
            Collection<IWhatsNew> listDocuments = _bIsDynamic ? whatsNewService.getDocumentsByCriterias( limitTimestamp, locale )
                    : whatsNewService.getModeratedDocuments( getId( ), locale );
            listElements.addAll( listDocuments );
        }

        Collections.sort( listElements, new WhatsNewComparator( _nElementsOrder, _bIsAscSort ) );

        return listElements;
    }

    /**
     * Returns the index of the first element to display, read from the request parameter of this portlet
     * @param request The HTTP Servlet request
     * @param strMinDisplayParameter The name of the request parameter holding that index
     * @return The index of the first element, 1 when the parameter is absent or not a number
     */
    private int getMinDisplay( HttpServletRequest request, String strMinDisplayParameter )
    {
        String strMinDisplay = ( request != null ) ? request.getParameter( strMinDisplayParameter ) : null;

        if ( StringUtils.isNumeric( strMinDisplay ) )
        {
            return Math.max( Integer.parseInt( strMinDisplay ), 1 );
        }

        return 1;
    }

    /**
     * Updates the current instance of the form portlet object
     */
    public void update(  )
    {
        CDI.current( ).select( WhatsNewPortletService.class ).get( ).update( this );
    }

    /**
     * Removes the current instance of the  the form portlet  object
     */
    public void remove(  )
    {
        CDI.current( ).select( WhatsNewPortletService.class ).get( ).remove( this );
    }
}
