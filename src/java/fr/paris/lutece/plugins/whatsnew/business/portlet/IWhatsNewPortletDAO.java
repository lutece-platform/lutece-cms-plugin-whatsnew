/*
 * Copyright (c) 2002-2013, Mairie de Paris
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
import fr.paris.lutece.portal.business.portlet.Portlet;
import fr.paris.lutece.portal.service.plugin.Plugin;

import java.util.List;


/**
 *
 * IWhatsNewPortletDAO
 *
 */
public interface IWhatsNewPortletDAO extends IPortletInterfaceDAO
{
    /**
     * Delete a record from the table
     * @param nPortletId The identifier of the portlet
     */
    void delete( int nPortletId );

    /**
     * Insert a new record in the table whatsnew_portlet
     * @param portlet the instance of the Portlet object to insert
     */
    void insert( Portlet portlet );

    /**
     * Loads the data of What's new Portlet whose identifier is specified in parameter
     * @param nPortletId The Portlet identifier
     * @return the whatsNew object
     */
    Portlet load( int nPortletId );

    /**
     * Update the record in the table
     * @param portlet The instance of the object portlet
     */
    void store( Portlet portlet );

    /**
     * Find all WhatsNew portlets
     * @return a list of {@link WhatsNewPortlet}
     */
    List<WhatsNewPortlet> findAll(  );

    /**
     * Load all page IDs associated to the given whatNewPortletId
     * @param nWhatsNewPortletId the ID of the portlet
     * @param plugin {@link Plugin}
     * @return a list of page IDs
     */
    List<Integer> loadPageIdsFromWhatsNewPortletId( int nWhatsNewPortletId, Plugin plugin );

    /**
     * Insert a link between a whatsnew portlet and a page
     * @param nWhatsNewPortletId the ID of the portlet
     * @param nPageId the page ID
     * @param plugin {@link Plugin}
     */
    void insertPageForWhatsNew( int nWhatsNewPortletId, int nPageId, Plugin plugin );

    /**
     * Delete all links of a whatsnew portlet to the pages
     * @param nWhatsNewPortletId the ID of the portlet
     * @param plugin {@link Plugin}
     */
    void deletePagesFromWhatsNew( int nWhatsNewPortletId, Plugin plugin );

    /**
     * Delete a link between a whatsnew portlet and a page
     * @param nWhatsNewPortletId the ID of the portlet
     * @param nPageId the page ID
     * @param plugin {@link Plugin}
     */
    void deleteLinkWhatsNewPortletToPage( int nWhatsNewPortletId, int nPageId, Plugin plugin );

    /**
     * Load all portlet IDs associated to the given whatsNewPortletId
     * @param nWhatsNewPortletId the ID of the portlet
     * @param plugin {@link Plugin}
     * @return a list of portlet IDs
     */
    List<Integer> loadPortletIdsFromWhatsNewPortletId( int nWhatsNewPortletId, Plugin plugin );

    /**
     * Insert a link between a whatsnew portlet and a portlet
     * @param nWhatsNewPortletId the ID of the whatsnew portlet
     * @param nPortletId the ID of the portlet
     * @param plugin {@link Plugin}
     */
    void insertPortletForWhatsNew( int nWhatsNewPortletId, int nPortletId, Plugin plugin );

    /**
     * Delete all links of a whatsnew portlet to the portlets
     * @param nWhatsNewPortletId the ID of the whatsnew portlet
     * @param plugin {@link Plugin}
     */
    void deletePortletsFromWhatsNew( int nWhatsNewPortletId, Plugin plugin );

    /**
     * Delete a link between a whatsnew portlet and a portlet
     * @param nWhatsNewPortletId the ID of the whatsnew portlet
     * @param nPortletId the ID of the portlet
     * @param plugin {@link Plugin}
     */
    void deleteLinkWhatsNewPortletToPortlet( int nWhatsNewPortletId, int nPortletId, Plugin plugin );

    /**
     * Load all the documents associated to the given whatsnewPortletId
     * @param nWhatsNewPortletId the ID of the portlet
     * @param plugin {@link Plugin}
     * @return a list of {@link PortletDocumentLink}
     */
    List<PortletDocumentLink> loadDocumentFromWhatsNewPortletId( int nWhatsNewPortletId, Plugin plugin );

    /**
     * Insert a link between a document and a whatsnew portlet
     * @param nWhatsNewId the ID of the whatsnew portlet
     * @param pdLink {@link PortletDocumentLink}
     * @param plugin {@link Plugin}
     */
    void insertDocumentForWhatsNew( int nWhatsNewId, PortletDocumentLink pdLink, Plugin plugin );

    /**
     * Delete all links of a whatsnew portlet to the documents
     * @param nWhatsNewPortletId the ID of the portlet
     * @param plugin {@link Plugin}
     */
    void deleteDocumentsFromWhatsNew( int nWhatsNewPortletId, Plugin plugin );

    /**
     * Delete a link between a whatsnew portlet and a document
     * @param nWhatsNewPortletId the ID of the portlet
     * @param pdLink {@link PortletDocumentLink}
     * @param plugin {@link Plugin}
     */
    void deleteLinkWhatsNewPortletToDocument( int nWhatsNewPortletId, PortletDocumentLink pdLink, Plugin plugin );
}
