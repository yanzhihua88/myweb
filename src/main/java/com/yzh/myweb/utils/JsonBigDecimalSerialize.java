package com.yzh.myweb.utils;/**
 * Created by lucky.ou on 2018/8/15.
 */


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 */
public class JsonBigDecimalSerialize extends JsonSerializer<BigDecimal> {

    private DecimalFormat df = new DecimalFormat("##.00");

    @Override
    public void serialize(BigDecimal value, JsonGenerator jgen,
                          SerializerProvider provider) throws IOException,
            JsonProcessingException {

        jgen.writeString(df.format(value));
    }
}
