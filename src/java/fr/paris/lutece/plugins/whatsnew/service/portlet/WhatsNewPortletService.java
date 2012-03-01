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
package fr.paris.lutece.plugins.whatsnew.service.portlet;

import fr.paris.lutece.plugins.whatsnew.business.PortletDocumentLink;
import fr.paris.lutece.plugins.whatsnew.business.portlet.WhatsNewPortlet;
import fr.paris.lutece.plugins.whatsnew.business.portlet.WhatsNewPortletHome;
import fr.paris.lutece.plugins.whatsnew.service.WhatsNewPlugin;
import fr.paris.lutece.portal.business.portlet.PortletHome;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;

import java.util.List;


/**
 *
 * WhatsNewPortletService
 *
 */
public class WhatsNewPortletService
{
    private static WhatsNewPortletService _singleton;

    /**
     * Return the ThemeService singleton
     *
     * @return the ThemeService singleton
     */
    public static WhatsNewPortletService getInstance(  )
    {
        if ( _singleton == null )
        {
            _singleton = new WhatsNewPortletService(  );
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
     * Get the portlet with the given portlet id
     * @param nPortletId the portlet id
     * @return a {@link WhatsNewPortlet}
     */
    public WhatsNewPortlet getPortlet( int nPortletId )
    {
        return (WhatsNewPortlet) PortletHome.findByPrimaryKey( nPortletId );
    }

    /**
     * Get the portlet type id
     * @return the portlet type id
     */
    public String getPortletTypeId(  )
    {
        return WhatsNewPortletHome.getInstance(  ).getPortletTypeId(  );
    }

    /**
     * Create a new {@link WhatsNewPortlet}
     * @param portlet a {@link WhatsNewPortlet}
     */
    public void create( WhatsNewPortlet portlet )
    {
        WhatsNewPortletHome.getInstance(  ).create( portlet );
    }

    /**
     * Update a {@link WhatsNewPortlet}
     * @param portlet a {@link WhatsNewPortlet}
     */
    public void update( WhatsNewPortlet portlet )
    {
        WhatsNewPortletHome.getInstance(  ).update( portlet );
    }

    /**
     * Remove a {@link WhatsNewPortlet}
     * @param portlet a {@link WhatsNewPortlet}
     */
    public void remove( WhatsNewPortlet portlet )
    {
        Plugin plugin = PluginService.getPlugin( WhatsNewPlugin.PLUGIN_NAME );
        WhatsNewPortletHome.getInstance(  ).remove( portlet );
        removeModeratedElements( portlet, plugin );
    }

    /**
     * Select all WhatsNewPortlet
     * @return a list of {@link WhatsNewPortlet}
     */
    public List<WhatsNewPortlet> selectAll(  )
    {
        return WhatsNewPortletHome.getInstance(  ).selectAll(  );
    }

    /**
     * Load all page IDs associated to the given whatNewPortletId
     * @param nWhatsNewPortletId the ID of the portlet
     * @param plugin {@link Plugin}
     * @return a list of page IDs
     */
    public List<Integer> getPageIdsFromWhatsNewId( int nWhatsNewPortletId, Plugin plugin )
    {
        return WhatsNewPortletHome.getInstance(  ).getPageIdsFromWhatsNewId( nWhatsNewPortletId, plugin );
    }

    /**
     * Load all portlet IDs associated to the given whatsNewPortletId
     * @param nWhatsNewPortletId the ID of the portlet
     * @param plugin {@link Plugin}
     * @return a list of portlet IDs
     */
    public List<Integer> getPortletIdsFromWhatsNewId( int nWhatsNewPortletId, Plugin plugin )
    {
        return WhatsNewPortletHome.getInstance(  ).getPortletIdsFromWhatsNewId( nWhatsNewPortletId, plugin );
    }

    /**
     * Load all the documents associated to the given whatsnewPortletId
     * @param nWhatsNewPortletId the ID of the portlet
     * @param plugin {@link Plugin}
     * @return a list of {@link PortletDocumentLink}
     */
    public List<PortletDocumentLink> getDocumentsFromWhatsNewId( int nWhatsNewPortletId, Plugin plugin )
    {
        return WhatsNewPortletHome.getInstance(  ).getDocumentsFromWhatsNewId( nWhatsNewPortletId, plugin );
    }

    /**
     * Insert a link between a whatsnew portlet and a page
     * @param nWhatsNewPortletId the ID of the portlet
     * @param nPageId the page ID
     * @param plugin {@link Plugin}
     */
    public void createModeratedPage( int nWhatsNewPortletId, int nPageId, Plugin plugin )
    {
        WhatsNewPortletHome.getInstance(  ).createModeratedPage( nWhatsNewPortletId, nPageId, plugin );
    }

    /**
     * Insert a link between a whatsnew portlet and a portlet
     * @param nWhatsNewPortletId the ID of the whatsnew portlet
     * @param nPortletId the ID of the portlet
     * @param plugin {@link Plugin}
     */
    public void createModeratedPortlet( int nWhatsNewPortletId, int nPortletId, Plugin plugin )
    {
        WhatsNewPortletHome.getInstance(  ).createModeratedPortlet( nWhatsNewPortletId, nPortletId, plugin );
    }

    /**
     * Insert a link between a document and a whatsnew portlet
     * @param nWhatsNewPortletId the ID of the whatsnew portlet
     * @param pdLink {@link PortletDocumentLink}
     * @param plugin {@link Plugin}
     */
    public void createModeratedDocument( int nWhatsNewPortletId, PortletDocumentLink pdLink, Plugin plugin )
    {
        WhatsNewPortletHome.getInstance(  ).createModeratedDocument( nWhatsNewPortletId, pdLink, plugin );
    }

    /**
     * Remove all the moderated elements from the given portlet
     * @param portlet the WhatsNewPortlet
     * @param plugin {@link Plugin}
     */
    public void removeModeratedElements( WhatsNewPortlet portlet, Plugin plugin )
    {
        removeModeratedPortlets( portlet, plugin );
        removeModeratedPages( portlet, plugin );
        removeModeratedDocuments( portlet, plugin );
    }

    /**
     * Delete all links of a whatsnew portlet to the pages
     * @param whatsNewPortlet the portlet
     * @param plugin {@link Plugin}
     */
    public void removeModeratedPages( WhatsNewPortlet whatsNewPortlet, Plugin plugin )
    {
        WhatsNewPortletHome.getInstance(  ).removeModeratedPages( whatsNewPortlet.getId(  ), plugin );
    }

    /**
     * Delete all links of a whatsnew portlet to the portlets
     * @param whatsNewPortlet the portlet
     * @param plugin {@link Plugin}
     */
    public void removeModeratedPortlets( WhatsNewPortlet whatsNewPortlet, Plugin plugin )
    {
        WhatsNewPortletHome.getInstance(  ).removeModeratedPortlets( whatsNewPortlet.getId(  ), plugin );
    }

    /**
     * Delete all links of a whatsnew portlet to the documents
     * @param whatsNewPortlet the portlet
     * @param plugin {@link Plugin}
     */
    public void removeModeratedDocuments( WhatsNewPortlet whatsNewPortlet, Plugin plugin )
    {
        WhatsNewPortletHome.getInstance(  ).removeModeratedDocuments( whatsNewPortlet.getId(  ), plugin );
    }

    /**
     * Delete a link between a whatsnew portlet and a page
     * @param nWhatsNewPortletId the ID of the portlet
     * @param nPageId the page ID
     * @param plugin {@link Plugin}
     */
    public void removeModeratedPage( int nWhatsNewPortletId, int nPageId, Plugin plugin )
    {
        WhatsNewPortletHome.getInstance(  ).removeModeratedPage( nWhatsNewPortletId, nPageId, plugin );
    }

    /**
     * Delete a link between a whatsnew portlet and a portlet
     * @param nWhatsNewPortletId the ID of the whatsnew portlet
     * @param nPortletId the ID of the portlet
     * @param plugin {@link Plugin}
     */
    public void removeModeratedPortlet( int nWhatsNewPortletId, int nPortletId, Plugin plugin )
    {
        WhatsNewPortletHome.getInstance(  ).removeModeratedPortlet( nWhatsNewPortletId, nPortletId, plugin );
    }

    /**
     * Delete a link between a whatsnew portlet and a document
     * @param nWhatsNewPortletId the ID of the portlet
     * @param pdLink {@link PortletDocumentLink}
     * @param plugin {@link Plugin}
     */
    public void removeModeratedDocument( int nWhatsNewPortletId, PortletDocumentLink pdLink, Plugin plugin )
    {
        WhatsNewPortletHome.getInstance(  ).removeModeratedDocument( nWhatsNewPortletId, pdLink, plugin );
    }
}
