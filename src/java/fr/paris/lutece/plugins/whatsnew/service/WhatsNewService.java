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
package fr.paris.lutece.plugins.whatsnew.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

import fr.paris.lutece.plugins.whatsnew.business.IWhatsNew;
import fr.paris.lutece.plugins.whatsnew.business.PortletDocumentLink;
import fr.paris.lutece.plugins.whatsnew.business.WhatsNewHome;
import fr.paris.lutece.plugins.whatsnew.business.WhatsNewType;
import fr.paris.lutece.plugins.whatsnew.business.WhatsNewTypeDocument;
import fr.paris.lutece.plugins.whatsnew.business.portlet.WhatsNewPortlet;
import fr.paris.lutece.plugins.whatsnew.service.portlet.WhatsNewPortletService;
import fr.paris.lutece.plugins.whatsnew.utils.constants.WhatsNewConstants;
import fr.paris.lutece.portal.business.portlet.Portlet;
import fr.paris.lutece.portal.business.portlet.PortletHome;
import fr.paris.lutece.portal.service.database.AppConnectionService;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.html.HtmlTemplate;


/**
 *
 * WhatsNewService
 *
 */
public class WhatsNewService
{
    private static WhatsNewService _singleton;

    /**
     * Return the WhatsNewService singleton
     * @return the WhatsNewService singleton
     */
    public static WhatsNewService getInstance(  )
    {
        if ( _singleton == null )
        {
            _singleton = new WhatsNewService(  );
        }

        return _singleton;
    }

    /**
     * Init
     */
    public void init(  )
    {
    }

    /**
     * Check if the plugin-document is activated and its pool is not null
     * @return true if the plugin-document is activated, false otherwise
     */
    public boolean isPluginDocumentActivated(  )
    {
        boolean bPluginDocumentActivated = false;
        Plugin pluginDocument = PluginService.getPlugin( WhatsNewConstants.DOCUMENT_PLUGIN_NAME );

        if ( PluginService.isPluginEnable( WhatsNewConstants.DOCUMENT_PLUGIN_NAME ) &&
                ( pluginDocument.getDbPoolName(  ) != null ) &&
                !AppConnectionService.NO_POOL_DEFINED.equals( pluginDocument.getDbPoolName(  ) ) )
        {
            bPluginDocumentActivated = true;
        }

        return bPluginDocumentActivated;
    }

    /**
     * Check if the given page is out of date
     * @param nPageId the page ID
     * @param limitTimestamp the date limit
     * @return true if it is out of date, false otherwise
     */
    public boolean isPageOutOfDate( int nPageId, Timestamp limitTimestamp )
    {
        return WhatsNewHome.isPageOutOfDate( nPageId, limitTimestamp );
    }

    /**
     * Check if the given portlet is out of date
     * @param nPortletId the portlet ID
     * @param limitTimestamp the date limit
     * @return true if it is out of date, false otherwise
     */
    public boolean isPortletOutOfDate( int nPortletId, Timestamp limitTimestamp )
    {
        return WhatsNewHome.isPortletOutOfDate( nPortletId, limitTimestamp );
    }

    /**
     * Check if the given document is out of date
     * @param pdLink {@link PortletDocumentLink}
     * @param limitTimestamp the date limit
     * @param plugin {@link Plugin}
     * @return true if it is out of date, false otherwise
     */
    public boolean isDocumentOutOfDate( PortletDocumentLink pdLink, Timestamp limitTimestamp, Plugin plugin )
    {
        return WhatsNewHome.isDocumentOutOfDate( pdLink, limitTimestamp, plugin );
    }

    /**
     * Initialize the number of days' combo
     * @return the html code of the combo
     */
    public ReferenceList getComboDays(  )
    {
        // Returns the list stored in the property file and the default value
        String strListe = AppPropertiesService.getProperty( WhatsNewConstants.PROPERTY_FRAGMENT_DAYS_COMBO_LIST );

        ReferenceList comboDaysList = new ReferenceList(  );

        StringTokenizer strTokSemiColon = new StringTokenizer( strListe, WhatsNewConstants.DELIMITER_SEMI_COLON );

        while ( strTokSemiColon.hasMoreTokens(  ) )
        {
            StringTokenizer strTokComa = new StringTokenizer( strTokSemiColon.nextToken(  ),
                    WhatsNewConstants.DELIMITER_COMA );

            while ( strTokComa.hasMoreTokens(  ) )
            {
                comboDaysList.addItem( Integer.parseInt( strTokComa.nextToken(  ) ), strTokComa.nextToken(  ) );
            }
        }

        return comboDaysList;
    }

