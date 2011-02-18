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
package fr.paris.lutece.plugins.whatsnew.business;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;

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
    private static final String SQL_QUERY_SELECT_DOCUMENTS_BY_CRITERIAS = " SELECT a.title, a.document_summary as description, a.date_modification, a.id_document, b.id_portlet " +
        " FROM document a, document_published b " + " WHERE (a.id_document = b.id_document) AND " +
        " ( a.date_modification between ? AND ? ) AND " +
        " ( a.date_validity_begin is null or ? > a.date_validity_begin ) AND " +
        " ( a.date_validity_end is null or ? < a.date_validity_end ) ";
    private static final String SQL_QUERY_SELECT_PORTLETS_BY_CRITERIAS = " SELECT a.name as title,'',a.date_update, a.id_page, a.id_portlet FROM core_portlet a WHERE a.date_update between ? AND ? ";
    private static final String SQL_QUERY_SELECT_PAGES_BY_CRITERIAS = " SELECT a.name as title, a.description, a.date_update, a.id_page FROM core_page a WHERE a.date_update between ? AND ? ";

    /**
     * Returns the list of the documents which correspond to the criteria given
     * @param dateLimit the timestamp giving the beginning of the period to watch
     * @param plugin {@link Plugin}
     * @param locale {@link Locale}
     * @return the list in form of a Collection object
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
            list.add( whatsNew );
        }

        daoUtil.free(  );

        return list;
    }

    /**
     * Returns the list of the portlets which correspond to the criteria given
     * @param dateLimit the timestamp giving the beginning of the period to watch
     * @param locale {@link Locale}
     * @return the list in form of a Collection object
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

            IWhatsNew whatsNew = new WhatsNewTypePortlet(  );
            whatsNew.setWhatsNewType( locale );
            whatsNew.setTitle( daoUtil.getString( nIndex++ ) );
            whatsNew.setDescription( daoUtil.getString( nIndex++ ) );
            whatsNew.setDateUpdate( daoUtil.getTimestamp( nIndex++ ) );
            whatsNew.setPageId( daoUtil.getInt( nIndex++ ) );
            whatsNew.setPortletId( daoUtil.getInt( nIndex++ ) );
            list.add( whatsNew );
        }

        daoUtil.free(  );

        return list;
    }

    /**
     * Returns the list of the pages which correspond to the criteria given
     * @param dateLimit the timestamp giving the beginning of the period to watch
     * @param locale {@link Locale}
     * @return the list in form of a Collection object
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
}
