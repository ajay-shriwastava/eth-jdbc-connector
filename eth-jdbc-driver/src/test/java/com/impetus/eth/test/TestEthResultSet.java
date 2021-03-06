/******************************************************************************* 
* * Copyright 2018 Impetus Infotech.
* *
* * Licensed under the Apache License, Version 2.0 (the "License");
* * you may not use this file except in compliance with the License.
* * You may obtain a copy of the License at
* *
* * http://www.apache.org/licenses/LICENSE-2.0
* *
* * Unless required by applicable law or agreed to in writing, software
* * distributed under the License is distributed on an "AS IS" BASIS,
* * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* * See the License for the specific language governing permissions and
* * limitations under the License.
******************************************************************************/
package com.impetus.eth.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.impetus.blkch.sql.DataFrame;
import com.impetus.eth.jdbc.EthResultSet;
import com.impetus.eth.jdbc.EthResultSetMetaData;
import com.impetus.test.catagory.UnitTest;

@Category(UnitTest.class)
public class TestEthResultSet extends TestCase {
    private List<List<Object>> data = new ArrayList<List<Object>>();

    private HashMap<String, Integer> columnNamesMap = new HashMap<>();

    private Map<String, String> aliasMapping = new HashMap<String, String>();

    private EthResultSet ethResultSet;

    String table;

    @Override
    protected void setUp() {

        columnNamesMap.put("value", 0);
        columnNamesMap.put("gas", 1);
        columnNamesMap.put("blocknumber", 2);

        List<Object> returnRec = new ArrayList<Object>();
        returnRec.add(544444);
        returnRec.add(213234);
        returnRec.add(15675);
        data.add(returnRec);
        returnRec = new ArrayList<Object>();
        returnRec.add(544434);
        returnRec.add(213233);
        returnRec.add(156333);
        data.add(returnRec);
        returnRec = new ArrayList<Object>();
        returnRec.add(544434);
        returnRec.add(213233);
        returnRec.add(156333);
        data.add(returnRec);

        returnRec = new ArrayList<Object>();
        returnRec.add(544410);
        returnRec.add(213232);
        returnRec.add(156334);
        data.add(returnRec);
        returnRec = new ArrayList<Object>();
        returnRec.add(544411);
        returnRec.add(2132235);
        returnRec.add(1563324);
        data.add(returnRec);
        aliasMapping.put("val", "value");
        DataFrame df = new DataFrame(data, columnNamesMap, aliasMapping);
        ethResultSet = new EthResultSet(df, java.sql.ResultSet.FETCH_FORWARD, java.sql.ResultSet.CONCUR_READ_ONLY, table);
    }

    @Test
    public void testEthResultSet() {
        boolean status = false;
        try {
            ethResultSet.beforeFirst();
            status = true;
        } catch (SQLException e) {

        }
        assertTrue(status);
    }

    @Test
    public void testEthResultSetNext() {
        String value = "";
        try {
            ethResultSet.next();
            value = ethResultSet.getObject(1).toString();
            ethResultSet.beforeFirst();
        } catch (SQLException e) {

        }
        assertEquals("544444", value);
    }

    @Test
    public void testEthResultSetPrevious() {
        String value = "";
        try {
            ethResultSet.next();
            ethResultSet.next();
            ethResultSet.next();
            ethResultSet.previous();
            value = ethResultSet.getObject("val").toString();
            ethResultSet.beforeFirst();
        } catch (SQLException e) {

        }
        assertEquals("544434", value);
    }

    @Test
    public void testEthResultSetLast() {
        String value = "";
        try {
            ethResultSet.last();
            value = ethResultSet.getObject("val").toString();
            ethResultSet.beforeFirst();
        } catch (SQLException e) {

        }
        assertEquals("544411", value);
    }

    @Test
    public void testEthResultSetAbsolute() {
        String value = "";
        try {
            ethResultSet.relative(5);
            value = ethResultSet.getObject("val").toString();
            ethResultSet.beforeFirst();
        } catch (SQLException e) {

        }
        assertEquals("544411", value);
    }

    @Test
    public void testEthResultSetIsLast() {
        boolean isLast = false;
        try {
            ethResultSet.last();
            isLast = ethResultSet.isLast();
            ethResultSet.beforeFirst();
        } catch (SQLException e) {

        }
        assertTrue(isLast);
    }

    @Test
    public void testEthResultSetIsFirst() {
        boolean isfirst = false;
        try {
            ethResultSet.first();
            isfirst = ethResultSet.isFirst();
            ethResultSet.beforeFirst();
        } catch (SQLException e) {

        }
        assertTrue(isfirst);
    }

    @Test
    public void testEthResultSetIsAfterLast() {
        boolean isAfterLast = false;
        try {
            ethResultSet.afterLast();
            isAfterLast=true;
            ethResultSet.beforeFirst();
        } catch (SQLException e) {

        }
        assertTrue(isAfterLast);
    }
    
    @Test
    public void testEthResultSetIs() {
        int size = 0;
        try {
            size=ethResultSet.getFetchSize();
        } catch (SQLException e) {

        }
        assertEquals(5, size);
    }
    @Test
    public void testEthResultSetMetadata() {
        int columnCount = 0;
        try {
            
           ethResultSet.next();
           EthResultSetMetaData rsMetaData= (EthResultSetMetaData) ethResultSet.getMetaData();
           columnCount=rsMetaData.getColumnCount();
           rsMetaData.getColumnLabel(0);
           rsMetaData.getColumnName(0);
           rsMetaData.getTableName(0);
           rsMetaData.isCaseSensitive(0);
            ethResultSet.beforeFirst();
        } catch (SQLException e) {

        }
        assertEquals(3,columnCount );
    }

    @Test
    public void testEthResultSetCloseBefore(){
        boolean closed = true;
        try {
            closed = ethResultSet.isClosed();
        }catch (SQLException e) {

        }
        assertEquals(false, closed);
    }

    @Test
    public void testEthResultSetCloseAfter(){
        boolean closed = false;
        try {
            ethResultSet.close();
            closed = ethResultSet.isClosed();
        }catch (SQLException e) {

        }
        assertEquals(true, closed);
    }

}
