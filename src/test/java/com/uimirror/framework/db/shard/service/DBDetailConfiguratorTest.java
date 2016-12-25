package com.uimirror.framework.db.shard.service;


import com.uimirror.framework.db.shard.model.DBDetail;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Jayaram
 *         3/11/16.
 */
public class DBDetailConfiguratorTest {

    private static DBDetailConfigurator dbDetailConfigurator;

    @BeforeClass
    public static void setUp() throws Exception {
        dbDetailConfigurator = new DBDetailConfigurator("shard", ".");
    }

    @Test
    public void testJsonMapping() throws Exception {

        DBDetail dbDetail = ObjectMapperFactory.getMapper().readValue(DBDetailConfiguratorTest.class.getResourceAsStream("/sample-db-conf.json"), DBDetail.class);
        DBDetail configure = dbDetailConfigurator.configure(dbDetail);
        assertThat(configure).isNotNull();
    }
}
