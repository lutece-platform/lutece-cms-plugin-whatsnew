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
import fr.paris.lutece.portal.business.portlet.Portlet;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * WhatsNewPortletDAO
 *
 */
public final class WhatsNewPortletDAO implements IWhatsNewPortletDAO
{
    // Whatsnew queries
    private static final String SQL_QUERY_SELECT_ALL = " SELECT id_portlet, show_documents, show_portlets, show_pages, period, nb_elements_max, elements_order, is_asc_sort, is_dynamic FROM whatsnew_portlet ";
    private static final String SQL_QUERY_INSERT = " INSERT INTO whatsnew_portlet (id_portlet, show_documents, show_portlets, show_pages, period, nb_elements_max, elements_order, is_asc_sort, is_dynamic) VALUES ( ?,?,?,?,?,?,?,?,? ) ";
    private static final String SQL_QUERY_DELETE = " DELETE FROM whatsnew_portlet WHERE id_portlet = ? ";
    private static final String SQL_QUERY_SELECT = " SELECT id_portlet, show_documents, show_portlets, show_pages, period, nb_elements_max, elements_order, is_asc_sort, is_dynamic FROM whatsnew_portlet WHERE id_portlet = ? ";
    private static final String SQL_QUERY_UPDATE = " UPDATE whatsnew_portlet SET show_documents = ?, show_portlets = ?, show_pages = ?, period = ?, nb_elements_max = ?, elements_order = ?, is_asc_sort = ?, is_dynamic = ? WHERE id_portlet = ? ";

    // SQL for table whatsnew_page_whatsnew
    private static final String SQL_QUERY_INSERT_PAGE_WHATSNEW = " INSERT INTO whatsnew_page_whatsnew (id_whatsnew_portlet, id_page) VALUES ( ?,? ) ";
    private static final String SQL_QUERY_SELECT_PAGE_WHATSNEW = " SELECT id_page FROM whatsnew_page_whatsnew WHERE id_whatsnew_portlet = ? ";
    private static final String SQL_QUERY_DELETE_PAGE_WHATSNEW_FROM_ID_WHATSNEW_PORTLET = " DELETE FROM whatsnew_page_whatsnew WHERE id_whatsnew_portlet = ? ";
    private static final String SQL_QUERY_DELETE_PAGE_WHATSNEW_FROM_ID_PAGE = " DELETE FROM whatsnew_page_whatsnew WHERE id_page = ? ";
    private static final String SQL_QUERY_DELETE_LINK_WHATSNEW_PORTLET_TO_PAGE = " DELETE FROM whatsnew_page_whatsnew WHERE id_whatsnew_portlet = ? AND id_page = ? ";

    // SQL for table whatsnew_portlet_whatsnew
    private static final String SQL_QUERY_INSERT_PORTLET_WHATSNEW = " INSERT INTO whatsnew_portlet_whatsnew (id_whatsnew_portlet, id_portlet) VALUES ( ?,? ) ";
    private static final String SQL_QUERY_SELECT_PORTLET_WHATSNEW = " SELECT id_portlet FROM whatsnew_portlet_whatsnew WHERE id_whatsnew_portlet = ? ";
    private static final String SQL_QUERY_DELETE_PORTLET_WHATSNEW_FROM_ID_WHATSNEW_PORTLET = " DELETE FROM whatsnew_portlet_whatsnew WHERE id_whatsnew_portlet = ? ";
    private static final String SQL_QUERY_DELETE_PORTLET_WHATSNEW_FROM_ID_PORTLET = " DELETE FROM whatsnew_portlet_whatsnew WHERE id_portlet = ? ";
    private static final String SQL_QUERY_DELETE_LINK_WHATSNEW_PORTLET_TO_PORTLET = " DELETE FROM whatsnew_portlet_whatsnew WHERE id_whatsnew_portlet = ? AND id_portlet = ? ";

