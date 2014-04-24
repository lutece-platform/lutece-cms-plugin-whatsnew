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
package fr.paris.lutece.plugins.whatsnew.utils.sort;

import fr.paris.lutece.plugins.whatsnew.business.ElementOrderEnum;
import fr.paris.lutece.plugins.whatsnew.business.IWhatsNew;

import java.io.Serializable;

import java.util.Comparator;


/**
 *
 * WhatsNewComparator
 *
 */
public class WhatsNewComparator implements Comparator<IWhatsNew>, Serializable
{
    private static final long serialVersionUID = 1035663950193810359L;
    private int _nOrder;
    private boolean _bIsAscSort;

    /**
     * Constructor
     * @param nOrder order
     * @param bIsAscSort true if it is sorted asc, false otherwise
     */
    public WhatsNewComparator( int nOrder, boolean bIsAscSort )
    {
        _nOrder = nOrder;
        _bIsAscSort = bIsAscSort;
    }

    /**
     * Compare two WhatsNew
     * @param w1 WhatsNew 1
     * @param w2 WhatsNew 2
     * @return in the ascending sort :
     * <ul>
     * <li>&lt; 0 if w1 is before w2</li>
     * <li>0 if w1 equals w2</li>
     * <li>&gt; 0 if w1 is after w2</li>
     * </ul>
     */
    public int compare( IWhatsNew w1, IWhatsNew w2 )
    {
        int nStatus = 0;

        if ( ( w1 != null ) && ( w2 != null ) )
        {
            if ( _nOrder == ElementOrderEnum.DATE.getId(  ) )
            {
                if ( ( w1.getDateUpdate(  ) != null ) && ( w2.getDateUpdate(  ) != null ) )
                {
                    nStatus = w1.getDateUpdate(  ).compareTo( w2.getDateUpdate(  ) );
                }
                else if ( ( w1.getDateUpdate(  ) == null ) && ( w2.getDateUpdate(  ) != null ) )
                {
                    nStatus = -1;
                }
                else if ( ( w1.getDateUpdate(  ) != null ) && ( w2.getDateUpdate(  ) == null ) )
                {
                    nStatus = 1;
                }
            }
            else if ( _nOrder == ElementOrderEnum.ALPHA.getId(  ) )
            {
                if ( ( w1.getTitle(  ) != null ) && ( w2.getTitle(  ) != null ) )
                {
                    nStatus = w1.getTitle(  ).toUpperCase(  ).compareTo( w2.getTitle(  ).toUpperCase(  ) );
                }
                else if ( ( w1.getTitle(  ) == null ) && ( w2.getTitle(  ) != null ) )
                {
                    nStatus = -1;
                }
                else if ( ( w1.getTitle(  ) != null ) && ( w2.getTitle(  ) == null ) )
                {
                    nStatus = 1;
                }
            }

            if ( ( nStatus != 0 ) && !_bIsAscSort )
            {
                nStatus = nStatus * ( -1 );
            }
        }

        return nStatus;
    }
}
