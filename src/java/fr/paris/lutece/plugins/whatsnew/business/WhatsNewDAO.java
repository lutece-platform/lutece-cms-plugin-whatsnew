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

import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;

import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 *
 * WhatsNewDAO
 *
 */
public class WhatsNewDAO implements IWhatsNewDAO
{
    private static final String SQL_QUERY_SELECT_DOCUMENTS_BY_CRITERIAS = " SELECT a.title, a.document_summary as description, a.date_modification, a.id_document, b.id_portlet, c.document_type_name " +
        " FROM document a INNER JOIN document_published b ON a.id_document = b.id_document " +
        " INNER JOIN document_type c ON a.code_document_type = c.code_document_type WHERE " +
        " ( a.date_modification between ? AND ? ) AND " +
        " ( a.date_validity_begin is null or ? > a.date_validity_begin ) AND " +
        " ( a.date_validity_end is null or ? < a.date_validity_end ) ";
    private static final String SQL_QUERY_SELECT_PORTLETS_BY_CRITERIAS = " SELECT p.name, p.date_update, p.id_page, p.id_portlet, pt.name, pa.name " +
        " FROM core_portlet p INNER JOIN core_portlet_type pt ON p.id_portlet_type = pt.id_portlet_type " +
        " INNER JOIN core_page pa ON p.id_page = pa.id_page " + " WHERE p.date_update between ? AND ? ";
    private static final String SQL_QUERY_SELECT_PAGES_BY_CRITERIAS = " SELECT name, description, date_update, id_page FROM core_page WHERE date_update between ? AND ? ";
    private static final String SQL_QUERY_SELECT_PORTLETS = " SELECT p.name, p.date_update, p.id_page, p.id_portlet, pt.name, pa.name " +
        " FROM core_portlet p INNER JOIN core_portlet_type pt ON p.id_portlet_type = pt.id_portlet_type " +
        " INNER JOIN core_page pa ON p.id_page = pa.id_page ";
    private static final String SQL_WHERE_ID_PORTLET = " id_portlet = ? ";
    private static final String SQL_QUERY_SELECT_PAGES = " SELECT name, description, date_update, id_page FROM core_page ";
    private static final String SQL_WHERE_ID_PAGE = " id_page = ? ";
    private static final String SQL_QUERY_SELECT_DOCUMENTS = " SELECT a.title, a.document_summary as description, a.date_modification, a.id_document, b.id_portlet, c.document_type_name " +
        " FROM document a INNER JOIN document_published b ON a.id_document = b.id_document " +
        " INNER JOIN document_type c ON a.code_document_type = c.code_document_type ";
    private static final String SQL_WHERE_ID_PORTLET_ID_DOCUMENT = " ( b.id_portlet = ? AND a.id_document = ? ) ";
    private static final String SQL_WHERE = " WHERE ";
    private static final String SQL_OR = " OR ";
    private static final String SQL_AND = " AND ";

