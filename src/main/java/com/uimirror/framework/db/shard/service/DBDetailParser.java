package com.uimirror.framework.db.shard.service;


import com.uimirror.framework.db.shard.model.DBDetail;
import org.springframework.core.io.DefaultResourceLoader;

import java.io.IOException;

/**
 * Helps to parse the json configuration from the location specified for the data base initialization.
 *
 * @author Jayaram
 *         3/11/16.
 */
public class DBDetailParser {

    /**
     * Loads the Json file from the specified location for the {@link DBDetail}
     * @param loc either file or classpath resource specifed in classpath: or file: format
     * @return parsed object {@link DBDetail}
     * @throws IOException in case config file not found.
     */
    public DBDetail parse(String loc, boolean isJosn) throws IOException {
        if(isJosn){
            return ObjectMapperFactory.getMapper().readValue(loc, DBDetail.class);
        }
        return ObjectMapperFactory.getMapper().readValue(new DefaultResourceLoader().getResource(loc).getInputStream(), DBDetail.class);
    }
}
