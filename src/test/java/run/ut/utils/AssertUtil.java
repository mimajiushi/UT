package run.ut.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.testng.Assert;

import java.util.Collection;
import java.util.Map;

/**
 * 为了方便使用，扩展了一些代码
 *
 * @author chenwenjie.star
 * @date 2021/7/29 10:43 上午
 */
public class AssertUtil extends Assert {

    /**
     * @see StringUtils#isBlank(CharSequence)
     */
    public static void assertNotBlank(String text, String message) {
        boolean fail;
        try {
            Assert.assertFalse(StringUtils.isBlank(text), message);
            fail = false;
        } catch (AssertionError e) {
            fail = true;
        }

        if (fail) {
            Assert.fail(message);
        }
    }

    public static void assertNotBlank(String[] text, String message) {
        boolean fail;
        try {
            for (String s : text) {
                Assert.assertFalse(StringUtils.isBlank(s), message);
            }
            fail = false;
        } catch (AssertionError e) {
            fail = true;
        }

        if (fail) {
            Assert.fail(message);
        }
    }

    /**
     * @see StringUtils#isBlank(CharSequence)
     */
    public static void assertBlank(String text, String message) {
        boolean fail;
        try {
            Assert.assertTrue(StringUtils.isBlank(text), message);
            fail = false;
        } catch (AssertionError e) {
            fail = true;
        }

        if (fail) {
            Assert.fail(message);
        }
    }

    public static void assertBlank(String[] text, String message) {
        boolean fail;
        try {
            for (String s : text) {
                Assert.assertTrue(StringUtils.isBlank(s), message);
            }
            fail = false;
        } catch (AssertionError e) {
            fail = true;
        }

        if (fail) {
            Assert.fail(message);
        }
    }

    /**
     * @see CollectionUtils#isEmpty(Collection)
     */
    public static void assertCollectionEmpty(Collection<?> collection, String message) {
        boolean fail;
        try {
            Assert.assertTrue(CollectionUtils.isEmpty(collection), message);
            fail = false;
        } catch (AssertionError e) {
            fail = true;
        }

        if (fail) {
            Assert.fail(message);
        }
    }

    /**
     * @see CollectionUtils#isEmpty(Map)
     */
    public static void assertCollectionEmpty(Map<?, ?> map, String message) {
        boolean fail;
        try {
            Assert.assertTrue(CollectionUtils.isEmpty(map), message);
            fail = false;
        } catch (AssertionError e) {
            fail = true;
        }

        if (fail) {
            Assert.fail(message);
        }
    }

    /**
     * @see CollectionUtils#isEmpty(Collection)
     */
    public static void assertCollectionNotEmpty(Collection collection, String message) {
        boolean fail;
        try {
            Assert.assertFalse(CollectionUtils.isEmpty(collection), message);
            fail = false;
        } catch (AssertionError e) {
            fail = true;
        }

        if (fail) {
            Assert.fail(message);
        }
    }

    /**
     * @see CollectionUtils#isEmpty(Map)
     */
    public static void assertCollectionNotEmpty(Map<?, ?> map, String message) {
        boolean fail;
        try {
            Assert.assertFalse(CollectionUtils.isEmpty(map), message);
            fail = false;
        } catch (AssertionError e) {
            fail = true;
        }

        if (fail) {
            Assert.fail(message);
        }
    }
}
