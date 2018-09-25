package lws.banksystem.client.cryption;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SHA512Test {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void crypt() {
        String crypt = SHA512.crypt("test");
    }

}