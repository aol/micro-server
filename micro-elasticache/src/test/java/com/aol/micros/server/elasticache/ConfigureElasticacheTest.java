package com.aol.micros.server.elasticache;

import com.aol.micro.server.elasticache.ConfigureElasticache;
import com.aol.micro.server.elasticache.TransientElasticacheDataConnection;
import net.spy.memcached.MemcachedClient;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertTrue;

/**
 * Created by gordonmorrow on 4/27/17.
 */
public class ConfigureElasticacheTest {


    ConfigureElasticache cache;

    @Autowired
    TransientElasticacheDataConnection<String> client;


    @Before
    public void setUp() throws Exception {
        cache = new ConfigureElasticache();
    }

    @Test
    public void simpleSetTest(){
        Boolean result=false;
       try {

           client.put("testKey10", 3600, "testValue1");
       }catch(Exception e){
           System.out.println("Not working");
           result=false;
       }
    }


    @Test
    public void simpleAddTest(){
        Boolean result=false;
        try {
            result = client.put("testKey20", 3600, "testValue2");
        }catch(Exception e){
            System.out.println("Not working");
            result=false;
        }
    }




}
