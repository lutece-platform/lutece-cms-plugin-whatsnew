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
package fr.paris.lutece.plugins.whatsnew.service.portlet;

import fr.paris.lutece.plugins.whatsnew.business.portlet.WhatsNewPortlet;
import fr.paris.lutece.plugins.whatsnew.business.portlet.WhatsNewPortletHome;
import fr.paris.lutece.portal.business.portlet.PortletHome;

import java.sql.Timestamp;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;


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
        WhatsNewPortletHome.getInstance(  ).remove( portlet );
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
}