    // SQL for table whatsnew_document_whatsnew
    private static final String SQL_QUERY_INSERT_DOCUMENT_WHATSNEW = " INSERT INTO whatsnew_document_whatsnew (id_whatsnew_portlet, id_portlet, id_document) VALUES ( ?,?,? ) ";
    private static final String SQL_QUERY_SELECT_DOCUMENT_WHATSNEW = " SELECT id_portlet, id_document FROM whatsnew_document_whatsnew WHERE id_whatsnew_portlet = ? ";
    private static final String SQL_QUERY_DELETE_DOCUMENT_WHATSNEW_FROM_ID_WHATSNEW_PORTLET = " DELETE FROM whatsnew_document_whatsnew WHERE id_whatsnew_portlet = ? ";
    private static final String SQL_QUERY_DELETE_DOCUMENT_WHATSNEW_FROM_ID_DOCUMENT_ID_PORTLET = " DELETE FROM whatsnew_document_whatsnew WHERE id_portlet = ? AND id_document = ? ";
    private static final String SQL_QUERY_DELETE_LINK_WHATSNEW_PORTLET_TO_DOCUMENT = " DELETE FROM whatsnew_document_whatsnew WHERE id_whatsnew_portlet = ? AND id_portlet = ? AND id_document = ?";

    ///////////////////////////////////////////////////////////////////////////////////////
    //Access methods to data

    /**
     * {@inheritDoc}
     */
    public void insert( Portlet portlet )
    {
        WhatsNewPortlet p = (WhatsNewPortlet) portlet;

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT );
        int nIndex = 1;

        daoUtil.setInt( nIndex++, p.getId(  ) );
        daoUtil.setBoolean( nIndex++, p.getShowDocuments(  ) );
        daoUtil.setBoolean( nIndex++, p.getShowPortlets(  ) );
        daoUtil.setBoolean( nIndex++, p.getShowPages(  ) );
        daoUtil.setInt( nIndex++, p.getPeriod(  ) );
        daoUtil.setInt( nIndex++, p.getNbElementsMax(  ) );
        daoUtil.setInt( nIndex++, p.getElementsOrder(  ) );
        daoUtil.setBoolean( nIndex++, p.getAscSort(  ) );
        daoUtil.setBoolean( nIndex++, p.getDynamic(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
     */
    public void delete( int nPortletId )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE );
        daoUtil.setInt( 1, nPortletId );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
     */
    public Portlet load( int nPortletId )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT );
        daoUtil.setInt( 1, nPortletId );
        daoUtil.executeQuery(  );

        WhatsNewPortlet portlet = new WhatsNewPortlet(  );

        int nIndex = 1;

        if ( daoUtil.next(  ) )
        {
            portlet.setId( daoUtil.getInt( nIndex++ ) );
            portlet.setShowDocuments( daoUtil.getBoolean( nIndex++ ) );
            portlet.setShowPortlets( daoUtil.getBoolean( nIndex++ ) );
            portlet.setShowPages( daoUtil.getBoolean( nIndex++ ) );
            portlet.setPeriod( daoUtil.getInt( nIndex++ ) );
            portlet.setNbElementsMax( daoUtil.getInt( nIndex++ ) );
            portlet.setElementsOrder( daoUtil.getInt( nIndex++ ) );
            portlet.setAscSort( daoUtil.getBoolean( nIndex++ ) );
            portlet.setDynamic( daoUtil.getBoolean( nIndex++ ) );
        }

        daoUtil.free(  );

