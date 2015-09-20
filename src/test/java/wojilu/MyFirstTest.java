package wojilu;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.junit.Test;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by shenyuyang
 */
public class MyFirstTest {


    @Test
    public void testPlus() {
        assertEquals(3, 3);
    }


    /**
     * 普通的 sha256 加密方案
     */
    @Test
    public void testPasswordHash() {

        ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
        String result1 = encoder.encodePassword("123456", "abc");
        System.out.println("result1=>" + result1);

        ShaPasswordEncoder encoder2 = new ShaPasswordEncoder(256);
        String result2 = encoder2.encodePassword("123456", "afdkafief3434");
        System.out.println("result2=>" + result2);

        assertEquals("a928ef78ccfadeb4d28250f0a2007c96d96861f70e099f12cfc4b1383a7045fb", result1);
        assertEquals("5932e60d57bb72be860de9e3e248d93b1bc55a33274276114f1722924a9780da", result2);

    }


    /**
     * spring 推荐的 BCrypt 加密方案
     */
    @Test
    public void testPasswordBCrypt() {


        int i = 0;
        while (i < 10) {
            String password = "123456";
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(password);

            System.out.println(hashedPassword);
            i++;
        }

        /*
$2a$10$UucoBTQSIMGcIQw3O9mdsuAAHQ8zX8vWRc.pvNW0OHNCz.UKy0oQu
$2a$10$ME/BJqcTlMJbD6NtJBDUtubG/ykbm7n0b5ojlBmvJN6Uc8gStKiXa
$2a$10$bIUEw0MycF3fMJZyS4jiPeKRZ2TEYC5MU6Pim3QpFjLeQmOtvgOL.
$2a$10$nbhqryZUA.cQWk/1pRQfsOU5fHgZ/pCcJfZiasTfVl4tA6IpM7x3S
$2a$10$alfUGrbAE5UnuOWMVFs8P.WdfzHBlcQ4OsuDRNA1sz79E1sJ8XvCy
$2a$10$IpgOSxKQ5h9CgsXMEad77.fGreXkU8ISR1dZSet6OLdyKDkFDtUxS
$2a$10$65SWwNLXy45rxVbIU30mB.C/ptyPyGyNT9b0kLUMSzbPchB6VX0BO
$2a$10$7dAmDKnPQFoPNPqfupGlW.qY0nTrpn/ZO6mRCM8d8wGn08La/5odO
$2a$10$Hvd4q7E2IIpKva5Wz8iofuPm2qvQR5t6jePpL23bEZlMizRX4Auq2
$2a$10$Bb9cT0BGhICCSS1mI98Cw.i.atUXkJPDUkINWmq2ywQ/KbPa56an6
        */


        Boolean isOk1 = BCrypt.checkpw("123456", "$2a$10$ME/BJqcTlMJbD6NtJBDUtubG/ykbm7n0b5ojlBmvJN6Uc8gStKiXa");
        Boolean isOk2 = BCrypt.checkpw("123456", "$2a$10$alfUGrbAE5UnuOWMVFs8P.WdfzHBlcQ4OsuDRNA1sz79E1sJ8XvCy");
        Boolean isOk3 = BCrypt.checkpw("123456", "$2a$10$7dAmDKnPQFoPNPqfupGlW.qY0nTrpn/ZO6mRCM8d8wGn08La/5odO");
        Boolean isOk4 = BCrypt.checkpw("123456", "$2a$10$alfUGrbAE5UnuOWMVFs8P.WdfzHBlcQ4OsuDRNA1sz79E1sJ8XvC8");

        assertTrue(isOk1);
        assertTrue(isOk2);
        assertTrue(isOk3);
        assertFalse(isOk4);


    }

    @Test
    public void  testXss(){
        String unsafe = "<table><tr><td>1</td></tr></table>" +
                "<img src='' alt='' />" +
                "<p><a href='http://example.com/' onclick='stealCookies()'>Link</a>" +
                "<object></object>" +
                "<script>alert(1);</script>" +
                "</p>";
        String safe = Jsoup.clean(unsafe, Whitelist.relaxed());
        System.out.println("safe: " + safe);
    }
}
