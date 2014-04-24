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
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;

import java.sql.Timestamp;

import java.util.Collection;
import java.util.List;
import java.util.Locale;


/**
 *
 * WhatsNewHome
 *
 */
public final class WhatsNewHome
{
    // Static variable pointed at the DAO instance
    private static IWhatsNewDAO _dao = (IWhatsNewDAO) SpringContextService.getPluginBean( "whatsnew",
            "whatsnew.whatsNewDAO" );

    /** Constructor */
    private WhatsNewHome(  )
    {
    }

    /**
     * Returns the list of the pages which correspond to the criteria given
     * @param dateLimit the timestamp giving the beginning of the period to watch
     * @param locale {@link Locale}
     * @return the list in form of a Collection object
     */
    public static Collection<IWhatsNew> selectPagesByCriterias( Timestamp dateLimit, Locale locale )
    {
        return _dao.selectPagesByCriterias( dateLimit, locale );
    }

    /**
     * Returns the list of the portlets which correspond to the criteria given
     * @param dateLimit the timestamp giving the beginning of the period to watch
     * @param locale {@link Locale}
     * @return the list in form of a Collection object
     */
    public static Collection<IWhatsNew> selectPortletsByCriterias( Timestamp dateLimit, Locale locale )
    {
        return _dao.selectPortletsByCriterias( dateLimit, locale );
    }

    /**
     * Returns the list of the articles which correspond to the criteria given
     * @param dateLimit the timestamp giving the beginning of the period to watch
     * @param locale {@link Locale}
     * @return the list in form of a Collection object
     */
    public static Collection<IWhatsNew> selectDocumentsByCriterias( Timestamp dateLimit, Locale locale )
    {
        Plugin pluginDocument = PluginService.getPlugin( WhatsNewConstants.DOCUMENT_PLUGIN_NAME );

        return _dao.selectDocumentsByCriterias( dateLimit, pluginDocument, locale );
    }

    /**
     * Returns the list of pages which correspond to the list of page IDs
     * @param listPageIds the list of page IDs
     * @param locale {@link Locale}
     * @return a collection of {@link IWhatsNew}
     */
    public static Collection<IWhatsNew> selectPages( List<Integer> listPageIds, Locale locale )
    {
        return _dao.selectPages( listPageIds, locale );
    }

    /**
     * Returns the list of portlets which correspond to the list of page IDs
     * @param listPortletIds the list of portlets IDs
     * @param locale {@link Locale}
     * @return a collection of {@link IWhatsNew}
     */
    public static Collection<IWhatsNew> selectPortlets( List<Integer> listPortletIds, Locale locale )
    {
        return _dao.selectPortlets( listPortletIds, locale );
    }

    /**
     * Returns the list of documents which correspond to the list of page IDs
     * @param listPortletDocumentLinks the list of page IDs
     * @param locale {@link Locale}
     * @return a collection of {@link IWhatsNew}
     */
    public static Collection<IWhatsNew> selectDocuments( List<PortletDocumentLink> listPortletDocumentLinks,
        Locale locale )
    {
        return _dao.selectDocuments( listPortletDocumentLinks, locale );
    }

    /**
     * Check if the given page is out of date
     * @param nPageId the page ID
     * @param dateLimit the date limit
     * @return true if it is out of date, false otherwise
     */
    public static boolean isPageOutOfDate( int nPageId, Timestamp dateLimit )
    {
        return _dao.isPageOutOfDate( nPageId, dateLimit );
    }

    /**
     * Check if the given portlet is out of date
     * @param nPortletId the portlet ID
     * @param dateLimit the date limit
     * @return true if it is out of date, false otherwise
     */
    public static boolean isPortletOutOfDate( int nPortletId, Timestamp dateLimit )
    {
        return _dao.isPortletOutOfDate( nPortletId, dateLimit );
    }

    /**
     * Check if the given document is out of date
     * @param pdLink {@link PortletDocumentLink}
     * @param dateLimit the date limit
     * @param plugin {@link Plugin}
     * @return true if it is out of date, false otherwise
     */
    public static boolean isDocumentOutOfDate( PortletDocumentLink pdLink, Timestamp dateLimit, Plugin plugin )
    {
        return _dao.isDocumentOutOfDate( pdLink, dateLimit, plugin );
    }
}