        return portlet;
    }

    /**
     * {@inheritDoc}
     */
    public void store( Portlet portlet )
    {
        WhatsNewPortlet p = (WhatsNewPortlet) portlet;

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE );
        int nIndex = 1;

        daoUtil.setBoolean( nIndex++, p.getShowDocuments(  ) );
        daoUtil.setBoolean( nIndex++, p.getShowPortlets(  ) );
        daoUtil.setBoolean( nIndex++, p.getShowPages(  ) );
        daoUtil.setInt( nIndex++, p.getPeriod(  ) );
        daoUtil.setInt( nIndex++, p.getNbElementsMax(  ) );
        daoUtil.setInt( nIndex++, p.getElementsOrder(  ) );
        daoUtil.setBoolean( nIndex++, p.getAscSort(  ) );
        daoUtil.setBoolean( nIndex++, p.getDynamic(  ) );

        daoUtil.setInt( nIndex++, p.getId(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
     */
    public List<WhatsNewPortlet> findAll(  )
    {
        List<WhatsNewPortlet> listPortlets = new ArrayList<WhatsNewPortlet>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_ALL );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            int nIndex = 1;
            WhatsNewPortlet portlet = new WhatsNewPortlet(  );
            portlet.setId( daoUtil.getInt( nIndex++ ) );
            portlet.setShowDocuments( daoUtil.getBoolean( nIndex++ ) );
            portlet.setShowPortlets( daoUtil.getBoolean( nIndex++ ) );
            portlet.setShowPages( daoUtil.getBoolean( nIndex++ ) );
            portlet.setPeriod( daoUtil.getInt( nIndex++ ) );
            portlet.setNbElementsMax( daoUtil.getInt( nIndex++ ) );
            portlet.setElementsOrder( daoUtil.getInt( nIndex++ ) );
            portlet.setAscSort( daoUtil.getBoolean( nIndex++ ) );
            portlet.setDynamic( daoUtil.getBoolean( nIndex++ ) );
            listPortlets.add( portlet );
        }

        daoUtil.free(  );

        return listPortlets;
    }

    // WHATSNEW_PAGE_WHATSNEW

    /**
     * {@inheritDoc}
     */
    public List<Integer> loadPageIdsFromWhatsNewPortletId( int nWhatsNewPortletId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_PAGE_WHATSNEW, plugin );
        daoUtil.setInt( 1, nWhatsNewPortletId );
        daoUtil.executeQuery(  );

        List<Integer> listPageIds = new ArrayList<Integer>(  );

        while ( daoUtil.next(  ) )
        {
            listPageIds.add( daoUtil.getInt( 1 ) );
        }

        daoUtil.free(  );

        return listPageIds;
    }

    /**
     * {@inheritDoc}
     */
    public void insertPageForWhatsNew( int nWhatsNewPortletId, int nPageId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT_PAGE_WHATSNEW, plugin );
        int nIndex = 1;

        daoUtil.setInt( nIndex++, nWhatsNewPortletId );
        daoUtil.setInt( nIndex++, nPageId );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
     */
    public void deletePagesFromWhatsNew( int nWhatsNewPortletId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_PAGE_WHATSNEW_FROM_ID_WHATSNEW_PORTLET, plugin );

        daoUtil.setInt( 1, nWhatsNewPortletId );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
     */
    public void deleteWhatsNewFromPage( int nPageId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_PAGE_WHATSNEW_FROM_ID_PAGE, plugin );

        daoUtil.setInt( 1, nPageId );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
     */
    public void deleteLinkWhatsNewPortletToPage( int nWhatsNewPortletId, int nPageId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_LINK_WHATSNEW_PORTLET_TO_PAGE, plugin );

        int nIndex = 1;
        daoUtil.setInt( nIndex++, nWhatsNewPortletId );
        daoUtil.setInt( nIndex++, nPageId );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    // WHATSNEW_PORTLET_WHATSNEW

    /**
     * {@inheritDoc}
     */
    public List<Integer> loadPortletIdsFromWhatsNewPortletId( int nWhatsNewPortletId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_PORTLET_WHATSNEW, plugin );
        daoUtil.setInt( 1, nWhatsNewPortletId );
        daoUtil.executeQuery(  );

        List<Integer> listPortletIds = new ArrayList<Integer>(  );

        while ( daoUtil.next(  ) )
        {
            listPortletIds.add( daoUtil.getInt( 1 ) );
        }

        daoUtil.free(  );

        return listPortletIds;
    }

    /**
     * {@inheritDoc}
     */
    public void insertPortletForWhatsNew( int nWhatsNewPortletId, int nPortletId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT_PORTLET_WHATSNEW, plugin );
        int nIndex = 1;

        daoUtil.setInt( nIndex++, nWhatsNewPortletId );
        daoUtil.setInt( nIndex++, nPortletId );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
     */
    public void deletePortletsFromWhatsNew( int nWhatsNewPortletId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_PORTLET_WHATSNEW_FROM_ID_WHATSNEW_PORTLET, plugin );

        daoUtil.setInt( 1, nWhatsNewPortletId );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
     */
    public void deleteWhatsNewFromPortlet( int nPortletId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_PORTLET_WHATSNEW_FROM_ID_PORTLET, plugin );

        daoUtil.setInt( 1, nPortletId );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
     */
    public void deleteLinkWhatsNewPortletToPortlet( int nWhatsNewPortletId, int nPortletId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_LINK_WHATSNEW_PORTLET_TO_PORTLET, plugin );

        int nIndex = 1;
        daoUtil.setInt( nIndex++, nWhatsNewPortletId );
        daoUtil.setInt( nIndex++, nPortletId );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    // WHATSNEW_DOCUMENT_WHATSNEW

    /**
     * {@inheritDoc}
     */
    public List<PortletDocumentLink> loadDocumentFromWhatsNewPortletId( int nWhatsNewPortletId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_DOCUMENT_WHATSNEW, plugin );
        daoUtil.setInt( 1, nWhatsNewPortletId );
        daoUtil.executeQuery(  );

        List<PortletDocumentLink> listPortletDocumentLink = new ArrayList<PortletDocumentLink>(  );

        while ( daoUtil.next(  ) )
        {
            int nIndex = 1;
            int nPortletId = daoUtil.getInt( nIndex++ );
            int nDocumentId = daoUtil.getInt( nIndex++ );
            PortletDocumentLink pdLink = new PortletDocumentLink( nPortletId, nDocumentId );
            listPortletDocumentLink.add( pdLink );
        }

        daoUtil.free(  );

        return listPortletDocumentLink;
    }

    /**
     * {@inheritDoc}
     */
    public void insertDocumentForWhatsNew( int nWhatsNewPortletId, PortletDocumentLink pdLink, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT_DOCUMENT_WHATSNEW, plugin );
        int nIndex = 1;

        daoUtil.setInt( nIndex++, nWhatsNewPortletId );
        daoUtil.setInt( nIndex++, pdLink.getPortletId(  ) );
        daoUtil.setInt( nIndex++, pdLink.getDocumentId(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
     */
    public void deleteDocumentsFromWhatsNew( int nWhatsNewPortletId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_DOCUMENT_WHATSNEW_FROM_ID_WHATSNEW_PORTLET, plugin );

        daoUtil.setInt( 1, nWhatsNewPortletId );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
     */
    public void deleteWhatsNewFromDocument( PortletDocumentLink pdLink, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_DOCUMENT_WHATSNEW_FROM_ID_DOCUMENT_ID_PORTLET, plugin );

        int nIndex = 1;
        daoUtil.setInt( nIndex++, pdLink.getPortletId(  ) );
        daoUtil.setInt( nIndex++, pdLink.getDocumentId(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
     */
    public void deleteLinkWhatsNewPortletToDocument( int nWhatsNewPortletId, PortletDocumentLink pdLink, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_LINK_WHATSNEW_PORTLET_TO_DOCUMENT, plugin );

        int nIndex = 1;
        daoUtil.setInt( nIndex++, nWhatsNewPortletId );
        daoUtil.setInt( nIndex++, pdLink.getPortletId(  ) );
        daoUtil.setInt( nIndex++, pdLink.getDocumentId(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }
}
