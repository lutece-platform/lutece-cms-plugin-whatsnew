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

import fr.paris.lutece.plugins.whatsnew.business.PortletDocumentLink;
import fr.paris.lutece.portal.business.portlet.IPortletInterfaceDAO;
import fr.paris.lutece.portal.business.portlet.PortletHome;
import fr.paris.lutece.portal.business.portlet.PortletTypeHome;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.spring.SpringContextService;

import java.util.List;


/**
 *
 * WhatsNewPortletHome
 *
 */
public class WhatsNewPortletHome extends PortletHome
{
    // Static variable pointed at the DAO instance
    private static IWhatsNewPortletDAO _dao = (IWhatsNewPortletDAO) SpringContextService.getPluginBean( "whatsnew",
            "whatsnew.whatsNewPortletDAO" );

    /** This class implements the Singleton design pattern. */
    private static WhatsNewPortletHome _singleton;

    /**
     * Constructor
    */
    public WhatsNewPortletHome(  )
    {
    }

    /**
     * Returns the instance of the WhatsNewPortletHome singleton
     * @return the WhatsNewPortletHome instance
     */
    public static WhatsNewPortletHome getInstance(  )
    {
        if ( _singleton == null )
        {
            _singleton = new WhatsNewPortletHome(  );
        }

        return _singleton;
    }

    /**
     * Returns the type of the portlet
     * @return The type of the portlet
     */
    public String getPortletTypeId(  )
    {
        String strCurrentClassName = this.getClass(  ).getName(  );
        String strPortletTypeId = PortletTypeHome.getPortletTypeId( strCurrentClassName );

        return strPortletTypeId;
    }

    /**
     * Returns the instance of the WhatsNewPortletDAO singleton
     * @return the instance of the WhatsNewPortletDAO
     */
    public IPortletInterfaceDAO getDAO(  )
    {
        return _dao;
    }

    /**
     * Select all WhatsNewPortlet
     * @return a list of {@link WhatsNewPortlet}
     */
    public List<WhatsNewPortlet> selectAll(  )
    {
        return _dao.findAll(  );
    }

    /**
     * Load all page IDs associated to the given whatNewPortletId
     * @param nWhatsNewPortletId the ID of the portlet
     * @param plugin {@link Plugin}
     * @return a list of page IDs
     */
    public List<Integer> getPageIdsFromWhatsNewId( int nWhatsNewPortletId, Plugin plugin )
    {
        return _dao.loadPageIdsFromWhatsNewPortletId( nWhatsNewPortletId, plugin );
    }

    /**
     * Load all portlet IDs associated to the given whatsNewPortletId
     * @param nWhatsNewPortletId the ID of the portlet
     * @param plugin {@link Plugin}
     * @return a list of portlet IDs
     */
    public List<Integer> getPortletIdsFromWhatsNewId( int nWhatsNewPortletId, Plugin plugin )
    {
        return _dao.loadPortletIdsFromWhatsNewPortletId( nWhatsNewPortletId, plugin );
    }

    /**
     * Load all the documents associated to the given whatsnewPortletId
     * @param nWhatsNewPortletId the ID of the portlet
     * @param plugin {@link Plugin}
     * @return a list of {@link PortletDocumentLink}
     */
    public List<PortletDocumentLink> getDocumentsFromWhatsNewId( int nWhatsNewPortletId, Plugin plugin )
    {
        return _dao.loadDocumentFromWhatsNewPortletId( nWhatsNewPortletId, plugin );
    }

    /**
     * Insert a link between a whatsnew portlet and a page
     * @param nWhatsNewPortletId the ID of the portlet
     * @param nPageId the page ID
     * @param plugin {@link Plugin}
     */
    public void createModeratedPage( int nWhatsNewPortletId, int nPageId, Plugin plugin )
    {
        _dao.insertPageForWhatsNew( nWhatsNewPortletId, nPageId, plugin );
    }

    /**
     * Insert a link between a whatsnew portlet and a portlet
     * @param nWhatsNewPortletId the ID of the whatsnew portlet
     * @param nPortletId the ID of the portlet
     * @param plugin {@link Plugin}
     */
    public void createModeratedPortlet( int nWhatsNewPortletId, int nPortletId, Plugin plugin )
    {
        _dao.insertPortletForWhatsNew( nWhatsNewPortletId, nPortletId, plugin );
    }

    /**
     * Insert a link between a document and a whatsnew portlet
     * @param nWhatsNewPortletId the ID of the whatsnew portlet
     * @param pdLink {@link PortletDocumentLink}
     * @param plugin {@link Plugin}
     */
    public void createModeratedDocument( int nWhatsNewPortletId, PortletDocumentLink pdLink, Plugin plugin )
    {
        _dao.insertDocumentForWhatsNew( nWhatsNewPortletId, pdLink, plugin );
    }

    /**
     * Delete all links of a whatsnew portlet to the pages
     * @param nWhatsNewPortletId the ID of the portlet
     * @param plugin {@link Plugin}
     */
    public void removeModeratedPages( int nWhatsNewPortletId, Plugin plugin )
    {
        _dao.deletePagesFromWhatsNew( nWhatsNewPortletId, plugin );
    }

    /**
     * Delete all links of a whatsnew portlet to the portlets
     * @param nWhatsNewPortletId the ID of the whatsnew portlet
     * @param plugin {@link Plugin}
     */
    public void removeModeratedPortlets( int nWhatsNewPortletId, Plugin plugin )
    {
        _dao.deletePortletsFromWhatsNew( nWhatsNewPortletId, plugin );
    }

    /**
     * Delete all links of a whatsnew portlet to the documents
     * @param nWhatsNewPortletId the ID of the portlet
     * @param plugin {@link Plugin}
     */
    public void removeModeratedDocuments( int nWhatsNewPortletId, Plugin plugin )
    {
        _dao.deleteDocumentsFromWhatsNew( nWhatsNewPortletId, plugin );
    }

    /**
     * Delete a link between a whatsnew portlet and a page
     * @param nWhatsNewPortletId the ID of the portlet
     * @param nPageId the page ID
     * @param plugin {@link Plugin}
     */
    public void removeModeratedPage( int nWhatsNewPortletId, int nPageId, Plugin plugin )
    {
        _dao.deleteLinkWhatsNewPortletToPage( nWhatsNewPortletId, nPageId, plugin );
    }

    /**
     * Delete a link between a whatsnew portlet and a portlet
     * @param nWhatsNewPortletId the ID of the whatsnew portlet
     * @param nPortletId the ID of the portlet
     * @param plugin {@link Plugin}
     */
    public void removeModeratedPortlet( int nWhatsNewPortletId, int nPortletId, Plugin plugin )
    {
        _dao.deleteLinkWhatsNewPortletToPortlet( nWhatsNewPortletId, nPortletId, plugin );
    }

    /**
     * Delete a link between a whatsnew portlet and a document
     * @param nWhatsNewPortletId the ID of the portlet
     * @param pdLink {@link PortletDocumentLink}
     * @param plugin {@link Plugin}
     */
    public void removeModeratedDocument( int nWhatsNewPortletId, PortletDocumentLink pdLink, Plugin plugin )
    {
        _dao.deleteLinkWhatsNewPortletToDocument( nWhatsNewPortletId, pdLink, plugin );
    }
}
