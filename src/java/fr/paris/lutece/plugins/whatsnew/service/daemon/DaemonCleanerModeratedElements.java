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
package fr.paris.lutece.plugins.whatsnew.service.daemon;

import fr.paris.lutece.plugins.whatsnew.business.PortletDocumentLink;
import fr.paris.lutece.plugins.whatsnew.business.portlet.WhatsNewPortlet;
import fr.paris.lutece.plugins.whatsnew.service.WhatsNewPlugin;
import fr.paris.lutece.plugins.whatsnew.service.WhatsNewService;
import fr.paris.lutece.plugins.whatsnew.service.portlet.WhatsNewPortletService;
import fr.paris.lutece.plugins.whatsnew.utils.constants.WhatsNewConstants;
import fr.paris.lutece.portal.service.daemon.Daemon;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;

import java.sql.Timestamp;

import java.util.List;
import java.util.Locale;


/**
 *
 * Daemon DaemonCleanerModeratedElements
 *
 */
public class DaemonCleanerModeratedElements extends Daemon
{
    /**
     * Daemon's treatment method
     */
    public void run(  )
    {
        Plugin plugin = PluginService.getPlugin( WhatsNewPlugin.PLUGIN_NAME );

        for ( WhatsNewPortlet portlet : WhatsNewPortletService.getInstance(  ).selectAll(  ) )
        {
            String strLanguage = AppPropertiesService.getProperty( WhatsNewConstants.PROPERTY_DAEMON_MODERATED_ELEMENTS_CLEANER_LANGUAGE,
                    "fr" );
            Locale locale = new Locale( strLanguage );
            Timestamp limitTimestamp = WhatsNewService.getInstance(  )
                                                      .getTimestampFromPeriodAndCurrentDate( portlet.getPeriod(  ),
                    locale );

            // PAGES
            List<Integer> listPageIds = WhatsNewPortletService.getInstance(  )
                                                              .getPageIdsFromWhatsNewId( portlet.getId(  ), plugin );

            for ( int nPageId : listPageIds )
            {
                if ( WhatsNewService.getInstance(  ).isPageOutOfDate( nPageId, limitTimestamp ) )
                {
                    WhatsNewPortletService.getInstance(  ).removeModeratedPage( portlet.getId(  ), nPageId, plugin );
                }
            }

            // PORTLETS
            List<Integer> listPortletIds = WhatsNewPortletService.getInstance(  )
                                                                 .getPortletIdsFromWhatsNewId( portlet.getId(  ), plugin );

            for ( int nPortletId : listPortletIds )
            {
                if ( WhatsNewService.getInstance(  ).isPortletOutOfDate( nPortletId, limitTimestamp ) )
                {
                    WhatsNewPortletService.getInstance(  ).removeModeratedPortlet( portlet.getId(  ), nPortletId, plugin );
                }
            }

            // DOCUMENT
            List<PortletDocumentLink> listLinks = WhatsNewPortletService.getInstance(  )
                                                                        .getDocumentsFromWhatsNewId( portlet.getId(  ),
                    plugin );
            Plugin pluginDocument = PluginService.getPlugin( WhatsNewConstants.DOCUMENT_PLUGIN_NAME );

            for ( PortletDocumentLink pdLink : listLinks )
            {
                if ( WhatsNewService.getInstance(  ).isDocumentOutOfDate( pdLink, limitTimestamp, pluginDocument ) )
                {
                    WhatsNewPortletService.getInstance(  ).removeModeratedDocument( portlet.getId(  ), pdLink, plugin );
                }
            }
        }
    }
}
