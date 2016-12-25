package com.uimirror.framework.db.shard.service;


import com.uimirror.framework.db.shard.model.DBDetail;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Jayaram
 *         3/11/16.
 */
public class DBDetailParserTest {

    @Test
    public void testParseConfig() throws Exception {
        DBDetail dbDetail = new DBDetailParser().parse("classpath:/sample-db-conf.json", false);
        assertThat(dbDetail).isNotNull();
        assertThat(dbDetail.getDbDetails()).hasSize(4);
        assertThat(dbDetail.getDbDetails().get(1).getShardDetails()).hasSize(2);

    }
}
