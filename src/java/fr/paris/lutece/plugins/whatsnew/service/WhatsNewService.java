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
package fr.paris.lutece.plugins.whatsnew.service;

import fr.paris.lutece.plugins.whatsnew.business.IWhatsNew;
import fr.paris.lutece.plugins.whatsnew.business.WhatsNewHome;
import fr.paris.lutece.plugins.whatsnew.utils.constants.WhatsNewConstants;
import fr.paris.lutece.portal.service.database.AppConnectionService;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;

import java.sql.Timestamp;

import java.util.Collection;
import java.util.Locale;


/**
 *
 * WhatsNewService
 *
 */
public class WhatsNewService
{
    private static WhatsNewService _singleton;

    /**
     * Return the ThemeService singleton
     * @return the ThemeService singleton
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
     * Get the documents by criteria
     * @param limitTimestamp the limit time
     * @param locale {@link Locale}
     * @return a collection of {@link IWhatsNew}
     */
    public Collection<IWhatsNew> getDocumentsByCriterias( Timestamp limitTimestamp, Locale locale )
    {
        return WhatsNewHome.selectDocumentsByCriterias( limitTimestamp, locale );
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
     * Check if the plugin-document is activated and its pool is not null
     * @return true if the plugin-document is activated, false otherwise
     */
    public boolean isPluginDocumentActivated(  )
    {
    	boolean bPluginDocumentActivated = false;
    	Plugin pluginDocument = PluginService.getPlugin( WhatsNewConstants.DOCUMENT_PLUGIN_NAME );
    	if ( PluginService.isPluginEnable( WhatsNewConstants.DOCUMENT_PLUGIN_NAME ) && pluginDocument.getDbPoolName(  ) != null &&
                !AppConnectionService.NO_POOL_DEFINED.equals( pluginDocument.getDbPoolName(  ) ) )
        {
    		bPluginDocumentActivated = true;
        }
    	return bPluginDocumentActivated;
    }
}
