package nablarch.core.date;

import nablarch.core.repository.ObjectLoader;
import nablarch.core.repository.SystemRepository;
import nablarch.util.FixedSystemTimeProvider;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * {@link SystemTimeUtil}のテストクラス
 * @author Miki Habu
 */
public class SystemTimeUtilTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    /**
     * テスト実施前準備
     * 
     */
    @Before
    public void setUp() {
        // リポジトリの初期化
        SystemRepository.load(new ObjectLoader() {
            @Override
            public Map<String, Object> load() {
                HashMap<String, Object> result = new HashMap<String, Object>();
                FixedSystemTimeProvider provider = new FixedSystemTimeProvider();
                provider.setFixedDate("20110107123456");
                result.put("systemTimeProvider", provider);
                return result;
            }
        });
    }
        
    /**
     * {@link SystemTimeUtil#getDate()}のテスト
     * 
     * @throws Exception 例外
     */
    @Test
    public void testGetDate() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Date expected = sdf.parse("20110107123456000");
        assertEquals(expected, SystemTimeUtil.getDate());
    }

    /**
     * {@link SystemTimeUtil#getDate()} のテスト。
     * <p/>
     * リポジトリに値がない場合、例外を送出するかどうか。
     * @throws Exception
     */
    @Test
    public void testGetDateErr() throws Exception {
        SystemRepository.clear();
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("specified systemTimeProvider is not registered in SystemRepository.");

        SystemTimeUtil.getDate();
    }
    
    /**
     * {@link SystemTimeUtil#getTimestamp()}のテスト
     */
    @Test
    public void testGetTimestamp() {
        Timestamp expected = Timestamp.valueOf("2011-01-07 12:34:56.000000000");
        assertEquals(expected, SystemTimeUtil.getTimestamp());
    }

    /**
     * {@link SystemTimeUtil#getDateString()}のテスト。
     */
    @Test
    public void testGetCurrentDateString() {
        assertEquals("20110107", SystemTimeUtil.getDateString());
    }

    /**
     * {@link SystemTimeUtil#getDateTimeString()}のテスト
     */
    @Test
    public void testGetDateTimeString() {
       assertEquals("20110107123456", SystemTimeUtil.getDateTimeString()); 
    }
    
    /**
     * {@link SystemTimeUtil#getDateTimeMillisString()}のテスト
     */
    @Test
    public void testGetDateTimeMillisString() {
        assertEquals("20110107123456000", SystemTimeUtil.getDateTimeMillisString());         
    }
    
}
