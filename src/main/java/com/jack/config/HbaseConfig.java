package com.jack.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * Hbase配置类
 * @author sixmonth
 *
 */
@Configuration
@ConfigurationProperties(prefix = HbaseConfig.CONF_PREFIX)
public class HbaseConfig {

    public static final String CONF_PREFIX = "hbase.conf";

    private Map<String,String> confMaps;

    public Map<String, String> getConfMaps() {
        return confMaps;
    }
    public void setConfMaps(Map<String, String> confMaps) {
        this.confMaps = confMaps;
    }
}