    /**
     * Get the list of WhatsNewType
     * @param locale {@link Locale}
     * @return a list of {@link WhatsNewType}
     */
    public List<WhatsNewType> getWhatsNewType( Locale locale )
    {
        List<WhatsNewType> listWhatsNewTypes = new ArrayList<WhatsNewType>(  );

        for ( IWhatsNew whatsNew : SpringContextService.getBeansOfType( IWhatsNew.class ) )
        {
            whatsNew.setWhatsNewType( locale );
            listWhatsNewTypes.add( whatsNew.getWhatsNewType(  ) );
        }

        return listWhatsNewTypes;
    }

    /**
     * Get the timestamp correspnding to a given day in the past.
     * It is calculated from a period to remove from the current date.
     * @param nDays the number of days between the current day and the timestamp to calculate.
     * @param locale {@link Locale}
     * @return the timestamp corresponding to the limit in the past for the period given in days, with hours, minutesn sec. and millisec. set to 0.
     */
    public Timestamp getTimestampFromPeriodAndCurrentDate( int nDays, Locale locale )
    {
        Calendar currentCalendar = new GregorianCalendar( locale );
        currentCalendar.set( Calendar.HOUR_OF_DAY, 0 );
        currentCalendar.set( Calendar.MINUTE, 0 );
        currentCalendar.set( Calendar.SECOND, 0 );
        currentCalendar.set( Calendar.MILLISECOND, 0 );

        currentCalendar.add( Calendar.DATE, -nDays );

        Timestamp timestampCurrent = new Timestamp( currentCalendar.getTimeInMillis(  ) );

        return timestampCurrent;
    }

    /**
     * Get the pages by criteria
     * @param limitTimestamp the limit time
     * @param locale {@link Locale}
     * @return a collection of {@link IWhatsNew}
     */
    public Collection<IWhatsNew> getPagesByCriterias( Timestamp limitTimestamp, Locale locale )
    {
        return WhatsNewHome.selectPagesByCriterias( limitTimestamp, locale );
    }

    /**
     * Get the portlets by criteria
     * @param limitTimestamp the limit time
     * @param locale {@link Locale}
     * @return a collection of {@link IWhatsNew}
     */
    public Collection<IWhatsNew> getPortletsByCriterias( Timestamp limitTimestamp, Locale locale )
    {
        return WhatsNewHome.selectPortletsByCriterias( limitTimestamp, locale );
    }

    /**
     * Get the documents by criteria
     * @param limitTimestamp the limit time
     * @param locale {@link Locale}
     * @return a collection of {@link IWhatsNew}
     */
    public Collection<IWhatsNew> getDocumentsByCriterias( Timestamp limitTimestamp, Locale locale )
    {
    	Collection<IWhatsNew> listWhatsNews = WhatsNewHome.selectDocumentsByCriterias( limitTimestamp, locale );
    	for ( IWhatsNew whatsNew : listWhatsNews )
    	{
    		Portlet portlet = PortletHome.findByPrimaryKey( whatsNew.getPortletId(  ) );
    		if ( portlet != null )
    		{
    			WhatsNewTypeDocument document = (WhatsNewTypeDocument) whatsNew;
    			document.setAssociatedPortletName( portlet.getName(  ) );
    		}
    	}
        return listWhatsNews;
    }

    /**
     * Get the moderated portlets from the given whatsNewPortletId
     * @param nWhatsNewPortletId the ID of the portlet
     * @param locale {@link Locale}
     * @return a collection of {@link IWhatsNew}
     */
    public Collection<IWhatsNew> getModeratedPortlets( int nWhatsNewPortletId, Locale locale )
    {
        Plugin plugin = PluginService.getPlugin( WhatsNewPlugin.PLUGIN_NAME );
        List<Integer> listPortletIds = WhatsNewPortletService.getInstance(  )
                                                             .getPortletIdsFromWhatsNewId( nWhatsNewPortletId, plugin );

        return WhatsNewHome.selectPortlets( listPortletIds, locale );
    }

    /**
     * Get the moderated pages from the given whatsNewPortletId
     * @param nWhatsNewPortletId the ID of the page
     * @param locale {@link Locale}
     * @return a collection of {@link IWhatsNew}
     */
    public Collection<IWhatsNew> getModeratedPages( int nWhatsNewPortletId, Locale locale )
    {
        Plugin plugin = PluginService.getPlugin( WhatsNewPlugin.PLUGIN_NAME );
        List<Integer> listPagesIds = WhatsNewPortletService.getInstance(  )
                                                           .getPageIdsFromWhatsNewId( nWhatsNewPortletId, plugin );

        return WhatsNewHome.selectPages( listPagesIds, locale );
    }