    /**
     * {@inheritDoc}
     */
    public Collection<IWhatsNew> selectPagesByCriterias( Timestamp dateLimit, Locale locale )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_PAGES_BY_CRITERIAS );
        int nIndex = 1;
        Timestamp timestampCurrent = new Timestamp( ( new Date(  ) ).getTime(  ) );
        daoUtil.setTimestamp( nIndex++, dateLimit );
        daoUtil.setTimestamp( nIndex++, timestampCurrent );
        daoUtil.executeQuery(  );

        List<IWhatsNew> list = new ArrayList<IWhatsNew>(  );

        while ( daoUtil.next(  ) )
        {
            nIndex = 1;

            IWhatsNew whatsNew = new WhatsNewTypePage(  );
            whatsNew.setWhatsNewType( locale );
            whatsNew.setTitle( daoUtil.getString( nIndex++ ) );
            whatsNew.setDescription( daoUtil.getString( nIndex++ ) );
            whatsNew.setDateUpdate( daoUtil.getTimestamp( nIndex++ ) );
            whatsNew.setPageId( daoUtil.getInt( nIndex++ ) );
            list.add( whatsNew );
        }

        daoUtil.free(  );

        return list;
    }

    /**
     * {@inheritDoc}
     */
    public Collection<IWhatsNew> selectPortletsByCriterias( Timestamp dateLimit, Locale locale )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_PORTLETS_BY_CRITERIAS );
        int nIndex = 1;
        Timestamp timestampCurrent = new Timestamp( ( new Date(  ) ).getTime(  ) );
        daoUtil.setTimestamp( nIndex++, dateLimit );
        daoUtil.setTimestamp( nIndex++, timestampCurrent );
        daoUtil.executeQuery(  );

        List<IWhatsNew> list = new ArrayList<IWhatsNew>(  );

        while ( daoUtil.next(  ) )
        {
            nIndex = 1;

            WhatsNewTypePortlet whatsNew = new WhatsNewTypePortlet(  );
            whatsNew.setWhatsNewType( locale );
            whatsNew.setTitle( daoUtil.getString( nIndex++ ) );
            whatsNew.setDescription( StringUtils.EMPTY );
            whatsNew.setDateUpdate( daoUtil.getTimestamp( nIndex++ ) );
            whatsNew.setPageId( daoUtil.getInt( nIndex++ ) );
            whatsNew.setPortletId( daoUtil.getInt( nIndex++ ) );

            String strType = I18nService.getLocalizedString( daoUtil.getString( nIndex++ ), locale );
            whatsNew.setType( strType );
            whatsNew.setRefPageName( daoUtil.getString( nIndex++ ) );
            list.add( whatsNew );
        }

        daoUtil.free(  );

        return list;
    }

    /**
     * {@inheritDoc}
     */
    public Collection<IWhatsNew> selectDocumentsByCriterias( Timestamp dateLimit, Plugin plugin, Locale locale )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_DOCUMENTS_BY_CRITERIAS, plugin );
        int nIndex = 1;
        Timestamp timestampCurrent = new Timestamp( ( new Date(  ) ).getTime(  ) );
        daoUtil.setTimestamp( nIndex++, dateLimit );
        daoUtil.setTimestamp( nIndex++, timestampCurrent );
        daoUtil.setTimestamp( nIndex++, timestampCurrent );
        daoUtil.setTimestamp( nIndex++, timestampCurrent );
        daoUtil.executeQuery(  );

        List<IWhatsNew> list = new ArrayList<IWhatsNew>(  );

        while ( daoUtil.next(  ) )
        {
            nIndex = 1;

            IWhatsNew whatsNew = new WhatsNewTypeDocument(  );
            whatsNew.setWhatsNewType( locale );
            whatsNew.setTitle( daoUtil.getString( nIndex++ ) );
            whatsNew.setDescription( daoUtil.getString( nIndex++ ) );
            whatsNew.setDateUpdate( daoUtil.getTimestamp( nIndex++ ) );
            whatsNew.setDocumentId( daoUtil.getInt( nIndex++ ) );
            whatsNew.setPortletId( daoUtil.getInt( nIndex++ ) );
            whatsNew.setType( daoUtil.getString( nIndex++ ) );
            list.add( whatsNew );
        }

        daoUtil.free(  );

        return list;
    }

    /**
     * {@inheritDoc}
     */
    public Collection<IWhatsNew> selectPages( List<Integer> listPageIds, Locale locale )
    {
        Collection<IWhatsNew> listWhatsNews = new ArrayList<IWhatsNew>(  );
        StringBuilder sbSQL = new StringBuilder( SQL_QUERY_SELECT_PAGES );

        if ( listPageIds.size(  ) > 0 )
        {
            sbSQL.append( SQL_WHERE );

            for ( int nIndex = 0; nIndex < listPageIds.size(  ); nIndex++ )
            {
                sbSQL.append( SQL_WHERE_ID_PAGE );

                if ( nIndex < ( listPageIds.size(  ) - 1 ) )
                {
                    sbSQL.append( SQL_OR );
                }
            }

            DAOUtil daoUtil = new DAOUtil( sbSQL.toString(  ) );
            int nIndex = 1;

            for ( Integer nPageId : listPageIds )
            {
                daoUtil.setInt( nIndex++, nPageId );
            }

            daoUtil.executeQuery(  );

            while ( daoUtil.next(  ) )
            {
                nIndex = 1;

                IWhatsNew whatsNew = new WhatsNewTypePage(  );
                whatsNew.setWhatsNewType( locale );
                whatsNew.setTitle( daoUtil.getString( nIndex++ ) );
                whatsNew.setDescription( daoUtil.getString( nIndex++ ) );
                whatsNew.setDateUpdate( daoUtil.getTimestamp( nIndex++ ) );
                whatsNew.setPageId( daoUtil.getInt( nIndex++ ) );
                listWhatsNews.add( whatsNew );
            }

            daoUtil.free(  );
        }

        return listWhatsNews;
    }

    /**
     * {@inheritDoc}
     */
    public Collection<IWhatsNew> selectPortlets( List<Integer> listPortletIds, Locale locale )
    {
        Collection<IWhatsNew> listWhatsNews = new ArrayList<IWhatsNew>(  );

        if ( listPortletIds.size(  ) > 0 )
        {
            StringBuilder sbSQL = new StringBuilder( SQL_QUERY_SELECT_PORTLETS );
            sbSQL.append( SQL_WHERE );

            for ( int nIndex = 0; nIndex < listPortletIds.size(  ); nIndex++ )
            {
                sbSQL.append( SQL_WHERE_ID_PORTLET );

                if ( nIndex < ( listPortletIds.size(  ) - 1 ) )
                {
                    sbSQL.append( SQL_OR );
                }
            }

            DAOUtil daoUtil = new DAOUtil( sbSQL.toString(  ) );
            int nIndex = 1;

            for ( Integer nPortletId : listPortletIds )
            {
                daoUtil.setInt( nIndex++, nPortletId );
            }

            daoUtil.executeQuery(  );

            while ( daoUtil.next(  ) )
            {
                nIndex = 1;

                WhatsNewTypePortlet whatsNew = new WhatsNewTypePortlet(  );
                whatsNew.setWhatsNewType( locale );
                whatsNew.setTitle( daoUtil.getString( nIndex++ ) );
                whatsNew.setDescription( StringUtils.EMPTY );
                whatsNew.setDateUpdate( daoUtil.getTimestamp( nIndex++ ) );
                whatsNew.setPageId( daoUtil.getInt( nIndex++ ) );
                whatsNew.setPortletId( daoUtil.getInt( nIndex++ ) );

                String strType = I18nService.getLocalizedString( daoUtil.getString( nIndex++ ), locale );
                whatsNew.setType( strType );
                whatsNew.setRefPageName( daoUtil.getString( nIndex++ ) );
                listWhatsNews.add( whatsNew );
            }

            daoUtil.free(  );
        }

        return listWhatsNews;
    }

    /**
     * {@inheritDoc}
     */
    public Collection<IWhatsNew> selectDocuments( List<PortletDocumentLink> listPortletDocumentLinks, Locale locale )
    {
        Collection<IWhatsNew> listWhatsNews = new ArrayList<IWhatsNew>(  );
        StringBuilder sbSQL = new StringBuilder( SQL_QUERY_SELECT_DOCUMENTS );

        if ( listPortletDocumentLinks.size(  ) > 0 )
        {
            sbSQL.append( SQL_WHERE );

            for ( int nIndex = 0; nIndex < listPortletDocumentLinks.size(  ); nIndex++ )
            {
                sbSQL.append( SQL_WHERE_ID_PORTLET_ID_DOCUMENT );

                if ( nIndex < ( listPortletDocumentLinks.size(  ) - 1 ) )
                {
                    sbSQL.append( SQL_OR );
                }
            }

            DAOUtil daoUtil = new DAOUtil( sbSQL.toString(  ) );
            int nIndex = 1;

            for ( PortletDocumentLink pdLink : listPortletDocumentLinks )
            {
                daoUtil.setInt( nIndex++, pdLink.getPortletId(  ) );
                daoUtil.setInt( nIndex++, pdLink.getDocumentId(  ) );
            }

            daoUtil.executeQuery(  );

            while ( daoUtil.next(  ) )
            {
                nIndex = 1;

                IWhatsNew whatsNew = new WhatsNewTypeDocument(  );
                whatsNew.setWhatsNewType( locale );
                whatsNew.setTitle( daoUtil.getString( nIndex++ ) );
                whatsNew.setDescription( daoUtil.getString( nIndex++ ) );
                whatsNew.setDateUpdate( daoUtil.getTimestamp( nIndex++ ) );
                whatsNew.setDocumentId( daoUtil.getInt( nIndex++ ) );
                whatsNew.setPortletId( daoUtil.getInt( nIndex++ ) );
                whatsNew.setType( daoUtil.getString( nIndex++ ) );
                listWhatsNews.add( whatsNew );
            }

            daoUtil.free(  );
        }

        return listWhatsNews;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isPageOutOfDate( int nPageId, Timestamp dateLimit )
    {
        boolean bIsOutOfDate = true;
        StringBuilder sbSQL = new StringBuilder(  );
        sbSQL.append( SQL_QUERY_SELECT_PAGES_BY_CRITERIAS );
        sbSQL.append( SQL_AND );
        sbSQL.append( SQL_WHERE_ID_PAGE );

        DAOUtil daoUtil = new DAOUtil( sbSQL.toString(  ) );
        int nIndex = 1;
        Timestamp timestampCurrent = new Timestamp( ( new Date(  ) ).getTime(  ) );
        daoUtil.setTimestamp( nIndex++, dateLimit );
        daoUtil.setTimestamp( nIndex++, timestampCurrent );
        daoUtil.setInt( nIndex++, nPageId );
        daoUtil.executeQuery(  );

        if ( daoUtil.next(  ) )
        {
            bIsOutOfDate = false;
        }

        daoUtil.free(  );

        return bIsOutOfDate;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isPortletOutOfDate( int nPortletId, Timestamp dateLimit )
    {
        boolean bIsOutOfDate = true;
        StringBuilder sbSQL = new StringBuilder(  );
        sbSQL.append( SQL_QUERY_SELECT_PORTLETS_BY_CRITERIAS );
        sbSQL.append( SQL_AND );
        sbSQL.append( SQL_WHERE_ID_PORTLET );

        DAOUtil daoUtil = new DAOUtil( sbSQL.toString(  ) );
        int nIndex = 1;
        Timestamp timestampCurrent = new Timestamp( ( new Date(  ) ).getTime(  ) );
        daoUtil.setTimestamp( nIndex++, dateLimit );
        daoUtil.setTimestamp( nIndex++, timestampCurrent );
        daoUtil.setInt( nIndex++, nPortletId );
        daoUtil.executeQuery(  );

        if ( daoUtil.next(  ) )
        {
            bIsOutOfDate = false;
        }

        daoUtil.free(  );

        return bIsOutOfDate;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isDocumentOutOfDate( PortletDocumentLink pdLink, Timestamp dateLimit, Plugin plugin )
    {
        boolean bIsOutOfDate = true;

        StringBuilder sbSQL = new StringBuilder(  );
        sbSQL.append( SQL_QUERY_SELECT_DOCUMENTS_BY_CRITERIAS );
        sbSQL.append( SQL_AND );
        sbSQL.append( SQL_WHERE_ID_PORTLET_ID_DOCUMENT );

        DAOUtil daoUtil = new DAOUtil( sbSQL.toString(  ), plugin );
        int nIndex = 1;
        Timestamp timestampCurrent = new Timestamp( ( new Date(  ) ).getTime(  ) );
        daoUtil.setTimestamp( nIndex++, dateLimit );
        daoUtil.setTimestamp( nIndex++, timestampCurrent );
        daoUtil.setTimestamp( nIndex++, timestampCurrent );
        daoUtil.setTimestamp( nIndex++, timestampCurrent );
        daoUtil.setInt( nIndex++, pdLink.getPortletId(  ) );
        daoUtil.setInt( nIndex++, pdLink.getDocumentId(  ) );
        daoUtil.executeQuery(  );

        if ( daoUtil.next(  ) )
        {
            bIsOutOfDate = false;
        }

        daoUtil.free(  );

        return bIsOutOfDate;
    }
}
