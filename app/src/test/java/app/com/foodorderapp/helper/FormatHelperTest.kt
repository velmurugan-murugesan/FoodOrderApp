package app.com.foodorderapp.helper

import junit.framework.Assert.assertEquals
import org.junit.Test

class FormatHelperTest {

    @Test
    fun priceDecimalFormatterTest(){
        assertEquals("", "100.50", FormatHelper.priceDecimalFormatter(100.5000f))
        assertEquals("", "50.00", FormatHelper.priceDecimalFormatter(50f))
    }

    @Test
    fun rattingDecimalFormatterTest(){
        assertEquals("", "4.5", FormatHelper.rattingDecimalFormatter(4.5000f))
    }

}