    /**
     * Get the moderated document from the given whatsNewPortletId
     * @param nWhatsNewPortletId the ID of the portlet
     * @param locale {@link Locale}
     * @return a collection of {@link IWhatsNew}
     */
    public Collection<IWhatsNew> getModeratedDocuments( int nWhatsNewPortletId, Locale locale )
    {
        Plugin plugin = PluginService.getPlugin( WhatsNewPlugin.PLUGIN_NAME );
        List<PortletDocumentLink> listPortletDocumentLinks = WhatsNewPortletService.getInstance(  )
                                                                                   .getDocumentsFromWhatsNewId( nWhatsNewPortletId,
                plugin );

        return WhatsNewHome.selectDocuments( listPortletDocumentLinks, locale );
    }

    /**
     * Get the HTML code of the moderated elements list
     * @param whatsNewPortlet the portlet
     * @param locale {@link Locale}
     * @return a HTML code
     */
    public String getModeratedElementsListHtml( WhatsNewPortlet whatsNewPortlet, Locale locale )
    {
        StringBuilder sbHtml = new StringBuilder(  );

        if ( !whatsNewPortlet.getDynamic(  ) )
        {
            Timestamp limitTimestamp = getTimestampFromPeriodAndCurrentDate( whatsNewPortlet.getPeriod(  ), locale );

            // PAGES
            if ( whatsNewPortlet.getShowPages(  ) )
            {
            	IWhatsNew whatsNew = (IWhatsNew) SpringContextService.getBean( WhatsNewConstants.BEAN_WHATSNEW_TYPE_PAGE );
                whatsNew.setWhatsNewType( locale );
                List<IWhatsNew> listElements = (List<IWhatsNew>) getPagesByCriterias( limitTimestamp, locale );
                List<IWhatsNew> listModeratedElements = (List<IWhatsNew>) getModeratedPages( whatsNewPortlet.getId(  ), locale );
                sbHtml.append( getHtmlModeratedElements( whatsNew, listElements, listModeratedElements, locale ) );
            }
            
            // PORTLETS
            if ( whatsNewPortlet.getShowPortlets(  ) )
            {
            	IWhatsNew whatsNew = (IWhatsNew) SpringContextService.getBean( WhatsNewConstants.BEAN_WHATSNEW_TYPE_PORTLET );
                whatsNew.setWhatsNewType( locale );
                List<IWhatsNew> listElements = (List<IWhatsNew>) getPortletsByCriterias( limitTimestamp, locale );
                List<IWhatsNew> listModeratedElements = (List<IWhatsNew>) getModeratedPortlets( whatsNewPortlet.getId(  ), locale );
                sbHtml.append( getHtmlModeratedElements( whatsNew, listElements, listModeratedElements, locale ) );
            }
            
            // DOCUMENTS
            if ( whatsNewPortlet.getShowDocuments(  ) )
            {
            	IWhatsNew whatsNew = (IWhatsNew) SpringContextService.getBean( WhatsNewConstants.BEAN_WHATSNEW_TYPE_DOCUMENT );
                whatsNew.setWhatsNewType( locale );
                List<IWhatsNew> listElements = (List<IWhatsNew>) getDocumentsByCriterias( limitTimestamp, locale );
                List<IWhatsNew> listModeratedElements = (List<IWhatsNew>) getModeratedDocuments( whatsNewPortlet.getId(  ), locale );
                sbHtml.append( getHtmlModeratedElements( whatsNew, listElements, listModeratedElements, locale ) );
            }
        }

        return sbHtml.toString(  );
    }

    /**
     * Get the html code form the moderated element
     * @param whatsNew {@link IWhatsNew}
     * @param listElements the list of elements
     * @param listModeratedElements the list of moderated elements
     * @param locale {@link Locale}
     * @return a HTML code
     */
    private String getHtmlModeratedElements( IWhatsNew whatsNew, List<IWhatsNew> listElements,
        List<IWhatsNew> listModeratedElements, Locale locale )
    {
        String strHtml = StringUtils.EMPTY;

        if ( ( listElements != null ) && ( whatsNew != null ) )
        {
            Map<String, Object> model = new HashMap<String, Object>(  );
            model.put( WhatsNewConstants.MARK_ELEMENTS_LIST, listElements );
            model.put( WhatsNewConstants.MARK_MODERATED_ELEMENTS_LIST, listModeratedElements );

            HtmlTemplate template = AppTemplateService.getTemplate( whatsNew.getTemplateModeratedElement(  ), locale,
                    model );
            strHtml = template.getHtml(  );
        }

        return strHtml;
    }
}
